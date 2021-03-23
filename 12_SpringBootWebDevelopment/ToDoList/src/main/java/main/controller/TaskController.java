package main.controller;

import main.model.Task;
import main.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/tasks")
    private ResponseEntity<?> add(@RequestParam(value = "name") String name,
                               @RequestParam(value = "person") String person) {
        return new ResponseEntity<>(taskService.add(name, person), HttpStatus.CREATED);
    }

    @GetMapping(value = {"/tasks", "/tasks/{id}"})
    private ResponseEntity<?> get(@PathVariable Map<String, String> pathMap,
                                  @RequestParam(name = "query", required = false) String name) {
        Object obj;
        String variableId = pathMap.get("id");

        if (variableId != null) {
            int id = Integer.parseInt(variableId);
            obj = taskService.getById(id);
        } else {
            if (name != null) {
                obj = taskService.getByName(name);
            } else {
                obj = taskService.getAll();
            }
        }

        return new ResponseEntity<>(obj, obj == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PutMapping(value = {"/tasks", "/tasks/{id}"})
    private ResponseEntity<?> update(@PathVariable Map<String, String> pathMap,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "person") String person,
                                  @RequestParam(value = "complete", required = false) boolean complete) {
        String variableId = pathMap.get("id");

        if (variableId != null) {
            int id = Integer.parseInt(variableId);
            Task task = taskService.updateById(id, name, person, complete);
            return new ResponseEntity<>(task, task == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } else {
            taskService.updateAll(name, person, complete);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = {"/tasks", "/tasks/{id}"})
    private ResponseEntity<?> delete(@PathVariable Map<String, String> pathMap) {
        String variableId = pathMap.get("id");

        if (variableId != null) {
            boolean isDelete = taskService.deleteById(Integer.parseInt(variableId));
            return new ResponseEntity<>(null, isDelete ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } else {
            taskService.deleteAll();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
