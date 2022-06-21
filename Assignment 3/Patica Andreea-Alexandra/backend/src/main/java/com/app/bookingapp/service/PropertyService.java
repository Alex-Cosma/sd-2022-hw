package com.app.bookingapp.service;

import com.app.bookingapp.data.dto.mapper.PropertyMapper;
import com.app.bookingapp.data.dto.model.PropertyDto;
import com.app.bookingapp.data.sql.entity.Property;
import com.app.bookingapp.data.sql.entity.User;
import com.app.bookingapp.data.sql.repo.PropertyRepository;
import com.app.bookingapp.data.sql.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final UserRepository userRepository;

    public PropertyDto findById(Long id){       //TODO test
        return propertyRepository.findById(id)
                .map(propertyMapper::propertyToPropertyDto)
                .orElseThrow(() -> new RuntimeException(format("Property with id %s not found", id)));
    }

    public List<Property> findAll(){
        return propertyRepository.findAll();
    }

    public List<Property> allPropertiesByOwner(String username){
        return propertyRepository.findAllByOwnerUsername(username);
    }

    public PropertyDto create(PropertyDto propertyDto){
        Optional<User> userOp = userRepository.findByUsername(propertyDto.getOwner().getUsername());

        Property property = propertyMapper.propertyDtoToProperty(propertyDto);

        userOp.ifPresent(property::setOwner);

        PropertyDto propertyDtoAdded;

        try{
            propertyDtoAdded = propertyMapper.propertyToPropertyDto(propertyRepository.save(property));
        }catch(Exception e){
            propertyDtoAdded = null;
        }

        return propertyDtoAdded;
    }

    public PropertyDto update(Long id, PropertyDto propertyDto){
        Property propertyEntity = propertyMapper.propertyDtoToProperty(propertyDto);
        propertyEntity.setId(id);

        Optional<User> userOp = userRepository.findByUsername(propertyDto.getOwner().getUsername());

        userOp.ifPresent(propertyEntity::setOwner);

        PropertyDto propertyDtoAdded;

        try{
            propertyDtoAdded = propertyMapper.propertyToPropertyDto(propertyRepository.save(propertyEntity));
        }catch(Exception e){
            propertyDtoAdded = null;
        }

        return propertyDtoAdded;
    }

    public void delete(Long id){
        propertyRepository.deleteById(id);
    }
}
