package is103.lostluggage.Model.Service.Instance.Details;

import is103.lostluggage.Model.Service.Model.LostLuggage;

/**
 *
 * @author Thijs Zijdel - 500782165
 */
public class LostLuggageDetailsInstance {
    private final static LostLuggageDetailsInstance instance = new LostLuggageDetailsInstance();

    public static LostLuggageDetailsInstance getInstance() {
        return instance;
    }

    private LostLuggage luggage = new LostLuggage();

    public LostLuggage currentLuggage() {
        return luggage;
    }
}
