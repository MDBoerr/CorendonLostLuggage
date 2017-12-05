package is103.lostluggage.Controllers.Manager;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author daron
 */
public class RetrievedLuggage {
    //inserts for ManagerTeruggebrachttebagage

    private SimpleStringProperty FormID, Date, Customer, Employee, Deliverer;

    public RetrievedLuggage(String FormID, String Date, String Customer, String Employee, String Deliverer) {

        this.FormID = new SimpleStringProperty(FormID);
        this.Date = new SimpleStringProperty(Date);
        this.Customer = new SimpleStringProperty(Customer);
        this.Employee = new SimpleStringProperty(Employee);
        this.Deliverer = new SimpleStringProperty(Deliverer);

    }

    public SimpleStringProperty getFormID() {
        return FormID;
    }

    public void setFormID(SimpleStringProperty FormID) {
        this.FormID = FormID;
    }

    public SimpleStringProperty getDate() {
        return Date;
    }

    public void setDate(SimpleStringProperty Date) {
        this.Date = Date;
    }

    public SimpleStringProperty getCustomer() {
        return Customer;
    }

    public void setCustomer(SimpleStringProperty Customer) {
        this.Customer = Customer;
    }

    public SimpleStringProperty getEmployee() {
        return Employee;
    }

    public void setEmployee(SimpleStringProperty Employee) {
        this.Employee = Employee;
    }

    public SimpleStringProperty getDeliverer() {
        return Deliverer;
    }

    public void setDeliverer(SimpleStringProperty Deliverer) {
        this.Deliverer = Deliverer;
    }

}
