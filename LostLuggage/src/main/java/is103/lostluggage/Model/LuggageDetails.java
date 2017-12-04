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
public class LuggageDetails {
 
    private final static LuggageDetails instance = new LuggageDetails();

    public static LuggageDetails getInstance() {
        return instance;
    }

    private FoundLuggage luggage = new FoundLuggage();

    public FoundLuggage currentLuggage() {
        return luggage;
    }

}
