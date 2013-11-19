package location;

import java.io.*;
import java.util.Scanner;

public class Location {

	private String name;
	private double lat;
	private double lon;
	
	public Location(String name, double lat, double lon){
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	public String getName(){
		return this.name;
	}
	
	public double getLat(){
		return this.lat;
	}
	
	public double getLon(){
		return this.lon;
	}
	
	public String toString(){
		String loc = "Name: " + this.name + "\nLatitude: " + this.lat +
				"\nLongitude: " + this.lon;
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
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}