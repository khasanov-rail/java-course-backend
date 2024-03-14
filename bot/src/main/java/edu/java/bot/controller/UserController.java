package edu.java.bot.controller;

import edu.java.bot.dto.request.UserRegistrationRequest;
import edu.java.bot.dto.request.UserSettingsUpdateRequest;
import edu.java.bot.dto.response.UserRegistrationResponse;
import edu.java.bot.dto.response.UserSettingsUpdateResponse;
import edu.java.bot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest request) {
        UserRegistrationResponse response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/settings/update")
    public ResponseEntity<UserSettingsUpdateResponse>
    updateUserSettings(@RequestBody UserSettingsUpdateRequest request) {
        UserSettingsUpdateResponse response = userService.updateUserSettings(request);
        return ResponseEntity.ok(response);
    }
}
