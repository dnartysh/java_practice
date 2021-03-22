package main;

import main.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    private final AtomicInteger taskId;
    private final HashMap<Integer, Task> tasks;

    public Storage() {
        this.taskId = new AtomicInteger();
        tasks = new HashMap<>();
        System.out.println("Storage service started...");
    }

    public Task addTask(String name, String person) {
        Task task = new Task(taskId.incrementAndGet());
        task.setName(name);
        task.setPerson(person);
        task.setCreateDate(new Date());
        task.setComplete(false);
        tasks.put(task.getId(), task);

        return task;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public synchronized boolean removeTask(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            return false;
        }

        return tasks.remove(id, task);
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public synchronized Task updateTask(int id, String name, String person, boolean complete) {
        Task task = tasks.get(id);

        if (task == null) {
            return null;
        }

        task.setName(name);
        task.setPerson(person);
        task.setComplete(complete);

        return task;
    }

    public void updateAllTasks(String name, String person, boolean complete) {
        synchronized (tasks) {
            tasks.forEach((integer, task) -> {
                task.setName(name);
                task.setPerson(person);
                task.setComplete(complete);
            });
        }
    }
}
