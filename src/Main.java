package src;

import src.menu.Menu;
import src.menu.ShowMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Menu menu = new ShowMenu();

        menu.display(scanner);

    }
}
