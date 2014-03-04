package com.frankmoley.services.pii.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.frankmoley.services.pii.data.domain.Person;

/**
 * @author Frank Moley
 */
@Repository
public interface PersonRepository extends MongoRepository<Person, String>{

    Person findByDisplayName(String displayName);
}
