/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model.Service.Interface;

import is103.lostluggage.Model.Service.Data.ServiceDataFound;

/**
 *
 * @author thijszijdel
 */

public interface FoundLuggageTable {
    
    /* COMMENTS WILL BE CHANGED HERE */
    
    
    /**  
     * Here will the found luggage table be set with the right data
     * The data (observable<foundLuggage>list) comes from the dataListFound
     * 
     * @param dataListFound
     * @void - No direct output 
     * @call - set lostLuggageTable             
     */    
    public void setFoundLuggageTable(ServiceDataFound dataListFound);
    
    /**  
     * Here is found luggage table overview initialized with the right values
     * 
     * @void - No direct output 
     */
    public void initializeFoundLuggageTable();
}
