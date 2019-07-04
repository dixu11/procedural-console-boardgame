package boardgame.components.entity;

import boardgame.components.entity.models.event.effect_blocks.EffectHowMuch;
import boardgame.components.entity.models.event.effect_blocks.EffectWhat;
import boardgame.components.entity.models.event.effect_blocks.EffectWho;
import boardgame.communication.player_interaction.Option;
import boardgame.logic.effect_reading.EffectTranslator;
import boardgame.utils.Utils;

public class Effect implements Option {

    public static final double COST_MODIFIER = 0.5;
    public static final double MAIN_LOOT_MODIFIER = 5;

    private int id;
    private static int nextId;
    private int activatedModifier;
    private int timesSelected;
    private double notActivatedMidifier;

    private EffectWho who;
    private EffectWhat profit;
    private EffectHowMuch howMuchProfit;
    private EffectWhat cost;
    private EffectHowMuch howMuchCost;
    private Event owner;

    private static Effect emptyInstance;


    public Effect(EffectWho who, EffectWhat profit, EffectHowMuch howMuchProfit, EffectWhat cost, EffectHowMuch howMuchCost) {
        this.who = who;
        this.profit = profit;
        this.howMuchProfit = howMuchProfit;
        this.cost = cost;
        this.howMuchCost = howMuchCost;
        activatedModifier = 1;
        notActivatedMidifier = 0.0;
        timesSelected = 0;

        id = nextId;
        nextId++;
    }

    @Override
    public String getOptionText() {
        return toString();
    }

    private double getBasicQuantity(EffectWhat what, EffectHowMuch howMuch) {
        double mainModifier = MAIN_LOOT_MODIFIER;
        if (owner.isExtreme()) {
            mainModifier *= 1.5;
        }
        double howMuchModifier = howMuch.getQuantityModifier();
        if (howMuch.equals(EffectHowMuch.AROUND_STANDARD)) {
            howMuchModifier +=  Math.random()/3 - Math.random()/3;
        }
        //test code


        return mainModifier /
                what.getValue() *
                howMuchModifier;



    }
    private int getQuantity(EffectWhat what, EffectHowMuch howMuch, double modifier) {
        double quantity = getBasicQuantity(what, howMuch);
        quantity *= modifier;
        int quantityRounded = (int) Math.round(quantity);
        if (quantityRounded == 0) {
            quantityRounded++;
        }
        if (owner.isPositive()) {
            return Math.abs(quantityRounded);
        } else {
            return - quantityRounded;
        }
    }

    public int getProfitQuantity() {
        double modifier = 1.0;
        if (!isPositive()) {
            modifier = COST_MODIFIER - notActivatedMidifier;

          return  getQuantity(profit, howMuchProfit, modifier)- activatedModifier;
        } else {
            modifier += notActivatedMidifier;
            return getQuantity(profit, howMuchProfit, modifier);
        }

    }

    public int getCostQuantity() {
        return getQuantity(cost, howMuchCost, COST_MODIFIER-notActivatedMidifier) * activatedModifier;
    }

    void setOwner(Event owner) {
        this.owner = owner;
    }

    public void refresh(boolean activated) {
        if (activated) {
            timesSelected++;
//            System.out.println("Last profit and cost:" + getProfitQuantity() + " " + getCostQuantity());
            activatedModifier *= 2;
//            System.out.println("After profit and cost:" + getProfitQuantity() + " " + getCostQuantity());
        } else {
            notActivatedMidifier += 0.05;
        }
    }


    //GS ETC

    public int getId() {
        return id;
    }

    public EffectWho getWho() {
        return who;
    }

    public EffectWhat getProfit() {
        return profit;
    }

    public EffectHowMuch getHowMuchProfit() {
        return howMuchProfit;
    }

    public EffectHowMuch getHowMuchCost() {
        return howMuchCost;
    }

    public Event getOwner() {
        return owner;
    }

    public boolean isPositive() {
        return owner.isPositive();
    }

    public EffectWhat getCost() {
        return cost;
    }

    public static Effect getEmptyInstance() {
        if (emptyInstance == null) {
            emptyInstance = new Effect(EffectWho.ACTIVATOR,
                    EffectWhat.COINS,
                    EffectHowMuch.EXACT_STANDARD,
                    EffectWhat.COINS,
                    EffectHowMuch.EXACT_STANDARD
            );
            emptyInstance.setOwner(Event.getEmptyInstance());
        }
        return emptyInstance;
    }

    @Override
    public String toString() {

        return  EffectTranslator.translateEffect(this) + "  " + Utils.getStars(timesSelected);
    }

}
