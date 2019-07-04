package boardgame.components.entity.models.event.effect_blocks;

public enum EffectHowMuch {
    EXACT_STANDARD(0.2, 1.0),
    AROUND_STANDARD(0.2,1.0),
    LITTLE(0.2,0.5),
    MANY(0.2,1.5),
    VERY_LITTLE(0.1,0.20),
    VERY_MANY(0.1,3.0);

    private double probability;
    private double quantityModifier;

    EffectHowMuch(double probability,double quantityModifier) {
        this.probability = probability;
        this.quantityModifier = quantityModifier;
    }

    public double getProbability() {
        return probability;
    }

    public double getQuantityModifier() {
        return quantityModifier;
    }
}
