/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Faces;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author rrs
 */
public class FacesDAO extends GerenciadorDAO {

    public FacesDAO(EntityManagerFactory emf) {
        super(emf);
    }

    
    
    
    public List<Faces> findFacesEntities() {
        return findFacesEntities(true, -1, -1);
    }

    public List<Faces> findFacesEntities(int maxResults, int firstResult) {
        return findFacesEntities(false, maxResults, firstResult);
    }

    private List<Faces> findFacesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Faces.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
//            em.close();
        }
    }

    public Faces findFaces(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Faces.class, id);
        } finally {
//            em.close();
        }
    }

    public int getFacesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Faces> rt = cq.from(Faces.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
//            em.close();
        }
    }
}
