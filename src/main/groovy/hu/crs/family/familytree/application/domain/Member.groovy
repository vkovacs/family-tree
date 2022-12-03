package hu.crs.family.familytree.application.domain

import groovy.transform.Immutable

@Immutable
class Member {
    String id = UUID.randomUUID().toString()
    String name
    Member mother
    Member father
    int yearOfBirth
    int yearOfDeath
}
