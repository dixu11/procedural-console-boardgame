package boardgame.repository;

import boardgame.Settings;
import boardgame.components.entity.*;
import boardgame.components.entity.models.EmptyEntity;
import boardgame.components.entity.models.competitor.Computer;
import boardgame.components.entity.models.competitor.Player;
import boardgame.components.field.Field;
import boardgame.factory.EffectFactory;
import boardgame.factory.EventFactory;
import boardgame.logic.mediator.EntityMediator;

import java.util.ArrayList;
import java.util.List;

public class EntityManager implements CompetitorHandler {  // let you get any entity quickly

    //DEPENDENCIES
    private Settings settings;
    private EntityMediator mediator;

    //COMPONENTS
    private List<Entity> entities;
    private List<Competitor> competitors;   //contains competitors in order of ID
    private List<Event> events;

    public EntityManager(Settings settings) {
        this.settings = settings;
    }


    //BUILDER METHODS
    public void buildEntities() {
        competitors = buildCompetitors();
        events = buildEvents();

        entities = new ArrayList<>();
        entities.addAll(competitors);
        entities.addAll(events);
        setMediatorToEntities();
    }

    public List<Competitor> buildCompetitors() {
        List<Competitor> competitors = new ArrayList<>();
        for (int i = 0; i < settings.getPlayersNum(); i++) {
            competitors.add(buildPlayer(0, i));
        }
        return competitors;
    }

    public Player buildPlayer(int field, int competitorId) {      // simple method, but can possibly be expanded in the future
        return new Player(field, competitorId);
    }

    public Competitor buildComputer(int field, int competitorId) {
        return new Computer(field, competitorId);
    }

    public List<Event> buildEvents() {
        EffectFactory effectFactory = new EffectFactory();
        EventFactory eventFactory = new EventFactory(effectFactory,mediator.getEventFields(),settings);
        return eventFactory.getEvents();
    }


    //INITIALISATION METGODS
    public void setMediatorToEntities() {
        for (Entity entity : entities) {
            entity.setMediator(mediator);
        }
    }

    public void setMediator(EntityMediator mediator) {
        this.mediator = mediator;
    }


    //ACCESS METHODS
    public List<Entity> getAll() {
        return entities;
    }

    public Entity getById(int id) {
        for (Entity entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return EmptyEntity.getInstance();
    }

    public List<Entity> getEntitiesOnField(int fieldId) {
        List<Entity> entitiesOnField = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getFieldId() == fieldId) {
                entitiesOnField.add(entity);
            }
        }
        return entitiesOnField;
    }

    public List<Competitor> getCompetitorsOnField(int fieldId) {
        List<Competitor> competitorsOnField = new ArrayList<>();
        for (Competitor competitor : competitors) {
            if (competitor.getFieldId() == fieldId) {
                competitorsOnField.add(competitor);
            }
        }
        return competitorsOnField;
    }

    public Competitor getCompetitor(int competitorId) {
        if (competitorId > competitors.size()) {
            throw new IllegalArgumentException();
        }
        return competitors.get(competitorId);
    }

    public List<Competitor> getCompetitors() {
        return competitors;
    }

    public List<Event> getEventsOnField(int fieldId) {
        List<Event> eventsOnField = new ArrayList<>();
        for (Event event : events) {
            if (event.getFieldId() == fieldId) {
                eventsOnField.add(event);
            }
        }
        return eventsOnField;
    }

    public List<Event> getEventsOnField(Field newField) {
        return getEventsOnField(newField.getId());
    }

}
