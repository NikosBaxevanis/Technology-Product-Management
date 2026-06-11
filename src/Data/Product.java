package Data;


/**
 * Η κλάση που αποτελεί το πρότυπο για κάθε προϊόν του καταστήματος και που στην ουσία στηρίζεται η κληρονομικότητα.
 * Περιλαμβάνει τα κοινά χαρακτηριστικά όλων των προϊόντων, όπως κωδικό, τίτλο,
 * κατασκευαστή, τιμή και απόθεμα.
 */
public abstract class Product {
    /** Ο μοναδικός κωδικός του προϊόντος (π.χ. C01, P01). */
    private String code;
    /** Ο τίτλος ή η ονομασία του προϊόντος (π.χ. MacBook Pro). */
    private String title;
    /** Ο κατασκευαστής στον οποίο ανήκει το προϊόν. */
    private Manufacturer manufacturer;
    /** Η τιμή πώλησης του προϊόντος σε ευρώ. */
    private double price;
    /** Το τρέχον διαθέσιμο απόθεμα του προϊόντος στην αποθήκη. */
    private int quantity;

    /**
     * Αρχικοποιεί τα βασικά κοινά χαρακτηριστικά ενός προϊόντος.
     * * @param code         Ο μοναδικός κωδικός του προϊόντος.
     * @param title        Ο τίτλος/μοντέλο του προϊόντος.
     * @param manufacturer Το αντικείμενο του κατασκευαστή.
     * @param price        Η τιμή του προϊόντος (πρέπει να είναι θετική).
     * @param quantity     Η αρχική ποσότητα αποθέματος (πρέπει να είναι μη αρνητική).
     */
    public Product(String code, String title, Manufacturer manufacturer, double price, int quantity) {
        this.code = code;
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
