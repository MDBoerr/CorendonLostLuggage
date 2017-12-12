package is103.lostluggage.Model;

/**
 *
 * @author Thijs Zijdel - 500782165
 */
public class LostLuggageDetails {
    private final static LostLuggageDetails instance = new LostLuggageDetails();

    public static LostLuggageDetails getInstance() {
        return instance;
    }

    private LostLuggage luggage = new LostLuggage();

    public LostLuggage currentLuggage() {
        return luggage;
    }
}
