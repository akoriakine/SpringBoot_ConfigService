package com.ak.cfg.dao;

import com.ak.cfg.model.Config;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ConfigDao {
    @PersistenceContext
    private EntityManager em;

    public Config findConfig(String appCode, Integer version) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Config> criteria = builder.createQuery(Config.class);
        Root<Config> from = criteria.from(Config.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get("appCode"), appCode),
                       builder.equal(from.get("version"), version));
        TypedQuery<Config> query = em.createQuery(criteria);

        try {
            return query.getSingleResult();
        } catch (Exception e) {}
        return null;
    }

    public List<Config> findVersions(String appCode) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Config> criteria = builder.createQuery(Config.class);
        Root<Config> from = criteria.from(Config.class);

        criteria.select(from);
        criteria.where(builder.equal(from.get("appCode"), appCode));
        criteria.orderBy(builder.desc(from.get("modified")));

        TypedQuery<Config> query = em.createQuery(criteria);
        List<Config> list = query.getResultList();
        return list;
    }

    public void update(String appCode, Integer version, String document) {
        Config config = findConfig(appCode, version);
        if (config == null) {
            config = new Config(appCode, version, LocalDateTime.now(), document);
            em.persist(config);
        } else {
            config.setDocument(document);
            config.setModified(LocalDateTime.now());
            em.merge(config);
        }
    }

}
