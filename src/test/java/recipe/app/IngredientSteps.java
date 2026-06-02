package recipe.app;

import recipe.app.model.User;
import recipe.app.model.UserRole;
import recipe.app.model.Ingredient;
import recipe.app.service.IngredientService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class IngredientSteps {
    private User user;
    private Ingredient selectedIngredient;
    private List<Ingredient> availableIngredients;
    private Exception exception;
    private final IngredientService service = new IngredientService();

    @Given("The user is logged in with role {string}")
    public void userLoggedInWithRole(String role) {
        user = new User("testuser", "test@mail.com", UserRole.valueOf(role), 10);
        selectedIngredient = null;
        exception = null;
    }

    @When("The user selects ingredient {string} with category {string}")
    public void selectIngredientWithCategory(String name, String category) {
        try {
            selectedIngredient = service.selectIngredient(user, name, category);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("The user opens the ingredient list")
    public void openIngredientList() {
        availableIngredients = service.getAvailableIngredients(user);
    }

    @Then("the ingredient is selected successfully")
    public void ingredientSelectedSuccessfully() {
        assertNull(exception);
        assertNotNull(selectedIngredient);
    }

    @Then("an ingredient error is shown {string}")
    public void showsIngredientError(String message) {
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Then("{int} ingredients are shown")
    public void showsIngredients(int expected) {
        assertNotNull(availableIngredients);
        assertEquals(expected, availableIngredients.size());
    }
}
