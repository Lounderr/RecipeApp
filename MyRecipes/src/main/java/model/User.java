package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private static final List<User> ALL_USERS = new ArrayList<>();

    public static void clearStore() {
        ALL_USERS.clear();
    }

    private String username;
    private String password;
    private Role role;
    private LocalDateTime promotionDate;
    private final List<Recipe> favorites = new ArrayList<>();

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        if (role.getPermissions().contains("PROMOTE_USER")) {
            this.promotionDate = LocalDateTime.now();
        }
    }

    public boolean hasPermission(String permission) {
        return role.getPermissions().contains(permission);
    }

    public static List<User> getAllUsers() {
        return ALL_USERS;
    }

    public static void addUserToStore(User user) {
        ALL_USERS.add(user);
    }

    public static User register(String username, String password) throws Exception {
        if (password.length() < 3) {
            throw new Exception("Password must be at least 3 characters long.");
        }
        for (User u : ALL_USERS) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                throw new Exception("Username already exists!");
            }
        }
        User newUser = new User(username, password, new Role(RoleEnum.USER));
        addUserToStore(newUser);
        return newUser;
    }

    public static User login(String username, String password) throws Exception {
        for (User u : ALL_USERS) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                if (u.getPassword().equals(password)) {
                    return u;
                } else {
                    throw new Exception("Invalid password!");
                }
            }
        }
        throw new Exception("User not found!");
    }

    public void addFavorite(Recipe recipe) throws Exception {
        if (!role.getPermissions().contains("ADD_FAVORITE")) {
            throw new Exception("Permission denied: ADD_FAVORITE");
        }
        if (!favorites.contains(recipe)) {
            favorites.add(recipe);
        } else {
            throw new Exception("Recipe is already in your favorites.");
        }
    }

    public void removeFavorite(Recipe recipe) throws Exception {
        if (!role.getPermissions().contains("REMOVE_FAVORITE")) {
            throw new Exception("Permission denied: REMOVE_FAVORITE");
        }
        if (favorites.contains(recipe)) {
            favorites.remove(recipe);
        } else {
            throw new Exception("Recipe not found in favorites.");
        }
    }

    public void reportRecipe(Recipe recipe, Report.Reason reason) throws Exception {
        if (!role.getPermissions().contains("REPORT_RECIPE")) {
            throw new Exception("Permission denied: REPORT_RECIPE");
        }
        Report report = new Report(this, recipe, reason);
        Report.addReportToStore(report);
    }

    public void promote(User targetUser) throws Exception {
        if (!role.getPermissions().contains("PROMOTE_USER")) {
            throw new Exception("Permission denied: PROMOTE_USER");
        }
        if (targetUser.hasPermission("PROMOTE_USER")) {
            throw new Exception("User already has admin privileges.");
        }
        targetUser.setRole(new Role(RoleEnum.ADMIN));
        targetUser.setPromotionDate(LocalDateTime.now());
    }

    public void demote(User targetAdmin) throws Exception {
        if (!role.getPermissions().contains("DEMOTE_ADMIN")) {
            throw new Exception("Permission denied: DEMOTE_ADMIN");
        }
        if (!targetAdmin.hasPermission("PROMOTE_USER")) {
            throw new Exception("Target does not have admin privileges.");
        }
        if (targetAdmin.getPromotionDate() != null && this.promotionDate != null) {
            if (targetAdmin.getPromotionDate().isBefore(this.promotionDate) || targetAdmin.getPromotionDate().isEqual(this.promotionDate)) {
                throw new Exception("Cannot demote an admin who was promoted before or at the same time as you.");
            }
        } else {
            throw new Exception("Promotion date rules violated. Cannot demote.");
        }
        targetAdmin.setRole(new Role(RoleEnum.USER));
        targetAdmin.setPromotionDate(null);
    }

    public void createRecipe(String title, String ingredients, String instructions) throws Exception {
        if (!role.getPermissions().contains("CREATE_RECIPE")) {
            throw new Exception("Permission denied: CREATE_RECIPE");
        }
        Recipe recipe = new Recipe(title, ingredients, instructions);
        Recipe.addRecipeToStore(recipe);
    }

    public void updateRecipe(Recipe recipe, String title, String ingredients, String instructions) throws Exception {
        if (!role.getPermissions().contains("UPDATE_RECIPE")) {
            throw new Exception("Permission denied: UPDATE_RECIPE");
        }
        recipe.setTitle(title);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
    }

    public void deleteRecipe(Recipe recipe) throws Exception {
        if (!role.getPermissions().contains("DELETE_RECIPE")) {
            throw new Exception("Permission denied: DELETE_RECIPE");
        }
        Recipe.removeRecipeFromStore(recipe);
        for (User u : ALL_USERS) {
            u.getFavorites().remove(recipe);
        }
        Report.getAllReports().removeIf(report -> report.getRecipe().equals(recipe));
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public LocalDateTime getPromotionDate() { return promotionDate; }
    public void setPromotionDate(LocalDateTime promotionDate) { this.promotionDate = promotionDate; }
    public List<Recipe> getFavorites() { return favorites; }
}
