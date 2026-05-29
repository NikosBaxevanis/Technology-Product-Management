package UI;

import Data.Manufacturer;
import Data.PersonalComputer;
import Data.Product;
import Data.SmartPhone;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public  class MainP {

    public MainP() {
        this.Keyb = new Scanner(System.in);
    }

    private static ArrayList<Product> allProducts = new ArrayList<>();
    private static ArrayList<Manufacturer> manufacturers = new ArrayList<>();

    Scanner Keyb;

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
                   // this.MenuInsert();
                    break;
                case 0:
                    System.out.println("Πραγματοποιήσατε έξοδο από το κατάστημα μας. Καλή συνέχεια.");
                    break;
                default:
                    System.out.println("Λάθος επιλογή. Παρακαλούμε προσπαθήστε ξανά.");
                    this.Pause();
            }
        } while(ch != 0);
    }

    public static void main(String[] args) {
        MainP MP = new MainP();

        Manufacturer m1 = new Manufacturer("M01", "Apple", "info@apple.com");
        Manufacturer m2 = new Manufacturer("M02", "Samsung", "info@samsung.com");
        manufacturers.add(m1);
        manufacturers.add(m2);

        allProducts.add(new PersonalComputer("C01", "MacBook Pro", m1, 1999.99, 10, 16, 512));
        allProducts.add(new SmartPhone("P01", "Galaxy S24", m2, 999.00, 15, "50MP", "Μαύρο"));

        MP.Menu();
    }

    void Pause() {
        System.out.print("\n\nΠιέστε <Enter> για συνέχεια  ");
        this.Keyb.nextLine();
    }

    public void Cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException var2) {
        } catch (InterruptedException var3) {
            Thread.currentThread().interrupt();
        }
    }


    private  void menuManufacturers() {
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
            ch = this.Keyb.nextInt();
            this.Keyb.nextLine();
            if (ch == 1) {
                System.out.print("Κωδικός: ");
                String code = Keyb.nextLine();
                System.out.print("Επωνυμία: ");
                String name = Keyb.nextLine();
                System.out.print("Email: ");
                String email = Keyb.nextLine();
                manufacturers.add(new Manufacturer(code, name, email));
                System.out.println("Ο κατασκευαστής προστέθηκε!");
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
                } else System.out.println("Δεν βρέθηκε!");
            } else if (ch == 3) {
                System.out.print("Δώστε κωδικό για διαγραφή: ");
                String code = Keyb.nextLine();
                Manufacturer m = findManufacturer(code);
                if (m != null) {
                    manufacturers.remove(m);
                    System.out.println("Διαγράφηκε επιτυχώς!");
                } else System.out.println("Δεν βρέθηκε!");
            } else if (ch == 4) {
                for (Manufacturer m : manufacturers) System.out.println(m);
                this.Pause();
            }
        }while (ch != 0);
    }

    private   Manufacturer findManufacturer(String code) {
        for (Manufacturer m : manufacturers) if (m.getCode().equalsIgnoreCase(code)) return m;
        return null;
    }


    private void menuPersonalComputers () {
        int ch;
        do {
            System.out.println("\n -- Διαχείριση Ηλεκτρονικών Υπολογιστών --");
            System.out.println("\n-- Πως θα θέλατε να διαχειριστείτε τον προσωπικό σας υπολογιστή; --");
            System.out.println("[1]...Εισαγωγή υπολογιστή");
            System.out.println("[2]...Διόρθωση υπολογιστή");
            System.out.println("[3]...Διαγραφή υπολογιστή ");
            System.out.println("[4]...Προβολή Όλων των υπολογιστών");
            System.out.println("[0]...Επιστροφή στο αρχικό Menu");
            ch = this.Keyb.nextInt();
            Keyb.nextLine();

            if (ch == 1) {
                System.out.print("Κωδικός: ");
                String code = Keyb.nextLine();
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
                double price = Keyb.nextDouble();
                System.out.print("Ποσότητα: ");
                int qty = Keyb.nextInt();
                System.out.print("Μέγεθος RAM (GB): ");
                int ram = Keyb.nextInt();
                System.out.print("Χωρητικότητα Σκληρού (GB): ");
                int storage = Keyb.nextInt();

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
                    c.setPrice(Keyb.nextDouble());
                    System.out.print("Νέα Ποσότητα: ");
                    c.setQuantity(Keyb.nextInt());
                    System.out.print("Νέα RAM: ");
                    c.setRamSize(Keyb.nextInt());
                    System.out.print("Νέο Storage: ");
                    c.setRomSize(Keyb.nextInt());
                    System.out.println("Ενημερώθηκε!");
                } else System.out.println("Δεν βρέθηκε ηλεκτρονικός υπολογιστής με αυτόν τον κωδικό!");
                this.Pause();
            } else if (ch == 3) {
                System.out.print("Δώστε κωδικό για διαγραφή: ");
                String code = Keyb.nextLine();
                Product p = findProduct(code);
                if (p instanceof PersonalComputer) {
                    allProducts.remove(p);
                    System.out.println("Διαγράφηκε!");
                } else System.out.println("Δεν βρέθηκε!");
                this.Pause();
            } else if (ch == 4) {
                for (Product p : allProducts) {
                    if (p instanceof PersonalComputer) System.out.println(p);
                }
                this.Pause();
            }
        }while (ch != 0);
    }




    private  void menuSmartPhones() {
        int ch;
        do {
            System.out.println("\n-- ΔΙΑΧΕΙΡΙΣΗ ΚΙΝΗΤΩΝ ΤΗΛΕΦΩΝΩΝ --");
            System.out.println("\n-- Πως θα θέλατε να διαχειριστείτε το SmartPhone σας; --");
            System.out.println("[1]...Εισαγωγή SmartPhone");
            System.out.println("[2]...Διόρθωση SmartPhone");
            System.out.println("[3]...Διαγραφή SmartPhone ");
            System.out.println("[4]...Προβολή Όλων των SmartPhones");
            System.out.println("[0]...Επιστροφή στο αρχικό Menu");
            ch = Keyb.nextInt();
            Keyb.nextLine();

            if (ch == 1) {
                System.out.print("Κωδικός: "); String code = Keyb.nextLine();
                System.out.print("Λεκτικό: "); String title = Keyb.nextLine();
                System.out.print("Κωδικός Κατασκευαστή: "); String mCode = Keyb.nextLine();
                Manufacturer m = findManufacturer(mCode);
                if (m == null) { System.out.println("Σφάλμα: Δεν υπάρχει αυτός ο κατασκευαστής!"); this.Pause(); continue; }
                System.out.print("Τιμή: "); double price = Keyb.nextDouble();
                System.out.print("Ποσότητα: "); int qty = Keyb.nextInt(); Keyb.nextLine();
                System.out.print("Ανάλυση Κάμερας: "); String cam = Keyb.nextLine();
                System.out.print("Χρώμα: "); String color = Keyb.nextLine();

                allProducts.add(new SmartPhone(code, title, m, price, qty, cam, color));
                System.out.println("Το κινητό προστέθηκε!");
                this.Pause();
            } else if (ch == 2) {
                System.out.print("Δώστε κωδικό κινητού για διόρθωση: "); String code = Keyb.nextLine();
                Product p = findProduct(code);

                if (p instanceof SmartPhone) {
                    SmartPhone s = (SmartPhone) p;
                    System.out.print("Νέο Λεκτικό: "); s.setTitle(Keyb.nextLine());
                    System.out.print("Νέα Τιμή: "); s.setPrice(Keyb.nextDouble());
                    System.out.print("Νέα Ποσότητα: "); s.setQuantity(Keyb.nextInt()); Keyb.nextLine();
                    System.out.print("Νέα Κάμερα: "); s.setCamRes(Keyb.nextLine());
                    System.out.print("Νέο Χρώμα: "); s.setColor(Keyb.nextLine());
                    System.out.println("Ενημερώθηκε!");
                } else System.out.println("Δεν βρέθηκε SmartPhone με αυτόν τον κωδικό!");
                this.Pause();
            } else if (ch == 3) {
                System.out.print("Δώστε κωδικό για διαγραφή: "); String code = Keyb.nextLine();
                Product p = findProduct(code);
                if (p instanceof SmartPhone) {
                    allProducts.remove(p);
                    System.out.println("Διαγράφηκε!");
                } else System.out.println("Δεν βρέθηκε!");
                this.Pause();
            } else if (ch == 4) {
                for (Product p : allProducts) {
                    if (p instanceof SmartPhone) System.out.println(p);
                }
                this.Pause();
            }
        }while (ch != 0);
    }


    private Product findProduct(String code) {
        for (Product p : allProducts) {
            if (p.getCode().equalsIgnoreCase(code)) return p;
        }
        return null;
    }

}
