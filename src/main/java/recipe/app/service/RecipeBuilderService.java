package recipe.app.service;

import recipe.app.model.Recipe;
import recipe.app.model.RecipeStatus;
import recipe.app.model.User;
import recipe.app.model.Ingredient;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

public class RecipeBuilderService {

    public Recipe createRecipe(User author, String title, String instructions, Ingredient ingredient) {
        if (StringUtils.isBlank(instructions)) {
            throw new IllegalArgumentException("Instructions cannot be empty");
        }
        if (StringUtils.isBlank(title)) {
            throw new IllegalArgumentException("Title is required");
        }
        if (author.getCredits() < 1) {
            throw new IllegalStateException("Insufficient credits");
        }
        author.setCredits(author.getCredits() - 1);
        Recipe recipe = new Recipe();
        recipe.setId(UUID.randomUUID().toString());
        recipe.setAuthorUsername(author.getUsername());
        recipe.setTitle(title);
        recipe.setInstructions(instructions);
        recipe.setIngredientKey(ingredient.getName() + "_" + ingredient.getCategory());
        recipe.setIngredient(ingredient);
        recipe.setStatus(RecipeStatus.PUBLISHED);
        
        author.getRecipes().add(recipe);
        
        return recipe;
    }

    public List<Recipe> getHistory(User user) {
        return user.getRecipes();
    }
}
