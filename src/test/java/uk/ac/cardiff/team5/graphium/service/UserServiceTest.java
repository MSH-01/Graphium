package uk.ac.cardiff.team5.graphium.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;
import uk.ac.cardiff.team5.graphium.utils.Utils;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void UserRegistrationTest() throws Exception {
        String randomString = Utils.generateRandomString(10);
        UserDTO userDTO = new UserDTO(
            "First Name",
            "Last Name",
            1L,
            randomString,
            randomString + "@email.com",
            "$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C"
        );

        userService.register(userDTO);

        assertEquals(randomString, userRepository.findByUsername(randomString).getUsername());

    }

    @Test
    public void UserRegistrationThroughWebTest() throws Exception {
        String randomString = Utils.generateRandomString(10);

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
                .param("firstName", "First Name")
                .param("lastName", "Last Name")
                .param("username", randomString)
                .param("email", randomString + "@email.com")
                .param("password", "password")
                .param("passwordConf", "password")
                .param("organisationId", "1"))
                .andDo(print());

        assertEquals(randomString, userRepository.findByUsername(randomString).getUsername());
    }

    @Test
    public void UsernameExistsTest() throws Exception {
        String randomString = Utils.generateRandomString(10);
        UserDTO userDTO = new UserDTO(
                "First Name",
                "Last Name",
                1L,
                randomString,
                randomString + "@email.com",
                "$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C"
        );
        userService.register(userDTO);

        assertTrue(userService.checkUsernameInUse(randomString));
    }

//    @Test
//    public void EmailExistsTest() throws Exception {
//        String randomString = Utils.generateRandomString(10);
//        UserDTO userDTO = new UserDTO(
//                "First Name",
//                "Last Name",
//                1L,
//                randomString,
//                randomString + "@email.com",
//                "$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C"
//        );
//        userService.register(userDTO);
//
//        assertTrue(userService.checkEmailInUse(randomString + "@email.com"));
//    }

    @Test
    public void CheckPasswordsMatchTest() throws Exception {
        assertTrue(userService.checkPasswordsMatch("a", "a"));
    }
}
