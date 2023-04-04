import interfaces.Menu;
import menu.ShowMenu;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // 1 сценарий (comment the others before starting the program):
        Scanner scanner = new Scanner(System.in);

        Menu menu = new ShowMenu();

        menu.display(scanner);

    }
}
