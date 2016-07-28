import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
//import org.apache.poi.ss.extractor.ExcelExtractor;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class excel extends FilePick {
	static String[] name=new String[100];
	static int nameCount[]=new int[100];
	static String[] heads=new String[100];
	static String[] date= new String[100];
	static String[] amt= new String[100];
	static int[] limit=new int[2];
	static int month;
	static String path;
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
	            	return print_content(cell);
	            }
	            
	       }
	    }
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
	}
	public static void pseudomain() throws IOException {
		System.out.println("."+path+".");
		read_Name();
		nameCount[0]--;
		assign_heads();
		retrievedate();
		retAmt();
		
		
//		for(int i=0;i<100;i++)
//			System.out.println(name[i]);
	}
}
