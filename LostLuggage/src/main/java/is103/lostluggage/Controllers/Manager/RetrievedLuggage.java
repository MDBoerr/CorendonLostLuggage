package is103.lostluggage.Controllers.Manager;

/**
 *
 * @author daron
 */
public class RetrievedLuggage {
        //inserts for ManagerTeruggebrachttebagage
    private int id;


    private String FormID;
    private String Date;
    private String Customer;
    private String Employee;
    private String Deliverer;

    public RetrievedLuggage(int id, String FormID, String Date, String Customer, String Employee, String Deliverer) {
        this.FormID = FormID;
        this.id = id;
        this.Date = Date;
        this.Customer = Customer;
        this.Employee = Employee;
        this.Deliverer = Deliverer;
    }    
    
        public String getFormID() {
        return FormID;
    }

    public void setFormID(String FormID) {
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
    
        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
