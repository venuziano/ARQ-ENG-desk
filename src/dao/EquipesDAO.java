/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Equipes;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author mf
 */
public class EquipesDAO extends GerenciadorDAO {
    
    public EquipesDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
    public List<Equipes> findEquipesEntities() {
        return findEquipesEntities(true, -1, -1);
    }
    
    public List<Equipes> findEquipesEntities(int maxResults, int firstResult) {
        return findEquipesEntities(false, maxResults, firstResult);
    }

    private List<Equipes> findEquipesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipes.class));
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
    
    public Equipes findEquipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipes.class, id);
        } finally {
//            em.close();
        }
    }
    
    public void inserirUsersEquipe(int usuario, int equipe) throws IOException {
       
        EntityManager em = getEntityManager();
        
        try {
                em.getTransaction().begin();
                StoredProcedureQuery query = em.createNamedStoredProcedureQuery("inserirUsersEquipe");
                query.setParameter("usuario", usuario);
                query.setParameter("equipe", equipe);
                
                query.execute();
                
                em.getTransaction().commit();
                em.close();
            
        }catch (Exception e) {
            log.get_log(e.toString());
        }finally {
//            em.close();
    }
}
    
    public void removerUsersEquipe(int usuario, int equipe) throws IOException {
       
        EntityManager em = getEntityManager();
        
        try {
                em.getTransaction().begin();
                StoredProcedureQuery query = em.createNamedStoredProcedureQuery("removerUsersEquipe");
                query.setParameter("usuario", usuario);
                query.setParameter("equipe", equipe);
                query.execute();
                
                em.getTransaction().commit();
                em.close();
            
        }catch (Exception e) {
            log.get_log(e.toString());
        }finally {
//            em.close();
    }
}
    
}
