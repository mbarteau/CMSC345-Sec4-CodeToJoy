package shortestPath;

public class ShortestDistance {

	public static void main(String[] args) {
		SimpleUnisexMutatorHibrid2OptEngine e = new SimpleUnisexMutatorHibrid2OptEngine();
		TSPConfiguration appConfiguration = new TSPConfiguration();
		appConfiguration.setInitialPopulationSize(1000);
		
		City[] cities = new City[100];
		for(int i = 0; i < 100; i ++)
			cities[i] = new City(i, appConfiguration, String.valueOf(i), (int)(Math.random()*100), (int)(Math.random()*100));


		City.initDistanceCache(cities.length);
		
		appConfiguration.setInitialPopulationSize(10000);
		e.initialize(appConfiguration,cities);
		for(int i=0; i<10; i++) {
			e.nextGeneration();
		}
		City[] citiesFinal = e.getBestChromosome().getCities();
		
		for (City c : citiesFinal) {
			System.out.println(c.getId() + "," + c.getX() + "," + c.getY());
		}
		System.out.println(e.getBestChromosome().getTotalDistance());
	}

}
