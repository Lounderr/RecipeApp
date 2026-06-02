package recipe.app.service;

import recipe.app.model.User;
import recipe.app.model.UserRole;
import recipe.app.model.Ingredient;
import recipe.app.repo.IngredientRepo;

import java.util.List;

public class IngredientService {

    private static final List<Ingredient> ALL_INGREDIENTS = IngredientRepo.findAll();

    public List<Ingredient> getAvailableIngredients(User user) {
        if (user.getRole() == UserRole.PREMIUM || user.getRole() == UserRole.ADMIN) {
            return ALL_INGREDIENTS;
        }
        return ALL_INGREDIENTS.stream().filter(v -> !v.isPremium()).toList();
    }

    public Ingredient selectIngredient(User user, String name, String category) {
        Ingredient ingredient = ALL_INGREDIENTS.stream()
                .filter(v -> v.getName().equals(name) && v.getCategory().equals(category))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found"));
        if (ingredient.isPremium() && user.getRole() == UserRole.USER) {
            throw new IllegalStateException("This ingredient requires a premium subscription");
        }
        return ingredient;
    }
}
