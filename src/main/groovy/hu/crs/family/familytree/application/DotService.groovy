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
            def motherId  = it.getKey().motherId

            //children names
            it.getValue().forEach{
                content += """
                    "$it.id" [label ="$it.name"]
                """
            }

            //children ids
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

        familyRepository.unknownParentIds().forEach{
            content += """
                    "$it" [label ="N/A"]
                """
        }

        "digraph G { ${content} }"
    }
}
