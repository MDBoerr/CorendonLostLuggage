/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model.Service.Interface;

import is103.lostluggage.Model.Service.Data.ServiceDataLost;

/**
 *
 * @author thijszijdel
 */
public interface LostLuggageTable {
    
    
    /* COMMENTS WILL BE CHANGED HERE */
    
    
    /**  
     * Here will the lost luggage table be set with the right data
     * The data (observable<lostluggage>list) comes from the dataListLost
     * 
     * @param dataListLost
     * @void - No direct output 
     * @call - set lostLuggageTable             
     */
    public void setLostLuggageTable(ServiceDataLost dataListLost);
    
    /**  
     * Here is lost luggage table overview initialized with the right values
     * 
     * @void - No direct output 
     */
    public void initializeLostLuggageTable();
}