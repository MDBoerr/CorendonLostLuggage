package is103.lostluggage.Model.Service.Instance.Matching;

import is103.lostluggage.Model.Service.Model.LostLuggage;

/**
 *
 * @author Thijs Zijdel - 500782165
 */
public class LostLuggageManualMatchingInstance {
    private final static LostLuggageManualMatchingInstance instance = new LostLuggageManualMatchingInstance();

    public static LostLuggageManualMatchingInstance getInstance() {
        return instance;
    }

    private LostLuggage luggage = new LostLuggage();

    public LostLuggage currentLuggage() {
        return luggage;
    }
}
