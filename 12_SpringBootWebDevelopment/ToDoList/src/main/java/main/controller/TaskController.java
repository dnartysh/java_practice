package main.controller;

import main.model.Task;
import main.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/tasks")
    private ResponseEntity<?> add(@RequestParam(value = "name") String name,
                               @RequestParam(value = "person") String person) {
        return new ResponseEntity<>(taskService.add(name, person), HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{id}")
    private ResponseEntity<?> get(@PathVariable int id) {
        Task task = taskService.getById(id);
        return new ResponseEntity<>(task, task == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/tasks")
    private ResponseEntity<?> getAll() {
        return new ResponseEntity<>(taskService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/tasks/search")
    private ResponseEntity<?> getSearch(@RequestParam("query") String name) {
        return new ResponseEntity<>(taskService.getByName(name), HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    private ResponseEntity<?> update(@PathVariable int id,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "person") String person,
                                  @RequestParam(value = "complete", required = false) boolean complete) {
        Task task = taskService.updateById(id, name, person, complete);
        return new ResponseEntity<>(task, task == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PutMapping("/tasks")
    private ResponseEntity<?> updateAll(@RequestParam(value = "name") String name,
            @RequestParam(value = "person") String person,
            @RequestParam(value = "complete", required = false) boolean complete) {
        taskService.updateAll(name, person, complete);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    private ResponseEntity<?> delete(@PathVariable int id) {
        boolean isDelete = taskService.deleteById(id);
        return new ResponseEntity<>(null, isDelete ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tasks")
    private ResponseEntity<?> deleteAll() {
        taskService.deleteAll();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}











