package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import interfaces.Organizable;
import model.Task;
import org.junit.jupiter.api.*;
import service.FileTaskOrganizer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    private final Organizable taskManager = new FileTaskOrganizer("resources/test.csv");
    private final HttpTaskServer server = new HttpTaskServer(taskManager);

    private final HttpClient client = HttpClient.newHttpClient();

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    HttpTaskServerTest() throws IOException {
    }

    @BeforeEach
    void setUp() {
        server.start();
        Task task = new Task(0, "testTask", LocalDate.of(2023, Month.APRIL, 6), false);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = task.getDueDate().format(dtf);

        taskManager.addTask(taskManager.createTask(task.getDescription(), date));
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @DisplayName("Можно создавать задачу")
    @Test
    void shouldCreateTask() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks");
        String someTask = gson.toJson(new Task(0, "testTask2",
                LocalDate.of(2023, Month.APRIL, 6), false));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(someTask))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();

        assertEquals(200, code);

        assertEquals(2, taskManager.getTasks().size());
    }

    @Test
    void shouldGetTasks() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        String tasks = gson.toJson(taskManager.getTasks());

        assertEquals(body, tasks);
    }

    @Test
    void shouldGetTaskById() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        String tasks = gson.toJson(taskManager.getTask(1));

        assertEquals(body, tasks);
    }

    @Test
    void shouldUpdateTask() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/1");
        String someTask = gson.toJson(new Task(1, "testTask2Changed",
                LocalDate.of(2023, Month.APRIL, 7), true));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .PUT(HttpRequest.BodyPublishers.ofString(someTask))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();

        assertEquals(200, code);

        Task task1 = taskManager.getTask(1);

        assertEquals("testTask2Changed", task1.getDescription());
        assertEquals(LocalDate.of(2023, Month.APRIL, 7), task1.getDueDate());
        assertTrue(task1.isDone());
    }

    @Test
    void shouldDeleteAllTasks() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();

        assertEquals(200, code);

        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void shouldDeleteTask() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();

        assertEquals(200, code);

        assertEquals(0, taskManager.getTasks().size());
    }
    @DisplayName("задача не удалится с неверным идентификатором")
    @Test
    void shouldNotDeleteTaskWithWrongId() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/4");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();

        assertEquals(200, code);

        assertEquals(1, taskManager.getTasks().size());
    }
    @DisplayName("должны получить ошибку, если указать букву вместо цифры ид для удаления")
    @Test
    void shouldGetErrorWhenDeleteNotNumberId() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/a");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();

        assertEquals(400, code);

        assertEquals(1, taskManager.getTasks().size());
    }
    @DisplayName("должны получить ошибку, если указать неправильный номер ид для конкретной задачи")
    @Test
    void shouldGetErrorIfUpdateWithWrongNumberId() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/2");
        String someTask = gson.toJson(new Task(1, "testTask2Changed",
                LocalDate.of(2023, Month.APRIL, 7), true));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .PUT(HttpRequest.BodyPublishers.ofString(someTask))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();

        assertEquals(400, code);

        Task task1 = taskManager.getTask(1);

        assertEquals("testTask", task1.getDescription());
        assertEquals(LocalDate.of(2023, Month.APRIL, 6), task1.getDueDate());
        assertFalse(task1.isDone());
    }

    @DisplayName("должны получить ошибку, если указать вместо цифры ид указать букву")
    @Test
    void shouldGetErrorWhenUpdatingTaskWithWrongId() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/a");
        String someTask = gson.toJson(new Task(1, "testTask2Changed",
                LocalDate.of(2023, Month.APRIL, 7), true));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .PUT(HttpRequest.BodyPublishers.ofString(someTask))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();

        assertEquals(400, code);

        Task task1 = taskManager.getTask(1);

        assertEquals("testTask", task1.getDescription());
        assertEquals(LocalDate.of(2023, Month.APRIL, 6), task1.getDueDate());
        assertFalse(task1.isDone());
    }
}