package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import interfaces.Organizable;
import model.Task;
import service.FileTaskOrganizer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static java.net.HttpURLConnection.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {

    public static final int PORT = 8080;

    private final HttpServer httpServer;
    private final Gson gson;

    private final Organizable taskManager;

    public HttpTaskServer(Organizable taskManager) throws IOException {
        this.taskManager = taskManager;
        httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        //httpServer.createContext("/register", this::register);
        httpServer.createContext("/tasks", this::handleTasks);
        gson = new Gson();
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/tasks");
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }

    private void register(HttpExchange exchange) {

    }

    private void handleTasks(HttpExchange exchange) {
        try (exchange) {
            String method = exchange.getRequestMethod();

            switch (method) {
                case "GET" -> handleGetTasks(exchange);
                case "POST" -> handlePostTasks(exchange);
                case "DELETE" -> handleDeleteTasks(exchange);
                case "PUT" -> handlePutTasks(exchange);
                default -> {
                    System.out.println(method + " метод не поддерживается. Принимаются запросы GET, POST, PUT, DELETE");
                    exchange.sendResponseHeaders(HTTP_BAD_METHOD, 0);
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void handlePutTasks(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        if (pathParts.length == 2) {
            System.out.println("Нужно указать идентификатор задачи для обновления задачи");
            exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            return;
        }
        int id = parsePathId(pathParts[3]);
        if (id == -1) {
            System.out.println("Некорректный идентификатор");
            exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
        } else {
            String taskString = readText(exchange);
            Task task = gson.fromJson(taskString, Task.class);
            if (task.getId() != id) {
                System.out.println("Идентификаторы задач не совпадают!");
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                return;
            }
            taskManager.updateTask(task);
            exchange.sendResponseHeaders(HTTP_OK, 0);
        }
    }

    private void handleDeleteTasks(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        if (pathParts.length == 2) {
            taskManager.deleteAllTasks();
            exchange.sendResponseHeaders(HTTP_OK, 0);
            return;
        }
        int id = parsePathId(pathParts[3]);
        if (id == -1) {
            System.out.println("Некорректный идентификатор");
            exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
        } else {
            taskManager.removeTask(id);
            exchange.sendResponseHeaders(HTTP_OK, 0);
        }
    }

    private void handlePostTasks(HttpExchange exchange) throws IOException {
        String taskString = readText(exchange);
        Task task = gson.fromJson(taskString, Task.class);
        String description = task.getDescription();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String localDate = task.getDueDate().format(formatter);
        taskManager.createTask(description, localDate);
        taskManager.addTask(task);
        exchange.sendResponseHeaders(HTTP_OK, 0);
    }

    private void handleGetTasks(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        String response;
        if (pathParts.length == 2) {
            response = gson.toJson(taskManager.getTasks());
            sendText(exchange, response);
        } else {
            int id = parsePathId(pathParts[3]);
            if (id == -1) {
                System.out.println("Некорректный идентификатор");
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            } else {
                response = gson.toJson(taskManager.getTask(id));
                sendText(exchange, response);
            }
        }
    }

    private int parsePathId(String pathPart) {
        try {
            return Integer.parseInt(pathPart);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    private void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(HTTP_OK, resp.length);
        h.getResponseBody().write(resp);
    }

    private String generateToken() {
        return "" + System.currentTimeMillis();
    }

    public static void main(String[] args) throws IOException {
        Organizable taskManager = new FileTaskOrganizer("resources/test.csv");

        HttpTaskServer server = new HttpTaskServer(taskManager);

        Task task = new Task(0, "testTask", LocalDate.of(2023, Month.APRIL, 6), false);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd. MM. yyyy");
        String date = task.getDueDate().format(dtf);

        taskManager.createTask(task.getDescription(), date);
        taskManager.addTask(task);

        Task task1 = new Task(1, "testTask2", LocalDate.of(2023, Month.APRIL, 6), false);
        String date1 = task1.getDueDate().format(dtf);

        taskManager.createTask(task1.getDescription(), date1);
        taskManager.addTask(task1);

        server.start();

        //server.stop();
    }
}
