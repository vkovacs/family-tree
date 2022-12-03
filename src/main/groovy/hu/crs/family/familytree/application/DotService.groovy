package hu.crs.family.familytree.application


import hu.crs.family.familytree.application.persistence.FamilyRepository
import org.springframework.stereotype.Service

@Service
class DotService {
    private final FamilyRepository familyRepository;

    DotService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository
    }

    String dot() {
        def family = familyRepository.getFamily()

        def content = ""
        family.each {
            def fatherId = it.getKey().fatherId
            def father = familyRepository.findById(fatherId)
            def fatherName = father != null ? father.getName() : "Unknown Father"
            def motherId  = it.getKey().motherId
            def mother = familyRepository.findById(motherId)
            def motherName = mother != null ? mother.getName() : "Unknown Mother"

//            //Unknown parent names
//            content += """
//            $fatherId [label ="$fatherName"]
//            $motherId [label ="$motherName"]
//            """

            //children names
            it.getValue().forEach{
                content += """
                    "$it.id" [label ="$it.name"]
                """

            }

            def children = it.getValue().collect {
                """
                    "${it.getId()}"
                """
            }.join(" ")

            content += """
                "$fatherId" -> {$children}
                "$motherId" -> {$children}
                "$fatherId" -> "$motherId"
                "$motherId" -> "$fatherId"
                """
        }
        "digraph G { ${content} }"
    }
}
