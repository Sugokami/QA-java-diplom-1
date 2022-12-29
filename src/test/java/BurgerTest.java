import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    private Burger burger;
    private String ingredientName = "Brioche";
    private float ingredientPrice = 50.00f;
    private float priceExpected = 150.00f;
    Ingredient ingredient = new Ingredient(IngredientType.FILLING, ingredientName, ingredientPrice);

    @Before
    public void createBurger() {
        burger = new Burger();
    }

    @Mock
    Bun bun;

    @Test
    public void addIngredientTest() {
        burger.addIngredient(ingredient);
        Assert.assertEquals(ingredient, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientTest() {
        int sizeExpected = 0;

        burger.addIngredient(ingredient);
        burger.removeIngredient(0);
        Assert.assertEquals(sizeExpected, burger.ingredients.size());
    }

    @Test
    public void moveIngredientTest() {
        Ingredient ingredient = new Ingredient(IngredientType.FILLING, ingredientName, ingredientPrice);

        burger.addIngredient(ingredient);
        burger.addIngredient(ingredient);
        burger.moveIngredient(0, 1);
        Assert.assertEquals(ingredient, burger.ingredients.get(1));
    }

    @Test
    public void getPriceTest() {
        Mockito.when(bun.getPrice()).thenReturn(ingredientPrice);
        burger.setBuns(bun);
        burger.addIngredient(ingredient);
        Assert.assertEquals(priceExpected, burger.getPrice(), 0);
    }

    @Test
    public void getReceiptTest() {
        Mockito.when(bun.getPrice()).thenReturn(ingredientPrice);
        Mockito.when(bun.getName()).thenReturn(ingredientName);
        burger.setBuns(bun);
        burger.addIngredient(ingredient);
        String expectedReceipt = "(====" + bun.getName() + "====)=filling" + bun.getName() + "=(====" + bun.getName() + "====)Price:" + String.format("%f", priceExpected);
        String actualReceipt = burger.getReceipt();
        Assert.assertEquals(expectedReceipt, actualReceipt.replaceAll("\r\n?|\n", "").replace(" ", ""));
    }
}
