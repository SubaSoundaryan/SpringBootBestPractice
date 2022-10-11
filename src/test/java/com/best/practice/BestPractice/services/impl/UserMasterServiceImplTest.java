package com.best.practice.BestPractice.services.impl;

import com.best.practice.BestPractice.dtos.UserMasterDto;
import com.best.practice.BestPractice.entities.UserMasterEntity;
import com.best.practice.BestPractice.exception.ResourceNotFound;
import com.best.practice.BestPractice.mappers.UserMasterMapper;
import com.best.practice.BestPractice.repositories.UserMasterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMasterServiceImplTest {

    @Mock
    UserMasterRepository userMasterRepository;
    @Mock
    UserMasterMapper userMasterMapper;
    @Captor
    private ArgumentCaptor<UserMasterEntity> userMasterEntityArgumentCaptor;

    //	it run before each test. Hence helps to avoid duplicate codes
    private UserMasterServiceImpl userMasterService;
    @BeforeEach
    public void setup(){
        userMasterService = new UserMasterServiceImpl(userMasterRepository,userMasterMapper);
    }

    @Test
    @DisplayName("Clean comment")
    void shouldNotContainsSwearInsideComment() {
        assertThat(userMasterService.containsSwearWords("This is a clean comment")).isFalse() ;
    }

    @Test
    @DisplayName("Should Throw Exception when Exception Contains Swear Words")
    void shouldFailWhenCommentContainsSwearWords() {

        assertThatThrownBy(() -> {
            userMasterService.containsSwearWords("This is a clean shit comment");
        }).isInstanceOf(ResourceNotFound.class)
                .hasMessage("Comments contains unacceptable language");
    }

    @Test
    @DisplayName("Get by username")
    void userLoginTest() {

        String userName = "kumar";
        UserMasterEntity queriedUserMasterEntity = new UserMasterEntity(1L, "Kumar");
        UserMasterDto userMasterDto = new UserMasterDto(1L,"kumar");

        Mockito.when(userMasterRepository.getUserByName(userName)).thenReturn(queriedUserMasterEntity);
        Mockito.when(userMasterMapper.toDto(Mockito.any(UserMasterEntity.class))).thenReturn(userMasterDto);


        UserMasterDto actualUserMasterDto = userMasterService.getUserByName(userName);

        Assertions.assertEquals(actualUserMasterDto.getId(), queriedUserMasterEntity.getId());
    }

    @Test
    @DisplayName("Get by username not found case")
    void getByUserNameFailureCase() {

        String userName = "kuuuuur";
        UserMasterEntity queriedUserMasterEntity = new UserMasterEntity(1L, "Kumar");
        UserMasterDto userMasterDto = new UserMasterDto(1L,"kumar");

        Mockito.when(userMasterRepository.getUserByName(userName)).thenReturn(null);

        assertThatThrownBy(() -> {
            userMasterService.getUserByName("kuuuuur");
        }).isInstanceOf(ResourceNotFound.class)
                .hasMessage("User not found");
    }

    @Test
    @DisplayName("add user")
    void addUser() {

        String userName = "jilbile";
        UserMasterEntity userMasterEntity = new UserMasterEntity(12L, userName);
        UserMasterEntity outUserMasterEntity = new UserMasterEntity(12L, userName);
        UserMasterDto userMasterDto = new UserMasterDto(null, "jilbile");
        UserMasterDto outUserMasterDto = new UserMasterDto(12L, "jilbile");


        Mockito.when(userMasterRepository.getUserByName(userMasterDto.getName())).thenReturn(null);
        Mockito.when(userMasterMapper.toEntity(userMasterDto)).thenReturn(userMasterEntity);
        Mockito.when(userMasterMapper.toDto(Mockito.any(UserMasterEntity.class))).thenReturn(outUserMasterDto);
        Mockito.when(userMasterRepository.save(Mockito.any(UserMasterEntity.class))).thenReturn(outUserMasterEntity);

        UserMasterDto actualOutput = userMasterService.addUser(userMasterDto);
        Assertions.assertEquals(actualOutput.getId(), outUserMasterEntity.getId());
        Assertions.assertEquals(actualOutput.getName(), outUserMasterEntity.getName());

//		check that how many times it was executed(userMasterRepository.save)
        Mockito.verify(userMasterRepository,Mockito.times(1)).save(ArgumentMatchers.any(UserMasterEntity.class));

//		it will catch the argument which was passed during the execution, and further we can verify it
        Mockito.verify(userMasterRepository,Mockito.times(1)).save(userMasterEntityArgumentCaptor.capture());

        Assertions.assertEquals(userMasterEntityArgumentCaptor.getValue().getId(), outUserMasterEntity.getId());
        Assertions.assertEquals(userMasterEntityArgumentCaptor.getValue().getName(), outUserMasterEntity.getName());

    }


//	for persistence layer test we are going to use embedded database


}