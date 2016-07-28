import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Menu extends excel{
 public static void menu(){
	 try{
			String cname=UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(cname);
		}catch(Exception cnf){
		}
		JFrame frame=new JFrame("Print Receipt");
		final JTextField start = new JTextField(6);
		final JTextField end = new JTextField(6);
		final JTextField months=new JTextField(10);
		final JButton printButton=new JButton("Print Receipt");
		final JButton limi=new JButton("enter range");
		final JButton choice=new JButton("Choose File");
		final JLabel  startl = new JLabel("Starting Row: ", JLabel.RIGHT);
	    final JLabel  endl = new JLabel("Ending Row: ", JLabel.CENTER);
	    final JLabel Month = new JLabel("Month:",JLabel.CENTER);
	    final JLabel headerLabel = new JLabel("Enter the following:", JLabel.CENTER);
	    final JLabel pathl= new JLabel("Choose file");
		frame.setSize(1800,1800);
		frame.setLayout(new GridLayout(6, 3));
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		final JPanel controlPanel = new JPanel();
	    controlPanel.setLayout(new FlowLayout());
	    controlPanel.add(startl);
	    controlPanel.add(start);
	    controlPanel.add(endl);
		controlPanel.add(end);
		controlPanel.add(Month);
		controlPanel.add(months);
		controlPanel.add(pathl);
		controlPanel.add(choice);
		choice.addActionListener(new ActionListener(){
			

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fpmain();
			}
		});
		printButton.setVisible(false);
		limi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String data = start.getText();
				limit[0]=Integer.parseInt(data);
				data = end.getText();
				limit[1]=Integer.parseInt(data);
				data =months.getText();
				computeMonth(data);
				path=pstr;
				headerLabel.setText("Click on print button"); 
//				limi.setVisible(false);
//				controlPanel.setVisible(false);
				try {
					pseudomain();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				printButton.setVisible(true);
			}
		});
		frame.add(headerLabel);
		frame.add(controlPanel);
		printButton.addActionListener(new PrintR());
		frame.add("left", limi);
		frame.add("right", printButton);
		frame.pack();
		frame.setVisible(true);
		
	}

}

