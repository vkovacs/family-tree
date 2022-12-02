package hu.crs.family.familytree.application.persistence

import hu.crs.family.familytree.application.domain.Member
import hu.crs.family.familytree.application.domain.Parents
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.containsInAnyOrder
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize

class FamilyRepositoryTest {
    FamilyRepository underTest = new FamilyRepository()

    @Test
    void addChildForSameParents() {
        //give
        def father = new Member(id: "fatherId")
        def mother = new Member(id: "motherId")

        def child = new Member(id: "childId", father: father, mother: mother)

        //when
        underTest.save(child)

        //then
        assertThat(underTest.getFamily(), aMapWithSize(1))
        assertThat(underTest.getFamily().get(new Parents(father, mother)), containsInAnyOrder(child))
    }

    @Test
    void addChildrenForSameParents() {
        //give
        def father = new Member(id: "fatherId")
        def mother = new Member(id: "motherId")

        def child0 = new Member(id: "childId0", father: father, mother: mother)
        def child1 = new Member(id: "childId1", father: father, mother: mother)

        //when
        underTest.save(child0)
        underTest.save(child1)

        //then
        assertThat(underTest.getFamily(), aMapWithSize(1))
        assertThat(underTest.getFamily().get(new Parents(father, mother)), containsInAnyOrder(child0, child1))
    }
}
