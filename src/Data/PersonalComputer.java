package Data;

public class PersonalComputer extends Product {
    private int ramSize;
    private int romSize;


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

    @Override
    public String toString() {
        return "Η/Υ [Κωδικός: " + getCode() + ", Λεκτικό: " + getTitle() + ", RAM: " + ramSize + "GB, Storage: " + romSize +
                "GB, Κατασκευαστής: " + (getManufacturer() != null ? getManufacturer().getName() : "N/A") +
                ", Τιμή: " + getPrice() + "€, Ποσότητα: " + getQuantity() + "]";
    }
}
