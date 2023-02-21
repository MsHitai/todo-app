package src.menu;

import src.service.Organizable;
import src.service.TaskOrganizer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ShowMenu implements Menu {
    Organizable organizer = new TaskOrganizer();

    private void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Поменять описание");
        System.out.println("3. Изменить дату");
        System.out.println("4. Пометить задачу как выполненная");
        System.out.println("5. Посмотреть список задач");
        System.out.println("6. Удалить задачу по id");
        System.out.println("0. Выйти из приложения");
    }
    @Override
    public void display(Scanner scanner) {
        boolean quit = false;
        while (!quit) {
            printMenu();
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    organizer.addTask(organizer.createTask(scanner));
                    break;
                case 2:
                    System.out.println("Введите id задачи");
                    int id = scanner.nextInt();
                    System.out.println("Введите новое описание");
                    scanner.nextLine();
                    String description = scanner.nextLine();
                    organizer.changeTask(id, description);
                    break;
                case 3:
                    System.out.println("Введите id задачи");
                    id = scanner.nextInt();
                    System.out.println("Введите новую дату в формате: дд. мм. гггг");
                    scanner.nextLine();
                    String date = scanner.nextLine();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd. MM. yyyy");
                    organizer.assignDeadLine(id, LocalDate.parse(date, dtf));
                    break;
                case 4:
                    System.out.println("Введите id задачи");
                    id = scanner.nextInt();
                    organizer.markAsDone(id);
                    System.out.println("Задача выполнена");
                    break;
                case 5:
                    System.out.println(organizer.getTasks());
                    break;
                case 6:
                    System.out.println("Введите id задачи");
                    id = scanner.nextInt();
                    organizer.removeTask(id);
                    break;
                case 0:
                    quit = true;
                    break;
                default:
                    System.out.println("Такой команды пока нет");
                    break;
            }
        }
    }

}
