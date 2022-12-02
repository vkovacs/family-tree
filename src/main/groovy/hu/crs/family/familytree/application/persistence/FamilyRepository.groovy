package hu.crs.family.familytree.application.persistence

import hu.crs.family.familytree.application.domain.Member
import hu.crs.family.familytree.application.domain.Parents
import org.springframework.stereotype.Repository

@Repository
class FamilyRepository {
    Map<Parents, List<Member>> family = new HashMap<>()

    void save(Member member) {
        Member father = member.getFather() != null ?: new Member(name: "Unknown Father")
        Member mother = member.getMother() != null ?: new Member(name: "Unknown Mother")
        def key = new Parents(father: father, mother: mother)
        if (family.get(key) == null) {
            family.put(key, Arrays.asList(member))
        }
        else {
            def members = family.get(key)
            members << member
        }

    }

    List<Member> listMembers() {
        family.values().stream().flatMap(List::stream).toList() as List<Member>
    }
}