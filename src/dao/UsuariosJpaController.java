/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.Equipes;
import java.util.ArrayList;
import java.util.Collection;
import entidade.Projetos;
import entidade.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rrs
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getEquipesCollection() == null) {
            usuarios.setEquipesCollection(new ArrayList<Equipes>());
        }
        if (usuarios.getProjetosCollection() == null) {
            usuarios.setProjetosCollection(new ArrayList<Projetos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Equipes> attachedEquipesCollection = new ArrayList<Equipes>();
            for (Equipes equipesCollectionEquipesToAttach : usuarios.getEquipesCollection()) {
                equipesCollectionEquipesToAttach = em.getReference(equipesCollectionEquipesToAttach.getClass(), equipesCollectionEquipesToAttach.getId());
                attachedEquipesCollection.add(equipesCollectionEquipesToAttach);
            }
            usuarios.setEquipesCollection(attachedEquipesCollection);
            Collection<Projetos> attachedProjetosCollection = new ArrayList<Projetos>();
            for (Projetos projetosCollectionProjetosToAttach : usuarios.getProjetosCollection()) {
                projetosCollectionProjetosToAttach = em.getReference(projetosCollectionProjetosToAttach.getClass(), projetosCollectionProjetosToAttach.getId());
                attachedProjetosCollection.add(projetosCollectionProjetosToAttach);
            }
            usuarios.setProjetosCollection(attachedProjetosCollection);
            em.persist(usuarios);
            for (Equipes equipesCollectionEquipes : usuarios.getEquipesCollection()) {
                equipesCollectionEquipes.getUsuariosCollection().add(usuarios);
                equipesCollectionEquipes = em.merge(equipesCollectionEquipes);
            }
            for (Projetos projetosCollectionProjetos : usuarios.getProjetosCollection()) {
                Usuarios oldResponsavelOfProjetosCollectionProjetos = projetosCollectionProjetos.getResponsavel();
                projetosCollectionProjetos.setResponsavel(usuarios);
                projetosCollectionProjetos = em.merge(projetosCollectionProjetos);
                if (oldResponsavelOfProjetosCollectionProjetos != null) {
                    oldResponsavelOfProjetosCollectionProjetos.getProjetosCollection().remove(projetosCollectionProjetos);
                    oldResponsavelOfProjetosCollectionProjetos = em.merge(oldResponsavelOfProjetosCollectionProjetos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuarios = em.merge(usuarios);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Projetos> projetosCollectionOrphanCheck = usuarios.getProjetosCollection();
            for (Projetos projetosCollectionOrphanCheckProjetos : projetosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Projetos " + projetosCollectionOrphanCheckProjetos + " in its projetosCollection field has a non-nullable responsavel field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Equipes> equipesCollection = usuarios.getEquipesCollection();
            for (Equipes equipesCollectionEquipes : equipesCollection) {
                equipesCollectionEquipes.getUsuariosCollection().remove(usuarios);
                equipesCollectionEquipes = em.merge(equipesCollectionEquipes);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public List<Usuarios> buscaUsuarios(String desc) {
       
        EntityManager em = getEntityManager();
        
        try {
           
            Query consulta = em.createNamedQuery(Usuarios.pesquisarNomeUsuario);

            consulta.setParameter("nome", "%" + desc + "%");
            
            return consulta.getResultList();
            
        }finally {
            em.close();
        }
    }
     
     
     public List<Usuarios> pegarIdUsuario() throws Exception {
            Usuarios u = new Usuarios();
        
            u.setId(8);
            u.setLogin("rrs");
            u.setSenha("2");
            u.setNome("Rafael");
            
            List<Usuarios> listaUsuario = new ArrayList<>();
            
            listaUsuario.add(u);
            
            return listaUsuario;
    }
    
}
