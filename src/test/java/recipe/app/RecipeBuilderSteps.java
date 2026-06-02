package recipe.app;

import recipe.app.model.Recipe;
import recipe.app.model.RecipeStatus;
import recipe.app.model.User;
import recipe.app.model.UserRole;
import recipe.app.model.Ingredient;
import recipe.app.service.RecipeBuilderService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RecipeBuilderSteps {
    private User user;
    private Ingredient selectedIngredient;
    private String instructions;
    private String title;
    private Recipe result;
    private Exception exception;
    private List<Recipe> history;
    private final RecipeBuilderService service = new RecipeBuilderService();

    @Given("The user is logged in with username {string} and {int} credits")
    public void userLoggedIn(String username, int credits) {
        user = new User(username, username + "@mail.com", UserRole.USER, credits);
        result = null;
        exception = null;
    }

    @And("The user selects ingredient {string} category {string}")
    public void selectIngredient(String name, String category) {
        selectedIngredient = new Ingredient(name, category, false);
    }

    @And("the user has {int} published recipes in history")
    public void hasRecipesInHistory(int count) {
        for (int i = 0; i < count; i++) {
            Recipe r = new Recipe(String.valueOf(i), user.getUsername(),
                    "Title " + i, "instructions " + i, "Tomato_Vegetable", RecipeStatus.PUBLISHED);
            user.getRecipes().add(r);
        }
    }

    @When("The user enters instructions {string}")
    public void enterInstructions(String instructions) {
        this.instructions = instructions;
    }

    @And("enters title {string}")
    public void enterTitle(String title) {
        this.title = title;
    }

    @And("clicks publish")
    public void clickPublish() {
        Ingredient ingredient = selectedIngredient != null ? selectedIngredient : new Ingredient("Tomato", "Vegetable", false);
        try {
            result = service.createRecipe(user, title, instructions, ingredient);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("The user opens the history")
    public void openHistory() {
        history = service.getHistory(user);
    }

    @Then("the recipe is published successfully")
    public void recipePublishedSuccessfully() {
        assertNull(exception);
        assertNotNull(result);
        assertEquals(RecipeStatus.PUBLISHED, result.getStatus());
    }

    @And("the user has {int} credits")
    public void userHasCredits(int expected) {
        assertEquals(expected, user.getCredits());
    }

    @Then("an error is shown {string}")
    public void showsError(String message) {
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Then("{int} recipes are shown")
    public void showsRecipes(int expected) {
        assertNotNull(history);
        assertEquals(expected, history.size());
    }
}
