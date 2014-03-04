package com.frankmoley.services.pii.data.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.frankmoley.services.pii.data.domain.Address;
import com.frankmoley.services.pii.data.domain.Person;
import com.frankmoley.services.pii.data.domain.Phone;
import com.frankmoley.services.pii.data.repository.PersonRepository;

/**
 * @author Frank Moley
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/applicationContext-test.xml")
@WebAppConfiguration
public class PersonControllerTest {

    private MockMvc mockMvc;
    private String personId = null;
    private Person person = null;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private WebApplicationContext wac;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setSsn("111-22-1212");
        person.setDisplayName("mockperson");
        person.setDlNumber("K00-11-2222");
        person.setSuffix("JR");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("mock@mock.com");
        emailAddresses.add("mock1@mock.com");
        person.setEmailAddresses(emailAddresses);
        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        address.setCity("Olathe");
        address.setState("KS");
        address.setStreetAddress("1200 E 151st Street");
        address.setZipCode("66062");
        addresses.add(address);
        person.setAddresses(addresses);
        person.setFirstName("John");
        person.setMiddleName("James");
        person.setLastName("Doe");
        person.setPrefix("MR");
        List<Phone> phones = new ArrayList<>();
        Phone phone = new Phone();
        phone.setAreaCode("913");
        phone.setCountryCode("1");
        phone.setPhoneNumber("5551212");
        person.setPhones(phones);
        this.personRepository.save(person);
        this.personId = person.getId();
    }

    @After
    public void tearDown() throws Exception {
        for (Person person : this.personRepository.findAll()) {
            this.personRepository.delete(person.getId());
        }

    }

    @Test
    public void testFindPerson_json() throws Exception {
        mockMvc.perform(get("/persons?displayName=mockperson").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")));
    }

    @Test
    public void testFindPerson_xml() throws Exception {
        mockMvc.perform(get("/persons?displayName=mockperson").accept(MediaType.parseMediaType("application/xml;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/xml;charset=UTF-8")));
    }

    @Test
    public void testFindPerson_invalidCall() throws Exception {
        mockMvc.perform(get("/persons").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddPerson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person tempPerson = new Person();
        tempPerson.setDisplayName("junittest");
        String jsonValue = mapper.writeValueAsString(tempPerson);
        mockMvc.perform(post("/persons").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).content(jsonValue))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")));
    }

    @Test
    public void testAddPerson_Exception() throws Exception {
        mockMvc.perform(post("/persons").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void testGetPerson() throws Exception {
        mockMvc.perform(get("/persons/" + personId).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")));
    }

    @Test
    public void testGetPerson_notFound() throws Exception {
        mockMvc.perform(get("/persons/1A45").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePerson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        person.setDisplayName("newJunitTestPerson");
        String jsonValue = mapper.writeValueAsString(person);
        mockMvc.perform(put("/persons/" + personId).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).content(jsonValue))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")));
    }

    @Test
    public void testDeletePerson() throws Exception {
        mockMvc.perform(delete("/persons/" + personId)).andExpect(status().isOk());
    }
}
