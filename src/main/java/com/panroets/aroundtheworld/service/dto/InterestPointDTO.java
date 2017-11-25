package com.panroets.aroundtheworld.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InterestPoint entity.
 */
public class InterestPointDTO implements Serializable {

    private String id;

    private String name;

    private String coordinates;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InterestPointDTO interestPointDTO = (InterestPointDTO) o;
        if(interestPointDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), interestPointDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InterestPointDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", coordinates='" + getCoordinates() + "'" +
            "}";
    }
}
