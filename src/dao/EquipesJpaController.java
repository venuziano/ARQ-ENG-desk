/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import entidade.Equipes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.Usuarios;
import java.util.ArrayList;
import java.util.Collection;
import entidade.Projetos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rrs
 */
public class EquipesJpaController implements Serializable {

    public EquipesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipes equipes) {
        if (equipes.getUsuariosCollection() == null) {
            equipes.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        if (equipes.getProjetosCollection() == null) {
            equipes.setProjetosCollection(new ArrayList<Projetos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : equipes.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getId());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            equipes.setUsuariosCollection(attachedUsuariosCollection);
            Collection<Projetos> attachedProjetosCollection = new ArrayList<Projetos>();
            for (Projetos projetosCollectionProjetosToAttach : equipes.getProjetosCollection()) {
                projetosCollectionProjetosToAttach = em.getReference(projetosCollectionProjetosToAttach.getClass(), projetosCollectionProjetosToAttach.getId());
                attachedProjetosCollection.add(projetosCollectionProjetosToAttach);
            }
            equipes.setProjetosCollection(attachedProjetosCollection);
            em.persist(equipes);
            for (Usuarios usuariosCollectionUsuarios : equipes.getUsuariosCollection()) {
                usuariosCollectionUsuarios.getEquipesCollection().add(equipes);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            for (Projetos projetosCollectionProjetos : equipes.getProjetosCollection()) {
                Equipes oldRefEquipeOfProjetosCollectionProjetos = projetosCollectionProjetos.getRefEquipe();
                projetosCollectionProjetos.setRefEquipe(equipes);
                projetosCollectionProjetos = em.merge(projetosCollectionProjetos);
                if (oldRefEquipeOfProjetosCollectionProjetos != null) {
                    oldRefEquipeOfProjetosCollectionProjetos.getProjetosCollection().remove(projetosCollectionProjetos);
                    oldRefEquipeOfProjetosCollectionProjetos = em.merge(oldRefEquipeOfProjetosCollectionProjetos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipes equipes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipes persistentEquipes = em.find(Equipes.class, equipes.getId());
            Collection<Usuarios> usuariosCollectionOld = persistentEquipes.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = equipes.getUsuariosCollection();
            Collection<Projetos> projetosCollectionOld = persistentEquipes.getProjetosCollection();
            Collection<Projetos> projetosCollectionNew = equipes.getProjetosCollection();
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getId());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            equipes.setUsuariosCollection(usuariosCollectionNew);
            Collection<Projetos> attachedProjetosCollectionNew = new ArrayList<Projetos>();
            for (Projetos projetosCollectionNewProjetosToAttach : projetosCollectionNew) {
                projetosCollectionNewProjetosToAttach = em.getReference(projetosCollectionNewProjetosToAttach.getClass(), projetosCollectionNewProjetosToAttach.getId());
                attachedProjetosCollectionNew.add(projetosCollectionNewProjetosToAttach);
            }
            projetosCollectionNew = attachedProjetosCollectionNew;
            equipes.setProjetosCollection(projetosCollectionNew);
            equipes = em.merge(equipes);
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    usuariosCollectionOldUsuarios.getEquipesCollection().remove(equipes);
                    usuariosCollectionOldUsuarios = em.merge(usuariosCollectionOldUsuarios);
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    usuariosCollectionNewUsuarios.getEquipesCollection().add(equipes);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                }
            }
            for (Projetos projetosCollectionOldProjetos : projetosCollectionOld) {
                if (!projetosCollectionNew.contains(projetosCollectionOldProjetos)) {
                    projetosCollectionOldProjetos.setRefEquipe(null);
                    projetosCollectionOldProjetos = em.merge(projetosCollectionOldProjetos);
                }
            }
            for (Projetos projetosCollectionNewProjetos : projetosCollectionNew) {
                if (!projetosCollectionOld.contains(projetosCollectionNewProjetos)) {
                    Equipes oldRefEquipeOfProjetosCollectionNewProjetos = projetosCollectionNewProjetos.getRefEquipe();
                    projetosCollectionNewProjetos.setRefEquipe(equipes);
                    projetosCollectionNewProjetos = em.merge(projetosCollectionNewProjetos);
                    if (oldRefEquipeOfProjetosCollectionNewProjetos != null && !oldRefEquipeOfProjetosCollectionNewProjetos.equals(equipes)) {
                        oldRefEquipeOfProjetosCollectionNewProjetos.getProjetosCollection().remove(projetosCollectionNewProjetos);
                        oldRefEquipeOfProjetosCollectionNewProjetos = em.merge(oldRefEquipeOfProjetosCollectionNewProjetos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipes.getId();
                if (findEquipes(id) == null) {
                    throw new NonexistentEntityException("The equipes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipes equipes;
            try {
                equipes = em.getReference(Equipes.class, id);
                equipes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipes with id " + id + " no longer exists.", enfe);
            }
            Collection<Usuarios> usuariosCollection = equipes.getUsuariosCollection();
            for (Usuarios usuariosCollectionUsuarios : usuariosCollection) {
                usuariosCollectionUsuarios.getEquipesCollection().remove(equipes);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            Collection<Projetos> projetosCollection = equipes.getProjetosCollection();
            for (Projetos projetosCollectionProjetos : projetosCollection) {
                projetosCollectionProjetos.setRefEquipe(null);
                projetosCollectionProjetos = em.merge(projetosCollectionProjetos);
            }
            em.remove(equipes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
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
            em.close();
        }
    }

    public Equipes findEquipes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipes.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipes> rt = cq.from(Equipes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
