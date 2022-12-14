package uk.ac.cardiff.team5.graphium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.AuditEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.AuditRepository;
import uk.ac.cardiff.team5.graphium.service.AuditService;

import javax.transaction.Transactional;
import java.util.List;

@Service("auditService")
@Component
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Override
    @Transactional
    public AuditEntity addAudit(AuditEntity audit){

        // Call repo method

        auditRepository.save(audit);


        return audit;
    }

    @Override
    public List<AuditEntity> getAudits(String username) {
        return auditRepository.findAllByUsername(username);
    }

    @Override
    public List<AuditEntity> getAuditByFileId(String fileId){
        return auditRepository.findAuditEntityByFileId(fileId);
    }
}
