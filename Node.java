/*****************************************************************
 * Node.java
 * 
 * Represents a building or a road intersection on a map.
 * Stores name of the building/intersection and it's latitude and longitude.
 * 
 * Constructor is called from WendyGraph.java
 * 
 * @author Julia McDonald
 * @date Dec. 18, 2017
 ****************************************************************/
public class Node implements Comparable<Node>{
  
  protected String name;
  protected double latitude;
  protected double longitude;
  protected boolean isBuilding; //false if is road intersection
  protected double weight; //variable used in path-finding algorithm
  protected Node prev; //preceding Node, used in path-finding algorithm
  
  /**
   * Creates a Node object that holds latitude, longitude, and whether or not the Node represents a 
   * building (true) or an intersection (false).
   * 
   * @param latitude - the latitude of the Node
   * @param longitude - the longitude of the Node
   * @param isBuilding - true if it is a building, false otherwise
   * @param name - name of buildings or name of intersections is "i" + number
   * @param weight - variable to be modified in shortest path-finding algorithm
   */
  public Node( String name, double latitude, double longitude, boolean isBuilding, double weight ) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.isBuilding = isBuilding;
    this.name = name;
    this.weight = weight;
    this.prev = null;
  }
  
  
  /*
   * Gets latitude of the Node
   * @return latitude
   */
  public double getLat() {
    return latitude;
  }
  
  /*
   * Gets longitude of the Node
   * @return longitude
   */
  public double getLon() {
    return longitude;
  }
  
  /*
   * Gets is the Node is a building
   * @return true if building, false otherwise
   */
  public boolean getisBuilding() {
    return isBuilding;
  }
  
  /*
   * Gets name of the node
   * @return name
   */
  public String getName() {
    return name;
  }
  
  /**
   * Returns a string representation of a Node
   * @return string containing the name of the node
   */
  public String toString() {
    return name;
  }
  

  /******************** Functions used in path-finding algorithm *****************/
  /*
   * Sets the weight of a node
   * @param w - the new weight of the node
   */
  public void setWeight( double w ) {
    weight = w;
  }
  
  /**
   * Gets weight
   * @param weight
   */
  public double getWeight() {
    return weight;
  }
  
  /*
   * Gets previous node in shortest path
   * @return Node
   */
  public Node getPrev() {
    return prev;
  }
  
  /*
   * sets the previous node in the shortest path
   * @param n - new previous Node
   */
  public void setPrev( Node n ) {
    prev = n;
  }
  
  /**
   * Compares two nodes
   * 
   * @param n - the node to be compared
   * @return Negative if less than, 0 if equal, positive if greater than
   */
  public int compareTo( Node n ) {
    if (this.weight == n.weight)
      return name.compareTo(n.name);
    return Double.compare( this.weight, n.weight );
  }
}