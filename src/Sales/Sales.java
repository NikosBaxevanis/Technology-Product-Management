package Sales;

import Data.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sales {

    private Product product;
    private int quantitySold;
    private LocalDateTime saleDate;

    public Sales(Product product, int quantitySold, LocalDateTime saleDate) {
        this.product = product;
        this.quantitySold = quantitySold;
        this.saleDate = saleDate;
    }

    public Product getProduct() { return product; }
    public int getQuantitySold() { return quantitySold; }
    public LocalDateTime getSaleDate() { return saleDate; }


    public double getTotalPrice() {
        return this.product.getPrice() * this.quantitySold;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "Πώληση: " + product.getTitle() +
                " | Τεμάχια: " + quantitySold +
                " | Συνολικό Κόστος: " + String.format("%.2f", getTotalPrice()) + "€" +
                " | Ημ/νία: " + saleDate.format(formatter);
    }

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


