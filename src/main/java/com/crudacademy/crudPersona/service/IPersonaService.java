package com.crudacademy.crudPersona.service;

import com.crudacademy.crudPersona.model.Persona;

import java.util.List;

public interface IPersonaService {

    public List<Persona> getPersonas();

    public Persona findPersona(Long id);

    public void savePersona(String nombre, String apellido);

    public void editPersona(Long id, String nombre, String apellido);

    public void deletePersona(Long id);

}
