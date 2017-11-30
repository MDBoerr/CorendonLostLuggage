
package is103.lostluggage.Controllers.Service;

import javafx.beans.property.SimpleStringProperty;

/**
 * Doel:
 * @author gebruiker
 */
public class Luggage {
    private String  
            idmissedLuggage, 
            obj_time, 
            obj_airport, 
            obj_date;
    
    private String 
            obj_name, 
            obj_address, 
            obj_residence, 
            obj_postalcode, 
            obj_country, 
            obj_email;
    
    private String 
            obj_labelnumber, 
            obj_flightnumber, 
            obj_destination;
    

    private String 
            obj_type,
            obj_brand,
            obj_color,
            obj_signatures;

    public Luggage(
            String data_idMissedLuggage, 
            String data_time, 
            String data_airport, 
            String data_date, 
            String data_name, 
            String data_address, 
            String data_residence, 
            String data_postalcode, 
            String data_country, 
            String data_email, 
            String data_labelnumber, 
            String data_flightnumber, 
            String data_destination, 
            String data_type, 
            String data_brand, 
            String data_color, 
            String data_signatures) {
        this.idmissedLuggage = data_idMissedLuggage;
        this.obj_time =        data_time;
        this.obj_airport =     data_airport;
        this.obj_date =        data_date;
        this.obj_name =        data_name;
        this.obj_address =     data_address;
        this.obj_residence =   data_residence;
        this.obj_postalcode =  data_postalcode;
        this.obj_country =     data_country;
        this.obj_email =       data_email;
        this.obj_labelnumber = data_labelnumber;
        this.obj_flightnumber =data_flightnumber;
        this.obj_destination = data_destination;
        this.obj_type =        data_type;
        this.obj_brand =       data_brand;
        this.obj_color =       data_color;
        this.obj_signatures =  data_signatures;
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
     * @return the obj_time
     */
    public String getObj_time() {
        return obj_time;
    }

    /**
     * @param obj_time the obj_time to set
     */
    public void setObj_time(String obj_time) {
        this.obj_time = obj_time;
    }

    /**
     * @return the obj_airport
     */
    public String getObj_airport() {
        return obj_airport;
    }

    /**
     * @param obj_airport the obj_airport to set
     */
    public void setObj_airport(String obj_airport) {
        this.obj_airport = obj_airport;
    }

    /**
     * @return the obj_date
     */
    public String getObj_date() {
        return obj_date;
    }

    /**
     * @param obj_date the obj_date to set
     */
    public void setObj_date(String obj_date) {
        this.obj_date = obj_date;
    }

    /**
     * @return the obj_name
     */
    public String getObj_name() {
        return obj_name;
    }

    /**
     * @param obj_name the obj_name to set
     */
    public void setObj_name(String obj_name) {
        this.obj_name = obj_name;
    }

    /**
     * @return the obj_address
     */
    public String getObj_address() {
        return obj_address;
    }

    /**
     * @param obj_address the obj_address to set
     */
    public void setObj_address(String obj_address) {
        this.obj_address = obj_address;
    }

    /**
     * @return the obj_residence
     */
    public String getObj_residence() {
        return obj_residence;
    }

    /**
     * @param obj_residence the obj_residence to set
     */
    public void setObj_residence(String obj_residence) {
        this.obj_residence = obj_residence;
    }

    /**
     * @return the obj_postalcode
     */
    public String getObj_postalcode() {
        return obj_postalcode;
    }

    /**
     * @param obj_postalcode the obj_postalcode to set
     */
    public void setObj_postalcode(String obj_postalcode) {
        this.obj_postalcode = obj_postalcode;
    }

    /**
     * @return the obj_country
     */
    public String getObj_country() {
        return obj_country;
    }

    /**
     * @param obj_country the obj_country to set
     */
    public void setObj_country(String obj_country) {
        this.obj_country = obj_country;
    }

    /**
     * @return the obj_email
     */
    public String getObj_email() {
        return obj_email;
    }

    /**
     * @param obj_email the obj_email to set
     */
    public void setObj_email(String obj_email) {
        this.obj_email = obj_email;
    }

    /**
     * @return the obj_labelnumber
     */
    public String getObj_labelnumber() {
        return obj_labelnumber;
    }

    /**
     * @param obj_labelnumber the obj_labelnumber to set
     */
    public void setObj_labelnumber(String obj_labelnumber) {
        this.obj_labelnumber = obj_labelnumber;
    }

    /**
     * @return the obj_flightnumber
     */
    public String getObj_flightnumber() {
        return obj_flightnumber;
    }

    /**
     * @param obj_flightnumber the obj_flightnumber to set
     */
    public void setObj_flightnumber(String obj_flightnumber) {
        this.obj_flightnumber = obj_flightnumber;
    }

    /**
     * @return the obj_destination
     */
    public String getObj_destination() {
        return obj_destination;
    }

    /**
     * @param obj_destination the obj_destination to set
     */
    public void setObj_destination(String obj_destination) {
        this.obj_destination = obj_destination;
    }

    /**
     * @return the obj_type
     */
    public String getObj_type() {
        return obj_type;
    }

    /**
     * @param obj_type the obj_type to set
     */
    public void setObj_type(String obj_type) {
        this.obj_type = obj_type;
    }

    /**
     * @return the obj_brand
     */
    public String getObj_brand() {
        return obj_brand;
    }

    /**
     * @param obj_brand the obj_brand to set
     */
    public void setObj_brand(String obj_brand) {
        this.obj_brand = obj_brand;
    }

    /**
     * @return the obj_color
     */
    public String getObj_color() {
        return obj_color;
    }

    /**
     * @param obj_color the obj_color to set
     */
    public void setObj_color(String obj_color) {
        this.obj_color = obj_color;
    }

    /**
     * @return the obj_signatures
     */
    public String getObj_signatures() {
        return obj_signatures;
    }

    /**
     * @param obj_signatures the obj_signatures to set
     */
    public void setObj_signatures(String obj_signatures) {
        this.obj_signatures = obj_signatures;
    }
    
}
