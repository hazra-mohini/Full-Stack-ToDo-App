package com.example.todoapp.controller;

import com.example.todoapp.model.ToDo;
import com.example.todoapp.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    // Display list of ToDos
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("todos", toDoService.getAllToDos());
        return "index";
    }

    // Show form to create a new ToDo
    @GetMapping("/add")
    public String showAddTodoForm(Model model) {
        ToDo todo = new ToDo();
        model.addAttribute("todo", todo);
        return "add-todo";
    }

    // Handle submission of new ToDo
    @PostMapping("/add")
    public String addTodo(@ModelAttribute("todo") ToDo todo) {
        toDoService.createToDo(todo);
        return "redirect:/";
    }

    // Show form to update an existing ToDo
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        ToDo todo = toDoService.getToDoById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ToDo ID: " + id));
        model.addAttribute("todo", todo);
        return "update-todo";
    }

    // Handle submission of the updated ToDo
    @PostMapping("/update/{id}")
    public String updateTodo(@PathVariable("id") Long id, @ModelAttribute("todo") ToDo todoDetails) {
        ToDo todo = toDoService.getToDoById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ToDo ID: " + id));
        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());
        todo.setCompleted(todoDetails.isCompleted());
        toDoService.updateToDo(todo);
        return "redirect:/";
    }

    // Delete a ToDo
    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable("id") Long id) {
        toDoService.deleteToDoById(id);
        return "redirect:/";
    }
}
