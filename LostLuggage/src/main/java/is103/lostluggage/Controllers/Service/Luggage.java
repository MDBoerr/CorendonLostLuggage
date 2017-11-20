
package is103.lostluggage.Controllers.Service;

/**
 * Doel:
 * @author gebruiker
 */
public class Luggage {
    private int id;
    private String label;
    private String merk;
    private String type;
    private String vlucht;
    private String luchthaven;
    private String kenmerken;
    private String reiziger;
    //private boolean schade;

    public Luggage(int id, String label, String merk, String type, String vlucht, String luchthaven, String kenmerken, String reiziger) {
        this.id = id;
        this.label = label;
        this.merk = merk;
        this.type = type;
        this.vlucht = vlucht;
        this.luchthaven = luchthaven;
        this.kenmerken = kenmerken;
        this.reiziger = reiziger;
    }

    
    
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the merk
     */
    public String getMerk() {
        return merk;
    }

    /**
     * @param merk the merk to set
     */
    public void setMerk(String merk) {
        this.merk = merk;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the vlucht
     */
    public String getVlucht() {
        return vlucht;
    }

    /**
     * @param vlucht the vlucht to set
     */
    public void setVlucht(String vlucht) {
        this.vlucht = vlucht;
    }

    /**
     * @return the luchthaven
     */
    public String getLuchthaven() {
        return luchthaven;
    }

    /**
     * @param luchthaven the luchthaven to set
     */
    public void setLuchthaven(String luchthaven) {
        this.luchthaven = luchthaven;
    }

    /**
     * @return the kenmerken
     */
    public String getKenmerken() {
        return kenmerken;
    }

    /**
     * @param kenmerken the kenmerken to set
     */
    public void setKenmerken(String kenmerken) {
        this.kenmerken = kenmerken;
    }

    /**
     * @return the reiziger
     */
    public String getReiziger() {
        return reiziger;
    }

    /**
     * @param reiziger the reiziger to set
     */
    public void setReiziger(String reiziger) {
        this.reiziger = reiziger;
    }

    
    
}
