package kml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import de.micromata.opengis.kml.v_2_2_0.*;
import location.Location;


public class PathToKML {
	private static Kml kml = KmlFactory.createKml();
	private static Document document = KmlFactory.createDocument();
	private static Placemark placemark = KmlFactory.createPlacemark();
	private static LineString lineString = KmlFactory.createLineString();	
	
	
	//private constructor for static class
	private PathToKML(){
	}
	
	
	public static void createPath(ArrayList<Location> tripList, String fileName) throws FileNotFoundException{
		document = kml.createAndSetDocument();
		final Style style = document.createAndAddStyle().withId("myDefaultStyles");
		style.createAndSetBalloonStyle().withColor("64F00014");
		style.createAndSetLabelStyle().withColor("7fffaaff").withScale(1.5);
		style.createAndSetLineStyle().withColor("641400FA").withWidth(3.0);

		placemark = document.createAndAddPlacemark().withName("Folder object 3 (Path)").withStyleUrl("#myDefaultStyles");;
		
		lineString = placemark.createAndSetLineString().withTessellate(true);
		
		//adds the locations from the array into kml to be written.
		for(Location loc:tripList){
			lineString.addToCoordinates(loc.getLon(), loc.getLat());
		}
		
		//calls the marshal function
		marshal(fileName);
	}
	
	
	public static void marshal(String fileName) throws FileNotFoundException{
		//
		kml.marshal(new File(fileName +".kml"));
	}
	
	public static void openFile(String programLoc, String fileLoc){
        try {
            Runtime.getRuntime().exec(new String[] {programLoc,fileLoc});
        } catch (Exception ex) {
            // something went wrong
        }

		
		
	}
	
	
	public static void main(String[] args)  throws FileNotFoundException{
		Location l1 = new Location("umbc", 39.254646,-76.713989);
		Location l2 = new Location("Acadia",44.33856,-68.27333);
		Location l3 = new Location("American Samoa",-14.23313,-169.47601);
		ArrayList<Location> someList = new ArrayList<Location>();
		someList.add(l1);
		someList.add(l2);
		someList.add(l3);
		PathToKML.createPath(someList, "test");
		

	}
}
