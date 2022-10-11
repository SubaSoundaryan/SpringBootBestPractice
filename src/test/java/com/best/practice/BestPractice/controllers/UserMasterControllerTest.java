package com.best.practice.BestPractice.controllers;

import com.best.practice.BestPractice.services.UserMasterService;
import com.best.practice.BestPractice.services.impl.UserMasterServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ModelResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = UserMasterController.class )
public class UserMasterControllerTest {
//instead of @Mock annotation, it automatically  injects all the dependencies required to work
    @MockBean
    private UserMasterService userMasterService;

//    it provides us the way to call our REST endPoints from our test,
//    mainly provides us servlet environment where we can make call to rest endPoints and it will return us mock response
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserMasterServiceImpl userMasterServiceImpl;

//    @MockBean
//    private JwtProvider jwtProvider;

    @Test
    @DisplayName("Gets all users")
    public void getAllTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/get-all-user"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}