package src;

import src.interfaces.Menu;
import src.menu.ShowMenu;
import src.model.Task;
import src.service.FileTaskOrganizer;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // 1 сценарий (comment the others before starting the program):
        Scanner scanner = new Scanner(System.in);

        Menu menu = new ShowMenu();

        menu.display(scanner);

        // 2 сценарий:
        /*FileTaskOrganizer fileTaskOrganizer = new FileTaskOrganizer("out/resources/save.csv");

        Task task1 = fileTaskOrganizer.createTask("build a snowman", "08. 03. 2023");
        fileTaskOrganizer.addTask(task1);

        Task task2 = fileTaskOrganizer.createTask("get some sleep", "07. 03. 2023");
        fileTaskOrganizer.addTask(task2);

        fileTaskOrganizer.markAsDone(2);*/

        //3-й сценарий:
        /*FileTaskOrganizer fileTaskOrganizer2 = FileTaskOrganizer.load("out/resources/save.csv");
        Task task2 = fileTaskOrganizer2.createTask("repair the chair", "08. 03. 2023");
        fileTaskOrganizer2.addTask(task2);
        fileTaskOrganizer2.removeTask(1);*/
    }
}
