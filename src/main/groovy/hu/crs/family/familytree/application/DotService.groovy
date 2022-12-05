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
        family.each { parents, children ->
            def fatherId = parents.fatherId
            def motherId = parents.motherId

            //children names
            children.forEach {
                content += """
                    "$it.id" [label ="$it.name"]
                """
            }

            def childrenIds = children.collect {
                """
                    "${it.getId()}"
                """
            }.join(" ")

            def unknownParentIds = familyRepository.unknownParentIds()

            if (!unknownParentIds.contains(motherId) && !unknownParentIds.contains(fatherId)) {
                content += """
                    "$motherId" -- {$childrenIds}
                    "$fatherId" -- {$childrenIds}
                    "$fatherId" -- "$motherId" [style=dashed, color = crimson]
                    """
            }
        }

        "graph G { ${content} }"
    }
}
