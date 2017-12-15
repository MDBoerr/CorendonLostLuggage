package is103.lostluggage.Model;

/**
 *
 * @author asus
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

    public String getRegistrationNr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
