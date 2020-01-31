/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.ComodosProjetos;
import entidade.Projetos;
import java.io.IOException;
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
public class ProjetosDAO extends GerenciadorDAO{
    
    public ProjetosDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
    public List<Projetos> buscaProjetos(String desc) throws IOException {
       
        EntityManager em = getEntityManager();
        
        try {
           
            Query consulta = em.createNamedQuery(Projetos.pesquisarNome);

            consulta.setParameter("nome", "%" + desc + "%");
            
            return consulta.getResultList();
            
        }finally {
//            em.close();
        }
    }
    
    public List<Projetos> findProjetosEntities() {
        return findProjetosEntities(true, -1, -1);
    }

    public List<Projetos> findProjetosEntities(int maxResults, int firstResult) {
        return findProjetosEntities(false, maxResults, firstResult);
    }

    private List<Projetos> findProjetosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projetos.class));
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

    public Projetos findProjetos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projetos.class, id);
        } finally {
//            em.close();
        }
    }

    public int getProjetosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projetos> rt = cq.from(Projetos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
//            em.close();
        }
    }
    
    public Collection<ComodosProjetos> buscaComodosProjeto(int idProjeto){
        EntityManager em = getEntityManager();
        try {
            Query consulta = em.createQuery("select c FROM ComodosProjetos c WHERE c.refProjeto =" + idProjeto);
            return consulta.getResultList();
        }finally {
//            em.close();
        }
    }
    
}
