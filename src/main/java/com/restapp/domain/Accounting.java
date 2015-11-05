package com.restapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Accounting.
 */
@Entity
@Table(name = "accounting")
public class Accounting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dailysalestotal")
    private String dailysalestotal;

    @Column(name = "dailytipstotal")
    private Double dailytipstotal;

    @Column(name = "addcouponvalue")
    private Double addcouponvalue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDailysalestotal() {
        return dailysalestotal;
    }

    public void setDailysalestotal(String dailysalestotal) {
        this.dailysalestotal = dailysalestotal;
    }

    public Double getDailytipstotal() {
        return dailytipstotal;
    }

    public void setDailytipstotal(Double dailytipstotal) {
        this.dailytipstotal = dailytipstotal;
    }

    public Double getAddcouponvalue() {
        return addcouponvalue;
    }

    public void setAddcouponvalue(Double addcouponvalue) {
        this.addcouponvalue = addcouponvalue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Accounting accounting = (Accounting) o;

        if ( ! Objects.equals(id, accounting.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Accounting{" +
            "id=" + id +
            ", dailysalestotal='" + dailysalestotal + "'" +
            ", dailytipstotal='" + dailytipstotal + "'" +
            ", addcouponvalue='" + addcouponvalue + "'" +
            '}';
    }
}
