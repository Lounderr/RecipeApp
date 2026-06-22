package org.example;

import controller.AppController;
import model.Recipe;
import model.Role;
import model.RoleEnum;
import model.User;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        seedData();
        
        AppController appController = new AppController();
        appController.start();
    }

    private static void seedData() {
        User admin = new User("admin", "admin", new Role(RoleEnum.ADMIN));
        admin.setPromotionDate(LocalDateTime.of(2000, 1, 1, 0, 0));
        User.addUserToStore(admin);

        User user = new User("user", "user", new Role(RoleEnum.USER));
        User.addUserToStore(user);

        Recipe r1 = new Recipe("Pancakes", "Flour, Milk, Eggs", "Mix and fry in a pan.");
        Recipe r2 = new Recipe("Spaghetti", "Pasta, Tomato Sauce, Meat", "Boil pasta, cook meat, mix with sauce.");
        Recipe.addRecipeToStore(r1);
        Recipe.addRecipeToStore(r2);
    }
}
