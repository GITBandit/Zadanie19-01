package pl.javastart.exercise.mockito;

import java.io.IOException;
import java.net.URISyntaxException;

public class ShopController {

    private Shop shop;

    public ShopController(ShopRepository shopRepository) {
        shop = shopRepository.findShop();

    }

    public void sellItem(Human human, String itemName) throws IOException, URISyntaxException {

        if (shop.hasItem(itemName)) {
            Item item = shop.findItemByName(itemName);
            if (item.getAgeRestriction() > human.getAge()) {
                throw new TooYoungException();
            } else if (human.getJob().equals("Policjant") && !item.isLegal()) {
                throw new PoliceException();
            } else {
                human.setMoney(human.getMoney() - item.getPrice());
                shop.setMoney(shop.getMoney() + item.getPrice());
                shop.sellItem(item);
                shop.playCashSound();
            }
        } else if (!shop.hasItem(itemName)){
            throw new OutOfStockException();
        }


    }

}
