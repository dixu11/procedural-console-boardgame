package boardgame.communication.drawing_board;

import boardgame.components.entity.Event;
import boardgame.repository.Board;
import boardgame.repository.EntityManager;
import boardgame.components.entity.Competitor;
import boardgame.components.field.Field;

import java.util.List;

public abstract class DrawingMode {

    private static final int DEFAULT_BUFFER = 1;
    private static final int DEFAULT_SPACES_NUMBER = 0;
    private static final String DEFAULT_LEFT_ARROW_ENDING = ">";
    private static final String DEFAULT_LEFT_ARROW_ELEMENT = "^";
    private static final int DEFAULT_BOTTOM_ARROW_LENGTH = 2;

    private Board board;
    private EntityManager entityManager;

    protected int drawingFieldId;
    protected Field drawingField;
    protected int boardSize;

    public DrawingMode(Board board, EntityManager entityManager) {
        this.board = board;
        this.entityManager = entityManager;
    }

    //TEMPLATE METHOD PATTERN
    final void draw() {
        boardSize = board.getBoardSize();
        int buffer = getBuffer();
        List<Field> fields = board.getFields();
        String leftArrow = "";

        for (int i = 0; i < fields.size(); i++) {
            drawingFieldId = i;
            if (i == 0) {
                leftArrow = getLeftArrowEnding();
            } else {
                leftArrow = getLeftArrowElement();
            }
            drawingField = fields.get(i);
            System.out.printf("%s%s%s%s ",
                    leftArrow,
                    getSpaces(buffer),
                    getSpaces(howManySpaces()),
                    drawingField);

            System.out.print(" ");
            drawEventsOnFIeld();

            List<Competitor> competitors = entityManager.getCompetitorsOnField(i);

            System.out.print(" ");
            System.out.print(getCompetitorsView(competitors));
            System.out.println();
        }

        System.out.print(leftArrow);
        System.out.println(getBottomArrow(getBottomArrowLength()));
    }

    protected int howManySpaces() {
        return DEFAULT_SPACES_NUMBER;
    }

    protected int getBuffer() {
        return DEFAULT_BUFFER;
    }

    protected String getLeftArrowEnding() {
        return DEFAULT_LEFT_ARROW_ENDING;
    }

    protected String getLeftArrowElement() {
        return DEFAULT_LEFT_ARROW_ELEMENT;
    }

    protected int getBottomArrowLength() {
        return DEFAULT_BOTTOM_ARROW_LENGTH;
    }


    private void drawEventsOnFIeld() {
        if (drawingField.isEncountered()) {
            List<Event> events =  entityManager.getEventsOnField(drawingFieldId);
            for (Event event : events) {
                System.out.print(event + " ");
            }
        }
    }

    protected String getCompetitorsView(List<Competitor> competitors) {
        StringBuilder competitorsViews = new StringBuilder();
        for (Competitor competitor : competitors) {
            competitorsViews.append(competitor);
            competitorsViews.append(" ");
        }
        return competitorsViews.toString();
    }

    protected String getSpaces(int amount) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }

    protected String getBottomArrow(int howLong) {
        StringBuilder arrow = new StringBuilder();
        for (int i = 0; i < howLong; i++) {
            if (i % 2 == 0) {
                arrow.append(" ");
            } else {
                arrow.append("<");
            }
        }
        return arrow.toString();
    }
}
