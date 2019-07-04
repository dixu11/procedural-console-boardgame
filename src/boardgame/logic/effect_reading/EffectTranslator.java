package boardgame.logic.effect_reading;

import boardgame.components.entity.Effect;
import boardgame.components.entity.models.event.effect_blocks.EffectWhat;
import boardgame.components.entity.models.event.effect_blocks.EffectWho;

public class EffectTranslator {

    public static String translateEffect(Effect effect) {
        return translateWho(effect) + " " +
                translateProfit(effect) +
                translateCost(effect);
    }

    private static String translateWho(Effect effect) {
        switch (effect.getWho()) {
            case ACTIVATOR:
                return "You";
            case CHOSEN_COMPETITOR:
                return "Chosen competitor";
            case RANDOM_COMPETITOR:
                return "Random competitor";
            default:
                return "";
        }
    }

    private static String translateProfit(Effect effect) {
        EffectWhat what = effect.getProfit();
        String message = what.toString();
        int quantity = Math.abs(effect.getProfitQuantity());
       return addContext(effect, effect.getProfit(), quantity,message);
    }

    private static String translateCost(Effect effect) {
        if (!effect.isPositive()) {
            return "";
        }
        EffectWhat what = effect.getCost();
        String message = what.toString();
        int quantity = Math.abs(effect.getCostQuantity());
        return " - cost: " + quantity + " " + message;
    }


    private static String addContext(Effect effect, EffectWhat what, int quantity, String message) {
        boolean positive = effect.isPositive();
        if (!effect.getWho().equals(EffectWho.ACTIVATOR)) {
            positive = !positive;
        }

        if (positive) {
            switch (what.getTranslation()) {
                case GAINS_LOSES:
                    message = "gains " + quantity + " " + message;
                    break;
                case BACK:
                    message = message + " " + quantity + " steppes";
            }
        } else {
            switch (what.getTranslation()) {
                case GAINS_LOSES:
                    message = "loses " + quantity + " " + message;
                    break;
                case BACK:
                    message = message + " back " + quantity + " steppes";
            }
        }
        return message;
    }
}
