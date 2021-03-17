package main;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import models.Storage;
import models.Task;

import java.util.List;

@RestController
public class TaskController {

    @PostMapping("/tasks")
    private ResponseEntity add(@RequestParam(value = "name") String name,
                               @RequestParam(value = "exec") String executor) {
        return new ResponseEntity(Storage.addTask(name, executor), HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{id}")
    private ResponseEntity get(@PathVariable int id) {
        Task task = Storage.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Task.class);
        }

        return new ResponseEntity(task, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    private List<Task> getAll() {
        return Storage.getTasks();
    }

    @PutMapping("/tasks/{id}")
    private ResponseEntity update(@PathVariable int id,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "exec") String executor,
                                  @RequestParam(value = "complete", required = false) boolean complete) {
        Task task = Storage.updateTask(id, name, executor, complete);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Task.class);
        }

        return new ResponseEntity(task, HttpStatus.OK);
    }

    @PutMapping("/tasks")
    private ResponseEntity updateAll(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "exec") String executor,
                                     @RequestParam(value = "complete", required = false) boolean complete) {
        Storage.updateAllTasks(name, executor, complete);
        return ResponseEntity.status(HttpStatus.OK).body(Task.class);
    }

    @DeleteMapping("/tasks/{id}")
    private ResponseEntity delete(@PathVariable int id) {
        if (Storage.removeTask(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Task.class);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Task.class);
    }

    @DeleteMapping("/tasks")
    private ResponseEntity deleteAll() {
        Storage.removeAllTasks();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Task.class);
    }
}
