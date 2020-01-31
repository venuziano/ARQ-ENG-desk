/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rrs
 */
@Entity
@Table(name = "anexos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anexos.findAll", query = "SELECT a FROM Anexos a")
    , @NamedQuery(name = "Anexos.findById", query = "SELECT a FROM Anexos a WHERE a.id = :id")
    , @NamedQuery(name = "Anexos.findByDtCriacao", query = "SELECT a FROM Anexos a WHERE a.dtCriacao = :dtCriacao")
    , @NamedQuery(name = "Anexos.findByPath", query = "SELECT a FROM Anexos a WHERE a.path = :path")
    , @NamedQuery(name = "Anexos.findByNomeLike", query = "SELECT a FROM Anexos a WHERE a.refSource = :idSource and "
            + "upper(remove_acento(a.nome)) like ( upper(remove_acento(:nome)) ) order by a.nome")})

public class Anexos implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String pesquisarNome = "Anexos.findByNomeLike";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    
    private Integer id;
    @Basic(optional = false)
    @Column(name = "dt_criacao")
    private String dtCriacao;
    
    @Basic(optional = false)
    @Column(name = "path")
    private String path;
    
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "family")// 0 = projetos;
    private int family;
    
    @Basic(optional = false)
    @Column(name = "ref_source")
    private int refSource;
    
    @JoinColumn(name = "ref_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuarios refUsuario;

    public Anexos() {
    }

    public Anexos(Integer id) {
        this.id = id;
    }

    public Anexos(Integer id, String dtCriacao, String path) {
        this.id = id;
        this.dtCriacao = dtCriacao;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(String dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        if (!(object instanceof Anexos)) {
            return false;
        }
        Anexos other = (Anexos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Anexos[ id=" + id + " ]";
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the family
     */
    public int getFamily() {
        return family;
    }

    /**
     * @param family the family to set
     */
    public void setFamily(int family) {
        this.family = family;
    }

    /**
     * @return the refSource
     */
    public int getRefSource() {
        return refSource;
    }

    /**
     * @param refSource the refSource to set
     */
    public void setRefSource(int refSource) {
        this.refSource = refSource;
    }

    /**
     * @return the refSource
     */
    
}
