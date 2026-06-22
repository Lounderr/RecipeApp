package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Recipe {
    private static final List<Recipe> ALL_RECIPES = new ArrayList<>();

    public static void clearStore() {
        ALL_RECIPES.clear();
    }

    private String id;
    private String title;
    private String ingredients;
    private String instructions;

    public Recipe(String title, String ingredients, String instructions) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public static List<Recipe> getAllRecipes() {
        return ALL_RECIPES;
    }

    public static void addRecipeToStore(Recipe recipe) {
        ALL_RECIPES.add(recipe);
    }
    
    public static void removeRecipeFromStore(Recipe recipe) {
        ALL_RECIPES.remove(recipe);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    
    @Override
    public String toString() {
        return String.format("[%s] %s\nIngredients: %s\nInstructions: %s\n", id, title, ingredients, instructions);
    }
}
