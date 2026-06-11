package UI;

import Data.Manufacturer;
import Data.PersonalComputer;
import Data.Product;
import Data.SmartPhone;
import Sales.Sales;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Η κεντρική κλάση ελέγχου της εφαρμογής (Controller / User Interface).
 * Διαχειρίζεται το μενού επιλογών, τη φόρτωση και αποθήκευση δεδομένων σε αρχεία CSV,
 * καθώς και τις CRUD λειτουργίες για προϊόντα, κατασκευαστές και πωλήσεις.
 */
public  class MainP {

    /**
     * Δημιουργεί ένα νέο αντικείμενο MainP και αρχικοποιεί τον Scanner
     * για την ανάγνωση από το σύστημα εισόδου (πληκτρολόγιο).
     */
    public MainP() {
        this.Keyb = new Scanner(System.in);
    }

    /** Λίστα που αποθηκεύει όλα τα προϊόντα (PersonalComputers και SmartPhones) του καταστήματος. */
    private static ArrayList<Product> allProducts = new ArrayList<>();
    /** Λίστα που αποθηκεύει όλους τους καταχωρημένους κατασκευαστές. */
    private static ArrayList<Manufacturer> manufacturers = new ArrayList<>();
    /** Λίστα που αποθηκεύει το ιστορικό όλων των πωλήσεων που έχουν πραγματοποιηθεί. */
    private static ArrayList<Sales> allSales = new ArrayList<>();

    Scanner Keyb;
    /** Ακολουθούν τα ονόματα των αρχείων csv για την αποθήκευση των κατασκευαστών, των προϊόντων και των πωλήσεων/στατιστικών **/
    private final static String ManufacturerFile = "Manufacturers.csv";
    private final static String ProductFile = "Products.csv";
    private final static String SaleFile = "Sales.csv";

    /**
     * Αποθηκεύει όλα τα τρέχοντα δεδομένα της εφαρμογής (κατασκευαστές, προϊόντα, πωλήσεις)
     * στα αντίστοιχα αρχεία CSV. Η κλήση της γίνεται αυτόματα κατά την έξοδο από το κεντρικό μενού.
     */
    public void SaveData() {
        // 1. Αποθήκευση Κατασκευαστών
        try (PrintWriter Pr = new PrintWriter(ManufacturerFile)) {
            for (Manufacturer m : manufacturers) {
                Pr.println(m.AsCsvLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Cannot open output file: " + ManufacturerFile);
        }

        // 2. Αποθήκευση Προϊόντων
        try (PrintWriter Pr = new PrintWriter(ProductFile)) {
            for (Product p : allProducts) {
                if (p instanceof PersonalComputer) {
                    Pr.println(((PersonalComputer) p).AsCsvLine());
                } else if (p instanceof SmartPhone) {
                    Pr.println(((SmartPhone) p).AsCsvLine());
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Cannot open output file: " + ProductFile);
        }

        // 3. Αποθήκευση Πωλήσεων
        try (PrintWriter Pr = new PrintWriter(SaleFile)) {
            for (Sales s : allSales) {
                Pr.println(s.AsCsvLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Cannot open output file: " + SaleFile);
        }
        System.out.println("Data Successfully Saved to CSV files...");
    }

    /**
     * Φορτώνει τα δεδομένα από τα αρχεία CSV (Manufacturers, Products, Sales)
     * κατά την εκκίνηση της εφαρμογής. Αν κάποιο αρχείο δεν βρεθεί, εμφανίζει
     * ενημερωτικό μήνυμα χωρίς να διακόπτεται η ροή του προγράμματος.
     */
    public void LoadData() {
        String Line = null;
        String[] FMat;

        // 1. Φόρτωση Κατασκευαστών
        try (BufferedReader Br = new BufferedReader(new InputStreamReader(new FileInputStream(ManufacturerFile)))) {
            while ((Line = Br.readLine()) != null) {
                FMat = Line.split(";");
                if (FMat.length == 3) {
                    manufacturers.add(new Manufacturer(FMat[0], FMat[1], FMat[2]));
                }
            }
            System.out.println("Manufacturers Loaded...");
        } catch (FileNotFoundException ex) {
            System.out.println(ManufacturerFile + " not found. Will be created on exit.");
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Data Error in Manufacturers...");
        }

        // 2. Φόρτωση Προϊόντων (PC & Phones)
        try (BufferedReader Br = new BufferedReader(new InputStreamReader(new FileInputStream(ProductFile)))) {
            while ((Line = Br.readLine()) != null) {
                FMat = Line.split(";");
                String type = FMat[0];
                String code = FMat[1];
                String title = FMat[2];
                String mCode = FMat[3];
                double price = Double.parseDouble(FMat[4]);
                int qty = Integer.parseInt(FMat[5]);

                Manufacturer m = findManufacturer(mCode);
                if (m == null) continue; // Προσπέραση αν δεν υπάρχει ο κατασκευαστής

                if (type.equals("PC")) {
                    int ram = Integer.parseInt(FMat[6]);
                    int storage = Integer.parseInt(FMat[7]);
                    allProducts.add(new PersonalComputer(code, title, m, price, qty, ram, storage));
                } else if (type.equals("PHONE")) {
                    String cam = FMat[6];
                    String color = FMat[7];
                    allProducts.add(new SmartPhone(code, title, m, price, qty, cam, color));
                }
            }
            System.out.println("Products Loaded...");
        } catch (FileNotFoundException ex) {
            System.out.println(ProductFile + " not found. Will be created on exit.");
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Data Error in Products...");
        }

        // 3. Φόρτωση Πωλήσεων
        try (BufferedReader Br = new BufferedReader(new InputStreamReader(new FileInputStream(SaleFile)))) {
            while ((Line = Br.readLine()) != null) {
                FMat = Line.split(";");
                if (FMat.length == 3) {
                    String pCode = FMat[0];
                    int qtySold = Integer.parseInt(FMat[1]);

                    try {
                        LocalDateTime date = LocalDateTime.parse(FMat[2]);
                        Product p = findProduct(pCode);
                        if (p != null) {
                            allSales.add(new Sales(p, qtySold, date));
                        }
                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Σφάλμα μορφής ημερομηνίας στην πώληση του προϊόντος: " + pCode);
                    }
                }
            }
            System.out.println("Sales History Loaded...");
        } catch (FileNotFoundException ex) {
            System.out.println(SaleFile + " not found.");
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Data Error in Sales...");
        }
    }





    /**
     * Η κύρια μέθοδος (main) εκκίνησης της εφαρμογής. Αρχικοποιεί τον ελεγκτή,
     * φορτώνει τα υπάρχοντα δεδομένα, εισάγει εικονικά (dummy) δεδομένα αν οι λίστες
     * είναι άδειες και εκκινεί το κεντρικό μενού.
     */
    public static void main(String[] args) {

        MainP MP = new MainP();
        MP.LoadData();

        if (manufacturers.isEmpty() && allProducts.isEmpty()) {

            Manufacturer m1 = new Manufacturer("M01", "Apple", "info@apple.com");
            Manufacturer m2 = new Manufacturer("M02", "Samsung", "info@samsung.com");
            manufacturers.add(m1);
            manufacturers.add(m2);

            allProducts.add(new PersonalComputer("C01", "MacBook Pro", m1, 1999.99, 10, 16, 512));
            allProducts.add(new SmartPhone("P01", "Galaxy S24", m2, 999.00, 15, "50MP", "Μαύρο"));

        }
        MP.Menu();
    }

    /**
     * Παγώνει προσωρινά τη ροή του προγράμματος και περιμένει από τον χρήστη
     * να πιέσει το πλήκτρο 'Enter' για να συνεχίσει.
     */
    void Pause() {
        System.out.print("\n\nΠιέστε <Enter> για συνέχεια  ");
        this.Keyb.nextLine();
    }

    /**
     * Καθαρίζει την οθόνη της κονσόλας.
     */
    public void Cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException var2) {
        } catch (InterruptedException var3) {
            Thread.currentThread().interrupt();
        }
    }


    /**
     * Διαβάζει έναν ακέραιο αριθμό με ασφάλεια. Αν ο χρήστης εισάγει άκυρα δεδομένα,
     * τον προτρέπει να ξαναπροσπαθήσει χωρίς να crashάρει η εφαρμογή.
     * * @return Ο έγκυρος ακέραιος που πληκτρολόγησε ο χρήστης.
     */
    private int readSafeInt() {
        while (true) {
            try {
                int num = Keyb.nextInt();
                Keyb.nextLine(); // Καθαρισμός του buffer
                return num;
            } catch (java.util.InputMismatchException e) {
                System.out.print("Μη έγκυρος αριθμός! Παρακαλώ εισάγετε ακέραιο: ");
                Keyb.nextLine(); // Καθαρισμός του buffer από το λάθος input
            }
        }
    }

    /**
     * Διαβάζει έναν δεκαδικό αριθμό με ασφάλεια.
     * * @return Ο έγκυρος δεκαδικός που πληκτρολόγησε ο χρήστης.
     */
    private double readSafeDouble() {
        while (true) {
            try {
                double num = Keyb.nextDouble();
                Keyb.nextLine(); // Καθαρισμός του buffer
                return num;
            } catch (java.util.InputMismatchException e) {
                System.out.print("Μη έγκυρος αριθμός! Παρακαλώ εισάγετε δεκαδικό (π.χ. 12,50): ");
                Keyb.nextLine(); // Καθαρισμός του buffer
            }
        }
    }


    /**
     * Αυτό είναι το κεντρικό μενού της εφαρμογής και δρομολογεί τον χρήστη
     * στα αντίστοιχα υπομενού ανάλογα με την επιλογή του.
     * Η μέθοδος επιλογής των υπομενού έχει κατασκευαστή με switch.
     */
    public  void Menu() {
        int ch;
        do {
            this.Cls();
            System.out.println("---Καλώς ήρθατε στο ηλεκτρονικό μας κατάστημα---");
            System.out.println("\n--- ΔΙΑΧΕΙΡΙΣΗ & MENU ΚΑΤΑΣΤΗΜΑΤΟΣ ΤΕΧΝΟΛΟΓΙΑΣ ---\n");
            System.out.println("[1]...Διαχείριση Ηλεκτρονικών Υπολογιστών");
            System.out.println("[2]...Διαχείριση Κινητών Τηλεφώνων");
            System.out.println("[3]...Διαχείριση Κατασκευαστών");
            System.out.println("[4]...Λειτουργίες Πωλήσεων & Στατιστικών");
            System.out.println("[0]...Έ ξ ο δ ο ς");
            System.out.print("Επιλογή : ");
            ch = this.Keyb.nextInt();
            this.Keyb.nextLine();
            switch (ch) {
                case 1:
                    this.menuPersonalComputers();
                    break;
                case 2:
                    this.menuSmartPhones();
                    break;
                case 3:
                    this.menuManufacturers();
                    break;
                case 4:
                    this.menuSalesAndStats();
                    break;
                case 0:
                    this.SaveData();
                    System.out.println("Πραγματοποιήσατε έξοδο από το κατάστημα μας. Καλή συνέχεια.");
                    break;
                default:
                    System.out.println("Λάθος επιλογή. Παρακαλούμε προσπαθήστε ξανά.");
                    this.Pause();
            }
        } while(ch != 0);
    }

    /**
     * Διαχειρίζεται το υπομενού για τις CRUD λειτουργίες των Κατασκευαστών.
     * Περιλαμβάνει έλεγχο εγκυρότητας επιλογών με readSafeInt και έλεγχο μοναδικότητας κωδικού.
     */
    private void menuManufacturers() {
        int ch;
        do {
            this.Cls();
            System.out.println("\n-- Διαχείριση Κατασκευαστών --");
            System.out.println("\n-- Πως θα θέλατε να διαχειριστείτε τον κωδικό κατασκευαστή; --");
            System.out.println("[1]...Εισαγωγή κατασκευαστή");
            System.out.println("[2]...Διόρθωση κατασκευαστή");
            System.out.println("[3]...Διαγραφή κατασκευαστή ");
            System.out.println("[4]...Προβολή Όλων των κατασκευαστών");
            System.out.println("[0]...Επιστροφή στο αρχικό Menu");
            System.out.print("Επιλογή : ");
            ch = readSafeInt();

            if (ch == 1) {
                System.out.print("Κωδικός: ");
                String code = Keyb.nextLine();

                // (Validation)
                if (findManufacturer(code) != null) {
                    System.out.println("Σφάλμα: Αυτός ο κωδικός κατασκευαστή υπάρχει ήδη!");
                    this.Pause();
                    continue;
                }

                System.out.print("Επωνυμία: ");
                String name = Keyb.nextLine();
                System.out.print("Email: ");
                String email = Keyb.nextLine();
                manufacturers.add(new Manufacturer(code, name, email));
                System.out.println("Ο κατασκευαστής προστέθηκε!");
                this.Pause();

            } else if (ch == 2) {
                System.out.print("Δώστε κωδικό για διόρθωση: ");
                String code = Keyb.nextLine();
                Manufacturer m = findManufacturer(code);
                if (m != null) {
                    System.out.print("Νέα Επωνυμία (" + m.getName() + "): ");
                    m.setName(Keyb.nextLine());
                    System.out.print("Νέο Email (" + m.getEmail() + "): ");
                    m.setEmail(Keyb.nextLine());
                    System.out.println("Τα στοιχεία ενημερώθηκαν!");
                } else {
                    System.out.println("Δεν βρέθηκε!");
                }
                this.Pause();

            } else if (ch == 3) {
                System.out.print("Δώστε κωδικό για διαγραφή: ");
                String code = Keyb.nextLine();
                Manufacturer m = findManufacturer(code);
                if (m != null) {
                    manufacturers.remove(m);
                    System.out.println("Διαγράφηκε επιτυχώς!");
                } else {
                    System.out.println("Δεν βρέθηκε!");
                }
                this.Pause();

            } else if (ch == 4) {
                System.out.println("\n--- ΛΙΣΤΑ ΚΑΤΑΣΚΕΥΑΣΤΩΝ ---");
                for (Manufacturer m : manufacturers) {
                    System.out.println(m);
                }
                this.Pause();
            }
        } while (ch != 0);
    }

    /**
     * Αναζητά έναν κατασκευαστή στη λίστα με βάση τον μοναδικό του κωδικό.
     * * @param code Ο κωδικός του κατασκευαστή προς αναζήτηση.
     * @return Το αντικείμενο Manufacturer αν βρεθεί, αλλιώς null.
     */
    private   Manufacturer findManufacturer(String code) {
        for (Manufacturer m : manufacturers) if (m.getCode().equalsIgnoreCase(code)) return m;
        return null;
    }


    /**
     * Διαχειρίζεται το υπομενού για τις CRUD λειτουργίες των Ηλεκτρονικών Υπολογιστών.
     * Εφαρμόζει ασφαλείς μεθόδους ανάγνωσης (readSafeInt, readSafeDouble) και ελέγχους εγκυρότητας.
     */
    private void menuPersonalComputers () {
        int ch;
        do {
            this.Cls(); // Προσθήκη καθαρισμού οθόνης για ομοιομορφία
            System.out.println("\n -- Διαχείριση Ηλεκτρονικών Υπολογιστών --");
            System.out.println("\n-- Πως θα θέλατε να διαχειριστείτε τον προσωπικό σας υπολογιστή; --");
            System.out.println("[1]...Εισαγωγή υπολογιστή");
            System.out.println("[2]...Διόρθωση υπολογιστή");
            System.out.println("[3]...Διαγραφή υπολογιστή ");
            System.out.println("[4]...Προβολή Όλων των υπολογιστών");
            System.out.println("[0]...Επιστροφή στο αρχικό Menu");
            System.out.print("Επιλογή : ");
            ch = readSafeInt();

            if (ch == 1) {
                System.out.print("Κωδικός: ");
                String code = Keyb.nextLine();

                // Validation: Έλεγχος μοναδικότητας κωδικού προϊόντος
                if (findProduct(code) != null) {
                    System.out.println("Σφάλμα: Αυτός ο κωδικός προϊόντος υπάρχει ήδη!");
                    this.Pause();
                    continue;
                }

                System.out.print("Λεκτικό: ");
                String title = Keyb.nextLine();
                System.out.print("Κωδικός Κατασκευαστή: ");
                String mCode = Keyb.nextLine();
                Manufacturer m = findManufacturer(mCode);
                if (m == null) {
                    System.out.println("Σφάλμα: Δεν υπάρχει αυτός ο κατασκευαστής!");
                    this.Pause();
                    continue;
                }

                System.out.print("Τιμή: ");
                double price = readSafeDouble();
                System.out.print("Ποσότητα: ");
                int qty = readSafeInt();
                System.out.print("Μέγεθος RAM (GB): ");
                int ram = readSafeInt();
                System.out.print("Χωρητικότητα Σκληρού (GB): ");
                int storage = readSafeInt();

                // Validation: Έλεγχος για θετικές/μη αρνητικές τιμές
                if (price <= 0 || qty < 0 || ram <= 0 || storage <= 0) {
                    System.out.println("Σφάλμα: Οι αριθμητικές τιμές (τιμή, RAM, αποθηκευτικός χώρος) πρέπει να είναι θετικές και η ποσότητα μη αρνητική!");
                    this.Pause();
                    continue;
                }

                allProducts.add(new PersonalComputer(code, title, m, price, qty, ram, storage));
                System.out.println("Ο υπολογιστής προστέθηκε!");
                this.Pause();

            } else if (ch == 2) {
                System.out.print("Δώστε κωδικό Η/Υ για διόρθωση: ");
                String code = Keyb.nextLine();
                Product p = findProduct(code);

                if (p instanceof PersonalComputer) {
                    PersonalComputer c = (PersonalComputer) p;
                    System.out.print("Νέο Λεκτικό: ");
                    c.setTitle(Keyb.nextLine());

                    System.out.print("Νέα Τιμή: ");
                    double newPrice = readSafeDouble(); // Αλλαγή σε readSafeDouble

                    System.out.print("Νέα Ποσότητα: ");
                    int newQty = readSafeInt(); // Αλλαγή σε readSafeInt

                    System.out.print("Νέα RAM: ");
                    int newRam = readSafeInt(); // Αλλαγή σε readSafeInt

                    System.out.print("Νέο Storage: ");
                    int newStorage = readSafeInt(); // Αλλαγή σε readSafeInt

                    // Validation: Έλεγχος τιμών και στη διόρθωση
                    if (newPrice <= 0 || newQty < 0 || newRam <= 0 || newStorage <= 0) {
                        System.out.println("Σφάλμα: Μη έγκυρες τιμές! Η διόρθωση ακυρώθηκε.");
                    } else {
                        c.setPrice(newPrice);
                        c.setQuantity(newQty);
                        c.setRamSize(newRam);
                        c.setRomSize(newStorage);
                        System.out.println("Ενημερώθηκε!");
                    }
                } else {
                    System.out.println("Δεν βρέθηκε ηλεκτρονικός υπολογιστής με αυτόν τον κωδικό!");
                }
                this.Pause();

            } else if (ch == 3) {
                System.out.print("Δώστε κωδικό για διαγραφή: ");
                String code = Keyb.nextLine();
                Product p = findProduct(code);
                if (p instanceof PersonalComputer) {
                    allProducts.remove(p);
                    System.out.println("Διαγράφηκε!");
                } else {
                    System.out.println("Δεν βρέθηκε!");
                }
                this.Pause();

            } else if (ch == 4) {
                System.out.println("\n--- ΛΙΣΤΑ ΗΛΕΚΤΡΟΝΙΚΩΝ ΥΠΟΛΟΓΙΣΤΩΝ ---");
                boolean found = false;
                for (Product p : allProducts) {
                    if (p instanceof PersonalComputer) {
                        System.out.println(p);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("Δεν υπάρχουν καταχωρημένοι υπολογιστές.");
                }
                this.Pause();
            }
        } while (ch != 0);
    }




    /**
     * Διαχειρίζεται το υπομενού για τις CRUD λειτουργίες των SmartPhones.
     * Χρησιμοποιεί readSafeInt και readSafeDouble για την αποφυγή σφαλμάτων εισαγωγής
     * και ελέγχει την εγκυρότητα των δεδομένων (τιμές, ποσότητες, κωδικούς).
     */
    private void menuSmartPhones() {
        int ch;
        do {
            this.Cls(); // Προσθήκη καθαρισμού οθόνης για ομοιομορφία
            System.out.println("\n-- Διαχείριση Κινητών Τηλεφώνων --");
            System.out.println("\n-- Πως θα θέλατε να διαχειριστείτε το SmartPhone σας; --");
            System.out.println("[1]...Εισαγωγή SmartPhone");
            System.out.println("[2]...Διόρθωση SmartPhone");
            System.out.println("[3]...Διαγραφή SmartPhone ");
            System.out.println("[4]...Προβολή Όλων των SmartPhones");
            System.out.println("[0]...Επιστροφή στο αρχικό Menu");
            System.out.print("Επιλογή : ");
            ch = readSafeInt();

            if (ch == 1) {
                System.out.print("Κωδικός: ");
                String code = Keyb.nextLine();

                // Validation: Έλεγχος μοναδικότητας κωδικού προϊόντος
                if (findProduct(code) != null) {
                    System.out.println("Σφάλμα: Αυτός ο κωδικός προϊόντος υπάρχει ήδη!");
                    this.Pause();
                    continue;
                }

                System.out.print("Λεκτικό: ");
                String title = Keyb.nextLine();

                System.out.print("Κωδικός Κατασκευαστή: ");
                String mCode = Keyb.nextLine();
                Manufacturer m = findManufacturer(mCode);
                if (m == null) {
                    System.out.println("Σφάλμα: Δεν υπάρχει αυτός ο κατασκευαστής!");
                    this.Pause();
                    continue;
                }

                System.out.print("Τιμή: ");
                double price = readSafeDouble(); // Χρήση safe μεθόδου

                System.out.print("Ποσότητα: ");
                int qty = readSafeInt(); // Χρήση safe μεθόδου

                System.out.print("Ανάλυση Κάμερας: ");
                String cam = Keyb.nextLine();

                System.out.print("Χρώμα: ");
                String color = Keyb.nextLine();

                // Validation: Έλεγχος για θετικές/μη αρνητικές τιμές
                if (price <= 0 || qty < 0) {
                    System.out.println("Σφάλμα: Η τιμή πρέπει να είναι θετική και η ποσότητα μη αρνητική!");
                    this.Pause();
                    continue;
                }

                allProducts.add(new SmartPhone(code, title, m, price, qty, cam, color));
                System.out.println("Το κινητό προστέθηκε!");
                this.Pause();

            } else if (ch == 2) {
                System.out.print("Δώστε κωδικό κινητού για διόρθωση: ");
                String code = Keyb.nextLine();
                Product p = findProduct(code);

                if (p instanceof SmartPhone) {
                    SmartPhone s = (SmartPhone) p;
                    System.out.print("Νέο Λεκτικό: ");
                    s.setTitle(Keyb.nextLine());

                    System.out.print("Νέα Τιμή: ");
                    double newPrice = readSafeDouble(); // Χρήση safe μεθόδου

                    System.out.print("Νέα Ποσότητα: ");
                    int newQty = readSafeInt(); // Χρήση safe μεθόδου

                    System.out.print("Νέα Κάμερα: ");
                    s.setCamRes(Keyb.nextLine());

                    System.out.print("Νέο Χρώμα: ");
                    s.setColor(Keyb.nextLine());

                    // Validation: Έλεγχος τιμών και στη διόρθωση
                    if (newPrice <= 0 || newQty < 0) {
                        System.out.println("Σφάλμα: Μη έγκυρες τιμές! Η διόρθωση ακυρώθηκε.");
                    } else {
                        s.setPrice(newPrice);
                        s.setQuantity(newQty);
                        System.out.println("Ενημερώθηκε!");
                    }
                } else {
                    System.out.println("Δεν βρέθηκε SmartPhone με αυτόν τον κωδικό!");
                }
                this.Pause();

            } else if (ch == 3) {
                System.out.print("Δώστε κωδικό για διαγραφή: ");
                String code = Keyb.nextLine();
                Product p = findProduct(code);
                if (p instanceof SmartPhone) {
                    allProducts.remove(p);
                    System.out.println("Διαγράφηκε!");
                } else {
                    System.out.println("Δεν βρέθηκε!");
                }
                this.Pause();

            } else if (ch == 4) {
                System.out.println("\n--- ΛΙΣΤΑ ΚΙΝΗΤΩΝ ΤΗΛΕΦΩΝΩΝ ---");
                boolean found = false;
                for (Product p : allProducts) {
                    if (p instanceof SmartPhone) {
                        System.out.println(p);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("Δεν υπάρχουν καταχωρημένα SmartPhones.");
                }
                this.Pause();
            }
        } while (ch != 0);
    }

    /**
     * Αναζητά ένα προϊόν στη γενική λίστα προϊόντων με βάση τον κωδικό του.
     * * @param code Ο κωδικός του προϊόντος προς αναζήτηση.
     * @return Το αντικείμενο Product αν βρεθεί, αλλιώς null.
     */
    private Product findProduct(String code) {
        for (Product p : allProducts) {
            if (p.getCode().equalsIgnoreCase(code)) return p;
        }
        return null;
    }

    /**
     * Διαχειρίζεται το μενού πωλήσεων και στατιστικών. Παρέχει επιλογές για:
     * 1. Καταχώρηση νέας πώλησης με έλεγχο διαθέσιμου αποθέματος.
     * 2. Προβολή του ιστορικού πωλήσεων.
     * 3. Υπολογισμό και εμφάνιση εσόδων ανά κατηγορία συσκευής (PC / SmartPhone).
     * 4. Υπολογισμό και εμφάνιση εσόδων ανά κατασκευαστή.
     */
    private void menuSalesAndStats() {
        int ch;
        do {
            this.Cls();
            System.out.println("\n-- Λειτουργίες Πωλήσεων & Στατιστικών --");
            System.out.println("[1]...Καταχώρηση Νέας Πώλησης");
            System.out.println("[2]...Προβολή Ιστορικού Πωλήσεων");
            System.out.println("[3]...Στατιστικά ανά Κατηγορία Συσκευής");
            System.out.println("[4]...Στατιστικά ανά Κατασκευαστή");
            System.out.println("[0]...Επιστροφή στο αρχικό Menu");
            System.out.print("Επιλογή : ");
            ch = this.Keyb.nextInt();
            this.Keyb.nextLine();

            if (ch == 1) {

                System.out.print("Δώστε τον κωδικό του προϊόντος προς πώληση: ");
                String code = Keyb.nextLine();
                Product p = findProduct(code);

                if (p == null) {
                    System.out.println("Το προϊόν δεν βρέθηκε!");
                    this.Pause();
                    continue;
                }

                System.out.println("Προϊόν: " + p.getTitle() + " | Διαθέσιμο Απόθεμα: " + p.getQuantity());
                System.out.print("Ποσότητα προς πώληση: ");
                int qtyToSell = Keyb.nextInt();
                Keyb.nextLine();

                if (qtyToSell <= 0) {
                    System.out.println("Λάθος ποσότητα!");
                } else if (qtyToSell > p.getQuantity()) {
                    System.out.println("Σφάλμα: Δεν υπάρχει αρκετό απόθεμα. Διαθέσιμα: " + p.getQuantity());
                } else {
                    p.setQuantity(p.getQuantity() - qtyToSell);

                    allSales.add(new Sales(p, qtyToSell, LocalDateTime.now()));

                    System.out.println("Η πώληση ολοκληρώθηκε επιτυχώς!");
                }
                this.Pause();

            } else if (ch == 2) {

                System.out.println("\n--- ΙΣΤΟΡΙΚΟ ΠΩΛΗΣΕΩΝ ---");
                if (allSales.isEmpty()) {
                    System.out.println("Δεν έχει γίνει καμία πώληση ακόμα.");
                } else {
                    for (Sales s : allSales) {
                        System.out.println(s);
                    }
                }
                this.Pause();

            } else if (ch == 3) {

                int pcQty = 0, phoneQty = 0;
                double pcRevenue = 0, phoneRevenue = 0;

                for (Sales s : allSales) {
                    if (s.getProduct() instanceof PersonalComputer) {
                        pcQty += s.getQuantitySold();
                        pcRevenue += s.getTotalPrice();
                    } else if (s.getProduct() instanceof SmartPhone) {
                        phoneQty += s.getQuantitySold();
                        phoneRevenue += s.getTotalPrice();
                    }
                }

                System.out.println("\n--- ΣΤΑΤΙΣΤΙΚΑ ΑΝΑ ΚΑΤΗΓΟΡΙΑ ---");
                System.out.printf("Ηλεκτρονικοί Υπολογιστές -> Τεμάχια: %d, Έσοδα: %.2f€\n", pcQty, pcRevenue);
                System.out.printf("Κινητά Τηλέφωνα          -> Τεμάχια: %d, Έσοδα: %.2f€\n", phoneQty, phoneRevenue);
                this.Pause();

            } else if (ch == 4) {
                System.out.println("\n--- ΣΤΑΤΙΣΤΙΚΑ ΑΝΑ ΚΑΤΑΣΚΕΥΑΣΤΗ ---");
                if (manufacturers.isEmpty()) {
                    System.out.println("Δεν υπάρχουν κατασκευαστές.");
                } else {
                    for (Manufacturer m : manufacturers) {
                        int totalQty = 0;
                        double totalRevenue = 0;

                        for (Sales s : allSales) {
                            if (s.getProduct().getManufacturer().getCode().equalsIgnoreCase(m.getCode())) {
                                totalQty += s.getQuantitySold();
                                totalRevenue += s.getTotalPrice();
                            }
                        }
                        System.out.printf("Κατασκευαστής: %-15s -> Συνολικά Τεμάχια: %d, Συνολικά Έσοδα: %.2f€\n",
                                m.getName(), totalQty, totalRevenue);
                    }
                }
                this.Pause();
            }
        } while (ch != 0);
    }

}
