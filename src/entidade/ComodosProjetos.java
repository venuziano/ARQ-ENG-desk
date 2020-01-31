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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "comodos_projetos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComodosProjetos.findAll", query = "SELECT c FROM ComodosProjetos c")
    , @NamedQuery(name = "ComodosProjetos.findById", query = "SELECT c FROM ComodosProjetos c WHERE c.id = :id")
    , @NamedQuery(name = "ComodosProjetos.findByNome", query = "SELECT c FROM ComodosProjetos c WHERE c.nome = :nome")
    , @NamedQuery(name = "ComodosProjetos.findByCargaTermicaT", query = "SELECT c FROM ComodosProjetos c WHERE c.cargaTermicaT = :cargaTermicaT")})

public class ComodosProjetos implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "carga_termica_t")
    private Double cargaTermicaT;
    
    @Column(name = "btus")
    private Double btus;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "refComodo")
    private Collection<Faces> facesCollection;
    
    @JoinColumn(name = "ref_projeto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Projetos refProjeto;

    public ComodosProjetos() {
    }

    public ComodosProjetos(Integer id) {
        this.id = id;
    }

    public ComodosProjetos(Integer id, String nome) {
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

    public Projetos getRefProjeto() {
        return refProjeto;
    }

    public void setRefProjeto(Projetos refProjeto) {
        this.refProjeto = refProjeto;
    }
    
    public Double getCargaTermicaT() {
        return cargaTermicaT;
    }

    public void setCargaTermicaT(Double cargaTermicaT) {
        this.cargaTermicaT = cargaTermicaT;
    }

    @XmlTransient
    public Collection<Faces> getFacesCollection() {
        return facesCollection;
    }

    public void setFacesCollection(Collection<Faces> facesCollection) {
        this.facesCollection = facesCollection;
    }
    
    public Double getBtus() {
        return btus;
    }

    public void setBtus(Double btus) {
        this.btus = btus;
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
        if (!(object instanceof ComodosProjetos)) {
            return false;
        }
        ComodosProjetos other = (ComodosProjetos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.ComodosProjetos[ id=" + id + " ]";
    }
    
}
