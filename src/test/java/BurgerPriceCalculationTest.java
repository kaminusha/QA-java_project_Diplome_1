import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// Параметризованный тест для проверки правильного расчёта цены - бургера (getPrice()) в классе Burger
@RunWith(Parameterized.class)
public class BurgerPriceCalculationTest {

    private static final float DELTA = 0.001f;// Допустимая погрешность для сравнения float

    private final String testScenario; // Название тестового сценария
    private final float expectedPrice; // Ожидаемая итоговая цена
    private final Ingredient[] testIngredients; // Массив ингредиентов для теста

    private Burger burger; // Тестируемый объект

    // Конструктор для параметризованных тестов
    public BurgerPriceCalculationTest(String testScenario, float expectedPrice, Ingredient[] testIngredients) {
        this.testScenario = testScenario;
        this.expectedPrice = expectedPrice;
        this.testIngredients = testIngredients;
    }

    // Метод, предоставляющий тестовые данные для параметризованного запуска
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // Сценарий 1: булки + один соус
                {"2 Булки(по 50 руб.) + соус(50 руб.)",
                        150.0f,
                        new Ingredient[]{createMockIngredient(50.0f)
                        }
                },
                // Сценарий 2: булки + начинка + соус
                {"2 Булки(по 50 руб.) + начинка(70 руб.)+ соус(50 руб.)",
                        220.0f,
                        new Ingredient[]{
                                createMockIngredient(70.0f),
                                createMockIngredient(50.0f)
                        }
                },
                // Сценарий 3: только булки (без ингредиентов)
                {"Только булки(по 50 руб.) без ингредиентов",
                        100.0f,
                        new Ingredient[]{
                        }
                },
                // Сценарий 4: булки + две начинки + соус
                {"2 Булки(по 50 руб.) + две начинки (80 + 10 руб.) + соус (10 руб.)",
                        200.0f,
                        new Ingredient[]{
                                createMockIngredient(80.0f),
                                createMockIngredient(10.0f),
                                createMockIngredient(10.0f)
                        }
                }
        });
    }

    // Создаёт мок-объект Ingredient с заданной ценой
    private static Ingredient createMockIngredient(float price) {
        Ingredient ingredient = Mockito.mock(Ingredient.class);
        when(ingredient.getPrice()).thenReturn(price);
        return ingredient;
    }

    // Подготовка перед каждым тестовым запуском
    @Before
    public void setUp() {
        // Создаём мок булки
        Bun mockBun = mock(Bun.class);
        when(mockBun.getPrice()).thenReturn(50.0f);
        // Инициализируем бургер и устанавливаем булку
        burger = new Burger();
        burger.setBuns(mockBun);

        // Добавляем все ингредиенты из тестового набора
        for (Ingredient ingredient : testIngredients) {
            burger.addIngredient(ingredient);
        }
    }

    // Тест: проверяем, что getPrice() возвращает ожидаемую цену, используя проверки разных сценариев
    @Test
    public void getPrice_shouldReturnCorrectTotalPriceForAllScenarios() {
        float actualPrice = burger.getPrice();
        assertEquals("Тест: " + testScenario + " → ожидаемая цена: " + expectedPrice + ", фактическая: " + actualPrice, expectedPrice, actualPrice, DELTA);
    }
}