package boardgame.components.entity.models.event.effect_blocks;

import boardgame.logic.effect_reading.TranslationType;

import static boardgame.logic.effect_reading.TranslationType.BACK;
import static boardgame.logic.effect_reading.TranslationType.GAINS_LOSES;


public enum EffectWhat {
    COINS(0.4, 1, "coins" , GAINS_LOSES),
    MOVES(0.3, 1, "moves",BACK),
    MAX_SPEED(0.15, 3, "maximum speed" , GAINS_LOSES),
    MIN_SPEED(0.15, 2.5,"minimum speed" , GAINS_LOSES);

    private double probability;
    private double value;
    private String view;
    private TranslationType translation;

    EffectWhat(double probability, double value, String view,TranslationType translation) {
        this.probability = probability;
        this.value = value;
        this.view = view;
        this.translation = translation;
    }


    public TranslationType getTranslation() {
        return translation;
    }

    public double getProbability() {
        return probability;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return view;
    }
}

//service locator