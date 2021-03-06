/*****************************************************************
  * Edge.java
  * 
  * Represents the path between two buildings or road intersections.
  * Stores the length of the path in meters.
  * 
  * Constructor is called in WendyGraph.java
  * 
  * @author Julia McDonald, Xinhui Xu
  * @date Dec 18, 2017
  ****************************************************************/
import java.text.DecimalFormat;

public class Edge {
  
  protected Node node1; //Building or intersection
  protected Node node2; //Building or intersection
  protected double length; //Distance of path connecting node1 and node2
  
  /**@author Xinhui
    * 
    * Creates a weighted Edge between two Nodes by setting the length equal to the
    * linear distance between the two lat/long coordinates.
    * 
    * @param n1 - Node #1
    * @param n2 - Node #2
    */
  public Edge( Node n1, Node n2 ) {
    length = getGreatCircleDistance(n1.getLat(), n1.getLon(), n2.getLat(), n2.getLon());
    node1 = n1;
    node2 = n2;
  }
  
  /* @author Xinhui
   * 
   * The 'haversine' formula to find distance in meter given the latitude&longitude of two points, 
   * referenced from https://www.movable-type.co.uk/scripts/latlong.html 
   * @param lat1 - latitude of first point.
   * @param lon1 - longitude of first point.
   * @param lat2 - latitude of 2nd point.
   * @param lon2 - longitude of 2nd point.
   * @return - the distance in meters.
   */
  public static double getGreatCircleDistance(double lat1, double lon1, double lat2, double lon2){
    double R = 6317E3; //Radius of Earth!
    double phi1 = Math.toRadians(lat1);
    double phi2 = Math.toRadians(lat2);
    double dPhi = Math.toRadians(lat2-lat1);
    double dLam = Math.toRadians(lon2-lon1);
    double a = Math.sin(dPhi / 2.0) * Math.sin(dPhi / 2.0) +
      Math.cos(phi1) * Math.cos(phi2) *
      Math.sin(dLam / 2.0) * Math.sin(dLam / 2.0);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double d = R * c;
    return d;     
  }
  
  /*@author Julia
   * Gets the distance in meters of this path.
   * @return length
   */
  public double getLength(){
    return length;    
  }
  
  /*@author Xinhui
   * Gets a formatted version of the distance in meters of this path.
   * @return string containing the formatted length
   */
  public String getLengthFormatted(int numDigitsAfterDecimal){
    DecimalFormat df = new DecimalFormat();
    df.setMaximumFractionDigits(numDigitsAfterDecimal);
    return df.format(getLength());
  }
  
  /*@author Julia
   * Gets one end of the path.
   * @return node1 - a building or intersection this Edge connects.
   */
  public Node getNode1() {
    return node1;
  }
  
  /*@author Julia
   * Gets the other end of the path.
   * @return node2 - the other building or intersection this Edge connects.
   */
  public Node getNode2() {
    return node2;
  }
  
  /*@author Julia
   * Given one end of the path, gets the other end (either node 1 or node 2).
   * 
   * @return node that is not known
   */
  public Node getOtherNode( Node known ) {
    if( known.equals( node1 ) )
      return node2;
    return node1;
  }
  
  /**@author Julia
   * Creates string representation of Edge (both nodes)
   * 
   * @return string (node1, node2) representing the Edge
   */
  public String toString() {
    return "(" + node1 + ", " + node2 + ")";
  }
}