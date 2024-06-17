package ReUseable;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReUseable {

    public String read_Properties_file(String Key)
    {
        Properties prop = new Properties();
        String value = null;
        try {
            prop.load(new FileInputStream(System.getProperty("user.dir") + "/Aadhar.properties"));
            value = prop.getProperty(Key);

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return value;
    }

    public String getCurrentDate(){
        SimpleDateFormat ft    = new SimpleDateFormat("YYYY-MM-dd");
        String str = ft.format(new Date());
        return str;
    }
    public String read_And_Print_XL_AsPerTestData(String testcasename , String columnName){

        String data = null;
        try{
            String XLFilePath = System.getProperty("user.dir")+"/Input/Credit Card.xlsx";
            FileInputStream myxlfile = new FileInputStream(XLFilePath);
            Workbook workbook = new XSSFWorkbook(myxlfile);
            Sheet sheet = workbook.getSheet("Credit");
            int lastRow = sheet.getLastRowNum();
            // System.out.println("The last row which has data =="+lastRow);

            // Loop for Row Iteration...
            for(int i=0;i<=lastRow;i++){
                Row row = sheet.getRow(i);
                // Get the last Column which has data
                int lastCell = row.getLastCellNum();
                Cell cell = row.getCell(0);
                String runtimeTestCaseName = cell.getStringCellValue();
                //   System.out.println("The First Column Value is ==>"+runtimeTestCaseName);
                if(runtimeTestCaseName.equals(testcasename)) {
                    // Loop for Column Iteration ...
                    Row RowNew = sheet.getRow(0);
                    for(int j=0;j<lastCell;j++){
                        Cell cellnew = RowNew.getCell(j);
                        String RunTimeCallValue = cellnew.getStringCellValue();
                        if(RunTimeCallValue.equals(columnName)) {
                            data = sheet.getRow(i).getCell(j).toString();
                            System.out.println("The XL value is ==>" + data);
                        }
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }




}
