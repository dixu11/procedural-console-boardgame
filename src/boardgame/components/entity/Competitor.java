package boardgame.components.entity;

import boardgame.components.entity.models.event.effect_blocks.EffectWhat;
import boardgame.logic.movement.Movable;
import boardgame.logic.movement.MoveResult;
import boardgame.logic.movement.Movement;
import boardgame.logic.movement.MovementType;
import boardgame.components.type.EntityType;
import boardgame.logic.mediator.EntityMediator;
import boardgame.communication.player_interaction.Option;
import boardgame.utils.Utils;

import java.util.List;

public abstract class Competitor extends Entity implements Movable, Option {

    protected Movement movement;

    //PUBLIC ATTRIBUTES
    public static final List<String> COMPETITORS_VIEWS = Utils.getUnmodifiableList(new String[]{
            "John","Rob","Sue","Max"
    });
    public static final int MAX_COMPIETITORS = COMPETITORS_VIEWS.size();
    public static final int STARTING_MIN_SPEED = 3;
    public static final int STARTING_MAX_SPEED = 6;
    public static final int DEFAULT_STARTING_COINS = 10;

    //ATTRIBUTES
    private int competitorId;
    private int coins;


    public Competitor(int field, EntityType type, int competitorId) {
        super(field, type, COMPETITORS_VIEWS.get(competitorId));
        this.competitorId = competitorId;
        coins = DEFAULT_STARTING_COINS;
        movement = new Movement(STARTING_MIN_SPEED,STARTING_MAX_SPEED,this);
    }

    @Override
    public void setMediator(EntityMediator mediator) {
        super.setMediator(mediator);
        movement.setMediator(mediator);
    }

    @Override
    public MoveResult move(int steps, MovementType type) {
        return movement.move(steps, type);
    }

    public int addLapsReward(int coinsPerLapReward) {
      return   movement.addLapsReward(coinsPerLapReward);
    }

    @Override
    public String getOptionText() {
        return getView();
    }

    public boolean isPlayer() {
        return type.equals(EntityType.PLAYER);
    }

    public void addCoins(int amount) {
        coins += amount;
        if (coins < 0) {
            coins = 0;
        }
    }

    public void addMaxSpeed(int amount) {
        movement.changeMaxSpeed(amount);
    }

    public void addMinSpeed(int amount) {
        movement.changeMinSpeed(amount);
    }


    public boolean buyFor(EffectWhat costIn, int costQuantity) {
        boolean success = false;
        switch (costIn) {
            case COINS:
                if (coins - costQuantity >= 0) {
                    addCoins(-costQuantity);
                    success = true;
                }
                break;
            case MOVES:
                move(-costQuantity, MovementType.EVENT);
                success = true;
                break;
            case MAX_SPEED:
                if (movement.canPayInMaxSpeed(costQuantity)) {
                    movement.changeMaxSpeed(-costQuantity);
                    success = true;
                }
                break;
            case MIN_SPEED:
                if (movement.canPayInMinSpeed(costQuantity)) {
                    movement.changeMinSpeed(-costQuantity);
                    success = true;
                }
                break;
        }
        return success;
    }



    //GS
    public int getCompetitorId() {
        return competitorId;
    }

    public int getMaxSpeed() {
      return movement.getMaxSpeed();
    }

    public int getMinSpeed() {
        return movement.getMinSpeed();
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getLaps() {
        return movement.getLapsDone();
    }



}
