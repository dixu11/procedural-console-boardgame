package boardgame.components.entity.models.event.models;

import boardgame.components.entity.Effect;
import boardgame.components.entity.StaticEvent;
import boardgame.components.type.EventType;

import java.util.List;

public class NegativeEvent extends StaticEvent {

    public NegativeEvent(int field, List<Effect> effects,  boolean isExtreme) {
        super(field, effects,EventType.NEGATIVE, isExtreme);
    }

    @Override
    public boolean isPositive() {
        return false;
    }
}
