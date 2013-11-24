package admin;

import location.Location;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class admin {
	
	//class constructor
	public admin(){
	}
	
	/**
	 * @name addLocation
	 * @param name
	 * @param lat
	 * @param lon
	 * @throws IOException
	 */
	public void addLocation(String name, double lat, double lon) throws IOException{
		//locations text file passed in
		File f = new File("locations.txt");
		//array of locations created and filled
		ArrayList<Location> inList = getLocList();
		//add our new location from parameters into arraylist
		inList.add(new Location(name, lat, lon));
		//write entire list with formatting to file
		BufferedWriter out = new BufferedWriter(new FileWriter(f));
		for(int i = 0; i < inList.size(); i++){
			out.write(inList.get(i).toString());
		}
		//close file to make changes appear
		out.close();
	}
	/**
	 * @name getLocList
	 * @param none
	 * @throws none
	 * @return Array of locations
	 */
	private ArrayList<Location> getLocList(){
		//file of locations
		File f = new File("locations.txt");
		//initialize arraylist for storage
		ArrayList<Location> locA= new ArrayList<Location>();
		try {
			//create scanner to read from file
			Scanner s = new Scanner(f);
			while(s.hasNext()){
				String line = s.nextLine();
				//split into 3 parameters of a location
				String[] data = line.split(",");
				double lat = Double.parseDouble(data[1]);
				double lon = Double.parseDouble(data[2]);
				//make location from input
				Location m = new Location(data[0], lat, lon);
				//add to array
				locA.add(m);
				Collections.sort(locA);
			}
			//close file
			s.close();
			//return
			return locA;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @name removeLocation
	 * @param l, the location to be removed
	 * @throws IOException
	 * @returns none
	 */
	public void removeLocation(Location l) throws IOException{
		//get locations.txt to read and write to
		File f = new File("locations.txt");
		//create list of current locations in list
		ArrayList<Location> inList = getLocList();
		//search list, remove any matching locations
		for(int i = 0; i < inList.size(); i++){
			if(inList.get(i).getName().equals(l.getName())){
				inList.remove(i);
			}
		}
		//rewrite new list to text file
		BufferedWriter out = new BufferedWriter(new FileWriter(f));
		for(int i = 0; i < inList.size(); i++){
			out.write(inList.get(i).toString());
		}
		//close file to commit changes
		out.close();
	}
	
	/**
	 * 
	 * @param l
	 * @throws IOException 
	 */
	public void modifyLocation(Location l, String name, double lat, double lon) throws IOException{
		//get locations.txt to read and write to
		File f = new File("locations.txt");
		//create list of current locations in list
		ArrayList<Location> inList = getLocList();
		//search list, check for any matching locations
		for(int i = 0; i < inList.size(); i++){
			if(inList.get(i).getName().equals(l.getName())){
				inList.get(i).setName(name);
				inList.get(i).setLat(lat);
				inList.get(i).setLon(lon);
			}
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(f));
		for(int i = 0; i < inList.size(); i++){
			out.write(inList.get(i).toString());
		}
		//close file to commit changes
		out.close();
	}
	
	//unit testing
	public static void main(String[] args) throws IOException{
		File f = new File("locations.txt");
		admin A = new admin();
		String line = "";
		Scanner s;
		try {
			s = new Scanner(f);
			line = s.nextLine();
			String[] data = line.split(",");
			double lat = Double.parseDouble(data[1]);
			double lon = Double.parseDouble(data[2]);
			Location l = new Location(data[0], lat, lon);
			//System.out.println(l.toString());
			//A.removeLocation(l);
			//A.addLocation(data[0], lat, lon);
			//A.modifyLocation(l, "My House", 27.5, 0.06);
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
