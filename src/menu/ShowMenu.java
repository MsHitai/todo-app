package menu;

import service.FileTaskOrganizer;
import interfaces.Menu;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ShowMenu implements Menu {
    FileTaskOrganizer organizer = FileTaskOrganizer.load("resources/save.csv");

    public ShowMenu() throws FileNotFoundException {
    }

    private void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Поменять описание");
        System.out.println("3. Изменить дату");
        System.out.println("4. Пометить задачу как выполненная");
        System.out.println("5. Посмотреть список задач");
        System.out.println("6. Удалить задачу по id");
        System.out.println("7. Удалить все задачи");
        System.out.println("8. Удалить все выполненные задачи");
        System.out.println("0. Выйти из приложения");
    }

    @Override
    public void display(Scanner scanner) {
        boolean quit = false;
        while (!quit) {
            printMenu();
            int input = -1;
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Введите целое число");
                scanner.nextLine();
            }
            switch (input) {
                case 1:
                    System.out.println("Введите описание");
                    scanner.nextLine();
                    String description = scanner.nextLine();
                    System.out.println("Введите новую дату в формате: дд-мм-гггг");
                    String date = scanner.nextLine();
                    organizer.addTask(organizer.createTask(description, date));
                    break;
                case 2:
                    System.out.println("Введите id задачи");
                    try {
                        int id = scanner.nextInt();
                        System.out.println("Введите новое описание");
                        scanner.nextLine();
                        description = scanner.nextLine();
                        organizer.changeTask(id, description);
                    } catch (InputMismatchException e) {
                        System.out.println("Введите целое число");
                        scanner.nextLine();
                    }
                    break;
                case 3:
                    System.out.println("Введите id задачи");
                    try {
                        int id = scanner.nextInt();
                        System.out.println("Введите новую дату в формате: дд-мм-гггг");
                        scanner.nextLine();
                        date = scanner.nextLine();
                        organizer.assignDeadLine(id, date);
                    } catch (InputMismatchException e) {
                        System.out.println("Введите целое число");
                        scanner.nextLine();
                    }
                    break;
                case 4:
                    System.out.println("Введите id задачи");
                    try {
                        int id = scanner.nextInt();
                        organizer.markAsDone(id);
                        System.out.println("Задача выполнена");
                    } catch (InputMismatchException e) {
                        System.out.println("Введите целое число");
                        scanner.nextLine();
                    }
                    break;
                case 5:
                    System.out.println(organizer.getTasks());
                    break;
                case 6:
                    System.out.println("Введите id задачи");
                    try {
                        int id = scanner.nextInt();
                        organizer.removeTask(id);
                    } catch (InputMismatchException e) {
                        System.out.println("Введите целое число");
                        scanner.nextLine();
                    }
                    break;
                case 7:
                    organizer.deleteAllTasks();
                    System.out.println("Успешно.");
                    break;
                case 8:
                    organizer.deleteAllDoneTasks();
                    System.out.println("Успешно.");
                    break;
                case 0:
                    quit = true;
                    scanner.close();
                    break;
                default:
                    System.out.println("Такой команды пока нет");
                    break;

            }
        }
    }

}
