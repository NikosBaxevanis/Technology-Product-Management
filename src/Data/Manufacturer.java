package Data;


/**
 * Αντιπροσωπεύει έναν κατασκευαστή προϊόντων (π.χ. Apple, Samsung).
 * Κρατάει τα βασικά στοιχεία επικοινωνίας και αναγνώρισης του κατασκευαστή
 * και παρέχει μεθόδους για τη διαχείριση και την εξαγωγή τους σε μορφή CSV.
 */
public class Manufacturer {

    /** Ο μοναδικός κωδικός αναγνώρισης του κατασκευαστή (π.χ. M01). */
    private String code;
    /** Η επωνυμία/όνομα της εταιρείας του κατασκευαστή. */
    private String name;
    /** Το email επικοινωνίας του κατασκευαστή. */
    private String email;


    /**
     * Κατασκευάζει ένα νέο αντικείμενο Κατασκευαστή με τα καθορισμένα στοιχεία.
     * * @param code  Ο μοναδικός κωδικός του κατασκευαστή.
     * @param name  Η επωνυμία του κατασκευαστή.
     * @param email Το email επικοινωνίας.
     */
    public Manufacturer(String code, String name, String email) {
        this.code = code;
        this.name = name;
        this.email = email;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    /**
     * Επιστρέφει μια ευανάγνωστη κειμενική αναπαράσταση του κατασκευαστή,
     * κατάλληλη για εμφάνιση στην κονσόλα ή σε αναφορές.
     * * @return Μια συμβολοσειρά με όλα τα attributes του κατασκευαστή.
     */
    @Override
    public String toString() {
        return "Κατασκευαστής [Κωδικός: " + code + ", Επωνυμία: " + name + ", Email: " + email + "]";
    }

    /**
     * Μετατρέπει τα δεδομένα του κατασκευαστή σε μια ενιαία γραμμή κειμένου
     * με διαχωριστικό το ελληνικό ερωτηματικό (;), έτοιμη για αποθήκευση σε αρχείο CSV.
     * <p>
     * Μορφή: {@code Κωδικός;Επωνυμία;Email}
     * </p>
     * * @return Μια συμβολοσειρά σε μορφή γραμμής CSV.
     */
    public String AsCsvLine() {
        return this.code + ";" + this.name + ";" + this.email;
    }
}
