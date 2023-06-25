package com.todoapplication.controller;

import com.todoapplication.entity.Status;
import com.todoapplication.entity.Task;
import com.todoapplication.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/")
    public ModelAndView index() {
        // define the view is index.html
        ModelAndView modelAndView = new ModelAndView("index");
        // add object to the model
        modelAndView.addObject("tasks", taskService.getAllTasks());
        return modelAndView;
    }

    @GetMapping("/create-task-form")
    public String showCreateForm(Task task) {
        // Pass task object to form
        /** Solution 1: Pass a task obj via parameter : just add (Task task) and return view, it will automatically
         *  pass the task obj to the view
         */
        /** Solution 2:
         * Add parameter (Model model)
         * Create new Task instance
         * Put the task instance to the model
         * Return the view
         */
        return "createTaskForm";
    }

    @GetMapping("/edit-task-form/{id}")
    public String showEditForm(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "editTaskForm";
    }


    @PostMapping("/create-task")
    public String createTask(@Valid @ModelAttribute("task") Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("error");
            return "createTaskForm";
        }
        taskService.createTask(task);
        return "redirect:/";
    }

    @PostMapping("/edit-task/{id}")
    public String updateTask(@PathVariable(value = "id") Long id, @Valid @ModelAttribute("task") Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "editTaskForm";
        }
        taskService.updateTask(id, task);
        return "redirect:/";
    }

    @GetMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable(value = "id") Long id, Model model) {
        taskService.deleteTask(id);
        return "redirect:/";
    }

    @GetMapping("/finish-task/{id}")
    public String finishTask(@PathVariable(value = "id") Long id) {
        taskService.finishTask(id);
        return "redirect:/";
    }
}
