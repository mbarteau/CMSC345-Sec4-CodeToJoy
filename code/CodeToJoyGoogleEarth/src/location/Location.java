package location; 
import java.io.*;
import java.util.Scanner;

/**
 * Class: Location
 * 
 * This is the location class, containing the information needed to create a location
 * @author Ben
 *
 */
public class Location implements Comparable {

	private String name;
	private double lat;
	private double lon;
	
	//constructor for location
	/**
	 * @param name
	 * @param lat
	 * @param lon
	 */
	public Location(String name, double lat, double lon){
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	@Override
	/**
	 * @name getName
	 * @param Object arg0, to be compared to
	 * @param return int, the value for comparison
	 */
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		Location l = (Location) arg0;
		return this.getName().compareTo(l.getName());
	}
	
	/**
	 * @name getName
	 * @param none
	 * @return this location's name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * @name getLat
	 * @param none
	 * @return this location's latitude
	 */
	public double getLat(){
		return this.lat;
	}
	
	/**
	 * @name getLon
	 * @param none
	 * @return this location's name
	 */
	public double getLon(){
		return this.lon;
	}
	
	/**
	 * @name setName
	 * @param name, a String to take the place of current name
	 * @return none
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @name setLat
	 * @param lat, a double to take the place of current lat
	 * @return none
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @name setLon
	 * @param lon, a double to take the place of current lon
	 * @return none
	 */	
	public void setLon(double lon) {
		this.lon = lon;
	}

	/**
	 * @name toString
	 * @param none
	 * @return loc, a string representation of a location
	 */
	public String toString(){
		String loc = this.name + "," + this.lat + "," + this.lon + "\n";
		return loc;
	}
	
	public static void main(String[] args) throws IOException{
		File f = new File("locations.txt");
		try {
			String line = "";
			Scanner s = new Scanner(f);
			line = s.nextLine();
			String[] data = line.split(",");
			for(int i = 0; i < 3; i++){
				//System.out.println(data[i]);
			}
			double lat = Double.parseDouble(data[1]);
			double lon = Double.parseDouble(data[2]);
			Location l = new Location(data[0], lat, lon);
			System.out.println(l.toString());
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}