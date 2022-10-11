package com.best.practice.BestPractice.services.impl;

import com.best.practice.BestPractice.dtos.UserMasterDto;
import com.best.practice.BestPractice.entities.UserMasterEntity;
import com.best.practice.BestPractice.exception.ResourceExist;
import com.best.practice.BestPractice.exception.ResourceNotFound;
import com.best.practice.BestPractice.mappers.UserMasterMapper;
import com.best.practice.BestPractice.repositories.UserMasterRepository;
import com.best.practice.BestPractice.services.UserMasterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserMasterServiceImpl implements UserMasterService {

    private final UserMasterRepository userMasterRepository;
    private final UserMasterMapper userMasterMapper;

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new ResourceNotFound("Comments contains unacceptable language");
        }
        return false;
    }

    @Override
    public UserMasterDto addUser(UserMasterDto userMasterDto) throws ResourceExist {
        UserMasterEntity entity = userMasterRepository.getUserByName(userMasterDto.getName());
        if (entity != null) {
            throw new ResourceExist("User name exist");
        }
        entity = userMasterMapper.toEntity(userMasterDto);
        entity = userMasterRepository.save(entity);

        return userMasterMapper.toDto(entity);
    }

    public int getAdd(int a, int b){
        int c = a + b;

        return c;
    }
    @Override
    public UserMasterDto getUserByName(String name) throws ResourceNotFound {

        UserMasterEntity userMasterEntity = userMasterRepository.getUserByName(name);
        if(userMasterEntity == null)
            throw new ResourceNotFound("User not found");

        UserMasterDto userMasterDto = userMasterMapper.toDto(userMasterEntity);

        int result = getAdd(5,5);
        if(result == 10)
            return userMasterDto;

        return null;
//        return Optional.ofNullable(userMasterRepository
//                .getUserByName(name))
//                .map(userMasterMapper::toDto)
//                .orElseThrow(() -> new ResourceNotFound("User not found"));
    }

    @Override
    public List<UserMasterDto> getUsers() {
        return Optional.ofNullable(userMasterRepository.findAll())
                .orElse(Collections.emptyList())
                .stream()
                .map(UserMasterMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}
