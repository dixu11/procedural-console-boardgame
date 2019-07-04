package boardgame.logic.mediator;

import boardgame.Settings;
import boardgame.components.field.Field;
import boardgame.repository.Board;
import boardgame.repository.EntityManager;

import java.util.List;


public class EntityMediator implements Mediator<Board, EntityManager> {         //simplifies communication between EntityManager and Board

    private Board board;
    private EntityManager entityManager;
    private Settings settings;

    public EntityMediator(Board board, EntityManager entityManager, Settings settings) {
        this.board = board;
        this.entityManager =entityManager;
        this.settings = settings;
    }


    public int getBoardSize() {
        return board.getBoardSize();
    }

    public List<Field> getEventFields() {
       return board.getEventFields();
    }

}
