package stepdefs;

import io.cucumber.java.Before;
import model.Recipe;
import model.Report;
import model.Role;
import model.RoleEnum;
import model.User;

import java.time.LocalDateTime;

public class SharedContext {

    public User currentUser;
    public Exception lastException;
    public Object lastResult;

    public User seniorAdmin;
    public User juniorAdmin;
    public User regularUser;
    public Recipe pastaRecipe;
    public Recipe saladRecipe;

    @Before
    public void resetAndSeed() {
        User.clearStore();
        Recipe.clearStore();
        Report.clearStore();

        currentUser   = null;
        lastException = null;
        lastResult    = null;

        seniorAdmin = new User("seniorAdmin", "admin123", new Role(RoleEnum.ADMIN));
        seniorAdmin.setPromotionDate(LocalDateTime.now().minusHours(1));
        User.addUserToStore(seniorAdmin);

        juniorAdmin = new User("juniorAdmin", "admin456", new Role(RoleEnum.ADMIN));
        User.addUserToStore(juniorAdmin);

        regularUser = new User("alice", "pass123", new Role(RoleEnum.USER));
        User.addUserToStore(regularUser);

        pastaRecipe = new Recipe("Pasta Carbonara", "eggs, bacon, parmesan", "boil pasta, mix sauce");
        Recipe.addRecipeToStore(pastaRecipe);

        saladRecipe = new Recipe("Caesar Salad", "lettuce, croutons, dressing", "toss and serve");
        Recipe.addRecipeToStore(saladRecipe);
    }
}
