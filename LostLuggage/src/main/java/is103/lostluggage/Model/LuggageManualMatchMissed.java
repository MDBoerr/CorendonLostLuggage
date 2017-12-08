package is103.lostluggage.Model;

/**
 *
 * @author Thijs Zijdel - 500782165
 */
public class LuggageManualMatchMissed {
    private final static LuggageManualMatchMissed instance = new LuggageManualMatchMissed();

    public static LuggageManualMatchMissed getInstance() {
        return instance;
    }

    private MissedLuggage luggage = new MissedLuggage();

    public MissedLuggage currentLuggage() {
        return luggage;
    }
}
