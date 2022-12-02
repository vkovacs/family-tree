package hu.crs.family.familytree.api;

import groovy.util.logging.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import static org.springframework.shell.command.invocation.InvocableShellMethod.log;

@Slf4j
@ShellComponent
public class FamilyTreeShellComponent {

    @ShellMethod(value = "Add member to family")
    public void addMember(@ShellOption("--name") String name) {
        log.info("Member to add: " + name);
    }
}
