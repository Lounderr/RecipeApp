package recipe.app.service;

import recipe.app.model.User;

public class SubscriptionService {

    public int getBalance(User user) {
        return user.getCredits();
    }

    public void purchaseCredits(User user, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        user.setCredits(user.getCredits() + amount);
    }

    public void spendCredit(User user) {
        if (user.getCredits() < 1) {
            throw new IllegalStateException("Insufficient credits");
        }
        user.setCredits(user.getCredits() - 1);
    }
}
