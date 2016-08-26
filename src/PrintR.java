import java.awt.Graphics;
import java.awt.print.*;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;

public class PrintR extends Menu  implements Printable,ActionListener{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int[] pageBreaks;  
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		PrinterJob job=PrinterJob.getPrinterJob();
		job.setPrintable(this);
		if(job.printDialog()){
			try{
				job.print();
			}catch(PrinterException ex){
				System.out.println("the job was not successfull");
			}
		}
	}

	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// TODO Auto-generated method stub
		pageFormat.setOrientation(PageFormat.LANDSCAPE);
		if(remarks[pageIndex+1].equalsIgnoreCase("none")){
			String ReceiptNo="123"+pageIndex;
			
			Font font = new Font("Serif",Font.PLAIN,12);
			Font boldFont = new Font ("serif", Font.BOLD, 18);
			FontMetrics metrics = graphics.getFontMetrics(font);
			int lineHeight = metrics.getHeight();

			if (pageBreaks == null) {
				int numLines=limit[1]*50;
				int linesPerPage = (int)(pageFormat.getImageableHeight()/lineHeight);
				int numBreaks = (numLines-1)/linesPerPage;
				pageBreaks = new int[numBreaks];
				for (int b=0; b<numBreaks; b++) {
					pageBreaks[b] = (b+1)*linesPerPage; 
				}
			}

			if (pageIndex > pageBreaks.length) {
				return NO_SUCH_PAGE;
			}

			Graphics2D g2d = (Graphics2D)graphics;
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			int y=0;
			graphics.setFont(boldFont);
			graphics.drawString("JMC RECEIPT", 300, 15);
			graphics.setFont(font);
			graphics.drawString("Receipt No."+ ReceiptNo, 0, 50);
			graphics.drawString("bankA/c    ",600,50);
			try {
				String lamt[]=amt[pageIndex+1].split(",");
				lamt[0]=lamt[0].substring(4);
				int amt=0;
				for(int i=0;i<lamt.length;i++)
					amt+=Integer.parseInt(lamt[i]);
				graphics.drawString("Received with thanks a sum of   Rs. "+amt+"/-    from:    "+name[pageIndex+1],90,200);
				graphics.setFont(new Font ("serif", Font.BOLD, 13));
				graphics.drawString("    Towards:   ", 100, 230);
				graphics.drawString("Mode of Payment:", 100, 390);
				String head[]= heads[pageIndex+1].split(",");
				head[0]=head[0].substring(4);
				graphics.setFont(font);
				graphics.drawString(cheqno[pageIndex+1], 250, 390);
				for(int j=0;j<nameCount[pageIndex+1];j++){
					y+=metrics.getHeight()+5;
					graphics.drawString("\n "+head_reference(head[j]),90,250+y );
					graphics.drawString("\n.................... \t"+lamt[j],220,250+y );
				}
				//				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return PAGE_EXISTS;
	}
	public static void main(String[] args) throws IOException{
		menu();
	}
}
