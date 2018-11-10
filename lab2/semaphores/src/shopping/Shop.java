package shopping;

import semaphore.BinarySemaphore;
import semaphore.CountingSemaphore;

public class Shop {
    private int shoppingCartsNumber;
    private ShoppingCart[] shoppingCarts;
    private boolean[] takenShoppingCarts;
    private final CountingSemaphore countingSemaphore;
    private final BinarySemaphore accessSemaphore;

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

    public ShoppingCart takeShoppingCart(int client) {
        countingSemaphore.P();
        accessSemaphore.P();
        int index = -1;
        for (int i = 0; i < this.shoppingCartsNumber && index == -1; i++)
            if (!this.takenShoppingCarts[i]) {
                this.takenShoppingCarts[i] = true;
                index = i;
            }
        accessSemaphore.V();
        return shoppingCarts[index];
    }

    public void putAwayShoppingCart(ShoppingCart shoppingCart, int client) {
        countingSemaphore.V();
        accessSemaphore.P();
        this.takenShoppingCarts[shoppingCart.getNumber()] = false;
        accessSemaphore.V();
    }
}
