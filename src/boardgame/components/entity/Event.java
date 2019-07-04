package boardgame.components.entity;

import boardgame.components.type.EventType;
import boardgame.components.entity.models.event.models.PositiveEvent;
import boardgame.components.type.EntityType;

import java.util.ArrayList;
import java.util.List;


public abstract class Event extends Entity {

    public static final int DEFAULT_POSITIVE_EFFECTS_QUANTITY = 4;
    public static final int DEFAULT_NEGATIVE_EFFECTS_QUANTITY = 3;

    protected List<Effect> effects;
    protected EventType type;
    private boolean positive;

    private static Event emptyInstance;

    public Event(int field, List<Effect> effects,  EventType type) {
        super(field, EntityType.EVENT, type.getView());
        this.type = type;
        this.effects = effects;
        setSelfAsOwner();

    }

    private void setSelfAsOwner() {
        for (Effect effect : effects) {
            effect.setOwner(this);
        }
    }


    //GS
    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public boolean isExtreme() {
        return false;
    }

    public boolean isPositive() {
        return positive;
    }

    public static Event getEmptyInstance() {
        if (emptyInstance == null) {
            emptyInstance = new PositiveEvent(-1, new ArrayList<>(), false) ;
        }
        return emptyInstance;
    }

    public abstract void refreshEffectsStatus(Effect effect);
}


