package is103.lostluggage.Model.Service.Data;

import is103.lostluggage.Controllers.Service.ServiceHomeViewController;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Model.LostLuggage;
import is103.lostluggage.Model.Service.Model.MatchLuggage;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Data model for the match related table views
 * -Automatic matching
 * -Potential matching 
 * (both are matched on the same way)
 * 
 * @author Thijs Zijdel - 500782165
 */
public class ServiceDataMatch {
    //Potential matching list
    private ObservableList<MatchLuggage> potentialMatchesList = FXCollections.observableArrayList(); 
    
    //create lost list 'item'
    private ObservableList<LostLuggage> observableLostLuggage = FXCollections.observableArrayList(); 
    //create found luggage list 
    private ObservableList<FoundLuggage> foundList = FXCollections.observableArrayList();
    
    //create lost list 'item'
    private ObservableList<FoundLuggage> observableFoundLuggage = FXCollections.observableArrayList(); 
    //create lost luggage list 
    private ObservableList<LostLuggage> lostList = FXCollections.observableArrayList();
    
   //Create one instance of this class
    public static ServiceDataMatch instance = null;
    
    //Get instance
    public static ServiceDataMatch getInstance() {
        //check if the instance already is setted
        if (instance == null) {
            synchronized(ServiceDataMatch.class) {
                if (instance == null) {
                    instance = new ServiceDataMatch();
                }
            }
        }
        return instance;
    }
    
    /**  
     * Here are two different observable lists being compared  
     * This will be checked in a private method
     * 
     * @param foundList            list of found luggage that will be compared
     * @param lostList             list of lost luggage that will be compared
     * @param matchPercentage      percentage that is required to be a match
     * @return ObservableList      of the type: match luggage  
     */
    public ObservableList<MatchLuggage> autoMatching(
                                        ObservableList<FoundLuggage> foundList, 
                                        ObservableList<LostLuggage> lostList, 
                                        int matchPercentage){
        //initializing a match list 
        ObservableList<MatchLuggage> matchingList;
        
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
        ServiceHomeViewController.setPotentialResetStatus(false);
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
        ServiceHomeViewController.setPotentialResetStatus(false);
    }

    /**  
     * Method to get the potentialMatchesList from the object
     * 
     * @return ObservableList< MatchLuggage> this
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
    private ObservableList<MatchLuggage> checkData(
                                        ObservableList<LostLuggage> lostList, 
                                        ObservableList<FoundLuggage> foundList, 
                                        int minPercentage){
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
                    //check if they are equal and both not null
                    if (lost.getBrand() == null ? found.getBrand() == null : 
                            lost.getBrand().equals(found.getBrand()) &&
                            lost.getBrand().trim().length() > 0 && found.getBrand().trim().length() > 0){
                        matchingPercentage += 10;
                    }
                    if (lost.getMainColor() == null ? found.getMainColor() == null :
                            lost.getMainColor().equals(found.getMainColor()) &&
                            lost.getMainColor().trim().length() > 0 && found.getMainColor().trim().length() > 0){
                        matchingPercentage += 10;
                    }
                    if ((lost.getSecondColor() == null ? found.getSecondColor() == null : 
                            lost.getSecondColor().equals(found.getSecondColor())) &&
                            lost.getSecondColor().trim().length() > 0 && found.getSecondColor().trim().length() > 0){
                        matchingPercentage += 10;
                    }
                    if (lost.getFlight() == null ? found.getFlight() == null : 
                            lost.getFlight().equals(found.getFlight()) &&
                            lost.getFlight().trim().length() > 0 && found.getFlight().trim().length() > 0){
                        matchingPercentage += 10;
                    }
                    if (lost.getWeight() != 0 && found.getWeight() != 0){
                        if ( ((lost.getWeight()/found.getWeight())-1)*100 < 50 ){
                            matchingPercentage += 10;
                        }
                    }
                    if (lost.getSize() == null ? found.getSize() == null : 
                            lost.getSize().equals(found.getSize()) &&
                            lost.getSize().trim().length() > 0 && found.getSize().trim().length() > 0){
                            matchingPercentage += 10;
                    }
                    if ((lost.getLuggageTag() == null ? found.getLuggageTag() == null : 
                            lost.getLuggageTag().equals(found.getLuggageTag())) &&
                            lost.getLuggageTag().length()>5 && found.getLuggageTag().length()>5){
                        matchingPercentage += 50;
                        if (matchingPercentage >= 100){
                            matchingPercentage=100;
                            System.out.println("Same: luggage tag");
                        }
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
