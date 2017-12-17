package is103.lostluggage.Model.Service.Data;

import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

/**
 * @author Thijs Zijdel - 500782165
 */
public class ServiceDataDetails {
    private String table;
    private String field;
    private String condition;
    
    private String typeData;
    
    private int i =0;
    
    public ServiceDataDetails(String table, String field, String condition){
        this.table = table;
        this.field = field;
        this.condition = condition;
    }
    
    private ObservableList<String> results = FXCollections.observableArrayList();
    private final MyJDBC db = MainApp.connectToDatabase();
    private ResultSet resultSet;
    
//    public ServiceDataLost() throws SQLException{
//        ServiceDataLost.missedLuggageList = setMissedLuggage();
//    }
    
    public ResultSet getServiceDetailsResultSet() throws SQLException{
      
            
       //chosen for try catch, so its not nescecary to throw evry time sqlexeption
        if (condition == null){

                resultSet = db.executeResultSetQuery("SELECT "+field+" FROM "+table+";");
        } else if ( condition.toUpperCase().contains("WHERE".toUpperCase()) ){
                resultSet = db.executeResultSetQuery("SELECT "+field+" FROM "+table+" "+condition+";");
        } else if ("all".equals(condition) || "*".equals(condition) && condition == null){
                resultSet = db.executeResultSetQuery("SELECT * FROM "+table+";");
        }
        
            
        return resultSet;
    }
    
    public ObservableList<String> getStringList() throws SQLException{ 
        ResultSet resultGetSetString = getServiceDetailsResultSet();
        
        while (resultGetSetString.next()){
            String result =     resultSet.getString(field);
            results.add(result);
        }
        
        return results;
    }
    
    public String[] getStringArrayList() throws SQLException{
        results = getStringList();
        String[] resultArray = new String[countHits()];
        results.forEach((String) -> {
            this.i++;
            try {
                resultArray[this.i]= resultSet.getString(field);
            } catch (SQLException ex) {
                Logger.getLogger(ServiceDataDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
            
            
        return resultArray;
        
        
                
    }
    
    public int countHits() throws SQLException{
        resultSet = db.executeResultSetQuery("SELECT COUNT("+field+") FROM "+table+";");
        while (resultSet.next()){
             int hits = resultSet.getInt("COUNT("+field+");");
             return hits;
        }
        return 0;
    }
    
    public int getIdValue() throws SQLException{
        ResultSet resultSetThisField = getServiceDetailsResultSet();
        int gotten = 0;
        while (resultSetThisField.next()){
            gotten =     resultSetThisField.getInt(this.field);
        }
        return gotten;
    }
    
    
}
