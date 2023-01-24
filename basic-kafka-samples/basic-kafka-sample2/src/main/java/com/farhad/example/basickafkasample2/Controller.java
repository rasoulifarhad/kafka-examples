package com.farhad.example.basickafkasample2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhad.example.basickafkasample2.common.Foo1;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class Controller {
    

    @Autowired
    private KafkaTemplate<Object,Object> template ;

    @PostMapping(path = "/send/foo/{what}")
    public void sendFoo(@PathVariable String what) {


        template.send("foo-what-topic",what);
        log.info("Sended: {}", new Foo1(what));

    }
}
