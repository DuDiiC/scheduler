package com.md.scheduler.commons;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable, Persistable<ID> {

    @Setter(value = AccessLevel.PRIVATE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected ID id;

    protected Instant createdOn;

    protected Instant updatedOn;

    @Override
    public boolean isNew() {
        return id == null;
    }

    @PrePersist
    void prePersist() {
        this.createdOn = Instant.now();
        this.updatedOn = Instant.now();
    }

    @PreUpdate
    void preMerge() {
        this.updatedOn = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.unproxy(this).getClass() != Hibernate.unproxy(o).getClass())
            return false;
        BaseEntity<ID> that = (BaseEntity<ID>) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

