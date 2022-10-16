package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Representing a buyer who lives in a specific residence,with name, personal
// username and password, account balance (in CAD), list of previously bought items
// and a to-buy wish list.
public class Buyer extends User {
    private List<Item> itemsToBuy;
    private List<Item> alreadyBought;

    // REQUIRES: length of name, username and password > 0 and unique
    //           username and password
    // EFFECTS : balance is set to 0; buyer's username and pass
    //           are personal; Initially no history of shopping and no
    //           item in the wish list.
    public Buyer(String nm, Residence res, String un, String pass) {
        super(nm, res, un, pass);
        itemsToBuy = new ArrayList<>();
        alreadyBought = new ArrayList<>();
    }

    public List<Item> getItemsToBuy() {
        return itemsToBuy;
    }

    public List<Item> getAlreadyBought() {
        return alreadyBought;
    }

    // MODIFIES : this
    // EFFECTS : Add an item to the wish list
    public void addItemToBuy(Item i) {
        itemsToBuy.add(i);
    }

    // EFFECTS : return how much money you should pay in order
    // to buy all items in the wish list
    public double howMuchToPay() {
        double sum = 0;
        for (Item i: itemsToBuy) {
            sum += i.getPrice();
        }
        return sum;
    }

    // MODIFIES : this
    // EFFECTS : pay for each item in your wish list (from the oldest added), remove it
    // and add to the shopping history until you run out of money.
    public void payForItems() {
        Iterator<Item> itr = itemsToBuy.iterator();
        while (itr.hasNext()) {
            Item i = itr.next();
            if (balance >= i.getPrice()) {
                itr.remove();
                alreadyBought.add(i);
                balance -= i.getPrice();
                i.buy(this);
            } else {
                notEnoughBalance(i);
            }
        }
    }

    // EFFECTS : return a string to say that you don't have enough money
    // to buy the item i.
    public String notEnoughBalance(Item i) {
        return "You don't have enough money to buy " + i.getName();
    }

    // MODIFIES : this
    // EFFECTS : remove the item from the wish list if it exists there
    public void removeFromWishlist(Item i) {
        if (this.getItemsToBuy().contains(i)) {
            itemsToBuy.remove(i);
        }
    }

}