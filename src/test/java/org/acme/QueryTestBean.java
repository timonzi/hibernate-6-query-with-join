package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class QueryTestBean {

    @Inject
    EntityManager em;


    @Transactional
    void createEntity() {
        final var childEntity = new ChildEntity();
        childEntity.setMasterField("masterValue001");
        childEntity.setChildField("childValue001");

        RelatedEntity relatedEntity = new RelatedEntity();
        relatedEntity.setRelatedField("relatedValue001");
        relatedEntity.setChildEntity(childEntity);

        RelatedEntity relatedEntity2 = new RelatedEntity();
        relatedEntity2.setRelatedField("relatedValue002");
        relatedEntity2.setChildEntity(childEntity);

        HashSet<RelatedEntity> relatedEntityHashSet = new HashSet<>();
        relatedEntityHashSet.add(relatedEntity);
        relatedEntityHashSet.add(relatedEntity2);

        childEntity.setRelatedEntitySet(relatedEntityHashSet);

        em.persist(childEntity);
        em.persist(relatedEntity);
        em.persist(relatedEntity2);
    }


    @Transactional
    List<ChildPojo> getByQuery() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ChildPojo> query = cb.createQuery(ChildPojo.class);
        Root<ChildEntity> root = query.from(ChildEntity.class);

        query.multiselect(
                root.get("masterField"),
                root.get("childField")
        );

        TypedQuery<ChildPojo> result = em.createQuery(query);
        List<ChildPojo> resultList = result.getResultList();

        for (ChildPojo pojo : resultList) {
            System.out.println("found: " + pojo);
        }

        return resultList;
    }


    @Transactional
    List<ChildPojo> getByQueryWithAdditionalJoin() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ChildPojo> query = cb.createQuery(ChildPojo.class);
        Root<ChildEntity> root = query.from(ChildEntity.class);
        Join<ChildEntity, RelatedEntity> relatedEntityJoin = root.join("relatedEntitySet");

        query.multiselect(
                root.get("masterField"),
                root.get("childField"),
                relatedEntityJoin.get("relatedField")
        );

        TypedQuery<ChildPojo> result = em.createQuery(query);
        List<ChildPojo> resultList = result.getResultList();

        for (ChildPojo pojo : resultList) {
            System.out.println("found: " + pojo);
        }

        return resultList;
    }

}
