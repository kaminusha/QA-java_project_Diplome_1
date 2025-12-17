import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import static org.junit.Assert.assertEquals;

// Тестовый класс для проверки базовых операций с объектом Burger
@RunWith(MockitoJUnitRunner.class)
public class BurgerTests {  // Используются моки (Bun, Ingredient) через Mockito для изоляции зависимостей
    @Mock
    private Bun bun;

    @Mock
    private Ingredient sauce;

    @Mock
    private Ingredient filling;

    private Burger burger;

    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;

    // Подготовка перед каждым тестом
    @Before // Создаём новый экземпляр Burger, чтобы тесты были независимы
    public void setUp() {
        burger = new Burger();
    }

    // Тест для setBuns()
    @Test
    public void setBuns_whenCalledWithBun_shouldAssignToBurgerBunField() {
        burger.setBuns(bun); // Устанавливаем булку в бургер
        assertEquals("Поле bun должно быть установлено в переданную булочку", bun, burger.bun);
    }

    // Тесты для addIngredient()
    @Test
    public void addIngredient_whenCalledOnce_shouldIncreaseIngredientsSizeByOne() {
        burger.addIngredient(sauce); // Добавляем один ингредиент
        // Проверяем размер списка ингредиентов должен стать равным 1
        assertEquals("Размер списка ингредиентов должен увеличиться на 1", 1, burger.ingredients.size());
    }

    @Test
    public void addIngredient_ShouldAddIngredientToTheEndOfListTest() {
        burger.addIngredient(sauce); // Добавляем ингредиент
        //Проверяем что ингредиент должен быть на первой позиции (индекс 0)
        assertEquals("Добавленный ингредиент должен находиться в начале списка (так как он первый)",
                sauce, burger.ingredients.get(FIRST_INDEX));
    }


    // Тесты для removeIngredient()
    @Test
    public void removeIngredient_whenTwoIngredientsPresent_shouldDecreaseSizeByOne() {
        // Добавляем два ингредиента
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        int initialSize = burger.ingredients.size();

        // Удаляем первый ингредиент (индекс 0)
        burger.removeIngredient(FIRST_INDEX);

        // Проверяем размер списка должен уменьшиться на 1
        assertEquals("Размер списка должен уменьшиться на 1 после удаления",
                initialSize - 1, burger.ingredients.size());
    }

    @Test
    public void removeIngredient_whenRemovingFirst_shouldShiftRemainingElementsLeft() {
        // Добавляем два ингредиента
        burger.addIngredient(sauce); // индекс 0
        burger.addIngredient(filling); // индекс 1
        // Удаляем ингредиент с индексом 0
        burger.removeIngredient(FIRST_INDEX);

        // на позиции 0 теперь должен быть filling
        assertEquals("После удаления элемента с индексом 0, на его месте должен быть filling",
                filling, burger.ingredients.get(FIRST_INDEX));
    }
    //Тесты для moveIngredient()
    @Test
    public void moveIngredient_whenSwappingTwo_shouldChangeTheirPositions() {
        // Добавляем два ингредиента
        burger.addIngredient(sauce); // индекс 0
        burger.addIngredient(filling); // индекс 1
        // Меняем местами элементы с индексами 0 и 1
        burger.moveIngredient(FIRST_INDEX, SECOND_INDEX);

        // на позиции 0 теперь filling
        assertEquals("Ингредиент 'filling' должен оказаться на первой позиции",
                filling, burger.ingredients.get(FIRST_INDEX));

        //
        assertEquals("После перемещения sauce должен оказаться на второй позиции",
                sauce, burger.ingredients.get(SECOND_INDEX));
    }

    @Test
    public void moveIngredient_whenCalled_shouldNotChangeTotalIngredientsCount() {
        // Добавляем два ингредиента
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        int initialSize = burger.ingredients.size();

        burger.moveIngredient(FIRST_INDEX, SECOND_INDEX); // Перемещаем ингредиент

        // Количество ингредиентов не должно измениться
        assertEquals("Количество ингредиентов не должно измениться после перемещения",
                initialSize, burger.ingredients.size());
    }
}
