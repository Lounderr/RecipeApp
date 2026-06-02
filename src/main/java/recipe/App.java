package recipe;

import recipe.app.model.Ingredient;
import recipe.app.model.Recipe;
import recipe.app.model.User;
import recipe.app.model.UserRole;
import recipe.app.service.IngredientService;
import recipe.app.service.RecipeBuilderService;
import recipe.app.service.SubscriptionService;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        SubscriptionService subscriptionService = new SubscriptionService();
        IngredientService ingredientService = new IngredientService();
        RecipeBuilderService recipeBuilderService = new RecipeBuilderService();

        User currentUser = new User("chef_john", "john@recipe.com", UserRole.USER, 5);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to RecipeApp!");

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("Logged in as: " + currentUser.getUsername() + " | Role: " + currentUser.getRole() + " | Credits: " + currentUser.getCredits());
            System.out.println("1. View Available Ingredients");
            System.out.println("2. Create a Recipe");
            System.out.println("3. View Recipe History");
            System.out.println("4. Purchase Credits");
            System.out.println("5. Upgrade to PREMIUM");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.println("\n--- Available Ingredients ---");
                        List<Ingredient> ingredients = ingredientService.getAvailableIngredients(currentUser);
                        if (ingredients.isEmpty()) {
                            System.out.println("No ingredients available.");
                        } else {
                            for (int i = 0; i < ingredients.size(); i++) {
                                Ingredient ing = ingredients.get(i);
                                System.out.println((i + 1) + ". " + ing.getName() + " (" + ing.getCategory() + ") - Premium: " + ing.isPremium());
                            }
                        }
                        break;

                    case "2":
                        System.out.println("\n--- Create a New Recipe ---");
                        List<Ingredient> availIngredients = ingredientService.getAvailableIngredients(currentUser);
                        if (availIngredients.isEmpty()) {
                            System.out.println("No ingredients available to create a recipe.");
                            break;
                        }

                        System.out.print("Enter recipe title: ");
                        String title = scanner.nextLine();

                        System.out.print("Enter recipe instructions: ");
                        String instructions = scanner.nextLine();

                        System.out.println("Select an ingredient by number:");
                        for (int i = 0; i < availIngredients.size(); i++) {
                            System.out.println((i + 1) + ". " + availIngredients.get(i).getName());
                        }
                        System.out.print("Choice: ");
                        int ingChoice = Integer.parseInt(scanner.nextLine());
                        
                        if (ingChoice < 1 || ingChoice > availIngredients.size()) {
                            System.out.println("Invalid ingredient choice.");
                            break;
                        }
                        Ingredient chosenIngredient = availIngredients.get(ingChoice - 1);

                        // Double check selection through the service
                        Ingredient verifiedIngredient = ingredientService.selectIngredient(currentUser, chosenIngredient.getName(), chosenIngredient.getCategory());

                        Recipe newRecipe = recipeBuilderService.createRecipe(currentUser, title, instructions, verifiedIngredient);

                        System.out.println("\nSuccess! Your recipe was published.");
                        System.out.println("Recipe ID: " + newRecipe.getId());
                        System.out.println("Remaining Credits: " + currentUser.getCredits());
                        break;

                    case "3":
                        System.out.println("\n--- Your Recipe History ---");
                        List<Recipe> history = recipeBuilderService.getHistory(currentUser);
                        if (history.isEmpty()) {
                            System.out.println("You haven't created any recipes yet.");
                        } else {
                            history.forEach(r -> System.out.println("- " + r.getTitle() + " (Main Ingredient: " + r.getIngredientKey() + ")"));
                        }
                        break;

                    case "4":
                        System.out.println("\n--- Purchase Credits ---");
                        System.out.print("Enter amount to purchase: ");
                        int amount = Integer.parseInt(scanner.nextLine());
                        subscriptionService.purchaseCredits(currentUser, amount);
                        System.out.println("Successfully added " + amount + " credits. New balance: " + currentUser.getCredits());
                        break;

                    case "5":
                        System.out.println("\n--- Upgrade Role ---");
                        if (currentUser.getRole() == UserRole.PREMIUM) {
                            System.out.println("You are already a PREMIUM user.");
                        } else {
                            currentUser.setRole(UserRole.PREMIUM);
                            System.out.println("Congratulations! You have been upgraded to PREMIUM.");
                        }
                        break;

                    case "6":
                        running = false;
                        System.out.println("Exiting RecipeApp. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
