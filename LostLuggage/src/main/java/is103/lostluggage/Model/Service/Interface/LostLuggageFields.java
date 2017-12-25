/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model.Service.Interface;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author thijszijdel
 */
public interface LostLuggageFields {
    
    /**  
     * Method to get a lost luggage resultSet 
     * Open for extensions
     * 
     * @return resultSet                resultSet for the wanted luggage
     * @throws java.sql.SQLException    getting the data from an database
     */ 
    public ResultSet getManualLostLuggageResultSet() throws SQLException;
    
    /**  
     * Method to set lost luggage fields with a given resultSet
     * Open for extensions
     * 
     * @param  resultSet                will be set on the fields
     * @void   no direct output         can be on different ways  
     * @throws java.sql.SQLException    getting the data from an database
     */
    public void setLostFields(ResultSet resultSet) throws SQLException;
}
