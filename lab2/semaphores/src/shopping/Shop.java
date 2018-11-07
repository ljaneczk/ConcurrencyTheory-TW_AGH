package shopping;

import semaphore.BinarySemaphore;
import semaphore.CountingSemaphore;

public class Shop {
    private int shoppingCartsNumber;
    private ShoppingCart[] shoppingCarts;
    private boolean[] takenShoppingCarts;
    private CountingSemaphore countingSemaphore;
    private BinarySemaphore accessSemaphore;

    public Shop(int shoppingCartsNumber) {
        this.shoppingCartsNumber = shoppingCartsNumber;
        this.shoppingCarts = new ShoppingCart[shoppingCartsNumber];
        this.takenShoppingCarts = new boolean[shoppingCartsNumber];
        this.countingSemaphore = new CountingSemaphore(shoppingCartsNumber);
        this.accessSemaphore = new BinarySemaphore();
        for (int i = 0; i < shoppingCartsNumber; i++) {
            this.shoppingCarts[i] = new ShoppingCart(i);
            this.takenShoppingCarts[i] = false;
        }
    }

    public int takeShoppingCart(int client) {
        countingSemaphore.P();
        accessSemaphore.P();
        int shoppingCartID = -1;
        for (int i = 0; i < this.shoppingCartsNumber && shoppingCartID == -1; i++)
            if (!this.takenShoppingCarts[i]) {
                this.takenShoppingCarts[i] = true;
                shoppingCartID = i;
            }
        accessSemaphore.V();
        System.out.println("Client " + client + " takes " + shoppingCartID + " shopping cart.");
        return shoppingCartID;
    }

    public void putAwayShoppingCart(int index, int client) {
        countingSemaphore.V();
        accessSemaphore.P();
        this.takenShoppingCarts[index] = false;
        accessSemaphore.V();
        System.out.println("Client " + client + " puts away " + index + " shopping cart.");
    }
}
