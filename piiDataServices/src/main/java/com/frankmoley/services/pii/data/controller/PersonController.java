package com.frankmoley.services.pii.data.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.frankmoley.services.pii.data.domain.Person;
import com.frankmoley.services.pii.data.exception.BadRequestException;
import com.frankmoley.services.pii.data.exception.NotFoundException;
import com.frankmoley.services.pii.data.exception.RequestConflictException;
import com.frankmoley.services.pii.data.repository.PersonRepository;

/**
 * @author Frank Moley
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    private static final String FORWARD_SLASH = "/";

    @RequestMapping(method=RequestMethod.GET)
    public Person findPerson(@RequestParam(value = "displayName")String displayName){
        if(null == displayName || displayName.isEmpty()){
            throw new BadRequestException("Display name must be specified");
        }else{
           return this.personRepository.findByDisplayName(displayName);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public Person addPerson(@RequestBody final Person model, HttpServletRequest request, HttpServletResponse response){
        model.setId(UUID.randomUUID().toString());
        Person person =  this.personRepository.save(model);
        if(null != person){
            String location = request.getRequestURI().toString() + FORWARD_SLASH + person.getId();
            response.addHeader("Location", location);
            response.setStatus(201);
        }
        return person;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable(value="id")String id){
        Person person = this.personRepository.findOne(id);
        if(null == person){
            throw new NotFoundException("Id not found");
        }
        return person;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public Person updatePerson(@PathVariable(value="id")String id, @RequestBody Person person){
        if(null == id || !id.equals(person.getId())){
            throw new RequestConflictException("Id doesn't match model id");
        }
        return this.personRepository.save(person);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void deletePerson (@PathVariable(value="id")String id){
        if(null == id){
            throw new BadRequestException("Id must be valid for request");
        }
        this.personRepository.delete(id);
    }

}
