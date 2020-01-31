/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Materiais;
import java.io.IOException;
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
public class MateriaisDAO extends GerenciadorDAO{
    
    public MateriaisDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
     public List<Materiais> buscaMateriais(String desc) throws IOException {
       
        EntityManager em = getEntityManager();
        
        try {
           
            Query consulta = em.createNamedQuery(Materiais.pesquisarNome);

            consulta.setParameter("nome", "%" + desc + "%");
            consulta.setParameter("situacao", 1);
            
            return consulta.getResultList();
            
        }finally {
//            em.close();
        }
    }
     
     public List<Materiais> buscaIdMaterialFace(String nomeMaterial) throws IOException {

        EntityManager em = getEntityManager();

        try {

            Query consulta = em.createNamedQuery(Materiais.pesquisarIdMaterialFace);

            consulta.setParameter("nome",nomeMaterial);

            return consulta.getResultList();

        } finally {
//            em.close();
        }
    }
    
     public List<Materiais> findMateriaisEntities() {
        return findMateriaisEntities(true, -1, -1);
    }

    public List<Materiais> findMateriaisEntities(int maxResults, int firstResult) {
        return findMateriaisEntities(false, maxResults, firstResult);
    }

    private List<Materiais> findMateriaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery(Materiais.montaPorSituacao);
            q.setParameter("situacao", 1);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
//            em.close();
        }
    }

    public Materiais findMateriais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Materiais.class, id);
        } finally {
//            em.close();
        }
    }
    
    public int getMateriaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materiais> rt = cq.from(Materiais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
//            em.close();
        }
    }
}
