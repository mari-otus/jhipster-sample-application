package ru.otus.spring.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Profiles.
 */
@Entity
@Table(name = "profiles")
public class Profiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "is_email_notify")
    private Boolean isEmailNotify;

    @Column(name = "is_phone_notify")
    private Boolean isPhoneNotify;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public Profiles login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public Profiles email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public Profiles mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Boolean isIsEmailNotify() {
        return isEmailNotify;
    }

    public Profiles isEmailNotify(Boolean isEmailNotify) {
        this.isEmailNotify = isEmailNotify;
        return this;
    }

    public void setIsEmailNotify(Boolean isEmailNotify) {
        this.isEmailNotify = isEmailNotify;
    }

    public Boolean isIsPhoneNotify() {
        return isPhoneNotify;
    }

    public Profiles isPhoneNotify(Boolean isPhoneNotify) {
        this.isPhoneNotify = isPhoneNotify;
        return this;
    }

    public void setIsPhoneNotify(Boolean isPhoneNotify) {
        this.isPhoneNotify = isPhoneNotify;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profiles)) {
            return false;
        }
        return id != null && id.equals(((Profiles) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profiles{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", isEmailNotify='" + isIsEmailNotify() + "'" +
            ", isPhoneNotify='" + isIsPhoneNotify() + "'" +
            "}";
    }
}
