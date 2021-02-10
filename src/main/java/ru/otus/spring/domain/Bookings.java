package ru.otus.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Bookings.
 */
@Entity
@Table(name = "bookings")
public class Bookings implements Serializable {

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
    @Column(name = "begin_date", nullable = false)
    private ZonedDateTime beginDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @Column(name = "delete_date")
    private ZonedDateTime deleteDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "bookings", allowSetters = true)
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

    public Bookings roomId(Integer roomId) {
        this.roomId = roomId;
        return this;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getLogin() {
        return login;
    }

    public Bookings login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ZonedDateTime getBeginDate() {
        return beginDate;
    }

    public Bookings beginDate(ZonedDateTime beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public void setBeginDate(ZonedDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Bookings endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public Bookings createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public Bookings updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public ZonedDateTime getDeleteDate() {
        return deleteDate;
    }

    public Bookings deleteDate(ZonedDateTime deleteDate) {
        this.deleteDate = deleteDate;
        return this;
    }

    public void setDeleteDate(ZonedDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Rooms getRoomId() {
        return roomId;
    }

    public Bookings roomId(Rooms rooms) {
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
        if (!(o instanceof Bookings)) {
            return false;
        }
        return id != null && id.equals(((Bookings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bookings{" +
            "id=" + getId() +
            ", roomId=" + getRoomId() +
            ", login='" + getLogin() + "'" +
            ", beginDate='" + getBeginDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", deleteDate='" + getDeleteDate() + "'" +
            "}";
    }
}
