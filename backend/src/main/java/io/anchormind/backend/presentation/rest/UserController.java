package io.anchormind.backend.presentation.rest;

import io.anchormind.backend.business.facade.UserFacade;
import io.anchormind.backend.domain.dto.UserCreateDTO;
import io.anchormind.backend.domain.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserCreateDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userFacade.create(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"No se pudo crear el usuario: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {

        // Este método llamará al Service que devuelve el DTO con 8 campos
        try {
            return ResponseEntity.ok(userFacade.findByUsername(username));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"No se encontró el usuario: " + e.getMessage() + "\"}");
        }
    }



    /**
     * PROCESO: Safe Delete de Usuarios
     * Adaptamos el controlador para que soporte la baja lógica.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userFacade.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"No se pudo dar de baja el usuario: " + e.getMessage() + "\"}");
        }
    }


}