/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model;

/**
 *
 * @author thijs zijdel
 */
public class FoundLuggage {
    private String  
            registrationNr, 
            dateFound, 
            timeFound, 
            luggageTag;
    
    private int 
            luggageType;
    
    private String
            brand;
    
    private int 
            mainColor,
            secondColor,
            size,
            weight;
    
    private String
            otherCharaccteristics;
    
    private int 
            passengerId;
            
    private String 
            arrivedWithFlight;
    
    private int 
            locationFound;
    
    private String
            employeeId;
    
    private int
            matchedId;

    public FoundLuggage(
            String registrationNr, 
            String dateFound, 
            String timeFound, 
            String luggageTag, 
            int luggageType, 
            String brand, 
            int mainColor, 
            int secondColor, 
            int size, 
            int weight, 
            String otherCharaccteristics, 
            int passengerId, 
            String arrivedWithFlight, 
            int locationFound, 
            String employeeId, 
            int matchedId) {
        this.registrationNr = registrationNr;
        this.dateFound = dateFound;
        this.timeFound = timeFound;
        this.luggageTag = luggageTag;
        this.luggageType = luggageType;
        this.brand = brand;
        this.mainColor = mainColor;
        this.secondColor = secondColor;
        this.size = size;
        this.weight = weight;
        this.otherCharaccteristics = otherCharaccteristics;
        this.passengerId = passengerId;
        this.arrivedWithFlight = arrivedWithFlight;
        this.locationFound = locationFound;
        this.employeeId = employeeId;
        this.matchedId = matchedId;
    }


    

    public FoundLuggage() {
        
    }

    /**
     * @return the registrationNr
     */
    public String getRegistrationNr() {
        return registrationNr;
    }

    /**
     * @param registrationNr the registrationNr to set
     */
    public void setRegistrationNr(String registrationNr) {
        this.registrationNr = registrationNr;
    }

    /**
     * @return the dateFound
     */
    public String getDateFound() {
        return dateFound;
    }

    /**
     * @param dateFound the dateFound to set
     */
    public void setDateFound(String dateFound) {
        this.dateFound = dateFound;
    }

    /**
     * @return the timeFound
     */
    public String getTimeFound() {
        return timeFound;
    }

    /**
     * @param timeFound the timeFound to set
     */
    public void setTimeFound(String timeFound) {
        this.timeFound = timeFound;
    }

    /**
     * @return the luggageTag
     */
    public String getLuggageTag() {
        return luggageTag;
    }

    /**
     * @param luggageTag the luggageTag to set
     */
    public void setLuggageTag(String luggageTag) {
        this.luggageTag = luggageTag;
    }

    /**
     * @return the luggageType
     */
    public int getLuggageType() {
        return luggageType;
    }

    /**
     * @param luggageType the luggageType to set
     */
    public void setLuggageType(int luggageType) {
        this.luggageType = luggageType;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the mainColor
     */
    public int getMainColor() {
        return mainColor;
    }

    /**
     * @param mainColor the mainColor to set
     */
    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
    }

    /**
     * @return the secondColor
     */
    public int getSecondColor() {
        return secondColor;
    }

    /**
     * @param secondColor the secondColor to set
     */
    public void setSecondColor(int secondColor) {
        this.secondColor = secondColor;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * @return the otherCharaccteristics
     */
    public String getOtherCharaccteristics() {
        return otherCharaccteristics;
    }

    /**
     * @param otherCharaccteristics the otherCharaccteristics to set
     */
    public void setOtherCharaccteristics(String otherCharaccteristics) {
        this.otherCharaccteristics = otherCharaccteristics;
    }

    /**
     * @return the passengerId
     */
    public int getPassengerId() {
        return passengerId;
    }

    /**
     * @param passengerId the passengerId to set
     */
    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    /**
     * @return the arrivedWithFlight
     */
    public String getArrivedWithFlight() {
        return arrivedWithFlight;
    }

    /**
     * @param arrivedWithFlight the arrivedWithFlight to set
     */
    public void setArrivedWithFlight(String arrivedWithFlight) {
        this.arrivedWithFlight = arrivedWithFlight;
    }

    /**
     * @return the locationFound
     */
    public int getLocationFound() {
        return locationFound;
    }

    /**
     * @param locationFound the locationFound to set
     */
    public void setLocationFound(int locationFound) {
        this.locationFound = locationFound;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the matchedId
     */
    public int getMatchedId() {
        return matchedId;
    }

    /**
     * @param matchedId the matchedId to set
     */
    public void setMatchedId(int matchedId) {
        this.matchedId = matchedId;
    }
    
    


}