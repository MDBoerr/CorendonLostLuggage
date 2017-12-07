/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model;

/**
 *
 * @author thijszijdel
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
