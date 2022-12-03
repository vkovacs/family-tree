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
    void add(String name,
             @ShellOption(value = "-m", defaultValue = "") String motherId,
             @ShellOption(value = "-f", defaultValue = "") String fatherId,
             @ShellOption(value = "-b", defaultValue = "") String yearOfBirth,
             @ShellOption(value = "-d", defaultValue = "") String yearOfDeath,
             @ShellOption(value = "-n", defaultValue = "") String note) {

        def yearOfBirthInt = yearOfBirth == "" ? null : yearOfBirth as Integer
        def yearOfDeathInt = yearOfDeath == "" ? null : yearOfDeath as Integer

        familyTreeService.addMember(name, motherId, fatherId, yearOfBirthInt, yearOfDeathInt, note)
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

    @ShellMethod(value = "Dot representation of the family")
    void dot() {
        familyTreeService.dot()
    }
}
