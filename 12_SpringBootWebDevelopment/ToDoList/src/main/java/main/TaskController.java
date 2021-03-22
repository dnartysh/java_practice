package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/tasks")
    private ResponseEntity<?> add(@RequestParam(value = "name") String name,
                               @RequestParam(value = "person") String person) {
        Task task = taskRepository.save(new Task(name, person));

        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{id}")
    private ResponseEntity<?> get(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @GetMapping("/tasks")
    private List<Task> getAll() {
        List<Task> listTasks = new ArrayList<>();
        taskRepository.findAll().forEach(listTasks::add);

        return listTasks;
    }

    @PutMapping("/tasks/{id}")
    private ResponseEntity<?> update(@PathVariable int id,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "person") String person,
                                  @RequestParam(value = "complete", required = false) boolean complete) {
        Optional<Task>  optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();

            task.setName(name);
            task.setPerson(person);
            task.setComplete(complete);

            taskRepository.save(task);

            return new ResponseEntity<>(task, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/tasks")
    private ResponseEntity<?> updateAll(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "person") String person,
                                     @RequestParam(value = "complete", required = false) boolean complete) {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);

        tasks.forEach(task -> {
            task.setName(name);
            task.setPerson(person);
            task.setComplete(complete);
            taskRepository.save(task);
        });

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    private ResponseEntity<?> delete(@PathVariable int id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            taskRepository.delete(taskOptional.get());
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tasks")
    private ResponseEntity<?> deleteAll() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);

        tasks.forEach(task -> taskRepository.delete(task));

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
