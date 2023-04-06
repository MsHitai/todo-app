package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import interfaces.Organizable;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.FileTaskOrganizer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpTaskServerTest {

    private final Organizable taskManager = new FileTaskOrganizer("resources/test.csv");
    private final HttpTaskServer server = new HttpTaskServer(taskManager);

    private final HttpClient client = HttpClient.newHttpClient();

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    private Task task;

    HttpTaskServerTest() throws IOException {
    }

    @BeforeEach
    void setUp() {
        server.start();
        task = new Task(0, "testTask", LocalDate.of(2023, Month.APRIL, 6), false);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd. MM. yyyy");
        String date = task.getDueDate().format(dtf);

        taskManager.createTask(task.getDescription(), date);
        taskManager.addTask(task);
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
}