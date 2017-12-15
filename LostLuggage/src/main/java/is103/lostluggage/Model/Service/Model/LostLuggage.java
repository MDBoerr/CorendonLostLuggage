
package is103.lostluggage.Model.Service.Model;


/**
 * missed luggage classe
 * 
 * 
 * @author Thijs Zijdel - 500782165
 */
public class LostLuggage {
     private String  
            registrationNr, 
            dateLost, 
            timeLost, 
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
            flight;
    

    
    private String
            employeeId;
    
    private int
            matchedId;

    public LostLuggage(
            String registrationNr, 
            String dateLost, 
            String timeLost, 
            String luggageTag, 
            int luggageType, 
            String brand, 
            int mainColor, 
            int secondColor, 
            int size, 
            int weight, 
            String otherCharaccteristics, 
            int passengerId, 
            String flight, 
            String employeeId, 
            int matchedId) {
        this.registrationNr = registrationNr;
        this.dateLost = dateLost;
        this.timeLost = timeLost;
        this.luggageTag = luggageTag;
        this.luggageType = luggageType;
        this.brand = brand;
        this.mainColor = mainColor;
        this.secondColor = secondColor;
        this.size = size;
        this.weight = weight;
        this.otherCharaccteristics = otherCharaccteristics;
        this.passengerId = passengerId;
        this.flight = flight;
        this.employeeId = employeeId;
        this.matchedId = matchedId;
    }


    

    public LostLuggage() {
        
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
     * @return the dateLost
     */
    public String getDateLost() {
        return dateLost;
    }

    /**
     * @param dateLost the dateLost to set
     */
    public void setDateLost(String dateLost) {
        this.dateLost = dateLost;
    }

    /**
     * @return the timeLost
     */
    public String getTimeLost() {
        return timeLost;
    }

    /**
     * @param timeLost the timeLost to set
     */
    public void setTimeLost(String timeLost) {
        this.timeLost = timeLost;
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
     * @return the flight
     */
    public String getFlight() {
        return flight;
    }

    /**
     * @param flight the flight to set
     */
    public void setFlight(String flight) {
        this.flight = flight;
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