package src.menu;

import src.model.Task;
import src.service.Organizable;
import src.service.TaskOrganizer;

import java.util.Scanner;

public class ShowMenu implements Menu {
    Organizable organizer = new TaskOrganizer();
    int input;

    private void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Поменять описание");
        System.out.println("3. Изменить дату");
        System.out.println("4. Пометить задачу как выполненная");
        System.out.println("5. Посмотреть список задач");
        System.out.println("0. Выйти из приложения");
    }
    @Override
    public void display(Scanner scanner) {
        printMenu();
        int input = scanner.nextInt();
        switch (input) {
            case 1:
                organizer.addTask(organizer.createTask(scanner));
                break;
            case 5:
                System.out.println(organizer.getTasks());
                break;

        }
    }

}
