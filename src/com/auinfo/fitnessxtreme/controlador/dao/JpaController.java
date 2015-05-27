/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auinfo.fitnessxtreme.controlador.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author BrunoRicardo
 */
public class JpaController<T> implements Serializable {

    private static EntityManager em;
    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("Sistema-Gerenciador-de-Treinos-Fitness-X-TremePU");
        em = emf.createEntityManager();
    }

    public JpaController() {
    }

    public boolean create(T obj) {
        try {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public boolean edit(T obj) {
        try {
            em.getTransaction().begin();
            obj = em.merge(obj);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public boolean destroy(T obj) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(obj));
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public List<T> findEntities(Query query) {
        return findEntities(true, -1, -1, query);
    }

    public List<T> findEntities(int maxResults, int firstResult, Query query) {
        return findEntities(false, maxResults, firstResult, query);
    }

    public Query getQuery(String consulta) {
        return em.createQuery(consulta);
    }

    private List<T> findEntities(boolean all, int maxResults, int firstResult, Query query) {
        if (!all) {
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);
        }
        return query.getResultList();
    }

    public T findContato(Class<T> classe, Long id) {
        return em.find(classe, id);
    }

    public int getCount(Class<T> classe) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(classe);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
