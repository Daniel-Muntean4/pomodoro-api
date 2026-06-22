package com.Daniel_Muntean4.pomodoro_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    public TaskController(TaskRepository taskRepository){
        this.taskRepository= taskRepository;
    }
    @PostMapping("/")
    public ResponseEntity<Task> postTask(@RequestBody Task task){
        Task saved = taskRepository.save(task);
        return ResponseEntity.created(URI.create("/api/tasks/"+saved.getId())).body(saved);
    }
    @GetMapping("/")
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task getTask( @PathVariable Long id){
        return taskRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found, task"));
    }

    @PutMapping("/{id}")
    public Task putTask(@PathVariable Long id, @RequestBody Task task){

        task.setId(id);
        return taskRepository.save(task);
    }
    @PatchMapping("/{id}")
    public Task patchTask(@PathVariable Long id, @RequestBody Task task){
        Task existing = taskRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(task.getTopicName()!=null){
            existing.setTopicName(task.getTopicName());
        }
        return taskRepository.save(existing);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


