package location; 
import java.io.*;
import java.util.Scanner;

public class Location implements Comparable {

	private String name;
	private double lat;
	private double lon;
	
	//constructor for location
	public Location(String name, double lat, double lon){
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		Location l = (Location) arg0;
		return this.getName().compareTo(l.getName());
	}
	
	/**
	 * @name getName
	 * @param none
	 * @return 
	 */
	public String getName(){
		return this.name;
	}
	
	public double getLat(){
		return this.lat;
	}
	
	public double getLon(){
		return this.lon;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

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