package com.Daniel_Muntean4.pomodoro_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicRepository topicRepository;
    public TopicController(TopicRepository topicRepository){
        this.topicRepository= topicRepository;
    }
    @PostMapping("/")
    public ResponseEntity<Topic> postTopic(@RequestBody Topic topic){
        Topic saved = topicRepository.save(topic);
        return ResponseEntity.created(URI.create("/api/topics/"+saved.getId())).body(saved);
    }
    @GetMapping("/")
    public List<Topic> getTopics(){
        return topicRepository.findAll();
    }

    @GetMapping("/{id}")
    public Topic getTopic( @PathVariable Long id){
        return topicRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found, topic"));
    }

    @PutMapping("/{id}")
    public Topic putTopic(@PathVariable Long id, @RequestBody Topic topic){

        topic.setId(id);
        return topicRepository.save(topic);
    }
    @PatchMapping("/{id}")
    public Topic patchTopic(@PathVariable Long id, @RequestBody Topic topic){
        Topic existing = topicRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(topic.getTopicName()!=null){
            existing.setTopicName(topic.getTopicName());
        }
        return topicRepository.save(existing);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id){
        topicRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


