package com.panroets.aroundtheworld.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.panroets.aroundtheworld.domain.enumeration.TripStatus;

/**
 * A DTO for the Trip entity.
 */
public class TripDTO implements Serializable {

    private String id;

    private String tripName;

    private TripStatus status;

    private String tripPathId;

    private String countryPaths;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public String getTripPathId() {
        return tripPathId;
    }

    public void setTripPathId(String tripPathId) {
        this.tripPathId = tripPathId;
    }

    public String getCountryPaths() {
        return countryPaths;
    }

    public void setCountryPaths(String countryPaths) {
        this.countryPaths = countryPaths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TripDTO tripDTO = (TripDTO) o;
        if(tripDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tripDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TripDTO{" +
            "id=" + getId() +
            ", tripName='" + getTripName() + "'" +
            ", status='" + getStatus() + "'" +
            ", tripPathId='" + getTripPathId() + "'" +
            ", countryPaths='" + getCountryPaths() + "'" +
            "}";
    }
}
