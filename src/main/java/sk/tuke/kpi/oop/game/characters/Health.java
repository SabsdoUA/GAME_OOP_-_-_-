package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int currentHealth;
    private final int maxHealth;
    private final List<FatigueEffect> fatigueEffects = new ArrayList<>();
    private boolean exhausted;

    public Health(int currentHealth, int maxHealth) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
    }

    public Health(int maxHealth) {
        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
    }


    public int getValue() {
        return currentHealth;
    }

    public void refill(int amount) {
        currentHealth = Math.min(currentHealth + amount, maxHealth);
    }

    public void restore() {
        currentHealth = maxHealth;
    }

    public void drain(int amount) {
        if (exhausted) return;

        currentHealth -= amount;
        if (currentHealth <= 0) {
            currentHealth = 0;
            triggerFatigue();
        }
    }

    public void exhaust() {
        if (exhausted) return;

        currentHealth = 0;
        triggerFatigue();
    }

    private void triggerFatigue() {
        if (exhausted) return;
        exhausted = true;

        for (FatigueEffect effect : fatigueEffects) {
            effect.apply();
        }
    }

    @FunctionalInterface
    public interface FatigueEffect {
        void apply();
    }

    public void onFatigued(FatigueEffect effect) {
        fatigueEffects.add(effect);
    }


}
