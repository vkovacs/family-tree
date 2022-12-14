package hu.crs.family.familytree.application.domain

import com.fasterxml.jackson.annotation.JsonValue
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Parents {
    String fatherId
    String motherId

    @Override
    @JsonValue
    String toString() {
        fatherId + ";" + motherId
    }

    //https://www.baeldung.com/jackson-map
    Parents(String toDeserialize) {
        (fatherId, motherId) = toDeserialize.split(";")
    }

    Parents(String fatherId, String motherId) {
        this.fatherId = fatherId
        this.motherId = motherId
    }
}
