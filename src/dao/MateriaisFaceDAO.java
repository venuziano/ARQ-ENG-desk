/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.MateriaisFace;
import entidade.MateriaisFacePK;
import java.util.Collection;
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
public class MateriaisFaceDAO extends GerenciadorDAO{
    
    public MateriaisFaceDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
    public Collection<MateriaisFace> buscaMaterialPorFace(int idFace){
        EntityManager em = getEntityManager();
        try {
            Query consulta = em.createQuery("select mf from MateriaisFace mf where mf.faces =" + idFace);
            return consulta.getResultList();
        }finally {
//            em.close();
        }
    }
    
    public List<MateriaisFace> findMateriaisFaceEntities() {
        return findMateriaisFaceEntities(true, -1, -1);
    }

    public List<MateriaisFace> findMateriaisFaceEntities(int maxResults, int firstResult) {
        return findMateriaisFaceEntities(false, maxResults, firstResult);
    }

    private List<MateriaisFace> findMateriaisFaceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MateriaisFace.class));
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

    public MateriaisFace findMateriaisFace(MateriaisFacePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MateriaisFace.class, id);
        } finally {
//            em.close();
        }
    }

    public int getMateriaisFaceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MateriaisFace> rt = cq.from(MateriaisFace.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
//            em.close();
        }
    }
    
}
