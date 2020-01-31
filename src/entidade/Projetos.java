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
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rrs
 */
@Entity
@Table(name = "projetos", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projetos.findAll", query = "SELECT p FROM Projetos p order by p.nome")
    , @NamedQuery(name = "Projetos.findById", query = "SELECT p FROM Projetos p WHERE p.id = :id")
    , @NamedQuery(name = "Projetos.findByNome", query = "SELECT p FROM Projetos p WHERE p.nome = :nome")
    , @NamedQuery(name = "Projetos.findByInfo", query = "SELECT p FROM Projetos p WHERE p.info = :info")
    , @NamedQuery(name = "Projetos.findByNomeLike", query = "SELECT p FROM Projetos p WHERE upper(remove_acento(p.nome)) like ( upper(remove_acento(:nome)) ) order by p.nome")})

@NamedStoredProcedureQuery(
    name = "remove_acento",
    procedureName = "remove_acento",
    parameters = {@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "texto")})

public class Projetos implements Serializable {

    public static final String pesquisarNome = "Projetos.findByNomeLike";
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
    
    @Basic(optional = false)
    @Column(name = "dt_criacao")
    private String dataCriacao;
    
    @Column(name = "situacao")// 1 = Em Andamento, 2 = Finalizado
    private int situacao;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "refSource")
    private Collection<Anexos> anexosCollection;
    
    @JoinColumn(name = "ref_equipe", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Equipes refEquipe;
    
    @JoinColumn(name = "responsavel", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuarios responsavel;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "refProjeto")
    private Collection<ComodosProjetos> comodosProjetosCollection;


    public Projetos() {
    }

    public Projetos(Integer id) {
        this.id = id;
    }

    public Projetos(Integer id, String nome) {
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

    public Equipes getRefEquipe() {
        return refEquipe;
    }

    public void setRefEquipe(Equipes refEquipe) {
        this.refEquipe = refEquipe;
    }

    public Usuarios getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuarios responsavel) {
        this.responsavel = responsavel;
    }

//    @XmlTransient
//    public Collection<Faces> getFacesCollection() {
//        return facesCollection;
//    }
//
//    public void setFacesCollection(Collection<Faces> facesCollection) {
//        this.facesCollection = facesCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projetos)) {
            return false;
        }
        Projetos other = (Projetos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Projetos[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ComodosProjetos> getComodosProjetosCollection() {
        return comodosProjetosCollection;
    }

    public void setComodosProjetosCollection(Collection<ComodosProjetos> comodosProjetosCollection) {
        this.comodosProjetosCollection = comodosProjetosCollection;
    }

    /**
     * @return the dataCriacao
     */
    public String getDataCriacao() {
        return dataCriacao;
    }

    /**
     * @param dataCriacao the dataCriacao to set
     */
    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    @XmlTransient
    public Collection<Anexos> getAnexosCollection() {
        return anexosCollection;
    }

    public void setAnexosCollection(Collection<Anexos> anexosCollection) {
        this.anexosCollection = anexosCollection;
    }

    /**
     * @return the situacao
     */

    
}
