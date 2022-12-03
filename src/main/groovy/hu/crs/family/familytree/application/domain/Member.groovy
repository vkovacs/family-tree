package hu.crs.family.familytree.application.domain

import groovy.transform.Immutable

@Immutable
class Member {
    String id = randomId()
    String name
    String motherId
    String fatherId
    Integer yearOfBirth
    Integer yearOfDeath
    String note

    static randomId() {
        UUID.randomUUID().toString().substring(0,8)
    }


    @Override
    String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", motherId='" + motherId + '\'' +
                ", fatherId='" + fatherId + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", yearOfDeath=" + yearOfDeath +
                ", note='" + note + '\'' +
                '}';
    }
}
