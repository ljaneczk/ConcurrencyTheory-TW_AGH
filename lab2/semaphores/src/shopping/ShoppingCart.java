package shopping;

public class ShoppingCart {
    private int number = 0;
    private String info = "";
    public ShoppingCart() { }
    public ShoppingCart(int number) {
        this.number = number;
    }
    public ShoppingCart(int number, String info) {
        this.number = number;
        this.info = info;
    }

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
}
