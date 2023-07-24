package org.acme;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class MasterEntity {
    @Id
    @GeneratedValue
    public Long id;

    public String masterField;


    public Long getId() {
        return id;
    }


    public String getMasterField() {
        return masterField;
    }

    public void setMasterField(final String masterField) {
        this.masterField = masterField;
    }
}
