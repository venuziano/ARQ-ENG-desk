/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "equipes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipes.findAll", query = "SELECT e FROM Equipes e")
    , @NamedQuery(name = "Equipes.findById", query = "SELECT e FROM Equipes e WHERE e.id = :id")
    , @NamedQuery(name = "Equipes.findByNome", query = "SELECT e FROM Equipes e WHERE e.nome = :nome")
    , @NamedQuery(name = "Equipes.findByInfo", query = "SELECT e FROM Equipes e WHERE e.info = :info")})


public class Equipes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Column(name = "info", nullable = true)
    private String info;
    @JoinTable(name = "usuarios_equipe", joinColumns = {
        @JoinColumn(name = "ref_equipe", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "ref_usuario", referencedColumnName = "id")})
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Usuarios> usuariosCollection;
    
    @OneToMany(mappedBy = "refEquipe")
    private Collection<Projetos> projetosCollection;

    public Equipes() {
    }

    public Equipes(Integer id) {
        this.id = id;
    }

    public Equipes(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @XmlTransient
    public Collection<Projetos> getProjetosCollection() {
        return projetosCollection;
    }

    public void setProjetosCollection(Collection<Projetos> projetosCollection) {
        this.projetosCollection = projetosCollection;
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
        if (!(object instanceof Equipes)) {
            return false;
        }
        Equipes other = (Equipes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "entidade.Equipes[ id=" + id + " ]";
//    }
    
    @Override
    public String toString() {
        return nome;
    }
    
}
