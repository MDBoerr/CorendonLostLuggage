/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model;

/**
 *
 * @author thijszijdel
 */
public class settings {

//    public settings mainApp = new settings(
//                        "English",  //language
//                        "#ababab",  //unfocus color
//                        "#e03636"   //alert color
//    
//    );
    
    
    private String language;
    private final String unfocusColor;
    private final String alertColor;
    
    public settings(String language, String unfocusColor, String alertColor){
        this.language = language;
        this.unfocusColor = unfocusColor;
        this.alertColor = alertColor;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }
    
    /**
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the unfocusColor
     */
    public String getUnfocusColor() {
        return unfocusColor;
    }

    /**
     * @return the alertColor
     */
    public String getAlertColor() {
        return alertColor;
    }
    
}