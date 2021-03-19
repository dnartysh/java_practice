package main;

import models.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class Storage {
    private final AtomicInteger taskId;
    private final HashMap<Integer, Task> tasks;

    public Storage() {
        this.taskId = new AtomicInteger();
        tasks = new HashMap<>();
        System.out.println("Storage service started...");
    }

    public Task addTask(String name, String responsiblePerson) {
        Task task = new Task(taskId.incrementAndGet());
        task.setName(name);
        task.setResponsiblePerson(responsiblePerson);
        task.setCreateDate(new Date());
        task.setComplete(false);
        tasks.put(task.getId(), task);

        return task;
    }

    public Task getTask(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }

        return null;
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

    public synchronized Task updateTask(int id, String name, String responsiblePerson, boolean complete) {
        Task task = tasks.get(id);

        if (task == null) {
            return null;
        }

        task.setName(name);
        task.setResponsiblePerson(responsiblePerson);
        task.setComplete(complete);

        return task;
    }

    public void updateAllTasks(String name, String responsiblePerson, boolean complete) {
        synchronized (tasks) {
            tasks.forEach((integer, task) -> {
                task.setName(name);
                task.setResponsiblePerson(responsiblePerson);
                task.setComplete(complete);
            });
        }
    }
}
