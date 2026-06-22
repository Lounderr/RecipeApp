package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Recipe;
import model.User;

import static org.junit.Assert.*;

public class FavoritesSteps {

    private final SharedContext ctx;

    public FavoritesSteps(SharedContext ctx) {
        this.ctx = ctx;
    }

    private Recipe findRecipeByTitle(String title) {
        return Recipe.getAllRecipes().stream()
                .filter(r -> r.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Test setup error: recipe '" + title + "' was not found in the store"));
    }

    private User findUserByName(String name) {
        return User.getAllUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Test setup error: user '" + name + "' was not found in the store"));
    }

    @Given("the recipe {string} is available in the system")
    public void theRecipeIsAvailableInTheSystem(String title) {
        boolean exists = Recipe.getAllRecipes().stream()
                .anyMatch(r -> r.getTitle().equals(title));
        assertTrue("Pre-condition violated: recipe '" + title + "' should be available in the system", exists);
    }

    @Given("I am logged in as a USER named {string}")
    public void iAmLoggedInAsAUserNamed(String name) {
        ctx.currentUser = findUserByName(name);
        assertEquals("Expected '" + name + "' to have the USER role",
                "USER", ctx.currentUser.getRole().getRoleEnum().name());
    }

    @Given("I am logged in as an ADMIN named {string}")
    public void iAmLoggedInAsAnAdminNamed(String name) {
        ctx.currentUser = findUserByName(name);
        assertEquals("Expected '" + name + "' to have the ADMIN role",
                "ADMIN", ctx.currentUser.getRole().getRoleEnum().name());
    }

    @And("I have already added {string} to my favorites")
    public void iHaveAlreadyAddedToMyFavorites(String title) {
        Recipe recipe = findRecipeByTitle(title);
        try {
            ctx.currentUser.addFavorite(recipe);
        } catch (Exception e) {
            throw new RuntimeException("Test pre-condition failed: could not add '" + title + "' to favorites: " + e.getMessage(), e);
        }
    }

    @When("I add {string} to my favorites")
    public void iAddToMyFavorites(String title) {
        Recipe recipe = findRecipeByTitle(title);
        try {
            ctx.currentUser.addFavorite(recipe);
            ctx.lastException = null;
        } catch (Exception e) {
            ctx.lastException = e;
        }
    }

    @When("I remove {string} from my favorites")
    public void iRemoveFromMyFavorites(String title) {
        Recipe recipe = findRecipeByTitle(title);
        try {
            ctx.currentUser.removeFavorite(recipe);
            ctx.lastException = null;
        } catch (Exception e) {
            ctx.lastException = e;
        }
    }

    @Then("my favorites list should contain {string}")
    public void myFavoritesListShouldContain(String title) {
        boolean found = ctx.currentUser.getFavorites().stream()
                .anyMatch(r -> r.getTitle().equals(title));
        assertTrue("Expected '" + title + "' to be present in the favorites list", found);
    }

    @Then("my favorites list should be empty")
    public void myFavoritesListShouldBeEmpty() {
        assertTrue("Expected the favorites list to be empty after removal", ctx.currentUser.getFavorites().isEmpty());
    }

    @Then("the favorites action should fail with message {string}")
    public void theFavoritesActionShouldFailWithMessage(String expectedMessage) {
        assertNotNull("Expected the favorites action to throw an exception, but none was thrown", ctx.lastException);
        assertEquals("Favorites failure message mismatch", expectedMessage, ctx.lastException.getMessage());
    }
}
