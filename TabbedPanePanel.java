
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
/**
 * @author Julia McDonald
 */
public class TabbedPanePanel extends JPanel {
  public TabbedPanePanel() {
    super(new GridLayout(1, 1));
    
    JTabbedPane tabbedPane = new JTabbedPane();
    
    JComponent aboutPanel = makeAboutPanel();
    tabbedPane.addTab("About", aboutPanel);
    
    JComponent wendyPanel = new TravelingWendyPanel(825, 580);
    tabbedPane.addTab("Map", wendyPanel );
    
    add(tabbedPane);
  }
  
  protected JComponent makeAboutPanel() {
    JPanel panel = new JPanel(false);
    JLabel text1 = new JLabel("Welcome to Traveling Wendy!");
    text1.setFont(new Font("Monospaced", Font.BOLD, 28));
    text1.setBorder(new EmptyBorder(10,0,10,0));

    JLabel text2 = new JLabel("Instructions: Click on the \"Map\" pane and select your start and "
                                + "endpoints on the Wellesley campus. The shortest path will be highlighted.");
    JLabel text3 = new JLabel( "Creators: Xinhui Xu & Julia McDonald");
    
    ImageIcon authors = new ImageIcon( "authors.JPG" );
    JLabel pic = new JLabel( authors );
    
    text1.setAlignmentX(Component.CENTER_ALIGNMENT);
    text2.setAlignmentX(Component.CENTER_ALIGNMENT);
    text3.setAlignmentX(Component.CENTER_ALIGNMENT);
    pic.setAlignmentX(Component.CENTER_ALIGNMENT);
    //GridLayout g = new GridLayout(2,1);
    
    panel.setLayout( new BoxLayout(panel, BoxLayout.Y_AXIS));
    
    panel.add(pic);
    panel.add(text1);
    panel.add(text2);
    panel.add(text3);
    
    return panel;
  }
  
}