package org.acme;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.Set;


@Entity
public class ChildEntity extends MasterEntity {
    private String childField;

    @OneToMany(mappedBy = "childEntity")
    public Set<RelatedEntity> relatedEntitySet;

    public String getChildField() {
        return childField;
    }

    public void setChildField(final String childField) {
        this.childField = childField;
    }

    public Set<RelatedEntity> getRelatedEntitySet() {
        return relatedEntitySet;
    }

    public void setRelatedEntitySet(final Set<RelatedEntity> relatedEntitySet) {
        this.relatedEntitySet = relatedEntitySet;
    }

    @Override
    public String toString() {
        return "ChildEntity{" +
                "childField='" + childField + '\'' +
                ", relatedEntitySet=" + relatedEntitySet +
                ", id=" + id +
                ", masterField='" + masterField + '\'' +
                '}';
    }
}
