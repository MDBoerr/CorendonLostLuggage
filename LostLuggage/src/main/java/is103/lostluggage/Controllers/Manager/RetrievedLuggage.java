package is103.lostluggage.Controllers.Manager;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author daron
 */
public class RetrievedLuggage {
    //inserts for ManagerTeruggebrachttebagage

    private int FormID;
    private String Date, Customer, Employee, Deliverer;

    public RetrievedLuggage(int FormID, String Date, String Customer, String Employee, String Deliverer) {

        this.FormID = FormID;
        this.Date = Date;
        this.Customer = Customer;
        this.Employee = Employee;
        this.Deliverer = Deliverer;

    }

    public int getFormID() {
        return FormID;
    }

    public void setFormID(int FormID) {
        this.FormID = FormID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String Customer) {
        this.Customer = Customer;
    }

    public String getEmployee() {
        return Employee;
    }

    public void setEmployee(String Employee) {
        this.Employee = Employee;
    }

    public String getDeliverer() {
        return Deliverer;
    }

    public void setDeliverer(String Deliverer) {
        this.Deliverer = Deliverer;
    }

}
