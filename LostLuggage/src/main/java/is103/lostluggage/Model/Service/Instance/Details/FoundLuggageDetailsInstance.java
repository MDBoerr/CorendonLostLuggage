package is103.lostluggage.Model.Service.Instance.Details;

import is103.lostluggage.Model.Service.Model.FoundLuggage;


/**
 *
 * @author Thijs Zijdel - 500782165
 */
public class FoundLuggageDetailsInstance {
 
    private final static FoundLuggageDetailsInstance instance = new FoundLuggageDetailsInstance();

    public static FoundLuggageDetailsInstance getInstance() {
        return instance;
    }

    private FoundLuggage luggage = new FoundLuggage();

    public FoundLuggage currentLuggage() {
        return luggage;
    }
    
    

}
