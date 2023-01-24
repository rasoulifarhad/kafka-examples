package com.farhad.example.basickafkasample;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.farhad.example.basickafkasample.Configuration.*;


@RestController
@RequiredArgsConstructor
public class Controller {

    private final Sender sender;

    @PostMapping(value="/api/send/blocking/{what}")
    public ResponseEntity<Void> sendBlock(@PathVariable String what) {

        
        sender.sendBlocking(TOPIC,"key" + what, what);
        return ResponseEntity.ok().body(null);
    
    }

    @PostMapping(value="/api/send/async/{what}")
    public ResponseEntity<Void> sendAsync(@PathVariable String what) {

        
        sender.sendAsyncWithLogCallback(TOPIC,"key" + what, what);
        return ResponseEntity.ok().body(null);
    
    }

}
