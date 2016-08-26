import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class excel extends FilePick {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String[] name=new String[100];
	static int nameCount[]=new int[100];
	static String[] heads=new String[100];
	static String[] date= new String[100];
	static String[] amt= new String[100];
	static String[] cheqno=new String[100];
	static String[] remarks=new String[100];
	static int[] limit=new int[2];
	static int month;
	static String path="/Users/keith2/Documents/JMC_Accounts_2016-17.xls";
	public static String print_content(Cell cell){
		 switch (cell.getCellType()) {
         case Cell.CELL_TYPE_STRING:
        	 return (cell.getRichStringCellValue().getString());
         case Cell.CELL_TYPE_NUMERIC:
             if (DateUtil.isCellDateFormatted(cell)) {
            	 DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy ");
                 String str = dateFormat.format((cell.getDateCellValue()));
            	 return str;
             } else {
            	 String str = Integer.toString((int)(cell.getNumericCellValue()));
                 return str;
             }
         default:
             return " ";
     }
	}
	public static String head_reference(String h)throws IOException{
		String Head;
		boolean test=false;
		InputStream inp = new FileInputStream(path);
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
	    Sheet sheet1 = wb.getSheetAt(0);
	    for (Row row : sheet1) {
	        for (Cell cell : row) {
	            CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
	            String col= (cellRef.formatAsString()).substring(0,1);
	            if(col.equals("A")){
	            	Head = print_content(cell);
	            	if(h.equalsIgnoreCase(Head)){
	            		test=true;
	            	}
	            }
	            if(col.equals("B") && test){
	            	wb.close();
	            	return print_content(cell);
	            }
	            
	       }
	    }
	    wb.close();
		return null;
	}
	public static void read_Name()throws IOException{
		String Name;
		int i=0;
		InputStream inp = new FileInputStream(path);
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
	    Sheet sheet = wb.getSheetAt(month);
	    for(Row row : sheet){
	    	for(Cell cell : row){
	    		CellReference cellRef= new CellReference(row.getRowNum(),cell.getColumnIndex());
	    		String col = (cellRef.formatAsString()).substring(0,1);
	    		String rw = (cellRef.formatAsString()).substring(1);
	    		int rw1= Integer.parseInt(rw);
	    		if(col.equalsIgnoreCase("D")&& rw1>=limit[0] && rw1<limit[1]){
	    			Name = print_content(cell);
	    			if(i==0){
	    				name[i] = Name;
	    				nameCount[i++]++;
	    			}
	    			if(Name.equalsIgnoreCase(name[i-1]) || i>100){
	    				nameCount[i-1]++;
	    			}
	    			else{
	    				name[i]=Name;
	    				nameCount[i++]++;
	    			}
	    		}
	    	}
	    }
	    wb.close();
	}
	public static void assign_heads()throws IOException{
		String Head = null;
		int i=0,headcounter=0;
		InputStream inp = new FileInputStream(path);
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
	    Sheet sheet = wb.getSheetAt(month);
	    for(Row row : sheet)
	    {
	    	for(Cell cell : row)
	    	{
	    		CellReference cellRef= new CellReference(row.getRowNum(),cell.getColumnIndex());
	    		String col = (cellRef.formatAsString()).substring(0,1);
	    		String rw = (cellRef.formatAsString()).substring(1);
	    		int rw1= Integer.parseInt(rw);
	    		if(col.equalsIgnoreCase("B") && rw1>=limit[0] && rw1<limit[1])
	    		{
	    			Head=print_content(cell);
	    			if(headcounter<nameCount[i])
	    			{
	    				headcounter++;
	    			}
	    			else
	    			{	
	    				headcounter=1;
	    				i++;
	    			}
	    			heads[i]+=Head+",";
	    		}
	    	}
	    }
	    wb.close();
	    		
	}
	
	public static void retrievedate()throws IOException{
		String Date;
		int Dcount=1,i=0;
		InputStream inp = new FileInputStream(path);
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
	    Sheet sheet = wb.getSheetAt(month);
	    for(Row row : sheet)
	    {
	    	for(Cell cell : row)
	    	{
	    		CellReference cellRef= new CellReference(row.getRowNum(),cell.getColumnIndex());
	    		String col = (cellRef.formatAsString()).substring(0,1);
	    		String rw = (cellRef.formatAsString()).substring(1);
	    		int rw1= Integer.parseInt(rw);
	    		if(col.equalsIgnoreCase("C") && rw1>=limit[0] && rw1<limit[1])
	    		{
	    			Date= print_content(cell);
	    			if(Dcount==nameCount[i]){
	    				date[i]=Date;
	    				i++;
	    				Dcount=1;
	    			}else{
	    				Dcount++;
	    			}
	    		}
	    	}
	    }
	    wb.close();
	}
	public static void retAmt()throws IOException{
		String Amount;
		int Acount=0,i=0;
		InputStream inp = new FileInputStream(path);
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
	    Sheet sheet = wb.getSheetAt(month);
	    for(Row row : sheet)
	    {
	    	for(Cell cell : row)
	    	{
	    		CellReference cellRef= new CellReference(row.getRowNum(),cell.getColumnIndex());
	    		String col = (cellRef.formatAsString()).substring(0,1);
	    		String rw = (cellRef.formatAsString()).substring(1);
	    		int rw1= Integer.parseInt(rw);
	    		if(col.equalsIgnoreCase("F") && rw1>=limit[0] && rw1<limit[1])
	    		{
	    			Amount=print_content(cell);
	    			if(Acount<nameCount[i]){
	    				Acount++;
	    			}else{
	    				Acount=1;
	    				i++;
	    			}
	    			amt[i]+=Amount+",";
	    		}
	    	}
	    }
	    wb.close();
	}
	
	public static void Remarks() throws IOException{
		int i=0,count=1;
		InputStream inp = new FileInputStream(path);
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
	    Sheet sheet = wb.getSheetAt(month);
	    for(Row row : sheet)
	    {
	    	for(Cell cell : row)
	    	{
	    		CellReference cellRef= new CellReference(row.getRowNum(),cell.getColumnIndex());
	    		String col = (cellRef.formatAsString()).substring(0,1);
	    		String rw = (cellRef.formatAsString()).substring(1);
	    		int rw1= Integer.parseInt(rw);
	    		if(col.equalsIgnoreCase("J") && rw1>=limit[0] && rw1<limit[1])
	    		{
	    			String temp= print_content(cell);
	    			if(!temp.equalsIgnoreCase(" ")){
	    				remarks[i]=temp;
	    			}
	    			else{
	    				remarks[i]="none";
	    			}
	    			if(count<nameCount[i]){
	    				count++;
	    			}
	    			else{
	    				i++;
	    				count=1;
	    			}
	    		}
	    	}
	    }
	    wb.close();
	}
	
	public static void ChequeNo() throws IOException{
		int i=0,count=1;
		InputStream inp = new FileInputStream(path);
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
		Sheet sheet = wb.getSheetAt(month);
	    for(Row row : sheet)
	    {
	    	for(Cell cell : row)
	    	{
	    		CellReference cellRef= new CellReference(row.getRowNum(),cell.getColumnIndex());
	    		String col = (cellRef.formatAsString()).substring(0,1);
	    		String rw = (cellRef.formatAsString()).substring(1);
	    		int rw1= Integer.parseInt(rw);
	    		if(col.equalsIgnoreCase("E") && rw1>=limit[0] && rw1<limit[1])
	    		{
	    			String temp = print_content(cell);
	    			if(temp.equalsIgnoreCase(" ")){
	    				
	    				cheqno[i]="cash";
	    			}
	    			else{
	    				cheqno[i]="Cheque - "+temp;
	    			}
	    			if(count<nameCount[i]){
	    				count++;
	    			}
	    			else{
	    				count=1;
	    				i++;
	    			}		
	    		}
	    	}
	    }
	    wb.close();
		
	}
	public static void computeMonth(String date){
		
		if(date.equalsIgnoreCase("april"))
			month=5;
		else if(date.equalsIgnoreCase("may"))
			month=6;
		else if(date.equalsIgnoreCase("june"))
			month=7;
		else if(date.equalsIgnoreCase("july"))
			month=8;
		else if(date.equalsIgnoreCase("august"))
			month=9;
		else if(date.equalsIgnoreCase("september"))
			month=10;
		else if(date.equalsIgnoreCase("october"))
			month=11;
		else if(date.equalsIgnoreCase("november"))
			month=12;
		else if(date.equalsIgnoreCase("december"))
			month=13;
		else if(date.equalsIgnoreCase("january"))
			month=14;
		else if(date.equalsIgnoreCase("february"))
			month=15;
		else if(date.equalsIgnoreCase("march"))
			month=16;
		else if(date.equalsIgnoreCase("backs"))
			month=17;
	}
	
	public static void RemarkHandler() throws IOException{
		int j=4,k=1;
		InputStream inp = new FileInputStream(path);
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
		FileOutputStream fileOut = new FileOutputStream(path);
		Sheet sheet = wb.getSheetAt(17);
		Row row=sheet.getRow(0);
		Cell cell=row.getCell(0);
		try{
			for(int i=0;i<limit[1] && remarks[i]!=null;i++){
				if(!remarks[i].equalsIgnoreCase("none")){
					row = sheet.getRow(j);
					for(k=1;k<10;k++)
					{
					    cell = row.getCell(k);
					    
					    if(isCellEmpty(cell))
					    {
					    	switch(k){
					    	case 1:
					    		cell = row.createCell(k);
					    		cell.setCellValue(heads[i]);
					    		
//					    		delEntery(heads,i);
					    		break;
					    	case 2:
					    		cell = row.createCell(k);
					    		cell.setCellValue(date[i]);
//					    		delEntery(date,i);
					    		break;
					    	case 3:
					    		cell = row.createCell(k);
					    		cell.setCellValue(name[i]);
//					    		delEntery(name,i);
					    		break;
					    	case 4:
					    		cell = row.createCell(k);
					    		cell.setCellValue(cheqno[i]);
//					    		delEntery(cheqno,i);
					    		break;
					    	case 5:
					    		cell = row.createCell(k);
					    		cell.setCellValue(amt[i]);
//					    		delEntery(amt,i);
					    		break;
					    	case 9:
					    		cell = row.createCell(k);
					    		cell.setCellValue(remarks[i]);
//					    		delEntery(remarks,i);
					    		break;
					    	}
					    }
					    else
					    {
					    	j++;
					    	Sheet sheet2 = wb.getSheetAt(17);
					    	try{
					    		for(Row row2 : sheet2)
							    {
							    	for(Cell cell2 : row2)
							    	{
							    		CellReference cellRef= new CellReference(row2.getRowNum(),cell2.getColumnIndex());
							    		String col = (cellRef.formatAsString()).substring(0,1);
							    		String rw = (cellRef.formatAsString()).substring(1);
							    		int rw1= Integer.parseInt(rw);
							    		if(col.equalsIgnoreCase("J") && rw1>=limit[0]+1 && rw1<limit[1])
							    		{
							    			String temp= print_content(cell2);
							    			if(temp.equalsIgnoreCase(remarks[i])){
							    				i++;
							    			}
							    			else{
							    				i--;
							    				throw new Exception();
							    			}
							    				
							    		}
							    	}
							    }
						    		
					    	}
					    	catch(Exception del){					    		
					    	}

					    	break;   	
					    }
					}
				}
			}		
		}
		catch(Exception e){
			System.out.println("e: "+e);
			
			
		}
		try{
			wb.write(fileOut);
		    fileOut.close();
		}
		catch(Exception e1){
			System.out.print("e1:"+e1);
		}
		

		wb.close();
	}
	public static boolean isCellEmpty(final Cell cell) {
	    if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
	        return true;
	    }

	    if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().isEmpty()) {
	        return true;
	    }

	    return false;
	}
	public static void computeReVals() throws IOException
	{
		int i=-limit[0];
		InputStream inp = new FileInputStream(path);
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
	    Sheet sheet = wb.getSheetAt(month);
	    for(Row row : sheet)
	    {
	    	for(Cell cell : row)
	    	{
	    		CellReference cellRef= new CellReference(row.getRowNum(),cell.getColumnIndex());
	    		String col = (cellRef.formatAsString()).substring(0,1);
	    		String rw = (cellRef.formatAsString()).substring(1);
	    		int rw1= Integer.parseInt(rw);
	    		if(col.equalsIgnoreCase("D") && rw1>=limit[0]+1 && rw1<limit[1])
	    		{
	    			name[i]=print_content(cell);
	    			nameCount[i]=1;
	    		}
	    		if(col.equalsIgnoreCase("B") && rw1>=limit[0]+1 && rw1<limit[1])
	    		{
	    			heads[i]=print_content(cell);
	    			
	    		}
	    		if(col.equalsIgnoreCase("C") && rw1>=limit[0]+1 && rw1<limit[1])
	    		{
	    			date[i]=print_content(cell);
	    			
	    		}
	    		if(col.equalsIgnoreCase("F") && rw1>=limit[0]+1 && rw1<limit[1])
	    		{
	    			amt[i]=print_content(cell);
	    		}
	    		if(col.equalsIgnoreCase("E") && rw1>=limit[0]+1 && rw1<limit[1])
	    		{
	    			cheqno[i]=print_content(cell);
	    		}
	    		if(col.equalsIgnoreCase("J") && rw1>=limit[0]+1 && rw1<limit[1])
	    		{
	    			remarks[i]=print_content(cell);
	    			System.out.println("remarks:"+remarks[i]);
	    		}
	    	}
	    	i++;
	    }
	}
	public static void calcRema(){
		
	}
	
	public static void pseudomain() throws IOException {
		System.out.println("."+path+".");
		if(month!=17)
		{
			read_Name();
			nameCount[0]--;
			assign_heads();
			retrievedate();
			retAmt();
			ChequeNo();
			Remarks();
			RemarkHandler();
		}
		else
			computeReVals();
		
		for(int i=0;i<100;i++)
			System.out.println("jremarks:"+remarks[i]);
	}
}
