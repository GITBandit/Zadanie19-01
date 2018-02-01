package pl.javastart.exercise.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ShopControllerTest {

    @Mock private ShopRepository shopRepository;

    private Shop shop;

    private ShopController shopController;
    private Map<Item, Integer> stock;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        stock = new HashMap<>();
        stock.put(new Item("Piwo", 18, 4, true), 4);

        shop = Mockito.spy(new Shop(1000,stock));
/*        shop.newStock(stock);
        shop.setMoney(1000);*/

        when(shopRepository.findShop()).thenReturn(shop);

        shopController = new ShopController(shopRepository);
    }

    @Test(expected = TooYoungException.class)
    public void shouldNotSellBeerToYoungling() throws IOException, URISyntaxException {
        // given
        Human human = new Human("Jan",15,"Uczeń",1000);

        // when
        shopController.sellItem(human, "Piwo");
    }

    @Test(expected = OutOfStockException.class)
    public void shouldNotHaveLessThanZeroItems() throws IOException, URISyntaxException {
        //given
        //Shop shop = new Shop(1000,stock);
        Item item1 = new Item("Piwo", 18, 4, true);
        stock.put(item1, 2);
        Human human = new Human("Piotr",35,"Listonosz",1000);

        //when
        shopController.sellItem(human,"Piwo");
        shopController.sellItem(human,"Piwo");
        shopController.sellItem(human,"Piwo");
    }

    @Test (expected = PoliceException.class)
    public void shouldNotSellIllegalToThePolice() throws IOException, URISyntaxException {
        //given
        Shop shop = new Shop(1000,stock);
        Item item1 = new Item("Narkotyki", 18, 4, false);
        stock.put(item1, 1);
        Human human = new Human("Marek",35,"Policjant",1000);


        //when
        shopController.sellItem(human,"Narkotyki");
    }

    @Test
    public void shouldPlaySound() throws IOException, URISyntaxException {
        //given
        Human human = new Human("Marek",35,"Rolnik",1000);
        stock = new HashMap<>();
        stock.put(new Item("Piwo", 18, 4, true), 4);
       // Shop shopSpy = Mockito.spy(new Shop(1000,stock));
        //when
        shopController.sellItem(human,"Piwo");

        //then
        Mockito.verify(shop,atLeastOnce()).playCashSound();
    }

    @Test
    public void shouldReduceNumberInStock() throws IOException, URISyntaxException {
        //given
        Human human = new Human("Marek",35,"Policjant",1000);
        Item itemR = new Item("Rower", 6, 4, true);
        stock.put(itemR, 4);
        //when
        shopController.sellItem(human,"Rower");
        //than
        assertThat(stock.get(itemR),is(3));

    }


    @Test
    public void shouldPassMoneyFromCustomerToShop() throws IOException, URISyntaxException {
        //given
        Human human = new Human("Marek",35,"Policjant",1000);
        //when
        shopController.sellItem(human,"Piwo");
        //than
        assertThat(human.getMoney(),is(996));
        assertThat(shop.getMoney(),is(1004));

    }

}
