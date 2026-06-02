package recipe.app.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private UserRole role;
    private int credits;
    private List<Recipe> recipes = new ArrayList<>();

    public User() {}

    public User(String username, String email, UserRole role, int credits) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.credits = credits;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
