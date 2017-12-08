package is103.lostluggage.Model;

/**
 *
 * @author Thijs Zijdel - 500782165
 */
public class LuggageManualMatchFound {
    private final static LuggageManualMatchFound instance = new LuggageManualMatchFound();

    public static LuggageManualMatchFound getInstance() {
        return instance;
    }

    private FoundLuggage luggage = new FoundLuggage();

    public FoundLuggage currentLuggage() {
        return luggage;
    }
}
