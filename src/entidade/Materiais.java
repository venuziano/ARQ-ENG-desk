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
@Table(name = "materiais")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materiais.findAll", query = "SELECT m FROM Materiais m" )
    , @NamedQuery(name = "Materiais.findById", query = "SELECT m FROM Materiais m WHERE m.id = :id")
    , @NamedQuery(name = "Materiais.findBySituacao", query = "SELECT m FROM Materiais m WHERE m.situacao = :situacao")
    , @NamedQuery(name = "Materiais.findByNome", query = "SELECT m FROM Materiais m WHERE m.nome = :nome")
//    , @NamedQuery(name = "Materiais.findByIdNome", query = "SELECT m.id FROM Materiais m WHERE m.nome = :nome")
    , @NamedQuery(name = "Materiais.findByCondutividade", query = "SELECT m FROM Materiais m WHERE m.condutividade = :condutividade")
    , @NamedQuery(name = "Materiais.findByInfo", query = "SELECT m FROM Materiais m WHERE m.info = :info")
    , @NamedQuery(name = "Materiais.findByTipo", query = "SELECT m FROM Materiais m WHERE m.tipo = :tipo")
    , @NamedQuery(name = "Materiais.findByNomeLike", query = "SELECT m FROM Materiais m WHERE m.situacao =:situacao and "
            + "upper(remove_acento(m.nome)) like ( upper(remove_acento(:nome)) ) order by m.nome")})

public class Materiais implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String pesquisarNome = "Materiais.findByNomeLike";
    public static final String pesquisarIdMaterialFace = "Materiais.findByNome";
    public static final String montaPorSituacao = "Materiais.findBySituacao";
    
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
    
    @Column(name = "condutividade")
    private Double condutividade;
    
    @Basic(optional = false)
    @Column(name = "situacao")
    private int situacao;
    
    @Basic(optional = false)
    @Column(name = "tipo") // 0 = transparente; 1 = opaco
    private int tipo;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "materiais")
    private Collection<MateriaisFace> materiaisFaceCollection;

    public Materiais() {
    }

    public Materiais(Integer id) {
        this.id = id;
    }

    public Materiais(Integer id, String nome, Double espessura, Double condutividade, Double rt) {
        this.id = id;
        this.nome = nome;
        this.condutividade = condutividade;
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
    public Collection<MateriaisFace> getMateriaisFaceCollection() {
        return materiaisFaceCollection;
    }

    public void setMateriaisFaceCollection(Collection<MateriaisFace> materiaisFaceCollection) {
        this.materiaisFaceCollection = materiaisFaceCollection;
    }

    public Double getCondutividade() {
        return condutividade;
    }

    public void setCondutividade(Double condutividade) {
        this.condutividade = condutividade;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof Materiais)) {
            return false;
        }
        Materiais other = (Materiais) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Materiais[ id=" + id + " ]";
    }
    
}
