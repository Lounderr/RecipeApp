package recipe.app.model;

public class Recipe {
    private String id;
    private String authorUsername;
    private String title;
    private String instructions;
    private String ingredientKey;
    private RecipeStatus status;
    private Ingredient ingredient;

    public Recipe() {}

    public Recipe(String id, String authorUsername, String title, String instructions, String ingredientKey, RecipeStatus status) {
        this.id = id;
        this.authorUsername = authorUsername;
        this.title = title;
        this.instructions = instructions;
        this.ingredientKey = ingredientKey;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIngredientKey() {
        return ingredientKey;
    }

    public void setIngredientKey(String ingredientKey) {
        this.ingredientKey = ingredientKey;
    }

    public RecipeStatus getStatus() {
        return status;
    }

    public void setStatus(RecipeStatus status) {
        this.status = status;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
