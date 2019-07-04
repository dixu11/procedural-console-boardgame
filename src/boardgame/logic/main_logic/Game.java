package boardgame.logic.main_logic;

import boardgame.Settings;
import boardgame.communication.Communicator;
import boardgame.components.entity.*;
import boardgame.logic.mediator.EntityMediator;
import boardgame.logic.effect_reading.EffectResult;
import boardgame.repository.Board;
import boardgame.repository.CompetitorHandler;
import boardgame.communication.player_interaction.OptionMenu;
import boardgame.repository.EntityManager;
import boardgame.logic.movement.MoveResult;
import boardgame.communication.drawing_board.BoardDrawer;
import boardgame.communication.drawing_board.Drawer;

import java.util.List;

public class Game {     //Class contains main game loop


    //DEPENDENCIES
    private GameManipulator manipulator;
    private Communicator communicator;
    private CompetitorHandler handler;

    private Settings settings;

    //ATTRIBUTES
    boolean running;


    public Game(Settings settings) {
        this.settings = settings;
        buildGame();
    }


    public void buildGame() {
        //components
        Board board = new Board(settings);
        EntityManager entityManager = new EntityManager(settings);

        initializeComponents(entityManager, board);

        //communication modules
        OptionMenu menu = new OptionMenu();
        Drawer drawer = new BoardDrawer(board, entityManager);
        communicator = new Communicator(drawer, menu, entityManager, settings);

        //logic modules
        manipulator = new GameManipulator(board, entityManager, settings);

        initializeMinorDependencies(entityManager);
    }

    private void initializeComponents(EntityManager entityManager, Board board) {
        EntityMediator mediator = new EntityMediator(board, entityManager, settings);

        entityManager.setMediator(mediator);
        board.setMediator(mediator);

        board.createBoard();
        entityManager.buildEntities();
    }

    private void initializeMinorDependencies(EntityManager entityManager) {
        handler = entityManager;
    }


    public void runGame() {
        running = true;

        while (running) {
            executeRound();
        }

       endTheGame();
    }


    private void executeRound() {
        communicator.showRoundStart();

        for (int i = 0; i < settings.getPlayersNum(); i++) {
            boolean hasWon = executeTurn(handler.getCompetitor(i));
            if (hasWon) {
                running = false;
                break;
            }
        }
    }

    private boolean executeTurn(Competitor competitor) {
        communicator.showTurnStart(competitor);
        MoveResult result = moveCompetitor(competitor);
        interactWithEvents(competitor, result);
        boolean hasWon = checkIfCompetitorWon(competitor);
        communicator.showTurnEnd(competitor,hasWon);
        return hasWon;
    }

    private MoveResult moveCompetitor(Competitor competitor) {
        MoveResult result = manipulator.standardMove(competitor);
        communicator.showMoveResult(competitor,result);
        return result;
    }

    private void interactWithEvents(Competitor activator, MoveResult result) {
        List<Event> events = result.getStaticEventsTriggered();
        if (events.isEmpty()) {
            return;
        }

        for (Event event : events) {
            executeEvent(activator, event);
        }
    }

    private void executeEvent(Competitor activator, Event event) {
        EffectResult result = new EffectResult(activator);

        communicator.announceStaticEvent(result, event);
        manipulator.executeChosenEffect(result);
        communicator.announceEffectResult(result);
        manipulator.refreshEffect(result, event);
    }

    private void endTheGame() {
        communicator.printCompetitorsRanking();
    }




    private boolean checkIfCompetitorWon(Competitor competitor) {
        return competitor.getCoins() >= settings.getCoinsToWin();
    }


}
