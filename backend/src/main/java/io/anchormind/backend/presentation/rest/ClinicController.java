package io.anchormind.backend.presentation.rest;

import io.anchormind.backend.business.facade.impl.ClinicFacadeImpl;
import io.anchormind.backend.business.services.impl.ClinicServiceImpl;
import io.anchormind.backend.domain.dto.ClinicDTO;
import io.anchormind.backend.domain.entities.Clinic;
import io.anchormind.backend.presentation.rest.base.BaseControllerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clinics")
public class ClinicController {
    private final ClinicFacadeImpl clinicFacade;

    public ClinicController(ClinicFacadeImpl clinicFacade) {
        this.clinicFacade = clinicFacade;
    }
    /**
     * PROCESO: Listado de Clínicas
     * Adaptamos el método genérico para asegurar que devuelva la lógica
     * procesada por tu ClinicServiceImpl.
     */

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            // El service ya está disponible por la herencia de BaseControllerImpl
            return ResponseEntity.status(HttpStatus.OK).body(clinicFacade.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"No se pudieron recuperar las clínicas.\"}");
        }
    }

    /**
     * PROCESO: Creación Administrativa
     * Aquí es donde el software se adapta a tu flujo:
     * Recibimos un DTO y dejamos que el servicio especializado haga el mapeo.
     */
        @PostMapping("/create")
        public ResponseEntity<?> create(@RequestBody ClinicDTO dto) {
        try {
            // Llamamos al método específico que creaste en ClinicServiceImpl
            return ResponseEntity.status(HttpStatus.CREATED).body(clinicFacade.save(dto));   //se ejecuta dentro de una transacción, mapea las entidades a DTOs y cierra la sesión de Hibernate.
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"No se pudo crear la clínica: " + e.getMessage() + "\"}");
        }
    }

    // Detalle por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            // Ahora la fachada debe tener este método o llamar al service a través de ella
            return ResponseEntity.status(HttpStatus.OK).body(clinicFacade.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Detalle por Nombre (Búsqueda específica)
    @GetMapping("/search")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clinicFacade.findByName(name));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Actualización de datos institucionales
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInstitutional(@PathVariable Long id, @RequestBody ClinicDTO dto) {
        try {
            // Delegamos la actualización a la fachada
            return ResponseEntity.status(HttpStatus.OK).body(clinicFacade.updateInstitutionalData(id, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"No se pudo actualizar: " + e.getMessage() + "\"}");
        }
    }

    /**
     * PROCESO: Baja Lógica (Safe Delete)
     * Sobrescribimos el delete genérico para que el software
     * ejecute la desactivación y no la eliminación física.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            // Este método en ClinicServiceImpl ya lo programamos
            // para que haga clinic.setActive(false)
            clinicFacade.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"No se pudo dar de baja la clínica: " + e.getMessage() + "\"}");
        }
    }
}