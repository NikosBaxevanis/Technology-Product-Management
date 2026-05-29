package Data;

public class Manufacturer {
    private String code;
    private String name;
    private String email;

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

    @Override
    public String toString() {
        return "Κατασκευαστής [Κωδικός: " + code + ", Επωνυμία: " + name + ", Email: " + email + "]";
    }
}
