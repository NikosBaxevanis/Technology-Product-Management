package Data;

    /**
     * Αντιπροσωπεύει ένα προϊόν κατηγορίας Κινητού Τηλεφώνου (SmartPhone).
     * Κληρονομεί τα βασικά χαρακτηριστικά από την κλάση  Product και τα επεκτείνει
     * εισάγοντας εξειδικευμένα χαρακτηριστικά όπως την ανάλυση της κάμερας και το χρώμα.
     */
    public class SmartPhone extends Product {

        /** Η ανάλυση της κάμερας του smartphone (π.χ. 50MP). */
        private String camRes;
        /** Το χρώμα της συσκευής (π.χ. Μαύρο). */
        private String color;


        /**
         * Κατασκευάζει ένα νέο αντικείμενο SmartPhone με όλα τα απαραίτητα στοιχεία.
         * Οι κοινές παράμετροι μεταβιβάζονται στον κατασκευαστή της υπερκλάσης Product.
         * * @param code         Ο μοναδικός κωδικός του κινητού.
         * @param title        Το λεκτικό/μοντέλο του κινητού.
         * @param manufacturer Ο κατασκευαστής του κινητού.
         * @param price        Η τιμή πώλησης.
         * @param quantity     Το διαθέσιμο απόθεμα.
         * @param camRes       Η ανάλυση της κάμερας.
         * @param color        Το χρώμα της συσκευής.
         */
        public SmartPhone(String code, String title, Manufacturer manufacturer, double price, int quantity, String camRes, String color) {
            super(code, title, manufacturer, price, quantity);
            this.camRes = camRes;
            this.color = color;
        }

        public String getCamRes() {
            return camRes;
        }

        public void setCamRes(String camRes) {
            this.camRes = camRes;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        /**
         * Επιστρέφει μια πλήρη και ευανάγνωστη περιγραφή των τεχνικών χαρακτηριστικών
         * και των στοιχείων πώλησης του smartphone.
         * * @return Μια μορφοποιημένη συμβολοσειρά με τα δεδομένα του κινητού.
         */
        @Override
        public String toString() {
            return "Κινητό [Κωδικός: " + getCode() + ", Λεκτικό: " + getTitle() + ", Κάμερα: " + camRes +
                    ", Χρώμα: " + color + ", Κατασκευαστής: " + (getManufacturer() != null ? getManufacturer().getName() : "N/A") +
                    ", Τιμή: " + getPrice() + "€, Ποσότητα: " + getQuantity() + "]";
        }

        /**
         * Μετατρέπει τα δεδομένα του κινητού σε μια γραμμή κειμένου με διαχωριστικό
         * το ελληνικό ερωτηματικό (;), ξεκινώντας με το αναγνωριστικό "PHONE", έτοιμη για αποθήκευση.
         * <p>
         * Μορφή: {@code PHONE;Κωδικός;Τίτλος;ΚωδικόςΚατασκευαστή;Τιμή;Ποσότητα;Κάμερα;Χρώμα}
         * </p>
         * * @return Μια συμβολοσειρά σε μορφή γραμμής CSV.
         */
        public String AsCsvLine() {
            return "PHONE;" + getCode() + ";" + getTitle() + ";" + getManufacturer().getCode() + ";" +
                    getPrice() + ";" + getQuantity() + ";" + this.camRes + ";" + this.color;
        }
    }
