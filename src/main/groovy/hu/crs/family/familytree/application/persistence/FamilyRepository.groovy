package hu.crs.family.familytree.application.persistence

import hu.crs.family.familytree.application.domain.Member
import hu.crs.family.familytree.application.domain.Parents
import org.springframework.stereotype.Repository

import java.util.stream.Stream

@Repository
class FamilyRepository {
    Map<Parents, List<Member>> family = new HashMap<>()

    void save(Member member) {
        def fatherId = member.getFatherId() ?: Member.randomId()
        def motherId = member.getMotherId() ?: Member.randomId()
        def key = new Parents(fatherId, motherId)

        family.merge(key, List.of(member), (members, value) -> (Stream.concat(members.stream(), Stream.of(member)).toList()))
    }

    List<Member> listMembers() {
        family.values().stream().flatMap(List::stream).toList() as List<Member>
    }
}