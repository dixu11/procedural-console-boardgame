package boardgame.components.field.models;

import boardgame.components.field.Field;
import boardgame.components.type.FieldType;

import java.util.Objects;

//NULL OBJECT PATTERN
public class EmptyField extends Field {

    private static Field instance;

    EmptyField() {super(FieldType.EMPTY); }

    //SINGLETON PATTERN
    public static Field getInstance() {
        if (Objects.isNull(instance)) {
            instance = new EmptyField();
        }
        return instance;
    }

}
