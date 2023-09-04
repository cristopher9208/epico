package com.mycompany.myapp.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Epicoitem.
 */
@Table("epicoitem")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Epicoitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("category")
    private String category;

    @Column("cost_price")
    private Float costPrice;

    @Column("unit_price")
    private Float unitPrice;

    @Column("pic_filename")
    private String picFilename;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Epicoitem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Epicoitem name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public Epicoitem category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getCostPrice() {
        return this.costPrice;
    }

    public Epicoitem costPrice(Float costPrice) {
        this.setCostPrice(costPrice);
        return this;
    }

    public void setCostPrice(Float costPrice) {
        this.costPrice = costPrice;
    }

    public Float getUnitPrice() {
        return this.unitPrice;
    }

    public Epicoitem unitPrice(Float unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPicFilename() {
        return this.picFilename;
    }

    public Epicoitem picFilename(String picFilename) {
        this.setPicFilename(picFilename);
        return this;
    }

    public void setPicFilename(String picFilename) {
        this.picFilename = picFilename;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Epicoitem)) {
            return false;
        }
        return id != null && id.equals(((Epicoitem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Epicoitem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", costPrice=" + getCostPrice() +
            ", unitPrice=" + getUnitPrice() +
            ", picFilename='" + getPicFilename() + "'" +
            "}";
    }
}
