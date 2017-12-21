package is103.lostluggage.Model.Service.Data;

import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Model.LostLuggage;
import is103.lostluggage.Model.Service.Model.MatchLuggage;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author thijszijdel
 */
public class ServiceDataMatch {
    
    
    //Potential matching list
    private ObservableList<MatchLuggage> potentialMatchesList = FXCollections.observableArrayList(); 
    
    
    
    //create lost list 'item'
    private ObservableList<LostLuggage> observableLostLuggage = FXCollections.observableArrayList(); 
    
    private ObservableList<FoundLuggage> foundList = FXCollections.observableArrayList();
    
     
        //create lost list 'item'
    private ObservableList<FoundLuggage> observableFoundLuggage = FXCollections.observableArrayList(); 
    
    private ObservableList<LostLuggage> lostList = FXCollections.observableArrayList();
    
    
    
     /**  
     * Here are two different observable lists being compared  
     * This will be checked in a private method
     * 
     * @param foundList            list of found luggage that will be compared
     * @param lostList             list of lost luggage that will be compared
     * @param matchPercentage      percentage that is required to be a match
     * @return ObservableList      of the type: match luggage  
     */
    public ObservableList<MatchLuggage> autoMatching(ObservableList<FoundLuggage> foundList, ObservableList<LostLuggage> lostList, int matchPercentage){
        //initializing a match list 
        ObservableList<MatchLuggage> matchingList = FXCollections.observableArrayList();
        
        //match list wil be get from  checkData()
        matchingList = checkData(lostList, foundList, matchPercentage);
        
        //the match list will be returned
        //Note: only matches higher than the matchPercentage
        return matchingList;
    }
   
    
    
    /**  
     * Here will the potential matches list been formed for lost luggage
     * the @param id is for getting the right resultSet to compare
     * Note: this method is being called from the lost luggage detail view
     * Note: the previous lists will also be cleared.
     * 
     * @param id            the luggage that needs to be compared
     * @void                the potentialMatchesList of this object will be set 
     */
    public void potentialMatchesForLostLuggage(String id) throws SQLException{
        //Clear the previous lists
        this.potentialMatchesList.clear();
        observableLostLuggage.clear();

        //get the found luggages list
        foundList = ServiceDataFound.getFoundLuggage();
        
        //create observable list of the giving lost luggage
        ServiceDataLost thisLuggage = new ServiceDataLost();
        //get the  resultset of the giving lost luggage
        ResultSet resultset = thisLuggage.getLostResultSet(id);
        
        //get lost luggage's observable list 
        observableLostLuggage = thisLuggage.getObservableList(resultset);
        
        //check wich luggages match in checkData()
        this.potentialMatchesList = checkData(observableLostLuggage, foundList, 10);
         
        //set the reset status to false so there wont be a reset.
        MainApp.setPotentialResetStatus(false);
    }
    
    
   
    
    /**  
     * Here will the potential matches list been formed for found luggage
     * the @param id is for getting the right resultSet to compare
     * Note: this method is being called from the found luggage detail view
     * Note: the previous lists will also be cleared.
     * 
     * @param id            the luggage that needs to be compared
     * @void                the potentialMatchesList of this object will be set
     */
    public void potentialMatchesForFoundLuggage(String id) throws SQLException {
        //Clear the previous lists
        this.potentialMatchesList.clear();
        observableFoundLuggage.clear();
        
        //get the lost luggages list
        lostList = ServiceDataLost.getLostLuggage();
        
        
        
        //create observable list of the giving found luggage
        ServiceDataFound thisLuggage = new ServiceDataFound();
        //get the  resultset of the giving found luggage
        ResultSet resultset = thisLuggage.getFoundResultSet(id);
        
        //get found luggage's observable list 
        observableFoundLuggage = thisLuggage.getObservableList(resultset);
        
        //check wich luggages match in checkData()
        this.potentialMatchesList = checkData(lostList, observableFoundLuggage, 10);
         
        //set the reset status to false so there wont be a reset.
        MainApp.setPotentialResetStatus(false);
    }

    
    /**  
     * Method to get the potentialMatchesList from the object
     * 
     * @return ObservableList<MatchLuggage> this
     */
    public ObservableList<MatchLuggage> getPotentialMatchesList(){
        return this.potentialMatchesList;
    }
        
    /**  
     * Here are two different observable lists actually being compared  
     * 
     * @param foundList            list of found luggage that will be compared
     * @param lostList             list of lost luggage that will be compared
     * @param minPercentage        percentage that is required to be a match
     * @return ObservableList      of the type: matched luggage ! 
     */
    private ObservableList<MatchLuggage> checkData(ObservableList<LostLuggage> lostList, ObservableList<FoundLuggage> foundList, int minPercentage){
        potentialMatchesList.clear();
        this.potentialMatchesList.clear();
        lostList.forEach((lost)-> {
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

                    if (lost.getSize()==found.getSize()
                                            && lost.getSize() != null 
                                            && found.getSize() != null){
                            matchingPercentage += 10;

                    }

                    if (lost.getLuggageTag()==found.getLuggageTag() 
                                            && lost.getLuggageTag() != null 
                                            && found.getLuggageTag() != null ){
                        matchingPercentage += 50;
                        if (matchingPercentage >= 100){matchingPercentage=100;System.out.println("Same: luggage tag");}
                    }

                    if (matchingPercentage>minPercentage){
                        this.potentialMatchesList.add(new MatchLuggage(
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
        return potentialMatchesList;
    }
}
