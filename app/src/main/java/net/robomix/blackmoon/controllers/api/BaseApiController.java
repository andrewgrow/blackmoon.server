package net.robomix.blackmoon.controllers.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseApiController {

    @GetMapping("/api")
    public ResponseEntity<Object> testApi() {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.CREATED);
        return response;
    }
}
