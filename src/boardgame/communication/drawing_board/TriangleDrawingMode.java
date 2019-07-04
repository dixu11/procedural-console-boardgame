package boardgame.communication.drawing_board;


import boardgame.repository.Board;
import boardgame.repository.EntityManager;

public class TriangleDrawingMode extends DrawingMode {

    public TriangleDrawingMode(Board board, EntityManager entityManager) {
        super(board, entityManager);
    }



    protected int getBottomArrowLength() {
        return boardSize - drawingFieldId + getBuffer();
    }

    @Override
    protected int howManySpaces() {
        if (boardSize / 2 < drawingFieldId) {
            return boardSize - drawingFieldId;
        }
       return drawingFieldId;
    }

}
