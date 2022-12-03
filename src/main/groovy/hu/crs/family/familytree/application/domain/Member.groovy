package hu.crs.family.familytree.application.domain

import groovy.transform.Immutable

@Immutable
class Member {
    String id = UUID.randomUUID().toString().substring(0,8)
    String name
    Member mother
    Member father
    int yearOfBirth
    int yearOfDeath
}
