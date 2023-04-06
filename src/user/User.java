package user;

import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private String userName;

    private final List<Task> userTasks = new ArrayList<>();

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Task> getUserTasks() {
        return userTasks;
    }

    public void addTasks(Task task) {
        userTasks.add(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) && userTasks.equals(user.userTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userTasks);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userTasks=" + userTasks +
                '}';
    }
}
