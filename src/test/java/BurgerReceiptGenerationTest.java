import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

// Параметризованный тест для проверки корректности формирования чека (getReceipt()) в классе Burger
@RunWith(Parameterized.class)
public class BurgerReceiptGenerationTest {

    private Burger burger;
    private final String testScenario; // Название тестового сценария

    private final String expectedReceipt; // Ожидаемый текст чека
    private final List<Ingredient> ingredients; // Список ингредиентов для теста

    // Конструктор для параметризованных тестов
    public BurgerReceiptGenerationTest(String testScenario, String expectedReceipt, List<Ingredient> ingredients) {
        this.testScenario = testScenario;
        this.expectedReceipt = expectedReceipt;
        this.ingredients = ingredients;
    }

    // Метод, предоставляющий тестовые данные для параметризованного запуска
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // Сценарий 1: пустой бургер (только булка)
                {"Пустой бургер (только булки)",
                        "(==== BunName ====)" + lineSeparator() +
                                "(==== BunName ====)" + lineSeparator() + lineSeparator() +
                                "Price: 100,000000" + lineSeparator(),
                        List.of()
                },
                // Сценарий 2: булка + один соус
                {"Бургер с булкой и соусом",
                        "(==== BunName ====)" + lineSeparator() +
                                "= sauce sauce1 =" + lineSeparator() +
                                "(==== BunName ====)" + lineSeparator() + lineSeparator() +
                                "Price: 110,000000" + lineSeparator(),
                        List.of(createMockIngredient(IngredientType.SAUCE, "sauce1", 10.0f))
                },
                // Сценарий 3: булка + начинка + соус
                {"Бургер с булкой, начинкой и соусом",
                        "(==== BunName ====)" + lineSeparator() +
                                "= filling fill1 =" + lineSeparator() +
                                "= sauce sauce2 =" + lineSeparator() +
                                "(==== BunName ====)" + lineSeparator() + lineSeparator() +
                                "Price: 180,000000" + lineSeparator(),
                        List.of(
                                createMockIngredient(IngredientType.FILLING, "fill1", 70.0f),
                                createMockIngredient(IngredientType.SAUCE, "sauce2", 10.0f)
                        )
                }
        });
    }

    // Создаёт мок-объект Ingredient с заданными параметрами
    private static Ingredient createMockIngredient(
            IngredientType type,
            String name,
            float price
    ) {
        Ingredient ingredient = Mockito.mock(Ingredient.class);
        Mockito.when(ingredient.getType()).thenReturn(type);
        Mockito.when(ingredient.getName()).thenReturn(name);
        Mockito.when(ingredient.getPrice()).thenReturn(price);
        return ingredient;
    }

    // Подготовка перед каждым тестовым запуском
    @Before
    public void setUp() {
        // Создаём мок булки
        Bun mockBun = Mockito.mock(Bun.class);
        Mockito.when(mockBun.getName()).thenReturn("BunName");
        Mockito.when(mockBun.getPrice()).thenReturn(50.0f);

        // Инициализируем бургер и устанавливаем булку
        burger = new Burger();
        burger.setBuns(mockBun);

        // Добавляем все ингредиенты из тестового набора
        for (Ingredient ingredient : ingredients) {
            burger.addIngredient(ingredient);
        }
    }

    //  Тест: Проверяем, что getReceipt() возвращает ожидаемый чек. Используем разные сценарии
    @Test
    public void getReceiptShouldGenerateCorrectReceiptForAllScenarios() {
        String actualReceipt = burger.getReceipt();
        assertEquals("Тест: " + testScenario + " → ожидаемый чек не совпадает с фактическим", expectedReceipt, actualReceipt);
    }

}
