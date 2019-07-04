package boardgame.components.entity.models.event.effect_blocks;

public enum EffectWho {
    ACTIVATOR(0.5), CHOSEN_COMPETITOR(0.25), RANDOM_COMPETITOR(0.25);

    private double probability;

    EffectWho(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }
}
