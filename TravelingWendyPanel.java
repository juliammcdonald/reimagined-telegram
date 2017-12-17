/*----------*/
import java.util.*;
import java.util.concurrent.TimeUnit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.*;
import com.mxgraph.util.*;


public class TravelingWendyPanel extends JPanel /*implements ChangeListener*/ {
  /*----------Instance variables----------*/
  private JLabel selectLabel, mapLabel;
  private WendyGraph wendyGraph;
  private String[] selectedNodes;

  
  /*----------Constructor----------*/  
  public TravelingWendyPanel(int graphViewportWidth, int graphViewportHeight){
    
    wendyGraph = new WendyGraph( "wellesleycoord.txt" );
    
    selectedNodes = new String[2];

    selectLabel = new JLabel("Select origin");    

    mapLabel = new JLabel("This is the map placeholder XD");
    
    setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();
    
    /*----------Map component---------*/    
    gc.weightx = 0.5;
    gc.weighty = 0.5;
    
    gc.gridx = 0;
    gc.gridy= 0;
    
    /*----------From ClickHandler.java----------*/
    final mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();
    
    /*----------Create vertex stylesheets----------*/
    mxStylesheet stylesheet = graph.getStylesheet();
    Hashtable<String, Object> style = new Hashtable<String, Object>();
    style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CYLINDER);
    style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    style.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
    style.put(mxConstants.STYLE_GRADIENTCOLOR, mxUtils.getHexColorString(Color.BLUE));
    style.put(mxConstants.STYLE_GRADIENT_DIRECTION, mxConstants.DIRECTION_NORTH); 
    style.put(mxConstants.STYLE_GRADIENTCOLOR, mxUtils.getHexColorString(Color.WHITE));
    stylesheet.putCellStyle("BUILDING", style);
    
    Hashtable<String, Object> style2 = new Hashtable<String, Object>();
    style2.put(mxConstants.STYLE_OPACITY, 50);
    style2.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    stylesheet.putCellStyle("INTERSECTION", style2);
    
    graph.getModel().beginUpdate();
    
    /*----------Plot all vertices----------*/    
    Vector<Object> vertexObjects = new Vector<Object>(wendyGraph.vertices.size());
    int[] pixelCoors = new int[2];
    for (Node vertex : wendyGraph.vertices){
      pixelCoors = wendyGraph.getPixelCoordinates( Math.abs(vertex.getLat()), 
                                                   Math.abs(vertex.getLon()),
                                                   graphViewportWidth,
                                                   graphViewportHeight);
      System.out.printf("Plotting: [%d, %d]\n",pixelCoors[0],pixelCoors[1]);      
      
      Object v;
      if (vertex.getisBuilding()){
        v = graph.insertVertex(parent, null, vertex.getName(), 
                         pixelCoors[0], pixelCoors[1], 
                         vertex.getName().length() * 10, 30,
                                      "BUILDING");
      } else { //intersection
        v = graph.insertVertex(parent, null, vertex.getName(), 
                         pixelCoors[0], pixelCoors[1], 
                         15, 15,
                               "INTERSECTION");
      }
      
      vertexObjects.add( v );
    }
    /*----------Draw all edges----------*/
    for (LinkedList<Edge> edgeList : wendyGraph.edges){
      for (Edge e : edgeList){
        Node n1 = e.getNode1();
        int index1 = wendyGraph.vertices.indexOf(n1);
        Node n2 = e.getNode2();
        int index2 = wendyGraph.vertices.indexOf(n2);
        
        graph.insertEdge(parent, null, /*e.getLengthFormatted(),*/ "", 
                         vertexObjects.get(index1), vertexObjects.get(index2),
        "endArrow=None;");
        System.out.printf("Drawing edge. . .\n");
      }
    }       
    graph.getModel().endUpdate();    
    
    final mxGraphComponent graphComponent = new mxGraphComponent(graph);
    graphComponent.setEnabled(false);

    add(graphComponent, gc);  
    
    /*----------Clickable cells----------*/  
    graphComponent.getGraphControl().addMouseListener(new MouseAdapter(){      
      public void mouseReleased(MouseEvent e){
        Object cell = graphComponent.getCellAt(e.getX(), e.getY());
        
        if ((cell != null) || (selectedNodes[0].equals("")) || (selectedNodes[1].equals(""))) {
          System.out.println("cell="+graph.getLabel(cell));
          
          if ( selectedNodes[0].equals("") ){            
            selectedNodes[0] = graph.getLabel(cell);
            selectLabel.setText("Select destination");
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "red", new Object[]{cell});
            
          } else if ( selectedNodes[1].equals("") ){ //origin is already selected
            selectedNodes[1] = graph.getLabel(cell);
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "red", new Object[]{cell});
            graphComponent.refresh();
            selectLabel.setText("Calculating. . .");
            
            /*----------Call Dijkstra ----------*/
            ArrayList<Node> shortestPath = wendyGraph.runDijkstra(selectedNodes[0], selectedNodes[1]);
            selectLabel.setText("The shortest path is: "+ shortestPath.toString());
            
          }
        }
      }
    });
    
    /*----------Display labels----------*/
    gc.weighty = 0.01;    
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridy = 1;
    add(selectLabel, gc);


    /*
    ImageIcon image = getBackgroundImage();
    graphComponent.setBackgroundImage(image);
    graphComponent.setPageScale(double);*/

  }
  
  
  //Paper paper = new Paper();
  //paper.setSize(image.getIconWidth(), image.getIconHeight());
  //graphComponent.getPageFormat().setPaper(paper);
  
  
}
