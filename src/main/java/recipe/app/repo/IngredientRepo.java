package recipe.app.repo;

import recipe.app.model.Ingredient;

import java.util.List;

public class IngredientRepo {
    public static List<Ingredient> findAll() {
        return List.of(
                new Ingredient("Tomato", "Vegetable", false),
                new Ingredient("Chicken", "Meat", false),
                new Ingredient("Truffle", "Fungi", true),
                new Ingredient("Saffron", "Spice", true)
        );
    }
}
