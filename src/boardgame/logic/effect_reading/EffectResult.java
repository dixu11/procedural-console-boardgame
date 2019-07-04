package boardgame.logic.effect_reading;

import boardgame.components.entity.Effect;
import boardgame.components.entity.Entity;
import boardgame.components.entity.models.event.effect_blocks.EffectWho;
import boardgame.logic.movement.MoveResult;

public class EffectResult {

    private Entity activator;  // for now it can be only Competitor
    private Effect effect;
    private MoveResult moveResult;
    private int chosenId;
    private boolean canAfford;

    public EffectResult(Entity activator) {
        this.activator = activator;
        this.effect = Effect.getEmptyInstance();
        this.moveResult = MoveResult.getEmptyInstance();
        chosenId = -1;
        canAfford = true;
    }


    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public void setMoveResult(MoveResult moveResult) {
        this.moveResult = moveResult;
    }

    public Effect getEffect() {
        return effect;
    }

    public boolean isOnChosenCompetitor() {
        return effect.getWho().equals(EffectWho.CHOSEN_COMPETITOR);
    }

    public int getChosenId() {
        return chosenId;
    }

    public void setChosenId(int chosenId) {
        this.chosenId = chosenId;
    }

    public Entity getActivator() {
        return activator;
    }

    public boolean isCanAfford() {
        return canAfford;
    }

    public void setCanAfford(boolean canAfford) {
        this.canAfford = canAfford;
    }
}
