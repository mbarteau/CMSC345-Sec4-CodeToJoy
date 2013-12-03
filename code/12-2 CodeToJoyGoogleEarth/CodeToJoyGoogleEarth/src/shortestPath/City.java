/*
 * $Source: f:/cvs/prgm/tsp/src/org/saiko/ai/genetics/tsp/City.java,v $
 * $Id: City.java,v 1.3 2005/08/23 23:18:04 dsaiko Exp $
 * $Date: 2005/08/23 23:18:04 $
 * $Revision: 1.3 $
 * $Author: dsaiko $
 *
 * Traveling Salesman Problem genetic algorithm.
 * This source is released under GNU public licence agreement.
 * dusan@saiko.cz
 * http://www.saiko.cz/ai/tsp/
 * 
 * Change log:
 * $Log: City.java,v $
 * Revision 1.3  2005/08/23 23:18:04  dsaiko
 * Finished.
 *
 * Revision 1.2  2005/08/13 12:53:02  dsaiko
 * XML2PDF report finished
 *
 * Revision 1.1  2005/08/12 23:52:17  dsaiko
 * Initial revision created
 *
 */

package shortestPath;

import location.Location;;

/**
 * @author Dusan Saiko (dusan@saiko.cz)
 * Last change $Date: 2005/08/23 23:18:04 $
 *
 * The city definition for traveling salesman problem.
 * City has basic properties as x and y coordinates and name
 * and some functionality to get the distance to other cities 
 */
public class City {

   /** String containing the CVS revision. **/
   public final static String CVS_REVISION = "$Revision: 1.3 $";
   
   /**
    * serialVersionUID
    */
   protected static final long serialVersionUID =-554746071631292503L;

   /**
    * X coordinate of the city. 
    * It could be S-JTSK coordinate [m].
    */
   protected double    		x;

   /**
    * Y coordinate of the city. 
    * It could be S-JTSK coordinate [m].
    */
   protected double    		y;
   
   
   /**
    * X coordinate of the city - original value in sjtsk coordinates. 
    * It could be S-JTSK coordinate [m].
    */
   final protected double            SJTSKX;

   /**
    * Y coordinate of the city - original value in sjtsk coordinates. 
    * It could be S-JTSK coordinate [m].
    */
   final protected double            SJTSKY;
   
   /**
    * city name
    */
   protected String 		name;
   
   
   /**
    * numeric id of the city - index of city in the original arrays of cities
    * main characteristic is, that id is less then the length of the city array 
    */
   protected int          id;

   /**
    * start city flag 0 city from which the salesman starts
    */
   protected boolean startCity=false;

   /**
    * cache for distances to other cities
    * the cities are indexed beginning from 0 and this index is written into id property
    * this id is then used as index into distanceCache array
    * this chache holds distances from [id1] city to [id2] city
    */
   static double distanceCache[][]=null;

   /**
    * configuration parameters of application
    * @see TSPConfiguration
    */
   protected TSPConfiguration configuration;
   
   private Location loc;
   
   public Location getLocation() {
	   return this.loc;
   }
   
   /**
    * Constructor for the city object
    * @param id int id of city (its index)
    * @param configuration configuration parameters of application
    * @param name - name of the city
    * @param x - X coordinate of the city [S-JTSK - [m]]
    * @param y - Y coordinate of the city [S-JTSK - [m]]
    * @see TSPConfiguration
    */
   public City(int id, TSPConfiguration configuration, Location loc) {
      this.id=id;
      this.x=loc.getLat();
      this.y=loc.getLon();
      this.SJTSKX=x;
      this.SJTSKY=y;
      this.name=loc.getName();
      this.configuration=configuration;
      this.loc = loc;
   }


   /**
    * initializes the distance cache for know number of cities
    * @param length - length of the cache = number of cities
    */
   static synchronized public void initDistanceCache(int length) {
      distanceCache=new double[length][length];
      //reset the cache to -1
      for(int i=0; i<length; i++) {
         for(int j=0; j<length; j++) {
            distanceCache[i][j]=-1;
         }
      }
   }
   
   /**
    * Computes distance over two cities.
    * If coorfinates are in S-JTSK, then this distance is in meters.
    * Uses the cache to hold the distances between two cities 
    * without having to compute them every time
    * @param otherCity
    * @param useCache - true if the cache should be used 
    * @return distance between the two cities. 
    */
   public double distance(City otherCity, boolean useCache) {
	   
	  //if(useCache==false) {
	  //	return distance(otherCity.getX(), otherCity.getY());   
	  //}
	  
      int id1=this.id;
      int id2=otherCity.id;
      if(id1==id2) return 0.0;
      
      if(id1>id2) {
         int swap=id1;
         id1=id2;
         id1=swap;
      }
      
      //distance is cached in the 2 dimensional array
      //we order the indexes of cities, so B->A is computed as A->B - it
      //saves us half of combinations
      double distance=distanceCache[id1][id2];
      if(distance==-1) {
      
    	  double lat1 = this.x;
    	  double lon1 = this.y;
    	  double lat2 = otherCity.getX();
    	  double lon2 = otherCity.getY();
    	  
    	  double R = 6371; // km
    	  double dLat = Math.toRadians(lat2-lat1);
    	  double dLon = Math.toRadians(lon2-lon1);
    	  lat1 = Math.toRadians(lat1);
    	  lat2 = Math.toRadians(lat2);
			
    	  double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
    			  Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
    	  double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    	  double d = R * c;
    	  
    	  //no distance found in cache, compute it
    	  distance=d;
    	  distanceCache[id1][id2]=distance;
      }
      return distance;
   }
   
   /**
    * Computes distance over two cities.
    * If coorfinates are in S-JTSK, then this distance is in meters.
    * Uses the cache to hold the distances between two cities 
    * without having to compute them every time
    * @param otherCity
    * @return distance between the two cities. 
    */
   public double distance(City otherCity) {
      return distance(otherCity,true);
   }
   
   /**
    * @param otherCity
    * @return cost for traveling to otherCity from this. It may differ from distance.
    */
   public double cost(City otherCity) {
      double distance=distance(otherCity);
      if(configuration.isRmsCost()) {
         return distance*distance;
      }
      return distance;
   }
   
   
   /**
    * @return Returns the name of the city
    */
   public String getName() {
      return name;
   }
   
   /**
    * @return Returns the x coordinate of city
    */
   public double getX() {
      return x;
   }
   
   /**
    * @return Returns the y coordinate of the city
    */
   public double getY() {
      return y;
   }
   
   
   
   
   /**
    * @return Returns the x coordinate of city - original value in sjtsk coordinates
    */
   public double getSJTSKX() {
      return SJTSKX;
   }
   
   /**
    * @return Returns the y coordinate of the city - original value in sjtsk coordinates
    */
   public double getSJTSKY() {
      return SJTSKY;
   }
   
   /**
    * @return Name of city with coordinates
    */
   @Override
   public String toString(){
      return name+": ["+x+";"+y+"]";
   }


   /**
    * @return true, if the ids of two cities are the same
    */
   @Override
   public boolean equals(Object obj) {
      if(obj==null) return false;
      if(obj==this) return true;
      if(!(obj instanceof City)) return false;
      return ((City)obj).id==this.id;
   }

   
   /**
    * return numeric id of the city - index of city in the original arrays of cities
    * main characteristic is, that id is less then the length of the city array 
    * @return id of the city.
    */
   public int getId() {
      return id;
   }
   
}