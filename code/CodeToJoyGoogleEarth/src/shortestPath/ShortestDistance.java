package shortestPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import location.Location;

public class ShortestDistance {

	/**
	 * Accepts an ArrayList of Location's. The first location will be the starting position, and the last location
	 * in the list is the end location.
	 * @param locations ArrayList of Location's
	 * @return shortest path between the first and last indices of the ArrayList in a new Array List.
	 */
	public static ArrayList<Location> getShortestPath(ArrayList<Location> locations) {
		
		if (locations.size() <= 3)
			return locations;
		
		SimpleUnisexMutatorHibrid2OptEngine e = new SimpleUnisexMutatorHibrid2OptEngine();
		TSPConfiguration appConfiguration = new TSPConfiguration();
		appConfiguration.setInitialPopulationSize(1000);
		
		int count = locations.size();
		City[] cities = new City[count];
		for(int i = 0; i < count; i ++) {
			cities[i] = new City(i, appConfiguration, locations.get(i));
		}

		City.initDistanceCache(cities.length);
		
		appConfiguration.setInitialPopulationSize(10000);
		e.initialize(appConfiguration,cities);
		for(int i=0; i<10; i++) {
			e.nextGeneration();
		}
		City[] citiesFinal = e.getBestChromosome().getCities();
		ArrayList<Location> returnLocations = new ArrayList<Location>();
		for(City c : citiesFinal) {
			returnLocations.add(c.getLocation());			
		}
		return returnLocations;
	}
	
	public static void main(String[] args) {
		ShortestDistance sd = new ShortestDistance();
		sd.run();
	}
	
	public void run() {
		
		ArrayList<Location> locations = new ArrayList<Location>();
		
		try {
			InputStream input = getClass().getResourceAsStream("testLocations.txt");
			Scanner s = new Scanner(input);
			while (s.hasNext()) {
				String line = s.nextLine();
				String[] data = line.split(",");
			
				double lat = Double.parseDouble(data[1]);
				double lon = Double.parseDouble(data[2]);
				Location l = new Location(data[0], lat, lon);
				locations.add(l);
			}
			s.close();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		ArrayList<Location> path = getShortestPath(locations);
		
		for (Location l : path) {
			System.out.print(l.getLon() + "," + l.getLat() + ",0 ");
		}
	}
}
