package is103.lostluggage.Model.Service.Model;

/**
 *
 * @author Thijs Zijdel - 500782165
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
    
    private String mainColor,
            secondColor;
    
    private int       weight;
    
    private String
            size,
            otherCharaccteristics;
    
    private int 
            passengerId;
            
    private String 
            arrivedWithFlight,
            locationFound,
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
            String mainColor, 
            String secondColor, 
            String size, 
            int weight, 
            String otherCharaccteristics, 
            int passengerId, 
            String arrivedWithFlight, 
            String locationFound, 
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
    public String getMainColor() {
        return mainColor;
    }

    /**
     * @param mainColor the mainColor to set
     */
    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
    }

    /**
     * @return the secondColor
     */
    public String getSecondColor() {
        return secondColor;
    }

    /**
     * @param secondColor the secondColor to set
     */
    public void setSecondColor(String secondColor) {
        this.secondColor = secondColor;
    }

    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
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
    public String getFlight() {
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
    public String getLocationFound() {
        return locationFound;
    }

    /**
     * @param locationFound the locationFound to set
     */
    public void setLocationFound(String locationFound) {
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