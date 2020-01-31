/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Anexos;
import java.io.IOException;
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
public class AnexosDAO extends GerenciadorDAO {

    public AnexosDAO(EntityManagerFactory emf) {
        super(emf);
    }

    public List<Anexos> buscaAnexosProjeto(int idProjeto) {
        EntityManager em = getEntityManager();
        try {
            Query consulta = em.createQuery("select a FROM Anexos a WHERE a.refSource =" + idProjeto);
            return consulta.getResultList();
        } finally {
            em.close();
        }
    }

//    public List<Anexos> buscaAnexosProjeto(String nomeAnexo) {
//        EntityManager em = getEntityManager();
//        try {
//            Query consulta = em.createQuery("select a FROM Anexos a WHERE a.nome =" + nomeAnexo);
//            return (List<Anexos>) consulta.getSingleResult();
//        } finally {
//            em.close();
//        }
//    }

    public List<Anexos> buscaAnexos(String desc, int idSource) throws IOException {

        EntityManager em = getEntityManager();

        try {

            Query consulta = em.createNamedQuery(Anexos.pesquisarNome);

            consulta.setParameter("nome", "%" + desc + "%");
            consulta.setParameter("idSource", "%" + idSource + "%");

            return consulta.getResultList();

        } finally {
            em.close();
        }
    }

    public List<Anexos> findAnexosEntities() {
        return findAnexosEntities(true, -1, -1);
    }

    public List<Anexos> findAnexosEntities(int maxResults, int firstResult) {
        return findAnexosEntities(false, maxResults, firstResult);
    }

    private List<Anexos> findAnexosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Anexos.class));
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

    public Anexos findAnexos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anexos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnexosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Anexos> rt = cq.from(Anexos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
