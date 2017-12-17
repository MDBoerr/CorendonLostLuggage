package is103.lostluggage.Model.Service.Data;

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
    //matching list
    private ObservableList<MatchLuggage> potentialMatchesList = FXCollections.observableArrayList(); 
    private boolean potentialMatchesReSet = false;
    
    public ObservableList<MatchLuggage> autoMatching(ObservableList<FoundLuggage> foundList, ObservableList<LostLuggage> lostList){
        ObservableList<MatchLuggage> matchingList = FXCollections.observableArrayList();
        
        matchingList = checkData(lostList, foundList, 10);
        
        return matchingList;
        
    }
    
    public boolean getPotentialResetStatus(){
        return this.potentialMatchesReSet;
    }
    public ObservableList<MatchLuggage> getPotentialMatchesList(){
        return this.potentialMatchesList;
    }
    public void setPotentialResetStatus(boolean b) {
        this.potentialMatchesReSet = b;
    }
    
    
    //is beeing called by a LOST luggage (details screen)
    public void potentialFoundMatches(String id) throws SQLException{
        //create lost list 'item'
        ObservableList<LostLuggage> observableItem = FXCollections.observableArrayList(); 
        
        

        //found luggages
        ObservableList<FoundLuggage> foundList = ServiceDataFound.getFoundLuggage();
        
        
        //get observable list of the 1 luggage
        ServiceDataLost thisLuggage = new ServiceDataLost();
        ResultSet resultset = thisLuggage.getLostResultSet(id);
        observableItem = thisLuggage.getObservableList(resultset);
        
        this.potentialMatchesList.clear();
        this.potentialMatchesList = checkData(observableItem, foundList, 10);
         
        this.potentialMatchesReSet = false;
        //return this.potentialMatchesList;
    }
    
     //is beeing called by a FOUND luggage (details screen)
    public void potentialLostMatches(String id) throws SQLException {
                //create lost list 'item'
        ObservableList<FoundLuggage> observableItem = FXCollections.observableArrayList(); 
        
        

        //lost luggages
        ObservableList<LostLuggage> lostList = ServiceDataLost.getLostLuggage();
        
        
        //get observable list of the 1 luggage
        ServiceDataFound thisLuggage = new ServiceDataFound();
        ResultSet resultset = thisLuggage.getFoundResultSet(id);
        observableItem = thisLuggage.getObservableList(resultset);
        
        this.potentialMatchesList.clear();
        this.potentialMatchesList = checkData(lostList, observableItem, 10);
         
        this.potentialMatchesReSet = false;
    }

    
    
    private ObservableList<MatchLuggage> checkData(ObservableList<LostLuggage> lostList, ObservableList<FoundLuggage> foundList, int minPercentage){
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
