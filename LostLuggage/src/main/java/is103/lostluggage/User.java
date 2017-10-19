/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Mike
 */
public class User {
    
    private SimpleStringProperty id, lastName, firstName, level, status;

    public User(String id, String lastName, String firstName, String level, String status) {

        this.id = new SimpleStringProperty(id);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.level = new SimpleStringProperty(level);
        this.status = new SimpleStringProperty(status);

    }

    public String getId() {
        return id.get();
    }


    public void setId(SimpleStringProperty id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(SimpleStringProperty lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName.get();
    }


    public void setFirstName(SimpleStringProperty firstName) {
        this.firstName = firstName;
    }

    public String getLevel() {
        return level.get();
    }


    public void setLevel(SimpleStringProperty level) {
        this.level = level;
    }

    public String getStatus() {
        return status.get();
    }


    public void setStatus(SimpleStringProperty status) {
        this.status = status;
    }
    
}
