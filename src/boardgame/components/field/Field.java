package boardgame.components.field;

import boardgame.components.type.FieldType;
import boardgame.logic.mediator.EntityMediator;

public abstract class Field {       //one piece of the board

    protected int id;
    protected char view;
    protected FieldType type;
    private boolean encountered;

    protected EntityMediator mediator;

    private static int nextId;

    public Field(FieldType type) {
        this.type = type;
        encountered = type.isEncountered();
        id = nextId;
        nextId++;
        view = type.getView();
    }

    public void setMediator(EntityMediator mediator) {
        this.mediator = mediator;
    }

    // GS ETC
    @Override
    public String toString() {
        if (encountered) {
            return view + "";
        }
        return "?";
    }

    public boolean isEncountered() {
        return encountered;
    }

    public void setEncountered(boolean encountered) {
        this.encountered = encountered;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getView() {
        return view;
    }

    public void setView(char view) {
        this.view = view;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public EntityMediator getMediator() {
        return mediator;
    }


}

