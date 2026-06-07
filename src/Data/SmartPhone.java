package Data;

    public class SmartPhone extends Product {
        private String camRes;
        private String color;

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

        @Override
        public String toString() {
            return "Κινητό [Κωδικός: " + getCode() + ", Λεκτικό: " + getTitle() + ", Κάμερα: " + camRes +
                    ", Χρώμα: " + color + ", Κατασκευαστής: " + (getManufacturer() != null ? getManufacturer().getName() : "N/A") +
                    ", Τιμή: " + getPrice() + "€, Ποσότητα: " + getQuantity() + "]";
        }

        public String AsCsvLine() {
            return "PHONE;" + getCode() + ";" + getTitle() + ";" + getManufacturer().getCode() + ";" +
                    getPrice() + ";" + getQuantity() + ";" + this.camRes + ";" + this.color;
        }
    }
