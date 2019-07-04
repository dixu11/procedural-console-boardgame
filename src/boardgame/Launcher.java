package boardgame;

import boardgame.logic.main_logic.Game;

public class Launcher {

    public static void main(String[] args) {
        Settings settings = Settings.createDefaultSettings();    // creating settings using factory method

                                                                 // creating settings using builder pattern
        /*Settings customSettings = Settings.createCustomSettings()
                .setCoinsToWinNum(50)
                .setFieldsNum(50)
                .setPlayersNum(4)
                .build();*/

        Game game = settings.createGame();
        game.runGame();
    }
}
