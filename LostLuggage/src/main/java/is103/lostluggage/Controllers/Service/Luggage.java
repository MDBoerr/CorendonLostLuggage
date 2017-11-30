
package is103.lostluggage.Controllers.Service;

import javafx.beans.property.SimpleStringProperty;

/**
 * Doel:
 * @author gebruiker
 */
public class Luggage {
    private String  
            idmissedLuggage, 
            timeField, 
            airportField, 
            dateDatepicker;
    
    private String 
            nameField, 
            addressField, 
            residenceField, 
            postalcodeField, 
            countryField, 
            emailField;
    
    private String 
            labelnumberField, 
            flightnumberField, 
            destinationField;
    

    private String 
            typeField,
            brandField,
            colorField,
            signaturesField;

    public Luggage(
            String idmissedLuggage, 
            String timeField, 
            String airportField, 
            String dateDatepicker, 
            String nameField, 
            String addressField, 
            String residenceField, 
            String postalcodeField, 
            String countryField, 
            String emailField, 
            String labelnumberField, 
            String flightnumberField, 
            String destinationField, 
            String typeField, 
            String brandField, 
            String colorField, 
            String signaturesField) {
        this.idmissedLuggage =  idmissedLuggage;
        this.timeField =        timeField;
        this.airportField =     airportField;
        this.dateDatepicker =   dateDatepicker;
        this.nameField =        nameField;
        this.addressField =     addressField;
        this.residenceField =   residenceField;
        this.postalcodeField =  postalcodeField;
        this.countryField =     countryField;
        this.emailField =       emailField;
        this.labelnumberField = labelnumberField;
        this.flightnumberField = flightnumberField;
        this.destinationField = destinationField;
        this.typeField =        typeField;
        this.brandField =       brandField;
        this.colorField =       colorField;
        this.signaturesField =  signaturesField;
    }

    /**
     * @return the idmissedLuggage
     */
    public String getIdmissedLuggage() {
        return idmissedLuggage;
    }

    /**
     * @param idmissedLuggage the idmissedLuggage to set
     */
    public void setIdmissedLuggage(String idmissedLuggage) {
        this.idmissedLuggage = idmissedLuggage;
    }

    /**
     * @return the timeField
     */
    public String getTimeField() {
        return timeField;
    }

    /**
     * @param timeField the timeField to set
     */
    public void setTimeField(String timeField) {
        this.timeField = timeField;
    }

    /**
     * @return the airportField
     */
    public String getAirportField() {
        return airportField;
    }

    /**
     * @param airportField the airportField to set
     */
    public void setAirportField(String airportField) {
        this.airportField = airportField;
    }

    /**
     * @return the dateDatepicker
     */
    public String getDateDatepicker() {
        return dateDatepicker;
    }

    /**
     * @param dateDatepicker the dateDatepicker to set
     */
    public void setDateDatepicker(String dateDatepicker) {
        this.dateDatepicker = dateDatepicker;
    }

    /**
     * @return the nameField
     */
    public String getNameField() {
        return nameField;
    }

    /**
     * @param nameField the nameField to set
     */
    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    /**
     * @return the addressField
     */
    public String getAddressField() {
        return addressField;
    }

    /**
     * @param addressField the addressField to set
     */
    public void setAddressField(String addressField) {
        this.addressField = addressField;
    }

    /**
     * @return the residenceField
     */
    public String getResidenceField() {
        return residenceField;
    }

    /**
     * @param residenceField the residenceField to set
     */
    public void setResidenceField(String residenceField) {
        this.residenceField = residenceField;
    }

    /**
     * @return the postalcodeField
     */
    public String getPostalcodeField() {
        return postalcodeField;
    }

    /**
     * @param postalcodeField the postalcodeField to set
     */
    public void setPostalcodeField(String postalcodeField) {
        this.postalcodeField = postalcodeField;
    }

    /**
     * @return the countryField
     */
    public String getCountryField() {
        return countryField;
    }

    /**
     * @param countryField the countryField to set
     */
    public void setCountryField(String countryField) {
        this.countryField = countryField;
    }

    /**
     * @return the emailField
     */
    public String getEmailField() {
        return emailField;
    }

    /**
     * @param emailField the emailField to set
     */
    public void setEmailField(String emailField) {
        this.emailField = emailField;
    }

    /**
     * @return the labelnumberField
     */
    public String getLabelnumberField() {
        return labelnumberField;
    }

    /**
     * @param labelnumberField the labelnumberField to set
     */
    public void setLabelnumberField(String labelnumberField) {
        this.labelnumberField = labelnumberField;
    }

    /**
     * @return the flightnumberField
     */
    public String getFlightnumberField() {
        return flightnumberField;
    }

    /**
     * @param flightnumberField the flightnumberField to set
     */
    public void setFlightnumberField(String flightnumberField) {
        this.flightnumberField = flightnumberField;
    }

    /**
     * @return the destinationField
     */
    public String getDestinationField() {
        return destinationField;
    }

    /**
     * @param destinationField the destinationField to set
     */
    public void setDestinationField(String destinationField) {
        this.destinationField = destinationField;
    }

    /**
     * @return the typeField
     */
    public String getTypeField() {
        return typeField;
    }

    /**
     * @param typeField the typeField to set
     */
    public void setTypeField(String typeField) {
        this.typeField = typeField;
    }

    /**
     * @return the brandField
     */
    public String getBrandField() {
        return brandField;
    }

    /**
     * @param brandField the brandField to set
     */
    public void setBrandField(String brandField) {
        this.brandField = brandField;
    }

    /**
     * @return the colorField
     */
    public String getColorField() {
        return colorField;
    }

    /**
     * @param colorField the colorField to set
     */
    public void setColorField(String colorField) {
        this.colorField = colorField;
    }

    /**
     * @return the signaturesField
     */
    public String getSignaturesField() {
        return signaturesField;
    }

    /**
     * @param signaturesField the signaturesField to set
     */
    public void setSignaturesField(String signaturesField) {
        this.signaturesField = signaturesField;
    }
    
}
