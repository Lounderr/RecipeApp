package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.User;

import static org.junit.Assert.*;

public class AdminUserManagementSteps {

    private final SharedContext ctx;

    public AdminUserManagementSteps(SharedContext ctx) {
        this.ctx = ctx;
    }

    private User findUserByName(String name) {
        return User.getAllUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Test setup error: user '" + name + "' was not found in the store"));
    }

    @Given("{string} exists as an ADMIN in the system")
    public void existsAsAdminInSystem(String username) {
        User user = findUserByName(username);
        assertEquals("Pre-condition violated: expected '" + username + "' to have the ADMIN role",
                "ADMIN", user.getRole().getRoleEnum().name());
    }

    @Given("{string} exists as a USER in the system")
    public void existsAsUserInSystem(String username) {
        User user = findUserByName(username);
        assertEquals("Pre-condition violated: expected '" + username + "' to have the USER role",
                "USER", user.getRole().getRoleEnum().name());
    }

    @And("{string} was promoted before {string}")
    public void wasPromotedBefore(String earlierName, String laterName) {
        User earlier = findUserByName(earlierName);
        User later   = findUserByName(laterName);
        assertNotNull("Expected '" + earlierName + "' to have a promotion date set", earlier.getPromotionDate());
        assertNotNull("Expected '" + laterName + "' to have a promotion date set", later.getPromotionDate());
        assertTrue("Expected '" + earlierName + "' to have been promoted strictly before '" + laterName + "'",
                earlier.getPromotionDate().isBefore(later.getPromotionDate()));
    }

    @Given("{string} is acting as the current admin")
    public void isActingAsTheCurrentAdmin(String adminName) {
        ctx.currentUser = findUserByName(adminName);
        assertTrue("Expected '" + adminName + "' to hold the PROMOTE_USER permission (ADMIN role)",
                ctx.currentUser.hasPermission("PROMOTE_USER"));
    }

    @Given("{string} is the current user")
    public void isTheCurrentUser(String username) {
        ctx.currentUser = findUserByName(username);
    }

    @When("{string} promotes {string} to admin")
    public void promotesToAdmin(String actorName, String targetName) {
        User actor  = findUserByName(actorName);
        User target = findUserByName(targetName);
        try {
            actor.promote(target);
            ctx.lastException = null;
        } catch (Exception e) {
            ctx.lastException = e;
        }
    }

    @When("{string} tries to promote {string}")
    public void triesToPromote(String actorName, String targetName) {
        User actor  = findUserByName(actorName);
        User target = findUserByName(targetName);
        try {
            actor.promote(target);
            ctx.lastException = null;
        } catch (Exception e) {
            ctx.lastException = e;
        }
    }

    @When("{string} demotes {string}")
    public void demotes(String actorName, String targetName) {
        User actor  = findUserByName(actorName);
        User target = findUserByName(targetName);
        try {
            actor.demote(target);
            ctx.lastException = null;
        } catch (Exception e) {
            ctx.lastException = e;
        }
    }

    @Then("{string} should now have the ADMIN role")
    public void shouldNowHaveAdminRole(String username) {
        User user = findUserByName(username);
        assertTrue("Expected '" + username + "' to hold the PROMOTE_USER permission (i.e., ADMIN role)",
                user.hasPermission("PROMOTE_USER"));
        assertEquals("Expected '" + username + "' to have role ADMIN",
                "ADMIN", user.getRole().getRoleEnum().name());
    }

    @And("{string} should have a promotion date assigned")
    public void shouldHaveAPromotionDateAssigned(String username) {
        User user = findUserByName(username);
        assertNotNull("Expected '" + username + "' to have a promotion date set after being promoted",
                user.getPromotionDate());
    }

    @Then("{string} should now have the USER role")
    public void shouldNowHaveUserRole(String username) {
        User user = findUserByName(username);
        assertFalse("Expected '" + username + "' to lose the PROMOTE_USER permission after demotion",
                user.hasPermission("PROMOTE_USER"));
        assertEquals("Expected '" + username + "' to have role USER after demotion",
                "USER", user.getRole().getRoleEnum().name());
    }

    @And("{string} should have no promotion date")
    public void shouldHaveNoPromotionDate(String username) {
        User user = findUserByName(username);
        assertNull("Expected promotion date to be cleared after demotion for '" + username + "'",
                user.getPromotionDate());
    }

    @Then("the admin action should fail with message {string}")
    public void theAdminActionShouldFailWithMessage(String expectedMessage) {
        assertNotNull("Expected the admin action to throw an exception, but none was thrown", ctx.lastException);
        assertEquals("Admin action failure message mismatch", expectedMessage, ctx.lastException.getMessage());
    }
}
