package Data;

/**
 * Αντιπροσωπεύει ένα προϊόν κατηγορίας Ηλεκτρονικού Υπολογιστή (Personal Computer).
 * Κληρονομεί τα βασικά χαρακτηριστικά από την κλάση  Product και τα επεκτείνει
 * προσθέτοντας τεχνικές προδιαγραφές για τη μνήμη RAM και τον αποθηκευτικό χώρο (Storage).
 */
public class PersonalComputer extends Product {
    /** Το μέγεθος της μνήμης RAM σε Gigabytes (GB). */
    private int ramSize;
    /** Η χωρητικότητα του σκληρού δίσκου (ROM/Storage) σε Gigabytes (GB). */
    private int romSize;

    /**
     * Κατασκευάζει ένα νέο αντικείμενο PersonalComputer με όλα τα απαραίτητα στοιχεία.
     * Οι κοινές παράμετροι μεταβιβάζονται στον κατασκευαστή της υπερκλάσης  Product.
     * * @param code         Ο μοναδικός κωδικός του υπολογιστή.
     * @param title        Το λεκτικό/μοντέλο του υπολογιστή.
     * @param manufacturer Ο κατασκευαστής του υπολογιστή.
     * @param price        Η τιμή πώλησης.
     * @param quantity     Το διαθέσιμο απόθεμα.
     * @param ramSize      Το μέγεθος της RAM σε GB (πρέπει να είναι θετικός αριθμός).
     * @param romSize      Η χωρητικότητα του δίσκου σε GB (πρέπει να είναι θετικός αριθμός).
     */
    public PersonalComputer(String code, String title, Manufacturer manufacturer, double price, int quantity, int ramSize, int romSize) {
        super(code, title, manufacturer, price, quantity);
        this.ramSize = ramSize;
        this.romSize = romSize;
    }

    public int getRamSize() {
        return ramSize;
    }

    public void setRamSize(int ramSize) {
        this.ramSize = ramSize;
    }

    public int getRomSize() {
        return romSize;
    }

    public void setRomSize(int romSize) {
        this.romSize = romSize;
    }

    /**
     * Επιστρέφει μια πλήρη και ευανάγνωστη περιγραφή των τεχνικών χαρακτηριστικών
     * και των στοιχείων πώλησης του υπολογιστή.
     * * @return Μια μορφοποιημένη συμβολοσειρά με τα δεδομένα του υπολογιστή.
     */
    @Override
    public String toString() {
        return "Η/Υ [Κωδικός: " + getCode() + ", Λεκτικό: " + getTitle() + ", RAM: " + ramSize + "GB, Storage: " + romSize +
                "GB, Κατασκευαστής: " + (getManufacturer() != null ? getManufacturer().getName() : "N/A") +
                ", Τιμή: " + getPrice() + "€, Ποσότητα: " + getQuantity() + "]";
    }

    /**
     * Μετατρέπει τα δεδομένα του υπολογιστή σε μια γραμμή κειμένου με διαχωριστικό
     * το ελληνικό ερωτηματικό (;), ξεκινώντας με το αναγνωριστικό "PC", έτοιμη για αποθήκευση.
     * <p>
     * Μορφή: {@code PC;Κωδικός;Τίτλος;ΚωδικόςΚατασκευαστή;Τιμή;Ποσότητα;RAM;Storage}
     * </p>
     * * @return Μια συμβολοσειρά σε μορφή γραμμής CSV.
     */
    public String AsCsvLine() {
        return "PC;" + getCode() + ";" + getTitle() + ";" + getManufacturer().getCode() + ";" +
                getPrice() + ";" + getQuantity() + ";" + this.ramSize + ";" + this.romSize;
    }
}
