package boardgame.repository;

import boardgame.Settings;
import boardgame.components.type.FieldType;
import boardgame.components.field.*;
import boardgame.components.field.models.*;
import boardgame.logic.mediator.EntityMediator;
import boardgame.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Board {        // class made in case i'll implement more boards

    public static final double EVENT_CHACE = 0.80;

    private Settings settings;
    private EntityMediator mediator;
    private List<Field> fields;
    private int boardSize;

    public Board(Settings settings) {
        this.settings = settings;
        boardSize = settings.getFieldsNum();
    }

    // INITIALISATION METHODS
   public void createBoard() {
        fields = new ArrayList<>();
        fields.add(createField(FieldType.START));
        for (int i = 1; i < boardSize - 1; i++) {
            if (Utils.roll(EVENT_CHACE)) {
                fields.add(createField(FieldType.EVENT));
            } else {
                fields.add(createField(FieldType.STANDARD));
            }
        }
        fields.add(createField(FieldType.META));
       setMediatorToFields();
    }

    private Field createField(FieldType type) {
        switch (type) {
            case START:
                return new StartField();
            case STANDARD:
                return new StandardField();
            case EVENT:
                return new EventField();
            case META:
                return new MetaField();
            default:
                return EmptyField.getInstance();
        }
    }

    public void setMediator(EntityMediator mediator) {
        this.mediator = mediator;

    }

    private void setMediatorToFields() {
        for (Field field : fields) {
            field.setMediator(mediator);
        }
    }




    public List<Field> getEventFields() {
        List<Field> eventFields = new ArrayList<>();
        for (Field field : fields) {
            if (field.getType().equals(FieldType.EVENT)) {
                eventFields.add(field);
            }
        }
        return eventFields;
    }

    public Field getById(int id) {
        for (Field field : fields) {
            if (field.getId() == id) {
                return field;
            }
        }
        return EmptyField.getInstance();
    }




    // GS ETC
    public List<Field> getFields() {
        return fields;
    }

    public int getBoardSize() {
        return boardSize;
    }


}
