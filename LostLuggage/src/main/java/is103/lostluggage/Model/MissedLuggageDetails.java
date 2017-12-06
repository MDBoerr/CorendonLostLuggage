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
