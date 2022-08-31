package uk.ac.cardiff.team5.graphium.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.AuditEntity;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;
import uk.ac.cardiff.team5.graphium.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
public class AuditingServiceTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuditService auditService;


    @Test
    public void CheckAuditExists() throws Exception {
        String randomString = Utils.generateRandomString(10);
        UserDTO userDTO = new UserDTO(
                "First Name",
                "Last Name",
                1L,
                randomString,
                randomString + "@email.com",
                "$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C"
        );

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        AuditEntity auditEntity = new AuditEntity(dtf.format(now), userDTO.getUsername(), "1", userDTO.getOrganisationId(),"DOWNLOADED","NULL");
        auditService.addAudit(auditEntity);

        List<AuditEntity> audits =  auditService.getAudits(userDTO.getUsername());

        assertFalse(audits.isEmpty());
    }


     @Test
     @WithUserDetails("adavies")
        public void WebCheckAudit() throws Exception {

         mvc.perform(get("/file/download/9")).andExpect(status().isOk());

         List<AuditEntity> audits = auditService.getAuditByFileId("9");
         List<AuditEntity> adaviesAudits = new ArrayList<AuditEntity>();
         for(int i=0; i<audits.size();i++){
             AuditEntity current = audits.get(i);
             if(Objects.equals(current.getUsername(), "adavies")){
                 adaviesAudits.add(current);
             }
         }
         assertFalse(adaviesAudits.isEmpty());
        }

    @Test
    @WithUserDetails("adavies")
    public void WebCheckAuditType() throws Exception {

        mvc.perform(get("/file/download/9")).andExpect(status().isOk());

        List<AuditEntity> audits = auditService.getAuditByFileId("9");
        List<AuditEntity> adaviesAudits = new ArrayList<AuditEntity>();
        for(int i=0; i<audits.size();i++){
            AuditEntity current = audits.get(i);
            if(Objects.equals(current.getUsername(), "adavies") & (Objects.equals(current.getAction(), "DOWNLOADED"))){
                adaviesAudits.add(current);
            }
        }
        assertFalse(adaviesAudits.isEmpty());
    }

}
