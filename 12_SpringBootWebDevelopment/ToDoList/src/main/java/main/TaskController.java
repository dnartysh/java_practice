package main;

import models.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final Storage storage;

    public TaskController(Storage storage) {
        this.storage = storage;
    }

    @PostMapping("/tasks")
    private ResponseEntity<?> add(@RequestParam(value = "name") String name,
                               @RequestParam(value = "exec") String executor) {
        return new ResponseEntity<>(storage.addTask(name, executor), HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{id}")
    private ResponseEntity<?> get(@PathVariable int id) {
        Task task = storage.getTask(id);
        if (task == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    private List<Task> getAll() {
        return storage.getTasks();
    }

    @PutMapping("/tasks/{id}")
    private ResponseEntity<?> update(@PathVariable int id,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "exec") String executor,
                                  @RequestParam(value = "complete", required = false) boolean complete) {
        Task task = storage.updateTask(id, name, executor, complete);
        if (task == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/tasks")
    private ResponseEntity<?> updateAll(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "exec") String responsiblePerson,
                                     @RequestParam(value = "complete", required = false) boolean complete) {
        storage.updateAllTasks(name, responsiblePerson, complete);
        return ResponseEntity.status(HttpStatus.OK).body(Task.class);
    }

    @DeleteMapping("/tasks/{id}")
    private ResponseEntity<?> delete(@PathVariable int id) {
        if (storage.removeTask(id)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tasks")
    private ResponseEntity<?> deleteAll() {
        storage.removeAllTasks();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
