package hu.crs.family.familytree.application.domain

import groovy.transform.Immutable

@Immutable
class Member {
    String id = randomId()
    String name
    String motherId
    String fatherId
    int yearOfBirth
    int yearOfDeath

    static randomId() {
        UUID.randomUUID().toString().substring(0,8)
    }
}
