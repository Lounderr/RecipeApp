package recipe.app.model;

public class Ingredient {
    private String name;
    private String category;
    private boolean premium;

    public Ingredient() {}

    public Ingredient(String name, String category, boolean premium) {
        this.name = name;
        this.category = category;
        this.premium = premium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
