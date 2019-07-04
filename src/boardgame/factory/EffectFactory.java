package boardgame.factory;

import boardgame.components.entity.Effect;
import boardgame.components.entity.models.event.effect_blocks.EffectHowMuch;
import boardgame.components.entity.models.event.effect_blocks.EffectWhat;
import boardgame.components.entity.models.event.effect_blocks.EffectWho;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EffectFactory {

    private List<Effect> randomEffect;

    public EffectFactory() {
        buildRandomEffects();
    }

    private void buildRandomEffects() {
        randomEffect = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            randomEffect.add(createRandomEffect());
        }

    }

    public List<Effect> getRandomEffects(int effectsQuantity) {
        List<Effect> randomEffects = new ArrayList<>();
        for (int i = 0; i < effectsQuantity; i++) {
            randomEffects.add(getRandomEffect());
        }
        return randomEffects;
    }

    public Effect getRandomEffect() {
        randomEffect.add(createRandomEffect());
        Random random = new Random();
        int randomIndex = random.nextInt(randomEffect.size());
        Effect found = randomEffect.get(randomIndex);
        randomEffect.remove(randomIndex);
        return found;
    }

    private Effect createRandomEffect() {
        Effect effect = new Effect(
                getRandomWho(),
                getRandomWhat(),
                getRandomHowMuch(),
                getRandomWhat(),
                getRandomHowMuch());


        if (effect.getProfit().equals(effect.getCost())) {
            return createRandomEffect();
        }
        return effect;
    }

    private EffectWho getRandomWho() {
        double sumPropability = 0;
        double randomNumber = Math.random();
        for (EffectWho effectBlock : EffectWho.values()) {
            sumPropability += effectBlock.getProbability();
            if (randomNumber <= sumPropability) {
                return effectBlock;
            }
        }
        throw new IllegalStateException("Can't generate random block");
    }

    private EffectWhat getRandomWhat() {
        double sumPropability = 0;
        double randomNumber = Math.random();
        for (EffectWhat effectBlock : EffectWhat.values()) {
            sumPropability += effectBlock.getProbability();
            if (randomNumber <= sumPropability) {
                return effectBlock;
            }
        }
        throw new IllegalStateException("Can't generate random block");
    }

    private EffectHowMuch getRandomHowMuch() {
        double sumPropability = 0;
        double randomNumber = Math.random();
        for (EffectHowMuch effectBlock : EffectHowMuch.values()) {
            sumPropability += effectBlock.getProbability();
            if (randomNumber <= sumPropability) {
                return effectBlock;
            }
        }
        throw new IllegalStateException("Can't generate random block");
    }


// supplier
    // consumer
    // function
}
