package wood.mike.design.behavioral.state.coffeemachine;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CoffeeMachineDriver {
    static void main() {
        new CoffeeMachineDriver().run();
    }
    void run() {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        while(run) {
            String menu = switch (coffeeMachine.getState()) {
                case IdleState _ -> "1: Present Card";
                case AuthorizedState _ -> "2: Select Drink";
                case BusyState _ -> "Machine busy... please wait.";
                case OutOfServiceState _ -> "Out of order. 3: Repair";
            };
            System.out.println(menu);
            int selection = getSelection(scanner);
            switch (selection) {
                case 1:
                    coffeeMachine.cardPresented();
                    break;
                case 2:
                    coffeeMachine.drinkSelected();
                    break;
                case 3:
                    coffeeMachine.clearError();
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    private int getSelection(final Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch(InputMismatchException ime) {
            scanner.nextLine();
            return 0;
        }
    }
}

