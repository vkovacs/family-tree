package hu.crs.family.familytree.application


import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@Log
@ShellComponent
class FamilyTreeShellComponent {
    @Autowired
    private FamilyTreeService familyTreeService

    @ShellMethod(value = "Add member to family")
    void addMember(@ShellOption("--name") String name) {
        familyTreeService.addMember(name)
    }

    @ShellMethod(value = "List family members")
    void listMembers() {
        familyTreeService.listMembers()
    }
}
