package com.fit.iuh.controllers.api;

import com.fit.iuh.enums.UserAccountState;
import com.fit.iuh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserAPIController {

    @Autowired
    private UserService userService;

    @PutMapping("/state-change")
    public ResponseEntity<Map<String, Object>> changeState(@RequestParam("id") int id, @RequestParam("state") UserAccountState state) {
        try {
            if (userService.changeState(id, state)) {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Change state successfully!", "errorCode", 200, "data", ""));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Error when change state!", "errorCode", 404, "data", ""));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Error: " + e.getMessage(), "errorCode", 500, "data", ""));
        }
    }

}
