package boardgame.logic.movement;

import boardgame.components.entity.Event;
import boardgame.components.field.Field;
import boardgame.components.field.models.EmptyField;

import java.util.ArrayList;
import java.util.List;

public class MoveResult {

    private int fromFieldId;
    private int toFieldId;
    private Field toField;
    private int distance;
    private int rollResult;
    private int lapsDoneInMove;
    private int labReward;
    private MovementType type;
    private List<Event> staticEventsTriggered;


    MoveResult() {
        staticEventsTriggered = new ArrayList<>();
        toField = EmptyField.getInstance();
        type = MovementType.DEFAULT_TYPE;
    }

    void addLap() {
        lapsDoneInMove++;
    }

    void substractLap() {
        lapsDoneInMove--;
    }


    void saveDistance(int destination) {
        distance = destination - fromFieldId;
    }


    //GS
    public Field getToField() {
        return toField;
    }

    public void setToField(Field toField) {
        this.toField = toField;
    }

    public void setStaticEventsTriggered(List<Event> staticEventsTriggered) {
        this.staticEventsTriggered = staticEventsTriggered;
    }

    void setFromFieldId(int fromFieldId) {
        this.fromFieldId = fromFieldId;
    }

    void setToFieldId(int toFieldId) {
        this.toFieldId = toFieldId;
    }

    void setDistance(int distance) {
        this.distance = distance;
    }

    public int getLapsDoneInMove() {
        return lapsDoneInMove;
    }

    void setLapsDoneInMove(int lapsDoneInMove) {
        this.lapsDoneInMove = lapsDoneInMove;
    }

    void setType(MovementType type) {
        this.type = type;
    }

    public int getFromFieldId() {
        return fromFieldId;
    }

    public int getToFieldId() {
        return toFieldId;
    }

    public int getDistance() {
        return distance;
    }

    public MovementType getType() {
        return type;
    }

    public int getRollResult() {
        return rollResult;
    }

    void setRollResult(int rollResult) {
        this.rollResult = rollResult;
    }

    public static MoveResult getEmptyInstance() {
        return new MoveResult();
    }

    public int getLabReward() {
        return labReward;
    }

    public void setLapReward(int labReward) {
        this.labReward = labReward;
    }

    public List<Event> getStaticEventsTriggered() {
        return staticEventsTriggered;
    }



}
