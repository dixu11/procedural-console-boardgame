package boardgame.communication.drawing_board;

import boardgame.repository.Board;
import boardgame.repository.EntityManager;
import boardgame.utils.Utils;

public class BoardDrawer implements Drawer {        //class that draw field and entity in board shape

    private Board board;
    private EntityManager entityManager;
    private DrawingMode mode;
    private BoardShape shape;

    public static final BoardShape DEFAULT_STARTING_BOARD_SHAPE = BoardShape.STANDARD;

    private static final String BOARD_DRAW_MESSAGE =
            "Board state: ";

    public BoardDrawer(Board board, EntityManager entityManager) {
        this.board = board;
        this.entityManager = entityManager;
        setShape(getRandomShape());

    }

    public void setShape(BoardShape shape) {
        this.shape = shape;
        switch (shape) {
            case STANDARD:
                mode = new StandardDrawingMode(board, entityManager);
                break;
            case TRIANGLE:
                mode = new TriangleDrawingMode(board, entityManager);
                break;
            case MOUNTAINS:
                mode = new MountainsDrawingMode(board, entityManager);
                break;
            case NO_SHAPE:
                setShape(DEFAULT_STARTING_BOARD_SHAPE);
                break;
        }
    }

    private BoardShape getRandomShape() {
        if (Utils.roll(0.5)) {
            return BoardShape.TRIANGLE;
        }
        return BoardShape.MOUNTAINS;
    }

    @Override
    public void draw() {
        System.out.println(BOARD_DRAW_MESSAGE);
        System.out.println();
        mode.draw();
        System.out.println();
    }


}
