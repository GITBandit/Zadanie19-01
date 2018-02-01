package pl.javastart.exercise.mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

public class Shop {

    private int money;
    private Map<Item, Integer> stock;

    public Shop(int money, Map<Item, Integer> stock) {
        this.money = money;
        this.stock = stock;
    }

    public void playCashSound() {
        /* zakładamy, że ta metoda odtwarza dźwięk https://www.youtube.com/watch?v=Wj_OmtqVLxY, nie musimy jej implementować,
        sprawdzamy tylko czy została uruchomiona */
    }

    public boolean hasItem(String itemName) {
        Set<Item> items = stock.keySet();

        for (Item item : items) {
            if(item.getName().equals(itemName))
                return true;

        }

        // TODO dodaj kod sprawdzający czy sklep na w asortymencie przedmot o danej nazwie
        return false;
    }

    public Item findItemByName(String itemName) {
        Set<Item> items = stock.keySet();

        for (Item item : items) {
            if(item.getName().equals(itemName))
                return item;

        }


        // TODO dodaj kod wyszukujący przedmiot po jego nazwie
        return null;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Map<Item, Integer> getStock() {
        return stock;
    }

    public void addToStock(Item item,Integer amount){
        stock.put(item,amount);
    }

    public void newStock(Map<Item, Integer> newStock){
        this.stock = newStock;
    }

    public void sellItem(Item itemSold) {
        int amount = stock.get(itemSold);

        // mniej niz 0?

        this.stock.replace(itemSold, (amount-1));

        if(stock.get(itemSold)<1){
            stock.remove(itemSold);
        }
    }

    public void playSound() throws URISyntaxException, IOException {
        String url = "https://www.youtube.com/watch?v=Wj_OmtqVLxY";

        if(Desktop.isDesktopSupported()){
            Desktop.getDesktop().browse(new URI(url));
        }
    }


}
