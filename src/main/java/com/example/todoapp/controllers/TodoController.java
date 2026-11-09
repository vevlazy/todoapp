package com.example.todoapp.controllers;

import com.example.todoapp.model.TodoItem;
import com.example.todoapp.repositories.TodoItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TodoController implements CommandLineRunner {

    private final TodoItemRepository todoItemRepository;

    public TodoController(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @GetMapping
    public String index(Model model){

        List<TodoItem> allsTodos = todoItemRepository.findAll();
        model.addAttribute("allTodos",allsTodos);
        model.addAttribute("newTodo", new TodoItem());
        return "index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute TodoItem todoItem){
        todoItemRepository.save(todoItem);
        return "redirect:/";
    }
    @PostMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id){
        todoItemRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/removeAll")
    public String removeAllItems(){
        todoItemRepository.deleteAll();
        return "redirect:/";
    }
    @PostMapping("/search")
    public String searchTodoItem(@RequestParam("searchTerm")String searchTerm,Model model){
        List<TodoItem> allTodo = todoItemRepository.findAll();
        List<TodoItem> result = allTodo.stream().filter(r->r.getTitle()
                .toLowerCase()
                .contains(searchTerm.toLowerCase()))
                .toList();
        model.addAttribute("allTodos",result);
        model.addAttribute("newTodo",new TodoItem());
        model.addAttribute("searchTerm",searchTerm);
        return "index";
    }
    @Override
    public void run(String... args) throws Exception {
    }
}
