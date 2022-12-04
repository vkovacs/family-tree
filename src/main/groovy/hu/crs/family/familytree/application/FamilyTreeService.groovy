package hu.crs.family.familytree.application

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import guru.nidi.graphviz.attribute.Color
import guru.nidi.graphviz.attribute.Style
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.model.MutableGraph
import guru.nidi.graphviz.parse.Parser
import hu.crs.family.familytree.application.domain.Member
import hu.crs.family.familytree.application.domain.Parents
import hu.crs.family.familytree.application.persistence.FamilyRepository
import hu.crs.family.familytree.application.persistence.FileRepository
import org.springframework.stereotype.Service

import java.time.Instant

@Slf4j
@Service
class FamilyTreeService {
    private final DotService dotService
    private final ObjectMapper objectMapper
    private final FileRepository fileRepository
    private final FamilyRepository familyRepository

    FamilyTreeService(DotService dotService, ObjectMapper objectMapper, FileRepository fileRepository, FamilyRepository familyRepository) {
        this.dotService = dotService
        this.objectMapper = objectMapper
        this.fileRepository = fileRepository
        this.familyRepository = familyRepository
    }

    Member addMember(String name, String motherId, String fatherId, Integer yearOfBirth, Integer yearOfDeath, String note) {
        def member = new Member(name: name, motherId: motherId, fatherId: fatherId, yearOfBirth: yearOfBirth, yearOfDeath: yearOfDeath, note: note)
        familyRepository.save(member)

        member
    }

    List<Member> listMembers() {
        familyRepository.listMembers()
    }

    void persistFamily() {
        def family = familyRepository.getFamily()
        def content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(family)
        def userHome =
        fileRepository.saveFile("family-tree.json", content)

        //backup
        def timestamp = Instant.now().epochSecond
        fileRepository.saveBackupFile("family-tree-backup-${timestamp}.json", content)
    }

    void loadFamily() {
        def json = fileRepository.load("family-tree.json")

        TypeReference<HashMap<Parents, List<Member>>> familyMapTypeReference = new TypeReference<HashMap<Parents, List<Member>>>() {
        }

        def family = objectMapper.readValue(json, familyMapTypeReference)

        familyRepository.setFamily(family)
    }

    String dot() {
        dotService.dot()
    }

    void image() {
        def dot = dot()

        MutableGraph g = new Parser().read(dot);

        g.graphAttrs()
                .add(Color.WHITE.gradient(Color.rgb("888888")).background().angle(90))
                .nodeAttrs().add(Color.WHITE.fill())
                .nodes().forEach(node ->
                node.add(
                        Color.named(node.name().toString()),
                        Style.lineWidth(4), Style.FILLED))

        Graphviz.fromGraph(g).width(7000).render(Format.PNG).toFile(new File(fileRepository.filePath("family-tree.png")))

    }
}
