package is103.lostluggage.Controllers.Manager;

/**
 *
 * @author daron
 */
public class customerdetails {
    private int formid;
    private String customerid, deivererid, emailid, adresid, dateid;

    public customerdetails(int formid, String customerid, String deivererid, String emailid, String adresid, String dateid) {
        this.formid = formid;
        this.customerid = customerid;
        this.deivererid = deivererid;
        this.emailid = emailid;
        this.adresid = adresid;
        this.dateid = dateid;
    }

    public int getFormid() {
        return formid;
    }

    public void setFormid(int formid) {
        this.formid = formid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getDeivererid() {
        return deivererid;
    }

    public void setDeivererid(String deivererid) {
        this.deivererid = deivererid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getAdresid() {
        return adresid;
    }

    public void setAdresid(String adresid) {
        this.adresid = adresid;
    }

    public String getDateid() {
        return dateid;
    }

    public void setDateid(String dateid) {
        this.dateid = dateid;
    }

}
