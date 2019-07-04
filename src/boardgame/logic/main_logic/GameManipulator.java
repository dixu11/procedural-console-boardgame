package boardgame.logic.main_logic;

import boardgame.Settings;
import boardgame.components.entity.*;
import boardgame.components.entity.models.event.effect_blocks.EffectWhat;
import boardgame.components.entity.models.event.effect_blocks.EffectWho;
import boardgame.logic.effect_reading.EffectResult;
import boardgame.components.field.Field;
import boardgame.repository.Board;
import boardgame.repository.EntityManager;
import boardgame.logic.movement.MoveResult;
import boardgame.logic.movement.MovementType;
import boardgame.utils.Utils;

import java.util.List;

public class GameManipulator {     //responsible for changing entity and board attributes

    //DEPENDENCIES
    private Board board;
    private EntityManager entityManager;
    private Settings settings;

    public GameManipulator(Board board, EntityManager entityManager, Settings settings) {
        this.board = board;
        this.entityManager = entityManager;
        this.settings = settings;
    }


    public MoveResult standardMove(Competitor competitor) {
        return moveTarget(competitor, 0, MovementType.RANDOM);

    }

    private MoveResult moveTarget(Competitor target, int steps, MovementType type) {
        MoveResult result = target.move(steps, type);
        addMovementRewards(target, result);
        triggerNewField(result);
        return result;
    }

    private void addMovementRewards(Competitor competitor, MoveResult result) {
        if (result.getLapsDoneInMove() > 0) {
            int reward = competitor.addLapsReward(settings.getCoinsPerLapReward());
            result.setLapReward(reward);
        }
    }

    private void triggerNewField(MoveResult result) {
        Field newField = board.getById(result.getToFieldId());
        result.setToField(newField);

        if (result.getType().equals(MovementType.EVENT)) {
            return;
        }

        newField.setEncountered(true);
        result.setStaticEventsTriggered(entityManager.getEventsOnField(newField));
    }


    public void executeChosenEffect(EffectResult effectResult) {
        Effect chosenEffect = effectResult.getEffect();
        if (chosenEffect.isPositive()) {
            executePositiveEffect(effectResult);
        } else {
            executeNegativeEffect(effectResult);
        }
    }

    private void executePositiveEffect(EffectResult effectResult) {
        Effect effect = effectResult.getEffect();
        Competitor target = getTargetCompetitor(effectResult);
        int profitQuantity = effect.getProfitQuantity();

        if (!payForEffect(effectResult)) {
            effectResult.setCanAfford(false);
            return;
        }

        profitQuantity = changeSignIfTargetIsEnemy(effect, profitQuantity);
        executeWhatEffect(target, profitQuantity, effect.getProfit(), effectResult);
    }

    private boolean payForEffect(EffectResult effectResult) {
        Competitor activator = Utils.castToCompetitor(effectResult.getActivator());
        Effect effect = effectResult.getEffect();
        int costQuantity = effect.getCostQuantity();
        return activator.buyFor(effect.getCost(), costQuantity);
    }

    private int changeSignIfTargetIsEnemy(Effect effect, int value) {
        if (!effect.getWho().equals(EffectWho.ACTIVATOR)) {
            return value * (-1);
        }
        return value;
    }

    private Competitor getTargetCompetitor(EffectResult effectResult) {
        Effect effect = effectResult.getEffect();
        switch (effect.getWho()) {
            case ACTIVATOR:
                return Utils.castToCompetitor(effectResult.getActivator());
            case RANDOM_COMPETITOR:
            case CHOSEN_COMPETITOR:
                return getCompetitor(effectResult.getChosenId());
            default:
                throw new IllegalStateException("No competitor to return");
        }
    }

    private void executeNegativeEffect(EffectResult effectResult) {
        Effect effect = effectResult.getEffect();
        Competitor target = getTargetCompetitor(effectResult);
        int quantity = effect.getProfitQuantity();
        quantity = changeSignIfTargetIsEnemy(effect, quantity);
        executeWhatEffect(target, quantity, effect.getProfit(), effectResult);
    }



    private void executeWhatEffect(Competitor target, int quantity, EffectWhat what, EffectResult result) {
        switch (what) {
            case COINS:
                target.addCoins(quantity);
                break;
            case MOVES:
                MoveResult moveResult = moveTarget(target, quantity, MovementType.EVENT);
                result.setMoveResult(moveResult);
                break;
            case MAX_SPEED:
                target.addMaxSpeed(quantity);
                break;
            case MIN_SPEED:
                target.addMinSpeed(quantity);
                break;
        }
    }

    public void refreshEffect(EffectResult effectResult, Event event) {
        event.refreshEffectsStatus(effectResult.getEffect());
    }



    public Competitor getCompetitor(int competitorId) {
        return entityManager.getCompetitor(competitorId);
    }

    public List<Competitor> getCompetitors() {
        return entityManager.getCompetitors();
    }


}
