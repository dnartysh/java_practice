package main.controller;

import main.model.Task;
import main.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Task> tasks = taskService.getAll();

        model.addAttribute("tasks", tasks);
        model.addAttribute("tasksCount", tasks.size());

        return "index";
    }

}
