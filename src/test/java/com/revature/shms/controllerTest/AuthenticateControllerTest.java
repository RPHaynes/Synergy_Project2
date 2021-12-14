package com.revature.shms.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.shms.auth.AuthenticationRequest;
import com.revature.shms.controllers.AuthenticationController;
import com.revature.shms.controllers.UserController;
import com.revature.shms.enums.Roles;
import com.revature.shms.models.User;
import com.revature.shms.repositories.UserRepository;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")

class AuthenticateControllerTest {
    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void GetJWTtokenAfterPostingUserTest() throws Exception {
        User user = new User();
        user.setUsername("mike");
        user.setPassword("123");

        int id = userRepository.save(user).getUserID();
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setRole(Roles.USER);
        authenticationRequest.setUsername("mike");
        authenticationRequest.setPassword("123");
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(authenticationRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").hasJsonPath())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content.substring(8, content.length()-2));
    }
}
