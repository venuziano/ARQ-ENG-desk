/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rrs
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")
    , @NamedQuery(name = "Usuarios.findById", query = "SELECT u FROM Usuarios u WHERE u.id = :id")
    , @NamedQuery(name = "Usuarios.findByNome", query = "SELECT u FROM Usuarios u WHERE u.nome = :nome")
    , @NamedQuery(name = "Usuarios.findByLogin", query = "SELECT u FROM Usuarios u WHERE u.login = :login")
    , @NamedQuery(name = "Usuarios.findBySenha", query = "SELECT u FROM Usuarios u WHERE u.senha = :senha")
    , @NamedQuery(name = "Usuarios.findByNomeLike", query = "SELECT u FROM Usuarios u WHERE upper(u.nome) like upper(:nome) order by u.nome")})
public class Usuarios implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "refUsuario")
    private Collection<Anexos> anexosCollection;

    public static final String pesquisarNomeUsuario = "Usuarios.findByNomeLike";
    public static final String pesquisarLoginUsuario = "Usuarios.findByLogin";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    
    @Basic(optional = false)
    @Column(name = "senha")
    private String senha;
    
    @ManyToMany(mappedBy = "usuariosCollection")
    private Collection<Equipes> equipesCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responsavel")
    private Collection<Projetos> projetosCollection;
    
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "refUsuario")
    private Collection<Permissoes> permissoesCollection;

    public Usuarios() {
    }

    public Usuarios(Integer id) {
        this.id = id;
    }

    public Usuarios(Integer id, String nome, String login, String senha) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @XmlTransient
    public Collection<Equipes> getEquipesCollection() {
        return equipesCollection;
    }

    public void setEquipesCollection(Collection<Equipes> equipesCollection) {
        this.equipesCollection = equipesCollection;
    }

    @XmlTransient
    public Collection<Projetos> getProjetosCollection() {
        return projetosCollection;
    }

    public void setProjetosCollection(Collection<Projetos> projetosCollection) {
        this.projetosCollection = projetosCollection;
    }
    
    @XmlTransient
    public Collection<Permissoes> getPermissoesCollection() {
        return permissoesCollection;
    }

    public void setPermissoesCollection(Collection<Permissoes> permissoesCollection) {
        this.permissoesCollection = permissoesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "entidade.Usuarios[ id=" + id + " ]";
//    }
    
    @Override
    public String toString () {
        return nome;
    }

    @XmlTransient
    public Collection<Anexos> getAnexosCollection() {
        return anexosCollection;
    }

    public void setAnexosCollection(Collection<Anexos> anexosCollection) {
        this.anexosCollection = anexosCollection;
    }
}