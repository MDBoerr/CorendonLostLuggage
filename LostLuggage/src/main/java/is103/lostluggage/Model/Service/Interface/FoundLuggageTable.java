package is103.lostluggage.Model.Service.Interface;

import is103.lostluggage.Model.Service.Model.FoundLuggage;
import javafx.collections.ObservableList;

/**
 *
 * @author thijszijdel
 */

public interface FoundLuggageTable {
    
    /* COMMENTS WILL BE CHANGED HERE */
    
    
    /**  
     * Here will be insured that found luggage table be set with the right data
     * 
     * @param  list of the type found luggage
     * @void - No direct output 
     * @call - set lostLuggageTable             
     */    
    public void setFoundLuggageTable(ObservableList<FoundLuggage> list);
    
    /**  
     * Here will be insured that found luggage table overview initialized with the right values
     * 
     * @void - No direct output 
     */
    public void initializeFoundLuggageTable();
}
