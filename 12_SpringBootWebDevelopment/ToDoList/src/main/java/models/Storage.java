package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    private static final AtomicInteger taskId = new AtomicInteger();
    private static final HashMap<Integer, Task> tasks = new HashMap<>();

    public static Task addTask(String name, String executor) {
        Task task = new Task(taskId.incrementAndGet());
        task.setName(name);
        task.setExecutor(executor);
        task.setCreateDate(new Date());
        task.setComplete(false);
        tasks.put(task.getId(), task);

        return task;
    }

    public static Task getTask(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }

        return null;
    }

    public static List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public static synchronized boolean removeTask(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            return false;
        }

        return tasks.remove(id, task);
    }

    public static void removeAllTasks() {
        tasks.clear();
    }

    public static synchronized Task updateTask(int id, String name, String executor, boolean complete) {
        Task task = tasks.get(id);

        if (task == null) {
            return null;
        }

        task.setName(name);
        task.setExecutor(executor);
        task.setComplete(complete);

        return task;
    }

    public static void updateAllTasks(String name, String executor, boolean complete) {
        synchronized (tasks) {
            tasks.forEach((integer, task) -> {
                task.setName(name);
                task.setExecutor(executor);
                task.setComplete(complete);
            });
        }
    }
}
