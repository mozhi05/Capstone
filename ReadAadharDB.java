package jdbclearn;

import java.sql.*;

import java.sql.DriverManager;

public class ReadAadharDB {

    public boolean isAadharNumberCorrect(String aadharNo, String dbName){
        boolean flag = false;
        try {
            Connection connection;
            connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306","root","4Management.");

            Statement statement = connection.createStatement(); // creating statement obj
            statement.execute(" use "+dbName+"");//using that statement obj, to use database
            //System.out.println("You are using AadharDB");
            ResultSet Sqlresult = statement.executeQuery("select count(*) as reowcount from Aadharrecord where Aadharno =\""+aadharNo+"\";");
            while (Sqlresult.next())
            {
                int runtimeValue = Sqlresult.getInt("reowcount");
                System.out.println(runtimeValue);
                if (runtimeValue!=0) {
                    flag = true;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        //System.out.println(flag);
        return flag;
    }

    public String readAadharTable(String aadharNo, String fieldName){
        String result = "";
        try {
            Connection connection;
            connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306","root","4Management.");
            Statement statement = connection.createStatement(); // creating statement obj
            statement.execute(" use Aadhar;");//using that statement obj, to use database
            ResultSet Sqlresult = statement.executeQuery("select "+fieldName+" from Aadharrecord where Aadharno =\""+aadharNo+"\";");
            while (Sqlresult.next()){
                result = Sqlresult.getString(fieldName);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return result;
    }
    public String readCreditTable(String Credit_Card_Number, String fieldName){
        String result = "";
        try {
            Connection connection;
            connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306","root","4Management.");
            Statement statement = connection.createStatement(); // creating statement obj
            statement.execute(" use Creditcard1;");//using that statement obj, to use database
            ResultSet Sqlresult = statement.executeQuery("select "+fieldName+" from Creditcard_Table1 where Creditcardnumber =\""+Credit_Card_Number+"\";");
            while (Sqlresult.next()){
                result = Sqlresult.getString(fieldName);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return result;
    }
    public String readPANTable(String Credit_Card_Number, String fieldName){
        String result = "";
        try {
            Connection connection;
            connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306","root","4Management.");
            Statement statement = connection.createStatement(); // creating statement obj
            statement.execute(" use Creditcard1;");//using that statement obj, to use database
            ResultSet Sqlresult = statement.executeQuery("select "+fieldName+" from Creditcard_Table2 where Creditcardnumber =\""+Credit_Card_Number+"\";");
            while (Sqlresult.next()){
                result = Sqlresult.getString(fieldName);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return result;
    }
}
