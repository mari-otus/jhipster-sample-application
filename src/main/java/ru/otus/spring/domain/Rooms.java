package ru.otus.spring.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Rooms.
 */
@Entity
@Table(name = "rooms")
public class Rooms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "room_name", nullable = false)
    private String roomName;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "has_conditioner")
    private Boolean hasConditioner;

    @Column(name = "has_videoconference")
    private Boolean hasVideoconference;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @Column(name = "delete_date")
    private ZonedDateTime deleteDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public Rooms roomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Rooms capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean isHasConditioner() {
        return hasConditioner;
    }

    public Rooms hasConditioner(Boolean hasConditioner) {
        this.hasConditioner = hasConditioner;
        return this;
    }

    public void setHasConditioner(Boolean hasConditioner) {
        this.hasConditioner = hasConditioner;
    }

    public Boolean isHasVideoconference() {
        return hasVideoconference;
    }

    public Rooms hasVideoconference(Boolean hasVideoconference) {
        this.hasVideoconference = hasVideoconference;
        return this;
    }

    public void setHasVideoconference(Boolean hasVideoconference) {
        this.hasVideoconference = hasVideoconference;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public Rooms createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public Rooms updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public ZonedDateTime getDeleteDate() {
        return deleteDate;
    }

    public Rooms deleteDate(ZonedDateTime deleteDate) {
        this.deleteDate = deleteDate;
        return this;
    }

    public void setDeleteDate(ZonedDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rooms)) {
            return false;
        }
        return id != null && id.equals(((Rooms) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rooms{" +
            "id=" + getId() +
            ", roomName='" + getRoomName() + "'" +
            ", capacity=" + getCapacity() +
            ", hasConditioner='" + isHasConditioner() + "'" +
            ", hasVideoconference='" + isHasVideoconference() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", deleteDate='" + getDeleteDate() + "'" +
            "}";
    }
}
