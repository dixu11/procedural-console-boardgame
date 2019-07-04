package boardgame;


import boardgame.logic.main_logic.Game;

//BUILDER PATTERN
public class Settings {     //contains general game settings like number of fieldsNum or playersNum
    public static final int DEFAULT_STARTING_FIELDS_NUM =12;
    public static final int DEFAULT_STARTING_PLAYERS_NUM = 3;
    public static final int DEFAULT_STARTING_COINS_TO_WIN_NUM = 50;
    public static final int  DEFAULT_STARTING_COINS_PER_LAP_REWARD = 10;


    private int fieldsNum, playersNum, coinsToWin, coinsPerLapReward;


    public Settings() {
        fieldsNum = DEFAULT_STARTING_FIELDS_NUM;
        playersNum = DEFAULT_STARTING_PLAYERS_NUM;
        coinsToWin = DEFAULT_STARTING_COINS_TO_WIN_NUM;
        coinsPerLapReward = DEFAULT_STARTING_COINS_PER_LAP_REWARD;
    }


    //FACTORY METHODS
    public static Settings createDefaultSettings() {
        return new Settings();
    }

    public static SettingsBuilder createCustomSettings() {
        return new SettingsBuilder();
    }

    public Game createGame(){
        return new Game(this);
    }



    //BUILDER PATTERN
    static class SettingsBuilder {
        private Settings settings;

        public SettingsBuilder() {
            settings = new Settings();
        }

        public SettingsBuilder setFieldsNum(int fieldsNum) {
            settings.setFieldsNum(fieldsNum);
            return this;
        }

        public SettingsBuilder setPlayersNum(int playersNum) {
            settings.setPlayersNum(playersNum);
            return this;
        }

        public SettingsBuilder setCoinsToWinNum(int coinsToWinNum) {
            settings.setCoinsToWin(coinsToWinNum);
            return this;
        }

        public SettingsBuilder setCoinsPerLapReward(int coinsPerLapReward) {
            settings.setCoinsPerLapReward(coinsPerLapReward);
            return this;
        }

        public Settings build() {
            return settings;
        }
    }





    //GS
    public int getFieldsNum() {
        return fieldsNum;
    }

    public void setFieldsNum(int fieldsNum) {
        this.fieldsNum = fieldsNum;
    }

    public int getPlayersNum() {
        return playersNum;
    }

    public void setPlayersNum(int playersNum) {
        this.playersNum = playersNum;
    }

    public int getCoinsToWin() {
        return coinsToWin;
    }

    public void setCoinsToWin(int coinsToWin) {
        this.coinsToWin = coinsToWin;
    }

    public int getCoinsPerLapReward() {
        return coinsPerLapReward;
    }

    public void setCoinsPerLapReward(int coinsPerLapReward) {
        this.coinsPerLapReward = coinsPerLapReward;
    }
}
