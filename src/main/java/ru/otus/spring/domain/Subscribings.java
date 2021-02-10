package ru.otus.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Subscribings.
 */
@Entity
@Table(name = "subscribings")
public class Subscribings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "room_id", nullable = false)
    private Integer roomId;

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @Column(name = "delete_date")
    private ZonedDateTime deleteDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "subscribings", allowSetters = true)
    private Rooms roomId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Subscribings roomId(Integer roomId) {
        this.roomId = roomId;
        return this;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getLogin() {
        return login;
    }

    public Subscribings login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public Subscribings createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public Subscribings updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public ZonedDateTime getDeleteDate() {
        return deleteDate;
    }

    public Subscribings deleteDate(ZonedDateTime deleteDate) {
        this.deleteDate = deleteDate;
        return this;
    }

    public void setDeleteDate(ZonedDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Rooms getRoomId() {
        return roomId;
    }

    public Subscribings roomId(Rooms rooms) {
        this.roomId = rooms;
        return this;
    }

    public void setRoomId(Rooms rooms) {
        this.roomId = rooms;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subscribings)) {
            return false;
        }
        return id != null && id.equals(((Subscribings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subscribings{" +
            "id=" + getId() +
            ", roomId=" + getRoomId() +
            ", login='" + getLogin() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", deleteDate='" + getDeleteDate() + "'" +
            "}";
    }
}
