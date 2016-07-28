import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class FilePick extends JFrame {

	static String pstr;
	static FilePick sfc = new FilePick();
   public FilePick() {
    super("File Chooser Test Frame");
    setSize(350, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Container c = getContentPane();
    c.setLayout(new FlowLayout());
    final JButton openButton = new JButton("Open");
    final JButton close =new JButton("Confirm");
    close.setVisible(false);
    final JLabel statusbar = 
                 new JLabel("Output of your selection will go here");
    
    // Create a file chooser that opens up as an Open dialog
    openButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        int option = chooser.showOpenDialog(FilePick.this);
        if (option == JFileChooser.APPROVE_OPTION) {
         File sf = chooser.getSelectedFile();
         pstr=sf.toString();
         System.out.println(pstr);
          statusbar.setText("You chose " + sf);
          close.setVisible(true);
        }
        else {
          statusbar.setText("You canceled.");
        }
      }
    });
    close.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			sfc.setVisible(false);
			
		}
    	
    });
    c.add(openButton);
    c.add(statusbar);
    c.add(close);
    
  }

  public static void fpmain() {
    
    sfc.setVisible(true);
  }
}