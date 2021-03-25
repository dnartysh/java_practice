package main.service;

import main.model.Task;
import main.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task add(String name, String person) {
        return taskRepository.save(new Task(name, person));
    }

    public Task getById(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> getByName(String name) {
        return taskRepository.findByName(name);
    }

    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);

        return tasks;
    }

    public Task updateById(int id, String name, String person, boolean complete) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setName(name);
            task.setPerson(person);
            task.setComplete(complete);
            taskRepository.save(task);

            return task;
        }

        return null;
    }

    public void updateAll(String name, String person, boolean complete) {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);

        tasks.forEach(task -> {
            task.setName(name);
            task.setPerson(person);
            task.setComplete(complete);
            taskRepository.save(task);
        });
    }

    public boolean deleteById(int id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            taskRepository.delete(taskOptional.get());
            return true;
        }

        return false;
    }

    public void deleteAll() {
        taskRepository.deleteAll();
    }
}





























