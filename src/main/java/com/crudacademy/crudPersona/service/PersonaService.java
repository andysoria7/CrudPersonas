package com.crudacademy.crudPersona.service;

import com.crudacademy.crudPersona.model.Persona;
import com.crudacademy.crudPersona.repository.IPersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService implements IPersonaService {

    @Autowired
    private IPersonaRepository persoRepo;


    @Override
    public List<Persona> getPersonas() {
        List<Persona> listaPersonas = persoRepo.findAll();
        return listaPersonas;
    }

    @Override
    public Persona findPersona(Long id) {
        Persona perso = persoRepo.findById(id).orElse(null);
        return perso;
    }

    @Override
    public void savePersona(String nombre, String apellido) {
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);

        persoRepo.save(persona);

    }

    @Override
    public void editPersona(Long id, String nombre, String apellido) {
        // Busco el objeto original
        Persona persona = this.findPersona(id);

        // Proceso de modificacion a nivel logico
        persona.setNombre(nombre);
        persona.setApellido(apellido);

        // Guardar los cambios
        persoRepo.save(persona);
    }

    @Override
    public void deletePersona(Long id) {
        persoRepo.deleteById(id);

    }
}
