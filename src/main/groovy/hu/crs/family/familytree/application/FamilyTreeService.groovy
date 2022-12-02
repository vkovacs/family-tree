package hu.crs.family.familytree.application

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import hu.crs.family.familytree.application.domain.Member
import hu.crs.family.familytree.application.domain.Parents
import hu.crs.family.familytree.application.persistence.FamilyRepository
import org.springframework.stereotype.Service

@Slf4j
@Service
class FamilyTreeService {
    private final ObjectMapper objectMapper
    private final FamilyRepository familyRepository

    FamilyTreeService(ObjectMapper objectMapper, FamilyRepository familyRepository) {
        this.objectMapper = objectMapper
        this.familyRepository = familyRepository
    }

    void addMember(String name) {
        familyRepository.save(new Member(name: name))
    }

    void listMembers() {
        def members = familyRepository.listMembers()
        println(members)
    }

    void persistFamily() {
        def family = familyRepository.getFamily()
        def myFile = new File("family.json")
        myFile.write(objectMapper.writeValueAsString(family))
    }

    void loadFamily() {
        def myFile = new File("family.json")
        def json = myFile.readLines()[0]

        TypeReference<HashMap<Parents, List<Member>>> familyMapTypeReference = new TypeReference<HashMap<Parents, List<Member>>>(){}

        def family = objectMapper.readValue(json, familyMapTypeReference)

        familyRepository.setFamily(family)
    }
}
