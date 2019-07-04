package boardgame.logic.movement;

public interface Movable {
    MoveResult move(int steps, MovementType type);
}
