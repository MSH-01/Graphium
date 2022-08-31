package uk.ac.cardiff.team5.graphium.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class OrganisationAPIControllerTest {
    @Autowired
    private MockMvc mvc;

//    @Test
//    @WithUserDetails("adavies")
//    public void getAllOrganisations() throws Exception {
//        mvc.perform(get("/api/organisation"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

}
