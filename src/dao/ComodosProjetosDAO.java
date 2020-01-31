/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.ComodosProjetos;
import entidade.Faces;
import entidade.Projetos;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author rrs
 */
public class ComodosProjetosDAO extends GerenciadorDAO{
    
    public ComodosProjetosDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
    public ComodosProjetos findComodos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComodosProjetos.class, id);
        } finally {
//            em.close();
        }
    }
    
    public Collection<ComodosProjetos> buscaComodosPorProjetos( int idProjeto){
        EntityManager em = getEntityManager();
        try {
            Query consulta = em.createQuery("select c from ComodosProjetos c where c.refProjeto =" + idProjeto);
            return consulta.getResultList();
        }finally {
//            em.close();
        }
    }
    
    public List<ComodosProjetos> findComodosEntities() {
        return findComodosEntities(true, -1, -1);
    }
    
    public List<ComodosProjetos> findComodosEntities(int maxResults, int firstResult) {
        return findComodosEntities(false, maxResults, firstResult);
    }
    
    private List<ComodosProjetos> findComodosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComodosProjetos.class));
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
    
    public Collection<Faces> buscaFaces(int idComodo){
        EntityManager em = getEntityManager();
        try {
            Query consulta = em.createQuery("select f from ComodosProjetos c, Faces f where f.refComodo =" + idComodo);
            return consulta.getResultList();
        }finally {
//            em.close();
        }
    }
}
