package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelData {
	public void saveToExcel(List<String> hospitals,String Name) throws IOException
	{
	
    HSSFWorkbook wb=new HSSFWorkbook();
    
    //creating a Sheet object using the sheet Name
    HSSFSheet sheet=wb.createSheet("Data");
    
    for(int i=0;i<hospitals.size();i++)
    {
	    //Create a row object to retrieve row at index 3
	    HSSFRow row2=sheet.createRow(i);
	    
	    //create a cell object to enter value in it using cell Index
	    row2.createCell(0).setCellValue(hospitals.get(i));
    }
    //write the data in excel using output stream
    FileOutputStream outputStream = new FileOutputStream(Name+".xls");
    wb.write(outputStream);
    wb.close();
}
}

