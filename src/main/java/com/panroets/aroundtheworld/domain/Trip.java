package com.panroets.aroundtheworld.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.Objects;

import com.panroets.aroundtheworld.domain.enumeration.TripStatus;

/**
 * A Trip.
 */
@Document(collection = "trip")
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("trip_name")
    private String tripName;

    @Field("status")
    private TripStatus status;

    @Field("trip_path_id")
    private String tripPathId;

    @Field("country_paths")
    private String countryPaths;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public Trip tripName(String tripName) {
        this.tripName = tripName;
        return this;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public TripStatus getStatus() {
        return status;
    }

    public Trip status(TripStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public String getTripPathId() {
        return tripPathId;
    }

    public Trip tripPathId(String tripPathId) {
        this.tripPathId = tripPathId;
        return this;
    }

    public void setTripPathId(String tripPathId) {
        this.tripPathId = tripPathId;
    }

    public String getCountryPaths() {
        return countryPaths;
    }

    public Trip countryPaths(String countryPaths) {
        this.countryPaths = countryPaths;
        return this;
    }

    public void setCountryPaths(String countryPaths) {
        this.countryPaths = countryPaths;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trip trip = (Trip) o;
        if (trip.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trip.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Trip{" +
            "id=" + getId() +
            ", tripName='" + getTripName() + "'" +
            ", status='" + getStatus() + "'" +
            ", tripPathId='" + getTripPathId() + "'" +
            ", countryPaths='" + getCountryPaths() + "'" +
            "}";
    }
}
