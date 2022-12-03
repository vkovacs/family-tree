package hu.crs.family.familytree.application.persistence

import hu.crs.family.familytree.application.domain.Member
import hu.crs.family.familytree.application.domain.Parents
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.contains
import static org.hamcrest.Matchers.containsInAnyOrder
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize

class FamilyRepositoryTest {
    FamilyRepository underTest = new FamilyRepository()

    @Test
    void addChildForSameParents() {
        //give
        def father = new Member(id: "fatherId")
        def mother = new Member(id: "motherId")

        def child = new Member(id: "childId", fatherId: "fatherId", motherId: "motherId")

        //when
        underTest.save(child)

        //then
        assertThat(underTest.getFamily(), aMapWithSize(1))
        assertThat(underTest.getFamily().get(new Parents(father.getId(), mother.getId())), containsInAnyOrder(child))
    }

    @Test
    void addChildrenForSameParents() {
        //give
        def father = new Member(id: "fatherId")
        def mother = new Member(id: "motherId")

        def child0 = new Member(id: "childId0", fatherId: "fatherId", motherId: "motherId")
        def child1 = new Member(id: "childId1", fatherId: "fatherId", motherId: "motherId")

        //when
        underTest.save(child0)
        underTest.save(child1)

        //then
        assertThat(underTest.getFamily(), aMapWithSize(1))
        assertThat(underTest.getFamily().get(new Parents(father.getId(), mother.getId())), containsInAnyOrder(child0, child1))
    }

    @Test
    void listAllMembers() {
        //give
        def father0 = new Member(id: "fatherId0")
        def mother0 = new Member(id: "motherId0")

        def child000 = new Member(id: "childId000", fatherId: "fatherId0", motherId: "motherId0")
        def child001 = new Member(id: "childId001", fatherId: "fatherId0", motherId: "motherId0")

        def father1 = new Member(id: "fatherId1")
        def mother1 = new Member(id: "motherId1")
        def child110 = new Member(id: "childId110", fatherId: "fatherId1", motherId: "motherId1")

        //when
        underTest.save(child000)
        underTest.save(child001)
        underTest.save(child110)

        //then
        assertThat(underTest.getFamily(), aMapWithSize(2))
        assertThat(underTest.getFamily().get(new Parents(father0.getId(), mother0.getId())), containsInAnyOrder(child000, child001))
        assertThat(underTest.getFamily().get(new Parents(father1.getId(), mother1.getId())), contains(child110))
    }
}
