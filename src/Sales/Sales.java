package Sales;

import Data.Product;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Αντιπροσωπεύει μια καταχωρημένη πώληση προϊόντος στο κατάστημα.
 **/
public class Sales {

    /** Το προϊόν που πουλήθηκε. */
    private Product product;
    /** Η ποσότητα των τεμαχίων που αγοράστηκαν. */
    private int quantitySold;
    /** Η ημερομηνία και η ώρα πραγματοποίησης της πώλησης. */
    private LocalDateTime saleDate;

    /**
     * Κατασκευάζει ένα νέο αντικείμενο Πώλησης με τα προκαθορισμένα στοιχεία του.
     * * @param product    Το προϊόν προς πώληση.
     * @param quantitySold Τα τεμάχια που πουλήθηκαν.
     * @param saleDate     Η χρονική στιγμή της συναλλαγής.
     */
    public Sales(Product product, int quantitySold, LocalDateTime saleDate) {
        this.product = product;
        this.quantitySold = quantitySold;
        this.saleDate = saleDate;
    }

    public Product getProduct() { return product; }
    public int getQuantitySold() { return quantitySold; }
    public LocalDateTime getSaleDate() { return saleDate; }

    /**
     * Υπολογίζει το συνολικό κόστος της συγκεκριμένης πώλησης.
     * * @return Το γινόμενο της τιμής του προϊόντος επί την ποσότητα που πουλήθηκε.
     */
    public double getTotalPrice() {
        return this.product.getPrice() * this.quantitySold;
    }


    /**
     * Επιστρέφει μια ευανάγνωστη προβολή της πώλησης με μορφοποιημένη ημερομηνία
     * και το συνολικό κόστος σε ευρώ.
     * * @return Μια συμβολοσειρά με τα πλήρη στοιχεία της πώλησης.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "Πώληση: " + product.getTitle() +
                " | Τεμάχια: " + quantitySold +
                " | Συνολικό Κόστος: " + String.format("%.2f", getTotalPrice()) + "€" +
                " | Ημ/νία: " + saleDate.format(formatter);
    }


    /**
     * Μετατρέπει τα στοιχεία της πώλησης σε μια γραμμή κειμένου κατάλληλη για αρχεία CSV.
     * Τα πεδία διαχωρίζονται με το χαρακτήρα ελληνικό ερωτηματικό (;).
     * <p>
     * Μορφή: {@code ΚωδικόςΠροϊόντος;Ποσότητα;Ημερομηνία}
     * </p>
     * * @return Μια συμβολοσειρά σε μορφή CSV γραμμής.
     */
    public String AsCsvLine() {
        return getProduct().getCode() + ";" + this.quantitySold + ";" + this.saleDate;
    }
}


/*
Ο λόγος που δεν χρησιμοποιούμε Setters:

Μια πώληση είναι ένα ιστορικό γεγονός.

Μια απόδειξη λιανικής αποτυπώνει τι συνέβη μια συγκεκριμένη χρονική στιγμή. Από τη στιγμή που εκδόθηκε, δεν αλλάζει.

Αν βάζαμε setSaleDate(), θα μπορούσε κάποιος κατά λάθος να αλλάξει την ημερομηνία μιας παλιάς πώλησης.

Αν βάζαμε setQuantitySold(), θα άλλαζαν τα έσοδα στα στατιστικά, χωρίς όμως να ενημερωθεί το πραγματικό απόθεμα στην αποθήκη, δημιουργώντας "λογιστικό" χάος.

Αφήνοντας μόνο Getters, διασφαλίζουμε ότι η πώληση, αφού δημιουργηθεί με τον Constructor, θα παραμείνει "κλειδωμένη" και ασφαλής από τυχαία λάθη στον υπόλοιπο κώδικα της MainP.*/


