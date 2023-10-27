package com.example.MySpringTests.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {
    private int id;
    private boolean isActive;
    private String name;
    private String description;
    private String deletedAt;

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company company)) return false;
        return getId() == company.getId() && isActive() == company.isActive() && Objects.equals(getName(), company.getName()) && Objects.equals(getDescription(), company.getDescription()) && Objects.equals(getDeletedAt(), company.getDeletedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isActive(), getName(), getDescription(), getDeletedAt());
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}
