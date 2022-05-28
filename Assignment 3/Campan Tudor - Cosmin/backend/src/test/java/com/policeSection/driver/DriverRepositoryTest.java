package com.policeSection.driver;

import com.policeSection.driver.model.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DriverRepositoryTest {

    @Autowired
    private DriverRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testSave() {
        Driver driverSaved = repository.save(Driver.builder()
                .name("whatever")
                .email("whatever")
                .sectionNumber(3)
                .build());

        assertNotNull(driverSaved);
        assertEquals(1,repository.findAll().size());
    }

    @Test
    public void testFindAll() {
        List<Driver> drivers= new ArrayList<>();
        Driver driverSaved1 = repository.save(Driver.builder()
                .name("whatever1")
                .email("whatever1")
                .sectionNumber(3)
                .build());
        Driver driverSaved2= repository.save(Driver.builder()
                .name("whatever2")
                .email("whatever2")
                .sectionNumber(3)
                .build());
        Driver driverSaved3= repository.save(Driver.builder()
                .name("whatever3")
                .email("whatever3")
                .sectionNumber(3)
                .build());
        drivers.add(driverSaved1);
        drivers.add(driverSaved2);
        drivers.add(driverSaved3);

        repository.saveAll(drivers);
        List<Driver> all = repository.findAll();
        assertEquals(drivers.size(), all.size());
    }


    @Test
    public void testFindById() {
        Driver driverSaved1 = repository.save(Driver.builder()
                .name("whatever1")
                .email("whatever1")
                .sectionNumber(3)
                .build());
        Driver driverFound = repository.findById(driverSaved1.getId()).get();
        assertEquals(driverSaved1.getName(), driverFound.getName());
    }

    @Test
    public void testUpdate(){
        Driver driverSaved1 = repository.save(Driver.builder()
                .name("whatever1")
                .email("whatever1")
                .sectionNumber(3)
                .build());
        driverSaved1.setName("newName");
        driverSaved1 = repository.save(driverSaved1);

        assertEquals(repository.findById(driverSaved1.getId()).get().getName(), "newName");
    }

    @Test
    public void testDelete(){
        Driver driverSaved1 = repository.save(Driver.builder()
                .name("whatever1")
                .email("whatever1")
                .sectionNumber(3)
                .build());
        repository.save(driverSaved1);
        repository.delete(driverSaved1);
        assertTrue(repository.findAll().isEmpty());
    }


}