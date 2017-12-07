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
