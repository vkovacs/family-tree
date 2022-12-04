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
import org.springframework.stereotype.Service

import java.time.Instant

@Slf4j
@Service
class FamilyTreeService {
    private final DotService dotService
    private final ObjectMapper objectMapper
    private final FamilyRepository familyRepository

    FamilyTreeService(DotService dotService, ObjectMapper objectMapper, FamilyRepository familyRepository) {
        this.dotService = dotService
        this.objectMapper = objectMapper
        this.familyRepository = familyRepository
    }

    Member addMember(String name, String motherId, String fatherId, Integer yearOfBirth, Integer yearOfDeath, String note) {
        def member = new Member(name: name, motherId: motherId, fatherId: fatherId, yearOfBirth: yearOfBirth, yearOfDeath: yearOfDeath, note: note)
        familyRepository.save(member)

        member
    }

    void listMembers() {
        def members = familyRepository.listMembers()
        println(members)
    }

    void persistFamily() {
        def family = familyRepository.getFamily()
        def content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(family)
        def userHome = System.getProperty("user.home");
        saveFile("${userHome}/.family/family.json", content)

        //backup
        def timestamp = Instant.now().epochSecond
        saveFile("${userHome}/.family/backup/family-backup-${timestamp}.json", content)
    }

    void loadFamily() {
        def userHome = System.getProperty("user.home");
        def myFile = new File("${userHome}/.family/family.json")

        def json = myFile.readLines().join()

        TypeReference<HashMap<Parents, List<Member>>> familyMapTypeReference = new TypeReference<HashMap<Parents, List<Member>>>() {
        }

        def family = objectMapper.readValue(json, familyMapTypeReference)

        familyRepository.setFamily(family)
    }

    String dot() {
        dotService.dot()
    }

    private void saveFile(String filename, String content) {
        def myFile = new File(filename)
        if (!myFile.exists()) myFile.getParentFile().mkdirs()

        myFile.write(content)
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
                        Style.lineWidth(4), Style.FILLED));

        def userHome = System.getProperty("user.home");
        def myPath = "${userHome}/.family/family.png"

        Graphviz.fromGraph(g).width(7000).render(Format.PNG).toFile(new File(myPath));

    }
}
