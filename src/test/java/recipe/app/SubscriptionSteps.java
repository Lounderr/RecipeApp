package recipe.app;

import recipe.app.model.User;
import recipe.app.model.UserRole;
import recipe.app.service.SubscriptionService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SubscriptionSteps {
    private User user;
    private Exception exception;
    private int balance;
    private final SubscriptionService service = new SubscriptionService();

    @Given("The user has a balance of {int} credits")
    public void userHasBalance(int credits) {
        user = new User("testuser", "test@mail.com", UserRole.USER, credits);
        exception = null;
    }

    @When("The user purchases {int} credits")
    public void purchaseCredits(int amount) {
        try {
            service.purchaseCredits(user, amount);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("The user spends {int} credit")
    public void spendCredit(int amount) {
        try {
            service.spendCredit(user);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("The user checks the balance")
    public void checkBalance() {
        balance = service.getBalance(user);
    }

    @Then("the balance is {int} credits")
    public void balanceIs(int expected) {
        if (exception == null) {
            assertEquals(expected, user.getCredits());
        }
    }

    @Then("a credit error is shown {string}")
    public void showsCreditError(String message) {
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }
}
