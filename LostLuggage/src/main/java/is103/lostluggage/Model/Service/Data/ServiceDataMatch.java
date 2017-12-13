/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model.Service.Data;

import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Model.LostLuggage;
import is103.lostluggage.Model.Service.Model.MatchLuggage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author thijszijdel
 */
public class ServiceDataMatch {
    public ObservableList<MatchLuggage> autoMatching(ObservableList<FoundLuggage> foundList, ObservableList<LostLuggage> lostList){
        ObservableList<MatchLuggage> matchingList = FXCollections.observableArrayList();
        
        //Start looping every item of lost list
        lostList.forEach((lost) -> {

            //With every item of found list
            foundList.forEach((found) -> {
                //set match id and percentage on zero.
                int matchedId = 0;
                int matchingPercentage = 0;
                
                //Contorle of different match possibilities:
                if (lost.getLuggageType() == found.getLuggageType()
                                        && lost.getLuggageType() != 0 
                                        && found.getLuggageType() != 0){
                    matchingPercentage += 10;
                }
                if (lost.getBrand().equals(found.getBrand())
                                        && lost.getBrand() != null 
                                        && found.getBrand() != null){
                    matchingPercentage += 10;
                }
                if (lost.getMainColor()==found.getMainColor()
                                        && lost.getMainColor() != 0 
                                        && found.getMainColor() != 0){
                    matchingPercentage += 10;
                }
                if (lost.getSecondColor()==found.getSecondColor()
                                        && lost.getSecondColor() != 0 
                                        && found.getSecondColor() != 0){
                    matchingPercentage += 10;
                }
                if (lost.getFlight()==found.getArrivedWithFlight()
                                        && lost.getFlight() != null 
                                        && found.getArrivedWithFlight() != null){
                    matchingPercentage += 10;
                }
                
                if (lost.getWeight() != 0 && found.getWeight() != 0){
                    if ( ((lost.getWeight()/found.getWeight())-1)*100 < 50 ){
                        matchingPercentage += 10;
                    }
                }
                
                if (lost.getSize() != 0 && found.getSize() != 0){
                    if ( ((lost.getSize()/found.getSize())-1)*100 < 70 ){
                        matchingPercentage += 10;
                    }
                }
                
                if (lost.getLuggageTag()==found.getLuggageTag() 
                                        && lost.getLuggageTag() != null 
                                        && found.getLuggageTag() != null ){
                    matchingPercentage += 50;
                    if (matchingPercentage >= 100){matchingPercentage=100;System.out.println("Same: luggage tag");}
                }
                
                if (matchingPercentage>10){
                    matchingList.add(new MatchLuggage(
                        found.getRegistrationNr(), 
                        lost.getRegistrationNr(), 
                        lost.getLuggageTag()+" | "+found.getLuggageTag(), 
                        matchingPercentage, 
                        lost.getLuggageType()+" | "+found.getLuggageType(), 
                        lost.getBrand()+" | "+found.getBrand(), 
                        lost.getMainColor()+" | "+found.getMainColor(), 
                        lost.getSecondColor()+" | "+found.getSecondColor(), 
                        lost.getSize()+" | "+found.getSize(), 
                        lost.getWeight()+" | "+found.getWeight(), 
                        lost.getOtherCharaccteristics()+" | "+found.getOtherCharaccteristics(), 
                        matchedId++));
                }
                
            });
        });  
        return matchingList;
        
    }
}
