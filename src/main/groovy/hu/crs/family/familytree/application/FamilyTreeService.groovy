package hu.crs.family.familytree.application

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import hu.crs.family.familytree.application.domain.Member
import hu.crs.family.familytree.application.domain.Parents
import hu.crs.family.familytree.application.persistence.FamilyRepository
import org.springframework.stereotype.Service

import java.time.Instant

@Slf4j
@Service
class FamilyTreeService {
    private final ObjectMapper objectMapper
    private final FamilyRepository familyRepository

    FamilyTreeService(ObjectMapper objectMapper, FamilyRepository familyRepository) {
        this.objectMapper = objectMapper
        this.familyRepository = familyRepository
    }

    void addMember(String name, String motherId, String fatherId, Integer yearOfBirth, Integer yearOfDeath, String note) {
        familyRepository.save(new Member(name: name, motherId: motherId, fatherId: fatherId, yearOfBirth: yearOfBirth, yearOfDeath: yearOfDeath, note: note))
    }

    void listMembers() {
        def members = familyRepository.listMembers()
        println(members)
    }

    void persistFamily() {
        def family = familyRepository.getFamily()
        def content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(family)
        def userHome = System.getProperty("user.home");
        saveFile("${userHome}/.family/family.json", content)

        //backup
        def timestamp = Instant.now().epochSecond
        saveFile("${userHome}/.family/backup/family-backup-${timestamp}.json", content)
    }

    void loadFamily() {
        def userHome = System.getProperty("user.home");
        def myFile = new File("${userHome}/.family/family.json")

        def json = myFile.readLines().join()

        TypeReference<HashMap<Parents, List<Member>>> familyMapTypeReference = new TypeReference<HashMap<Parents, List<Member>>>(){}

        def family = objectMapper.readValue(json, familyMapTypeReference)

        familyRepository.setFamily(family)
    }

    private void saveFile(String filename, String content) {
        def myFile = new File(filename)
        if (!myFile.exists()) myFile.getParentFile().mkdirs()

        myFile.write(content)
    }
}
