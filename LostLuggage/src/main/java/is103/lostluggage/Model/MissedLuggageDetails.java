package is103.lostluggage.Model;

/**
 *
 * @author Thijs Zijdel - 500782165
 */
public class MissedLuggageDetails {
    private final static MissedLuggageDetails instance = new MissedLuggageDetails();

    public static MissedLuggageDetails getInstance() {
        return instance;
    }

    private MissedLuggage luggage = new MissedLuggage();

    public MissedLuggage currentLuggage() {
        return luggage;
    }
}
