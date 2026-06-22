package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.RoleEnum;
import model.User;

import static org.junit.Assert.*;

public class AuthenticationSteps {

    private final SharedContext ctx;

    public AuthenticationSteps(SharedContext ctx) {
        this.ctx = ctx;
    }

    @Given("no user with username {string} exists in the system")
    public void noUserWithUsernameExists(String username) {
        boolean exists = User.getAllUsers().stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
        assertFalse("Pre-condition violated: user '" + username + "' should NOT exist in the system", exists);
    }

    @Given("a user {string} already exists in the system")
    public void aUserAlreadyExistsInTheSystem(String username) {
        boolean exists = User.getAllUsers().stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
        assertTrue("Pre-condition violated: user '" + username + "' should already exist in the system", exists);
    }

    @When("I register with username {string} and password {string}")
    public void iRegisterWithUsernameAndPassword(String username, String password) {
        try {
            ctx.lastResult    = User.register(username, password);
            ctx.lastException = null;
        } catch (Exception e) {
            ctx.lastException = e;
            ctx.lastResult    = null;
        }
    }

    @When("I log in with username {string} and password {string}")
    public void iLogInWithUsernameAndPassword(String username, String password) {
        try {
            ctx.lastResult    = User.login(username, password);
            ctx.lastException = null;
        } catch (Exception e) {
            ctx.lastException = e;
            ctx.lastResult    = null;
        }
    }

    @Then("the registration should succeed")
    public void theRegistrationShouldSucceed() {
        assertNull("Expected registration to succeed but got exception: " +
                (ctx.lastException != null ? ctx.lastException.getMessage() : "null"),
                ctx.lastException);
        assertNotNull("Expected a User object to be returned after registration", ctx.lastResult);
    }

    @And("the registered user should have the USER role")
    public void theRegisteredUserShouldHaveTheUserRole() {
        User user = (User) ctx.lastResult;
        assertEquals("Newly registered accounts must always start with the USER role",
                RoleEnum.USER, user.getRole().getRoleEnum());
    }

    @Then("the registration should fail with message {string}")
    public void theRegistrationShouldFailWithMessage(String expectedMessage) {
        assertNotNull("Expected registration to fail, but no exception was thrown", ctx.lastException);
        assertEquals("Registration failure message mismatch", expectedMessage, ctx.lastException.getMessage());
    }

    @Then("the login should succeed")
    public void theLoginShouldSucceed() {
        assertNull("Expected login to succeed but got exception: " +
                (ctx.lastException != null ? ctx.lastException.getMessage() : "null"),
                ctx.lastException);
        assertNotNull("Expected a User object to be returned after login", ctx.lastResult);
    }

    @And("the logged-in user should be {string}")
    public void theLoggedInUserShouldBe(String expectedUsername) {
        User user = (User) ctx.lastResult;
        assertEquals("Logged-in username mismatch", expectedUsername, user.getUsername());
    }

    @Then("the login should fail with message {string}")
    public void theLoginShouldFailWithMessage(String expectedMessage) {
        assertNotNull("Expected login to fail, but no exception was thrown", ctx.lastException);
        assertEquals("Login failure message mismatch", expectedMessage, ctx.lastException.getMessage());
    }
}
