package is103.lostluggage.Model.Service.Instance.Matching;

import is103.lostluggage.Model.Service.Model.LostLuggage;

/**
 *
 * @author Thijs Zijdel - 500782165
 */
public class LuggageManualMatchLost {
    private final static LuggageManualMatchLost instance = new LuggageManualMatchLost();

    public static LuggageManualMatchLost getInstance() {
        return instance;
    }

    private LostLuggage luggage = new LostLuggage();

    public LostLuggage currentLuggage() {
        return luggage;
    }
}
