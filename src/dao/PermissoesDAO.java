/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Permissoes;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author mf
 */
public class PermissoesDAO extends GerenciadorDAO{
    
    public PermissoesDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
    public List<Permissoes> findPermissoesEntities() {
        return findPermissoesEntities(true, -1, -1);
    }

    public List<Permissoes> findPermissoesEntities(int maxResults, int firstResult) {
        return findPermissoesEntities(false, maxResults, firstResult);
    }

    private List<Permissoes> findPermissoesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permissoes.class));
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
    
    public List<String> findTelas() {
        EntityManager em = getEntityManager();
        try {
            
            Query consulta = em.createNamedQuery(Permissoes.buscarTelas);
            
            return consulta.getResultList();
        } finally {
//            em.close();
        }
    }
    
    public Collection<Permissoes> buscaPermissoesUsuario(int id) {
       
        EntityManager em = getEntityManager();
        
        try {
           
            Query consulta = em.createNamedQuery(Permissoes.buscarPermissoesUsuario);
            
            consulta.setParameter("id", id);
            
            return consulta.getResultList();
            
        }finally {
//            em.close();
        }
    }
    
}
