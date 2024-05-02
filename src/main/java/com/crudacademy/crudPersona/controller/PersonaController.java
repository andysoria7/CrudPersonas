package com.crudacademy.crudPersona.controller;
import com.crudacademy.crudPersona.model.Persona;
import com.crudacademy.crudPersona.service.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonaController {

    @Autowired
    private IPersonaService persoServ;

    // Endpoint para traer a todas las personas
    @GetMapping("/personas")
    public List<Persona> getPersona() {
        return persoServ.getPersonas();
    }

    // Endpoint para traer a una persona por id
    @GetMapping("persona/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable(value = "id") Long id) {
        Persona persona = persoServ.findPersona(id);
        if (persona == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(persona);
    }

    // Endpoint para crear una persona
    @PostMapping("/persona")
    public ResponseEntity<String> createPersona(@RequestBody Persona persona) {
        // Verificar si se ha establecido manualmente un ID
        if (persona.getId() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede establecer manualmente el ID");
        }

        // Lógica para guardar la persona
        persoServ.savePersona(persona.getNombre(), persona.getApellido());
        return ResponseEntity.status(HttpStatus.CREATED).body("Persona creada con exito");
    }

    // Endpointpara editar una persona
    @PutMapping("/persona/{id}")
    public ResponseEntity<String> editarPersona(@PathVariable Long id, @RequestBody Persona persona) {
        // Ignorar el ID del cuerpo de la solicitud y utilizar el ID proporcionado en la URL
        persona.setId(id);

        try {
            // Verificar si la persona existe antes de editarla
            if (persoServ.findPersona(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La persona con el ID " + id + " no existe");
            }
        // Lógica para editar la persona
            persoServ.editPersona(id, persona.getNombre(), persona.getApellido());
            return ResponseEntity.status(HttpStatus.OK).body("Persona editada con éxito");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la persona: " + e.getMessage());
        }
    }

    // Endpoint para borrar una persona
    @DeleteMapping("/persona/{id}")
    public ResponseEntity<String> eliminarPersona(@PathVariable Long id) {
        // Lógica para eliminar la persona
        try {
            // Verificar si la persona existe antes de intentar eliminarla
            if (persoServ.findPersona(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La persona con el ID " + id + " no existe");
            }

            // Si la persona existe, eliminarla
            persoServ.deletePersona(id);
            return ResponseEntity.status(HttpStatus.OK).body("Persona eliminada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la persona: " + e.getMessage());
        }
    }
}