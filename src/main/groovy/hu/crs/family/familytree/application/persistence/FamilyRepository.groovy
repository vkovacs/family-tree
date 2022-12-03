package hu.crs.family.familytree.application.persistence

import hu.crs.family.familytree.application.domain.Member
import hu.crs.family.familytree.application.domain.Parents
import org.springframework.stereotype.Repository

import java.util.stream.Stream

@Repository
class FamilyRepository {
    Map<Parents, List<Member>> family = new HashMap<>()

    void save(Member member) {
        Member father = member.getFather() ?: new Member(name: "Unknown Father")
        Member mother = member.getMother() ?: new Member(name: "Unknown Mother")
        def key = new Parents(father.getId(), mother.getId())

        family.merge(key, List.of(member), (members, value) -> (Stream.concat(members.stream(), Stream.of(member)).toList()))
    }

    List<Member> listMembers() {
        family.values().stream().flatMap(List::stream).toList() as List<Member>
    }
}