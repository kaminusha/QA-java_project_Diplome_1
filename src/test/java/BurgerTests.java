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
    private Bun bun; // Мок-объект булки для тестирования


    @Mock
    private Ingredient sauce; // Мок-объект соуса для тестирования

    @Mock
    private Ingredient filling; // Мок-объект начинки для тестирования

    private Burger burger; // Тестируемый объект бургера

    // Константы для индексов в списке ингредиентов
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;

    // Подготовка перед каждым тестом
    @Before // Создаём новый экземпляр Burger, чтобы тесты были независимы
    public void setUp() {
        burger = new Burger();
    }

    // Тест для setBuns()
    @Test
    public void setBunsShouldAssignBunToBurger() {
        burger.setBuns(bun); // Устанавливаем булку в бургер
        assertEquals("Поле bun должно быть установлено в переданную булочку", bun, burger.bun);
    }

    // Тесты для addIngredient()
    @Test
    public void addIngredientShouldIncreaseIngredientsSizeByOne() {
        burger.addIngredient(sauce); // Добавляем один ингредиент
        // Проверяем размер списка ингредиентов должен стать равным 1
        assertEquals("Размер списка ингредиентов должен увеличиться на 1", 1, burger.ingredients.size());
    }

    @Test
    public void addIngredientShouldAddToEndOfList() {
        burger.addIngredient(sauce); // Добавляем ингредиент
        // Проверяем, что ингредиент должен быть на первой позиции (индекс 0)
        assertEquals("Добавленный ингредиент должен находиться в начале списка (так как он первый)",
                sauce, burger.ingredients.get(FIRST_INDEX));
    }


    // Тесты для removeIngredient()
    @Test
    public void removeIngredientShouldDecreaseSizeByOne() {
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

    // Проверяем, что после удаления первого ингредиента остальные сдвигаются влево
    @Test
    public void removeIngredientShouldShiftElementsLeft() {
        // Добавляем два ингредиента
        burger.addIngredient(sauce); // индекс 0
        burger.addIngredient(filling); // индекс 1
        // Удаляем ингредиент с индексом 0
        burger.removeIngredient(FIRST_INDEX);

        // на позиции 0 теперь должен быть filling
        assertEquals("После удаления элемент должен сдвинуться влево",
                filling, burger.ingredients.get(FIRST_INDEX));
    }

    // Тесты для moveIngredient()
    // Проверяем, что первый ингредиент перемещается на вторую позицию (индекс 1)
    @Test
    public void moveIngredientShouldMoveFirstIngredientToSecondPosition() {
        // Добавляем два ингредиента
        burger.addIngredient(sauce); // индекс 0
        burger.addIngredient(filling); // индекс 1
        // Меняем местами элементы с индексами 0 и 1
        burger.moveIngredient(FIRST_INDEX, SECOND_INDEX);

        // на позиции 0 теперь filling
        assertEquals("Первый ингредиент должен переместиться на позицию 1",
                filling, burger.ingredients.get(FIRST_INDEX));
    }

    // Проверяем, что второй ингредиент перемещается на первую позицию (индекс 0)
    @Test
    public void moveIngredientShouldMoveSecondIngredientToFirstPosition () {
        // Добавляем два ингредиента
        burger.addIngredient(sauce);   // индекс 0
        burger.addIngredient(filling);  // индекс 1
        // Меняем местами элементы с индексами 0 и 1
        burger.moveIngredient(FIRST_INDEX, SECOND_INDEX);

        // После перемещения sauce должен быть на позиции 1
        assertEquals("Второй ингредиент должен переместиться на позицию 0",
            sauce, burger.ingredients.get(SECOND_INDEX));
        }

    //Проверяем, что общее количество ингредиентов не меняется после перемещения
    @Test
    public void moveIngredientShouldNotChangeTotalCount () {
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