package hu.crs.family.familytree.application.domain

import groovy.transform.Immutable

import java.time.LocalDate

@Immutable
class Member {
    String id = UUID.randomUUID().toString()
    String name
    Member mother
    Member father
    LocalDate dateOfBirth
    LocalDate dateOfDeath
}
