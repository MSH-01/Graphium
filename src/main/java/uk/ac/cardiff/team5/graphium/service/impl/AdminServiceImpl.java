package uk.ac.cardiff.team5.graphium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.PartnershipEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.OrganisationRepository;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.PartnershipRepository;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;
import uk.ac.cardiff.team5.graphium.service.AdminService;
import uk.ac.cardiff.team5.graphium.service.dto.OrganisationDTO;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private PartnershipRepository partnershipRepository;

    @Override
    public List<UserDTO> verify(Long organisationId) {
        OrganisationEntity organisation = organisationRepository.findById(organisationId).get();
        return userRepository.findUserEntitiesByOrganisation(organisation)
                .stream()
                .filter(c -> c.getOrganisation_approved() == false)
                .map(c -> new UserDTO(c))
                .collect(Collectors.toList());
    }

    @Override
    public void verifyUser(String userName) {
        userRepository.verifyUser(userName);
    }

    @Override
    public List<OrganisationDTO> findPossiblePartners(String name) {
        Long organisationId = userRepository.findByUsername(name).getOrganisation().getOrganisationId();
        return organisationRepository.findPossiblePartners(organisationId)
                .stream()
                .map(c -> new OrganisationDTO(c))
                .collect(Collectors.toList());
    }

    @Override
    public void recordPartnership(String orgId, String name) {
        OrganisationEntity sharingOrganisation = userRepository.findByUsername(name).getOrganisation();
        OrganisationEntity readingOrganisation = organisationRepository.findByOrganisationId(Long.valueOf(orgId));
        PartnershipEntity partnership = new PartnershipEntity(sharingOrganisation, readingOrganisation);
        partnershipRepository.save(partnership);
    }

    @Override
    public List<UserDTO> getOrganisationMembers(String username) {
        OrganisationEntity organisation = organisationRepository.findById(userRepository.findByUsername(username).getOrganisation().getOrganisationId()).get();
        return userRepository.findUserEntitiesByOrganisation(organisation)
                .stream()
                .filter(c -> c.getOrganisation_approved() == true)
                .map(c -> new UserDTO(c))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrganisationDTO> findSharingPartners(String name) {
        Long organisationId = userRepository.findByUsername(name).getOrganisation().getOrganisationId();
        return organisationRepository.findPartnersThatYouShareWith(organisationId)
                .stream()
                .map(c -> new OrganisationDTO(c))
                .collect(Collectors.toList());
    }
    @Override
    public List<OrganisationDTO> findPartnersThatYouCanView(String name) {
        Long organisationId = userRepository.findByUsername(name).getOrganisation().getOrganisationId();
        return organisationRepository.findPartnersThatYouCanView(organisationId)
                .stream()
                .map(c -> new OrganisationDTO(c))
                .collect(Collectors.toList());
    }
    @Override
    public boolean partnershipAlreadyExists(String orgId, String name){
        return partnershipRepository.alreadyExists(orgId,name);
    }
    @Override
    public boolean canVerifyUser(String userName, String adminName){
        return userRepository.canVerifyUser(userName,adminName);
    }
}
