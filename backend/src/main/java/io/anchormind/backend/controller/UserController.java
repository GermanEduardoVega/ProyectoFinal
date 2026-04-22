package io.anchormind.backend.controller;

import io.anchormind.backend.dto.UserCreateDTO;
import io.anchormind.backend.dto.UserDTO;
import io.anchormind.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        // Este método llamará al Service que devuelve el DTO con 8 campos
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO dto) {
        return ResponseEntity.status(201).body(userService.create(dto));
    }
}