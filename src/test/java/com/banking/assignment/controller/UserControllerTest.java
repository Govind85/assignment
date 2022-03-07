package com.banking.assignment.controller;

import com.banking.assignment.payload.request.LoginRequest;
import com.banking.assignment.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static com.banking.assignment.util.JsonHelper.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private UserController controller;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;
    private ObjectMapper objectMapper;

    public UserControllerTest() {

    }
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testLoginSuccess() throws Exception {


        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("admin");

        this.mockMvc
                .perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    public void testLoginFail() throws Exception {


        LoginRequest request = new LoginRequest();
        request.setUsername("adm");
        request.setPassword("adm");

        this.mockMvc
                .perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isBadRequest())
                .andExpect(header().doesNotExist(HttpHeaders.AUTHORIZATION))
                .andReturn();
    }

}
