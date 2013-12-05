package kml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import de.micromata.opengis.kml.v_2_2_0.*;
import location.Location;


public class PathToKML {
	//initialize variables
	private static Kml kml = KmlFactory.createKml();
	private static Document document = KmlFactory.createDocument();
	private static Placemark placemark = KmlFactory.createPlacemark();
	private static LineString lineString = KmlFactory.createLineString();	
	
	
	/**
	 * Empty constructor
	 */
	private PathToKML(){
	}
	
	/**
	 * createPath	- Creates the path passed in into a kml file
	 * @param tripList - ArrayList of locations
	 * @param fileName - name of the file to be created
	 * @throws FileNotFoundException - exception if file can't be found
	 */
	public static void createPath(ArrayList<Location> tripList, String fileName) throws FileNotFoundException{
		//initializing variables
		document = kml.createAndSetDocument();
		//create a style for colored lines
		final Style style = document.createAndAddStyle().withId("myDefaultStyles");
		style.createAndSetLineStyle().withColor("641400FA").withWidth(3.0);

		//create a placemark with the name of their trip and a style
		placemark = document.createAndAddPlacemark().withName(fileName + " trip").withStyleUrl("#myDefaultStyles");;
		
		//create a linestring to add coordinates to
		lineString = placemark.createAndSetLineString().withTessellate(true);
		
		//adds the locations from the array into kml to be written.
		for(Location loc:tripList){
			lineString.addToCoordinates(loc.getLon(), loc.getLat());
		}
		
		//calls the marshal function
		kml.marshal(new File(fileName +".kml"));
	}
	
	
	/**
	 * openFile			-opens google earth and the file that they created.
	 * @param programLoc- location of google earth
	 * @param fileLoc	- location of the kml file
	 */
	public static void openFile(String programLoc, String fileLoc){
        try {
            Runtime.getRuntime().exec(new String[] {programLoc,fileLoc});
        } catch (Exception ex) {
            // something went wrong
        }		
	}
	
	
	/**
	 * main			- unit testing
	 * @param args
	 * @throws FileNotFoundException
	 */
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
