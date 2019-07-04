package boardgame.components.entity.models;

import boardgame.components.entity.Entity;
import boardgame.components.type.EntityType;

import java.util.Objects;

public class EmptyEntity extends Entity {

    private static EmptyEntity instance;

    private EmptyEntity() {
        super(-1, EntityType.EMPTY, "");
    }

    public static EmptyEntity getInstance() {
        if (Objects.isNull(instance)) {
            instance = new EmptyEntity();
        }
        return instance;
    }


    @Override
    public boolean isEmpty() {
        return true;
    }
}
