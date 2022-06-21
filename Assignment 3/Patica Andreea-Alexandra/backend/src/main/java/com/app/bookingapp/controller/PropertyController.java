package com.app.bookingapp.controller;

import com.app.bookingapp.data.dto.model.PropertyDto;
import com.app.bookingapp.data.sql.entity.Property;
import com.app.bookingapp.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.bookingapp.utils.URLMapping.*;

@RestController
@RequestMapping(PROPERTY)
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping
    public List<Property> allProperties(){
        return propertyService.findAll();
    }

    @GetMapping(USERNAME)
    public List<Property> allPropertiesByOwner(@PathVariable String username){
        return propertyService.allPropertiesByOwner(username);
    }

    @PostMapping()
    public PropertyDto create(@RequestBody PropertyDto property){
        return propertyService.create(property);
    }

    @PatchMapping(ID)
    public PropertyDto update(@PathVariable Long id, @RequestBody PropertyDto property) {
        return propertyService.update(id, property);
    }

    @DeleteMapping(ID)
    public void delete(@PathVariable Long id) {
        propertyService.delete(id);
    }
}
