package hu.crs.family.familytree.application.domain

import com.fasterxml.jackson.annotation.JsonValue

class Parents {
    String fatherId
    String motherId

    @Override
    @JsonValue
    String toString() {
        fatherId + ";" + motherId
    }

    Parents(String toDeserialize) {
        (fatherId, motherId) = toDeserialize.split(";")
    }
}
