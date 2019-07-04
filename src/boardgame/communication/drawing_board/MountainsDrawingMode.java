package boardgame.communication.drawing_board;


import boardgame.repository.Board;
import boardgame.repository.EntityManager;
import boardgame.utils.Utils;

public class MountainsDrawingMode extends DrawingMode {

    private static final double MOUNTAINS_HEIGHT = 0.60;
    private static final int INITIAL_MOUNTAINS_HEIGHT = 3;

    private int actualSpacesQuantity;

    public MountainsDrawingMode(Board board, EntityManager entityManager) {
        super(board, entityManager);
    }

    @Override
    protected int howManySpaces() {
        if (drawingFieldId < INITIAL_MOUNTAINS_HEIGHT) {
            return drawingFieldId;
        }

        if (drawingFieldId == INITIAL_MOUNTAINS_HEIGHT) {
            actualSpacesQuantity = drawingFieldId;
        }


        if (boardSize-drawingFieldId>0 && boardSize-drawingFieldId<INITIAL_MOUNTAINS_HEIGHT) {
            actualSpacesQuantity--;
            return actualSpacesQuantity;
        }


        if (Utils.roll(MOUNTAINS_HEIGHT)) {
            actualSpacesQuantity++;
        } else {
            actualSpacesQuantity--;
        }


        return actualSpacesQuantity;

    }


    @Override
    protected int getBottomArrowLength() {
        return actualSpacesQuantity;
    }


}
