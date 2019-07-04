package boardgame.components.entity;

import boardgame.components.type.EventType;

import java.util.List;

//event that stays on the field
public abstract class StaticEvent extends Event {
    private boolean isExtreme;

    public StaticEvent(int field, List<Effect> effects, EventType type, boolean isExtreme) {
        super(field, effects, type);
        this.isExtreme = isExtreme;
    }

    @Override
    public void refreshEffectsStatus(Effect activatedEffect) {
        for (Effect effect : effects) {
            if (effect.equals(activatedEffect)) {
                effect.refresh(true);
            } else {
                effect.refresh(false);
            }

        }
    }

    public boolean isExtreme() {
        return isExtreme;
    }
}
