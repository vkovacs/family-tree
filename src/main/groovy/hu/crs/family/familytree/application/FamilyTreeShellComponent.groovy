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
             @ShellOption(value = "-m", defaultValue = "", help = "Specify the id of the mother") String motherId,
             @ShellOption(value = "-f", defaultValue = "", help = "Specify the id of the father") String fatherId,
             @ShellOption(value = "-b", defaultValue = "", help = "Specify the year of birth") String yearOfBirth,
             @ShellOption(value = "-d", defaultValue = "", help = "Specify the year of death") String yearOfDeath,
             @ShellOption(value = "-n", defaultValue = "", help = "Specify notes") String note) {

        def yearOfBirthInt = yearOfBirth == "" ? null : yearOfBirth as Integer
        def yearOfDeathInt = yearOfDeath == "" ? null : yearOfDeath as Integer

        def member = familyTreeService.addMember(name, motherId, fatherId, yearOfBirthInt, yearOfDeathInt, note)
        println("${member.name} - ${member.id}")
    }

    @ShellMethod(value = "List family members")
    void list() {
        def members = familyTreeService.listMembers()
        println(members)
    }

    @ShellMethod(value = "Persist family into file in user home")
    void save() {
        familyTreeService.persistFamily()
    }

    @ShellMethod(value = "Load family from file")
    void load() {
        familyTreeService.loadFamily()
    }

    @ShellMethod(value = "Dot representation of the family")
    void dot() {
        def dot = familyTreeService.dot()
        println(dot)
    }

    @ShellMethod(value = "Save image of the family tree")
    void image() {
        familyTreeService.image()
    }
}
