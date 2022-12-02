package hu.crs.family.familytree.application


import groovy.util.logging.Slf4j
import hu.crs.family.familytree.application.domain.Member
import hu.crs.family.familytree.application.persistence.FamilyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Slf4j
@Service
class FamilyTreeService {
    @Autowired
    private FamilyRepository familyRepository

    void addMember(String name) {
        familyRepository.save(new Member(name: name))
    }

    List<Member> listMembers() {
        def members = familyRepository.listMembers()
        println(members)
        members
    }
}
