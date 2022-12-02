package hu.crs.family.familytree.application

import groovy.util.logging.Log
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@Log
@ShellComponent
class FamilyTreeShellComponent {
    private FamilyTreeService familyTreeService

    FamilyTreeShellComponent(FamilyTreeService familyTreeService) {
        this.familyTreeService = familyTreeService
    }

    @ShellMethod(value = "Add member to family")
    void add(@ShellOption("--name") String name) {
        familyTreeService.addMember(name)
    }

    @ShellMethod(value = "List family members")
    void list() {
        familyTreeService.listMembers()
    }

    @ShellMethod(value = "Persist family into file")
    void persist() {
        familyTreeService.persistFamily()
    }

    @ShellMethod(value = "Load family drom file")
    void load() {
        familyTreeService.loadFamily()
    }
}
