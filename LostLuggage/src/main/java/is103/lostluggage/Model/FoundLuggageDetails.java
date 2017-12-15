/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model;

import javax.naming.Context;

/**
 *
 * @author thijszijdel
 */
public class FoundLuggageDetails {
 
    private final static FoundLuggageDetails instance = new FoundLuggageDetails();

    public static FoundLuggageDetails getInstance() {
        return instance;
    }

    private LostLuggage luggage = new LostLuggage();

    public LostLuggage currentLuggage() {
        return luggage;
    }

}
