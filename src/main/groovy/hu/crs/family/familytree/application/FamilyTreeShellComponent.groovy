package hu.crs.family.familytree.application

import groovy.util.logging.Log
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@Log
@ShellComponent
class FamilyTreeShellComponent {
    private FamilyTreeService familyTreeService

    FamilyTreeShellComponent(FamilyTreeService familyTreeService) {
        this.familyTreeService = familyTreeService
    }

    @ShellMethod(value = "Add member to family")
    void add(String memberSpecifier) {
        def (name, mother, father, yearOfBirth, yearOfDeath, note) = memberSpecifier.split(";")

        yearOfBirth = yearOfBirth == "" ? null : yearOfBirth as Integer
        yearOfDeath = yearOfDeath == "" ? null : yearOfDeath as Integer

        familyTreeService.addMember(name, mother, father, yearOfBirth, yearOfDeath, note)
    }

    @ShellMethod(value = "List family members")
    void list() {
        familyTreeService.listMembers()
    }

    @ShellMethod(value = "Persist family into file")
    void save() {
        familyTreeService.persistFamily()
    }

    @ShellMethod(value = "Load family drom file")
    void load() {
        familyTreeService.loadFamily()
    }
}
