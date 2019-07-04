package boardgame.communication;


import boardgame.Settings;
import boardgame.communication.drawing_board.Drawer;
import boardgame.communication.player_interaction.Option;
import boardgame.communication.player_interaction.OptionMenu;
import boardgame.components.entity.*;
import boardgame.components.entity.models.event.effect_blocks.EffectWho;
import boardgame.logic.effect_reading.EffectResult;
import boardgame.logic.movement.MoveResult;
import boardgame.components.type.FieldType;
import boardgame.repository.CompetitorHandler;
import boardgame.utils.Utils;

import java.util.*;


public class Communicator {

    private Drawer drawer;
    private OptionMenu menu;
    private CompetitorHandler handler;
    private Settings settings;

    private static final String ACHIEVEMENT_MESSAGE = "Congratulations!";
    private static final String BIG_ACHIEVEMENT_MESSAGE = "Incredible!!";

    public Communicator(Drawer drawer, OptionMenu menu,
                        CompetitorHandler handler, Settings settings) {

        this.drawer = drawer;
        this.menu = menu;
        this.handler = handler;
        this.settings = settings;
    }

    public void showRoundStart() {
        clearScreen();
        announceNewTurn();
        printCompetitorsRanking();
        enterAndClear();
    }

    public void showTurnStart(Competitor competitor) {
        clearScreen();
        greetCompetitor(competitor);
        enterAndClear();
        drawBoard();
        printCompetitorStatistics(competitor);
        announceDiceRoll();
    }

    public void showMoveResult(Competitor competitor, MoveResult result) {
        showMoveResultParameters(competitor, result);
        announceNewField(result);
        drawBoard();
        announceLapDone(result);
    }

    public void showTurnEnd(Competitor competitor, boolean hasWon) {
        printEndTurnMessage(competitor);
        enterAndClear();
        announceIfWon( hasWon);
    }


   public void printCompetitorsRanking() {
        List<Competitor> competitorsCopy = new ArrayList<>(handler.getCompetitors());
        Comparator<Competitor> compareByCoins = (o1, o2) -> {
            if (o1.getCoins() != o2.getCoins()) {
                return Integer.compare(o2.getCoins(), o1.getCoins());
            } else if (o1.getLaps() != o2.getLaps()) {
                return Integer.compare(o2.getLaps(), o1.getLaps());
            } else {
                return Integer.compare(o2.getFieldId(), o1.getFieldId());
            }
        };

        competitorsCopy.sort(compareByCoins);

        final String winnerMessage = String.format("Player %s is winning!\n\n", competitorsCopy.get(0).getView());
        printSystemMessage(winnerMessage);
        final String top = "\tName     Coins Speed Laps Field";
        System.out.println(top);

        for (int i = 0; i < competitorsCopy.size(); i++) {
            Competitor competitor = competitorsCopy.get(i);
            final String competitorRecord = String.format(
                    "%2d. %-8s %5d %3d-%-3d%3d   %3d\n",
                    i + 1,
                    competitor.getView(),
                    competitor.getCoins(),
                    competitor.getMinSpeed(),
                    competitor.getMaxSpeed(),
                    competitor.getLaps(),
                    competitor.getFieldId() + 1
            );

            System.out.println(competitorRecord);
        }
    }


    private void drawBoard() {
        drawer.draw();
    }

    private void greetCompetitor(Competitor competitor) {
        final String greeting = ("Player %s starts turn!\n\n");
        System.out.printf(greeting, competitor.getView());
    }

    private void announceNewTurn() {
        final String startTurnMessage = "New round!\n\nActual Standings:\n";
        System.out.println(startTurnMessage);
    }

    private void printCompetitorStatistics(Competitor competitor) {

        System.out.printf(
                "Name: %s\n" +
                        "Position: %d\n" +
                        "Speed: %d - %d\n" +
                        "Coins: %d\n" +
                        "Coins to win: %d\n" +
                        "Laps done: %d\n",

                competitor.getView(),
                competitor.getFieldId() + 1,
                competitor.getMinSpeed(),
                competitor.getMaxSpeed(),
                competitor.getCoins(),
                settings.getCoinsToWin() - competitor.getCoins(),
                competitor.getLaps()
        );
        System.out.println();
    }
    private void announceDiceRoll() {
        final String dieRollMessage = "Press <Enter> to roll a die!";
        waitForEnter(dieRollMessage);
        clearScreen();
    }

    private void showMoveResultParameters(Competitor competitor, MoveResult result) {
        if (competitor.isPlayer()) {
            final String rollResultMessage = String.format("You rolled %d!\n", result.getRollResult());
            System.out.println(rollResultMessage);
            enterAndClear();

            final String basicMoveMessage = String.format(
                    "You moved %d steps from field %d to %d\n",
                    result.getDistance(),
                    result.getFromFieldId() + 1,
                    competitor.getFieldId() + 1
            );

            System.out.println(basicMoveMessage);
        }
    }

    private void announceNewField(MoveResult result) {
        FieldType type = result.getToField().getType();
        final String fieldTypeMessage = String.format("You stepped on %s field.\n",
                type.name().toLowerCase());

        System.out.println(fieldTypeMessage);

        if (type == FieldType.EVENT) {
            final String eventMessage = "You triggered an event!\n";
            printSystemMessage(eventMessage);
        }
    }

    private void announceLapDone(MoveResult result) {
        final String labReachPrefix = "You have reached end of the map";
        final String labReachSufiux = String.format("and earned %d coins as reward!",
                result.getLabReward());

        if (result.getLapsDoneInMove() == 1 && result.getLabReward() > 0) {

            enterAndClear();
            System.out.println(ACHIEVEMENT_MESSAGE);
            System.out.println();
            System.out.println(labReachPrefix + " " + labReachSufiux);
            System.out.println();

        } else if (result.getLapsDoneInMove() > 1 && result.getLabReward() > 0) {


            enterAndClear();
            System.out.println(BIG_ACHIEVEMENT_MESSAGE);
            System.out.println();
            final String multipleLapsMessage = String.format("%s %d times %s",
                    labReachPrefix,
                    result.getLapsDoneInMove(),
                    labReachSufiux
            );

            System.out.println(multipleLapsMessage);
            System.out.println();
        }
    }



    private void announceIfWon(boolean hasWon) {
        if (hasWon) {
                clearScreen();
                final String playerWonMessage = String.format(
                        "You have collected %d coins and won the game!!",
                        settings.getCoinsToWin()
                );

                printSystemMessage(ACHIEVEMENT_MESSAGE);
                System.out.println();
                printSystemMessage(playerWonMessage);
                System.out.println();
                waitForEnter();
                clearScreen();
            }
    }


    public void announceStaticEvent(EffectResult effectResult, Event event) {
        enterAndClear();
        List<Effect> effects = event.getEffects();
        final String startMessage = "You have encountered %s%s event on the field.\n";
        String eventType = "";
        if (event.isExtreme()) {
            eventType = "powerful ";
        }
        printSystemMessage(String.format(startMessage, eventType, event.isPositive())
                .replace("true", "positive")
                .replace("false", "negative")
        );

        int decisionId;
        if (effects.size() == 1) {
            decisionId = 0;
        } else if (effects.size() > 1) {
            decisionId = chooseOption(effects);
        } else {
            decisionId = -1;
        }
        effectResult.setEffect(effects.get(decisionId));
        printSystemMessage("Chosen effect: " + effectResult.getEffect().toString() + "\n");
        Competitor competitor = setTarget(effectResult);
        printSystemMessage("Target: " + competitor + "\n");
        enterAndClear();
    }

    public void announceEffectResult(EffectResult effectResult) {
        final String positiveMessage = "Effect executed correctly. ";
        final String canNotAffordMessage = "Nothing has changed because you can't afford the price! ";
        if (effectResult.isCanAfford()) {
            printSystemMessage(positiveMessage + "\n");
        } else {
            printSystemMessage(canNotAffordMessage + "\n");
        }

    }

    public Competitor setTarget(EffectResult effectResult) { //metoda do przeniesienia do warstwy logiki
        EffectWho who = effectResult.getEffect().getWho();
        Competitor activator = Utils.castToCompetitor(effectResult.getActivator());
        int chosenCompetitorId = activator.getId();
        if (who.equals(EffectWho.ACTIVATOR)) {
            effectResult.setChosenId(chosenCompetitorId);
            return activator;
        }

        List<Competitor> competitorOptions = new ArrayList<>(handler.getCompetitors());
        competitorOptions.remove(effectResult.getActivator());


        int chosenOptionIndex = -1;
        switch (who) {
            case CHOSEN_COMPETITOR:
                chosenOptionIndex = menu.chooseOption(competitorOptions);
                break;
            case RANDOM_COMPETITOR:
                chosenOptionIndex = new Random().nextInt(competitorOptions.size());
                break;
        }
        chosenCompetitorId = competitorOptions.get(chosenOptionIndex).getId();
        effectResult.setChosenId(chosenCompetitorId);
        return competitorOptions.get(chosenOptionIndex);
    }

    public int chooseOption(List<? extends Option> options) {
        return menu.chooseOption(options);
    }

    private void printEndTurnMessage(Competitor competitor) {
        printSystemMessage(String.format("%s turn ended\n", competitor.getView()));
    }

    private void enterAndClear() {
        waitForEnter();
        clearScreen();
    }

    private void waitForEnter() {
        final String WAIT_FOR_ENTER_MESSAGE =
                "Press <Enter> to continue...";

        waitForEnter(WAIT_FOR_ENTER_MESSAGE);
    }

    private void waitForEnter(String customMessage) {
        Scanner scanner = new Scanner(System.in);
        printSystemMessage(customMessage);
        scanner.nextLine();
    }

    private void printSystemMessage(String message) {
        final String SYSTEM_MESSAGE_PREFIX = "\t#";
        System.out.println(SYSTEM_MESSAGE_PREFIX + message);
    }

    private void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }


}
