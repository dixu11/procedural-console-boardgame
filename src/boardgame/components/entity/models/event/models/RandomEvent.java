package boardgame.components.entity.models.event.models;

import boardgame.components.entity.Effect;
import boardgame.factory.EventFactory;
import boardgame.components.entity.StaticEvent;
import boardgame.components.type.EventType;
import boardgame.utils.Utils;

import java.util.List;

public class RandomEvent extends StaticEvent {


    public RandomEvent(int field, List<Effect> effects, boolean isExtreme) {
        super(field, effects, EventType.RANDOM, isExtreme);
    }

    @Override
    public boolean isPositive() {
        return Utils.roll(EventFactory.POSITIVE_NEGATIVE_RATIO);
    }

}
