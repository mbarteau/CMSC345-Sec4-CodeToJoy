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
	
	
	
	private PathToKML(){
	}
	
	public static void createPath(ArrayList<Location> tripList) throws FileNotFoundException{
		document = kml.createAndSetDocument();
		final Style style = document.createAndAddStyle().withId("myDefaultStyles");
		style.createAndSetBalloonStyle().withColor("64F00014");
		style.createAndSetLabelStyle().withColor("7fffaaff").withScale(1.5);
		style.createAndSetLineStyle().withColor("6414F0FF").withWidth(3.0);

		placemark = document.createAndAddPlacemark().withName("Folder object 3 (Path)").withStyleUrl("#myDefaultStyles");;
		
		lineString = placemark.createAndSetLineString().withTessellate(true);
		
		
		for(Location loc:tripList){
			lineString.addToCoordinates(loc.getLon(), loc.getLat());
		}
		
		marshal();
	}
	
	public static void marshal() throws FileNotFoundException{
		kml.marshal(new File("TestKml.kml"));
	}
	
	public static void openFile(){
        try {
            Runtime.getRuntime().exec(new String[] {
            "D:\\Program Files (x86)\\Google\\Google Earth\\client\\googleearth.exe",
            "C:\\Users\\Matt\\Downloads\\Workspace\\testKML\\testKml.kml"
        });
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
		PathToKML.createPath(someList);
		PathToKML.openFile();

	}
}
