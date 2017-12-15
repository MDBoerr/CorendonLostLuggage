package is103.lostluggage.Model.Service.Instance.Matching;

import is103.lostluggage.Model.Service.Model.FoundLuggage;

/**
 *
 * @author Thijs Zijdel - 500782165
 */
public class FoundLuggageManualMatchingInstance {
    private final static FoundLuggageManualMatchingInstance instance = new FoundLuggageManualMatchingInstance();

    public static FoundLuggageManualMatchingInstance getInstance() {
        return instance;
    }

    private FoundLuggage luggage = new FoundLuggage();

    public FoundLuggage currentLuggage() {
        return luggage;
    }
}
