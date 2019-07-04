package boardgame.factory;

import boardgame.Settings;
import boardgame.components.entity.Effect;
import boardgame.components.entity.Event;
import boardgame.components.type.EventType;
import boardgame.components.entity.models.event.models.NegativeEvent;
import boardgame.components.entity.models.event.models.PositiveEvent;
import boardgame.components.entity.models.event.models.RandomEvent;
import boardgame.components.field.Field;

import java.util.ArrayList;
import java.util.List;

public class EventFactory {

    public static final double EXTREME_EVENT_CHACE = 0.25;
    public static final double RANDOM_EVENT_CHANCE = 0.0; // RANDOM EVENTS FUTURE TEMPORARY REMOVED
    public static final double POSITIVE_NEGATIVE_RATIO = 0.66; //0.66



    private EffectFactory effectFactory;
    private List<Event> events;
    private List<Field> eventFields;
    private Settings settings;


    public EventFactory(EffectFactory effectFactory, List<Field> eventFields, Settings settings) {
        this.effectFactory = effectFactory;
        this.eventFields = eventFields;
        this.settings = settings;
        buildStaticEvents();
    }

    public List<Event> getEvents() {
        return events;
    }

    private void buildStaticEvents() {
        events = new ArrayList<>();
        for (Field eventField : eventFields) {
            events.add(createRandomEvent(eventField.getId()));
        }
    }

    private Event createRandomEvent(int fieldId) {
        double eventTypeRoll = Math.random();
        if (eventTypeRoll <= RANDOM_EVENT_CHANCE) {
            return createEvent(EventType.RANDOM,fieldId);
        } else if (eventTypeRoll <= POSITIVE_NEGATIVE_RATIO) {
            return createEvent(EventType.POSITIVE,fieldId);
        } else {
            return createEvent(EventType.NEGATIVE,fieldId);
        }
    }

    private Event createEvent(EventType type, int fieldId) {
        boolean isExtreme = Math.random() <= EXTREME_EVENT_CHACE;
        List<Effect> effects = effectFactory.getRandomEffects(type.getEffectsQuantity());
        switch (type){
            case POSITIVE:
                return new PositiveEvent(fieldId,effects,isExtreme);
            case NEGATIVE:
                return new NegativeEvent(fieldId,effects,isExtreme);
            case RANDOM:
                return new RandomEvent(fieldId,effects,isExtreme);
        }
        throw new IllegalStateException("Can't create Event");
    }


}
