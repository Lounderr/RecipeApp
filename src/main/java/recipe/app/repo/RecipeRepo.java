package recipe.app.repo;

import recipe.app.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepo {
    private static final List<Recipe> recipes = new ArrayList<>();

    public static void save(Recipe recipe) {
        recipes.add(recipe);
    }

    public static List<Recipe> findByAuthor(String username) {
        return recipes.stream()
                .filter(r -> r.getAuthorUsername().equals(username))
                .toList();
    }

    public static void clear() {
        recipes.clear();
    }
}
