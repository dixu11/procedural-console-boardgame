package boardgame.components.type;

import boardgame.components.entity.Event;

public enum EventType {
    POSITIVE(":)",Event.DEFAULT_POSITIVE_EFFECTS_QUANTITY),
    NEGATIVE(":(",Event.DEFAULT_NEGATIVE_EFFECTS_QUANTITY),
    RANDOM("?",Event.DEFAULT_NEGATIVE_EFFECTS_QUANTITY);


    private String view;
    private int effectsQuantity;

    EventType(String view, int effectsQuantity) {
        this.view = view;
        this.effectsQuantity = effectsQuantity;
    }

    public String getView() {
        return view;
    }


    public int getEffectsQuantity() {
        return effectsQuantity;
    }

    @Override
    public String toString() {
        return view;
    }
}
