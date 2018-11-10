package shopping;

public class ShoppingCart {
    private int number;
    private String info;

    public ShoppingCart(int number, String info) { this.number = number;this.info = info; }
    public ShoppingCart(int number) { this(number, ""); }
    public ShoppingCart() { this(0, ""); }

    public int getNumber() {
        return number;
    }

    void setNumber(int number) {
        this.number = number;
    }

    public String getInfo() {
        return info;
    }

    void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Shopping cart " + number + ((info.equals("") ? "" : " " + info));
    }
}
