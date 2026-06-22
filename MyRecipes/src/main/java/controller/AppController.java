package controller;

import model.Recipe;
import model.Report;
import model.User;

import java.util.List;
import java.util.Scanner;

public class AppController {
    private final Scanner scanner;
    private User currentUser;

    public AppController() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            clearConsole();
            System.out.println("======================================");
            System.out.println("      Welcome to MyRecipes App!       ");
            System.out.println("======================================");

            if (currentUser == null) {
                showAuthMenu();
            } else {
                showMainMenu();
            }
            pause();
        }
    }

    private void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void showAuthMenu() {
        System.out.println("\n--- Authentication ---");
        System.out.println("1. Log in");
        System.out.println("2. Register");
        System.out.println("0. Exit App");
        System.out.print("Select an option: ");
        
        String option = scanner.nextLine();
        
        switch (option) {
            case "1":
                loginFlow();
                break;
            case "2":
                registerFlow();
                break;
            case "0":
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void loginFlow() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            currentUser = User.login(username, password);
            System.out.println("Login successful! Welcome, " + currentUser.getUsername());
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private void registerFlow() {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine();
        System.out.print("Choose a password: ");
        String password = scanner.nextLine();
        
        try {
            currentUser = User.register(username, password);
            System.out.println("Registration successful! You are now logged in as " + currentUser.getUsername());
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private void showMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("Role: " + currentUser.getRole());
        
        System.out.println("1. Read Recipes");
        System.out.println("2. Add Recipe to Favorites");
        System.out.println("3. See Favorite Recipes");
        System.out.println("4. Remove Recipe from Favorites");
        System.out.println("5. Report Recipe");
        
        if (currentUser.hasPermission("CREATE_RECIPE")) System.out.println("6. Create Recipe");
        if (currentUser.hasPermission("UPDATE_RECIPE")) System.out.println("7. Update Recipe");
        if (currentUser.hasPermission("DELETE_RECIPE")) System.out.println("8. Delete Recipe");
        if (currentUser.hasPermission("PROMOTE_USER")) System.out.println("9. Promote User to Admin");
        if (currentUser.hasPermission("DEMOTE_ADMIN")) System.out.println("10. Demote Admin");
        if (currentUser.hasPermission("VIEW_REPORTS")) System.out.println("11. View Reports");
        
        System.out.println("0. Log out");
        System.out.print("Select an option: ");
        
        String option = scanner.nextLine();
        
        try {
            switch (option) {
                case "1": readRecipes(); break;
                case "2": addFavorite(); break;
                case "3": seeFavorites(); break;
                case "4": removeFavorite(); break;
                case "5": reportRecipe(); break;
                case "6": if (checkPermission("CREATE_RECIPE")) createRecipe(); break;
                case "7": if (checkPermission("UPDATE_RECIPE")) updateRecipe(); break;
                case "8": if (checkPermission("DELETE_RECIPE")) deleteRecipe(); break;
                case "9": if (checkPermission("PROMOTE_USER")) promoteUser(); break;
                case "10": if (checkPermission("DEMOTE_ADMIN")) demoteAdmin(); break;
                case "11": if (checkPermission("VIEW_REPORTS")) viewReports(); break;
                case "0":
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private boolean checkPermission(String permission) {
        if (!currentUser.hasPermission(permission)) {
            System.out.println("Permission denied: " + permission);
            return false;
        }
        return true;
    }

    private void readRecipes() {
        List<Recipe> recipes = Recipe.getAllRecipes();
        if (recipes.isEmpty()) {
            System.out.println("No recipes available.");
            return;
        }
        System.out.println("\n--- All Recipes ---");
        for (int i = 0; i < recipes.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, recipes.get(i).getTitle());
            System.out.println("  Ingredients: " + recipes.get(i).getIngredients());
            System.out.println("  Instructions: " + recipes.get(i).getInstructions());
            System.out.println("-------------------------");
        }
    }

    private Recipe selectRecipe() {
        List<Recipe> recipes = Recipe.getAllRecipes();
        if (recipes.isEmpty()) {
            System.out.println("No recipes available.");
            return null;
        }
        for (int i = 0; i < recipes.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, recipes.get(i).getTitle());
        }
        System.out.print("Enter recipe number: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < recipes.size()) {
                return recipes.get(index);
            } else {
                System.out.println("Invalid recipe number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
        return null;
    }

    private void addFavorite() throws Exception {
        Recipe recipe = selectRecipe();
        if (recipe != null) {
            currentUser.addFavorite(recipe);
            System.out.println("Added to favorites!");
        }
    }

    private void seeFavorites() {
        List<Recipe> favs = currentUser.getFavorites();
        if (favs.isEmpty()) {
            System.out.println("You have no favorite recipes.");
            return;
        }
        System.out.println("\n--- Your Favorites ---");
        for (Recipe r : favs) {
            System.out.println("- " + r.getTitle());
        }
    }

    private void removeFavorite() throws Exception {
        List<Recipe> favs = currentUser.getFavorites();
        if (favs.isEmpty()) {
            System.out.println("You have no favorite recipes.");
            return;
        }
        for (int i = 0; i < favs.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, favs.get(i).getTitle());
        }
        System.out.print("Enter favorite number to remove: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < favs.size()) {
                currentUser.removeFavorite(favs.get(index));
                System.out.println("Removed from favorites!");
            } else {
                System.out.println("Invalid number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void reportRecipe() throws Exception {
        Recipe recipe = selectRecipe();
        if (recipe != null) {
            System.out.println("Select Reason:");
            System.out.println("1. Incorrect");
            System.out.println("2. Spam");
            System.out.println("3. Bad");
            System.out.print("Enter reason number: ");
            String reasonOpt = scanner.nextLine();
            Report.Reason reason;
            switch (reasonOpt) {
                case "1": reason = Report.Reason.INCORRECT; break;
                case "2": reason = Report.Reason.SPAM; break;
                case "3": reason = Report.Reason.BAD; break;
                default: 
                    System.out.println("Invalid reason.");
                    return;
            }
            currentUser.reportRecipe(recipe, reason);
            System.out.println("Recipe reported successfully.");
        }
    }

    private void createRecipe() throws Exception {
        System.out.print("Enter recipe title: ");
        String title = scanner.nextLine();
        System.out.print("Enter ingredients: ");
        String ingredients = scanner.nextLine();
        System.out.print("Enter instructions: ");
        String instructions = scanner.nextLine();

        currentUser.createRecipe(title, ingredients, instructions);
        System.out.println("Recipe created successfully!");
    }

    private void updateRecipe() throws Exception {
        Recipe recipe = selectRecipe();
        if (recipe != null) {
            System.out.print("Enter new recipe title (leave blank to keep current: " + recipe.getTitle() + "): ");
            String title = scanner.nextLine();
            if (title.isBlank()) title = recipe.getTitle();

            System.out.print("Enter new ingredients (leave blank to keep current): ");
            String ingredients = scanner.nextLine();
            if (ingredients.isBlank()) ingredients = recipe.getIngredients();

            System.out.print("Enter new instructions (leave blank to keep current): ");
            String instructions = scanner.nextLine();
            if (instructions.isBlank()) instructions = recipe.getInstructions();

            currentUser.updateRecipe(recipe, title, ingredients, instructions);
            System.out.println("Recipe updated successfully!");
        }
    }

    private void deleteRecipe() throws Exception {
        Recipe recipe = selectRecipe();
        if (recipe != null) {
            currentUser.deleteRecipe(recipe);
            System.out.println("Recipe deleted successfully!");
        }
    }

    private void promoteUser() throws Exception {
        List<User> users = User.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("%d. %s (Role: %s)\n", i + 1, users.get(i).getUsername(), users.get(i).getRole());
        }
        System.out.print("Enter user number to promote: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < users.size()) {
                currentUser.promote(users.get(index));
                System.out.println("User promoted to ADMIN successfully!");
            } else {
                System.out.println("Invalid user number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void demoteAdmin() throws Exception {
        List<User> users = User.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("%d. %s (Role: %s, Promoted: %s)\n", i + 1, users.get(i).getUsername(), users.get(i).getRole(), users.get(i).getPromotionDate());
        }
        System.out.print("Enter admin number to demote: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < users.size()) {
                currentUser.demote(users.get(index));
                System.out.println("Admin demoted to USER successfully!");
            } else {
                System.out.println("Invalid user number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void viewReports() {
        List<Report> reports = Report.getAllReports();
        if (reports.isEmpty()) {
            System.out.println("No reports available.");
            return;
        }
        System.out.println("\n--- Reports ---");
        for (Report r : reports) {
            System.out.println(r.toString());
        }
    }
}
