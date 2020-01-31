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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mf
 */
@Entity
@Table(name = "permissoes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permissoes.findAll", query = "SELECT p FROM Permissoes p")
    , @NamedQuery(name = "Permissoes.findById", query = "SELECT p FROM Permissoes p WHERE p.id = :id")
    , @NamedQuery(name = "Permissoes.findByAcao", query = "SELECT p FROM Permissoes p WHERE p.acao = :acao")
    , @NamedQuery(name = "Permissoes.findByOrigem", query = "SELECT DISTINCT p.origem FROM Permissoes p")
    , @NamedQuery(name = "Permissoes.findByUsuario", query = "SELECT p FROM Permissoes p, Usuarios u WHERE u.id = :id")})
public class Permissoes implements Serializable {


    public static final String buscarTelas = "Permissoes.findByOrigem";
    public static final String buscarPermissoesUsuario = "Permissoes.findByUsuario";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "acao")
    private String acao;
    
    @Basic(optional = false)
    @Column(name = "origem")
    private String origem;
    
    @Basic(optional = false)
    @Column(name = "status")
    private int status;
    
    @JoinColumn(name = "ref_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuarios refUsuario;

    public Permissoes() {
    }

    public Permissoes(Integer id) {
        this.id = id;
    }

    public Permissoes(Integer id, String acao, String origem, Integer status) {
        this.id = id;
        this.acao = acao;
        this.origem = origem;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Usuarios getRefUsuario() {
        return refUsuario;
    }

    public void setRefUsuario(Usuarios refUsuario) {
        this.refUsuario = refUsuario;
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
        if (!(object instanceof Permissoes)) {
            return false;
        }
        Permissoes other = (Permissoes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Permissoes[ id=" + id + " ]";
    }
    
}
