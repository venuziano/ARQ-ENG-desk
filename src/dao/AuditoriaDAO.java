/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Auditoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author rafael.silva
 */
public class AuditoriaDAO extends GerenciadorDAO{
    
    public AuditoriaDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
    public List<Auditoria> findAuditoriaEntities() {
        return findAuditoriaEntities(true, -1, -1);
    }

    public List<Auditoria> findAuditoriaEntities(int maxResults, int firstResult) {
        return findAuditoriaEntities(false, maxResults, firstResult);
    }

    private List<Auditoria> findAuditoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Auditoria.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Auditoria findAuditoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Auditoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuditoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Auditoria> rt = cq.from(Auditoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }    
}
