package is103.lostluggage.Model;


/**
 *
 * @author Thijs Zijdel - 500782165
 */
public class FoundLuggageDetails {
 
    private final static FoundLuggageDetails instance = new FoundLuggageDetails();

    public static FoundLuggageDetails getInstance() {
        return instance;
    }

    private FoundLuggage luggage = new FoundLuggage();

    public FoundLuggage currentLuggage() {
        return luggage;
    }

}
