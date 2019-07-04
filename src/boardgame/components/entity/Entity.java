package boardgame.components.entity;

import boardgame.components.type.EntityType;
import boardgame.logic.mediator.EntityMediator;

public abstract class Entity {      // every object that can be on fieldId player/static effect / trap / item etc

    //DEPENDENCIES
    protected EntityMediator mediator;

    //ATTRIBUTES
    private int id;
    protected int fieldId;
    protected EntityType type;
    private String view;


    //STATICS
    private static int nextId;

    public Entity(int field, EntityType type, String view) {
        this.type = type;
        this.fieldId = field;
        this.view = view;
        id = nextId;
        nextId++;
    }


    //INITIALIZATION METHOD
    public void setMediator(EntityMediator mediator) {
        this.mediator = mediator;
    }


    //METHODS
    public boolean isEmpty() {
        return false;
    }



    //GS ETC
    @Override
    public String toString() {
        return view + "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}
