package org.launchcode.nextchapter.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private int id;

    private String displayName;

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity abstractEntity = (AbstractEntity) o;
        return id == abstractEntity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
