package boardgame.communication.player_interaction;

import java.util.List;
import java.util.Scanner;

public class OptionMenu {     //class that present menu options and asks for input

    private Scanner scanner;

    private final static String OPENING_MESSAGE = "Choose one from available options: ";
    private final static String WRONG_NUMBER_ERROR = "Wpisałeś liczbę poza zakresem opcji! Spróbuj ponownie.";
    private final static String LETTERS_ERROR = "Możesz wpisywać wyłącznie cyfry. Spróbuj ponownie.";


    public OptionMenu() {
        scanner = new Scanner(System.in);
    }


    public int chooseOption(List<? extends Option> options) {
        boolean incorrectInput;
        int decisionId;

        do {

            System.out.println(OPENING_MESSAGE);
            for (int i = 0; i < options.size(); i++) {
                Option option = options.get(i);
                System.out.printf("%2d. %s\n", i + 1, option.getOptionText());
            }
            System.out.println();

            decisionId = readNumberInput() -1;

            if (decisionId < 0) {
                System.out.println(LETTERS_ERROR);
                incorrectInput = true;
                continue;
            }

            if (decisionId < options.size()) {
                incorrectInput = false;
            } else {
                System.out.println(WRONG_NUMBER_ERROR);
                incorrectInput = true;
            }

        } while (incorrectInput);
        return decisionId;
    }

    private int readNumberInput() {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return -1;
            }
        }

        return Integer.parseInt(input);
    }






    //GS
}
