/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import apoio.ConexaoDB;
import apoio.GerenciarAuditoria;
import entidade.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author rafael.silva
 */
public class UsuariosDAO extends GerenciadorDAO{
   
     GerenciarAuditoria ga = new GerenciarAuditoria();
     Usuarios u;
    
    public UsuariosDAO(EntityManagerFactory emf) {
        super(emf);
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
//            em.close();
        }
    }

    public Usuarios findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
//            em.close();
        }
    }
    
    public List<Usuarios> buscaUsuarios(String desc) {
       
        EntityManager em = getEntityManager();
        
        try {
           
            Query consulta = em.createNamedQuery(Usuarios.pesquisarNomeUsuario);

            consulta.setParameter("nome", "%" + desc + "%");
            
            return consulta.getResultList();
            
        }finally {
//            em.close();
        }
    }
    
    public Usuarios getUsuarioLogin(String login) {
       
        EntityManager em = getEntityManager();
        
        try {
           
            Query consulta = em.createNamedQuery(Usuarios.pesquisarLoginUsuario);

            consulta.setParameter("login", login );
            
            return (Usuarios) consulta.getSingleResult();
            
        }finally {
//            em.close();
        }
    }
    

//    public Usuarios getUsuarioLogin(String login) throws Exception {
//        Connection connection = ConexaoDB.getInstance().getConnection();
//
//        PreparedStatement ps;
//        ResultSet rs;
//        
//        Usuarios u = new Usuarios();
//
//        String sql = "select id, nome, login, senha from usuarios where login = ?";
//
//        ps = connection.prepareStatement(sql);
//        
//        ps.setString(1, login);
//        
//        rs = ps.executeQuery();
//        
//        while (rs.next()){
//            
//        u.setId(rs.getInt("id"));
//        u.setNome(rs.getString("nome"));
//        u.setLogin(rs.getString("login"));
//        u.setSenha(rs.getString("senha"));
//        
//        }
//        
//        return u;
//    }
    
    /*public boolean getUsuarioLogin2(String login, String senha) throws Exception {
        Connection connection = ConexaoDB.getInstance().getConnection();

        PreparedStatement ps;
        ResultSet rs;
        
        boolean validate = false;

        String sql = "select u.id as id, u.nome as nome, u.login, u.senha from usuarios u where u.login = ? and u.senha = ?";

        ps = connection.prepareStatement(sql);
        
        rs = ps.executeQuery();
        
        ga.setIdAutor(rs.getInt("id"));
        ga.setAutor(rs.getString("nome"));
        ps.setString(1, login);
        ps.setString(2, senha);

        rs = ps.executeQuery();

        while (rs.next()) {
            validate = true;
        }

        return validate;
    }*/
}
