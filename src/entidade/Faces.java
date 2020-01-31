/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "faces")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faces.findAll", query = "SELECT f FROM Faces f")
    , @NamedQuery(name = "Faces.findById", query = "SELECT f FROM Faces f WHERE f.id = :id")
    , @NamedQuery(name = "Faces.findByDescricao", query = "SELECT f FROM Faces f WHERE f.descricao = :descricao")
    , @NamedQuery(name = "Faces.findByTipo", query = "SELECT f FROM Faces f WHERE f.tipo = :tipo")
    , @NamedQuery(name = "Faces.findByOrientacaoSolar", query = "SELECT f FROM Faces f WHERE f.orientacaoSolar = :orientacaoSolar")
    , @NamedQuery(name = "Faces.findByResistenciaTotal", query = "SELECT f FROM Faces f WHERE f.resistenciaTotal = :resistenciaTotal")
    , @NamedQuery(name = "Faces.findByTransmitancia", query = "SELECT f FROM Faces f WHERE f.transmitancia = :transmitancia")
    , @NamedQuery(name = "Faces.findByFatorSolar", query = "SELECT f FROM Faces f WHERE f.fatorSolar = :fatorSolar")
    , @NamedQuery(name = "Faces.findByArea", query = "SELECT f FROM Faces f WHERE f.area = :area")
    , @NamedQuery(name = "Faces.findByFluxoOpaco", query = "SELECT f FROM Faces f WHERE f.fluxoOpaco = :fluxoOpaco")
    , @NamedQuery(name = "Faces.findByFluxoTransparente", query = "SELECT f FROM Faces f WHERE f.fluxoTransparente = :fluxoTransparente")})

public class Faces implements Serializable {
    
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    
    @Basic(optional = false)
    @Column(name = "tipo") //0 = parede, 1 = cobertura 2 = laje inferior
    private int tipo;
    
    @Basic(optional = false)
    @Column(name = "orientacao_solar")
    private String orientacaoSolar;
    
    @JoinColumn(name = "ref_comodo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ComodosProjetos refComodo;
    
     @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "faces")
    private Collection<MateriaisFace> materiaisFaceCollection;
     
     @Column(name = "resistencia_total")
    private Double resistenciaTotal;
     
    @Column(name = "transmitancia")
    private Double transmitancia;
    
    @Column(name = "fator_solar")
    private Double fatorSolar;
    
    @Column(name = "fluxo_opaco")
    private Double fluxoOpaco;
    
    @Column(name = "fluxo_transparente")
    private Double fluxoTransparente;
    
    @Basic(optional = false)
    @Column(name = "area")
    private Double area;
    
    @Column(name = "carga_fluxo_opaco")
    private Double cargaFluxoOpaco;
    
    @Column(name = "carga_fluxo_transparente")
    private Double cargaFluxoTransparente;
    
    @Basic(optional = false)
    @Column(name = "absorvidade_tinta")
    private Double absorvidadeTinta;
    
    @Basic(optional = false)
    @Column(name = "radiacao_solar_ins")
    private Double radiacaoSolarIns;
     
    public Faces() {
    }

    public Faces(Integer id) {
        this.id = id;
    }

    public Faces(Integer id, String descricao, int tipo, String orientacaoSolar) {
        this.id = id;
        this.descricao = descricao;
        this.tipo = tipo;
        this.orientacaoSolar = orientacaoSolar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Double getResistenciaTotal() {
        return resistenciaTotal;
    }

    public void setResistenciaTotal(Double resistenciaTotal) {
        this.resistenciaTotal = resistenciaTotal;
    }

    @XmlTransient
    public Collection<MateriaisFace> getMateriaisFaceCollection() {
        return materiaisFaceCollection;
    }

    public void setMateriaisFaceCollection(Collection<MateriaisFace> materiaisFaceCollection) {
        this.materiaisFaceCollection = materiaisFaceCollection;
    }
    
    public String getOrientacaoSolar() {
        return orientacaoSolar;
    }

    public void setOrientacaoSolar(String orientacaoSolar) {
        this.orientacaoSolar = orientacaoSolar;
    }

    public Double getFluxoOpaco() {
        return fluxoOpaco;
    }

    public void setFluxoOpaco(Double fluxoOpaco) {
        this.fluxoOpaco = fluxoOpaco;
    }

    public Double getFluxoTransparente() {
        return fluxoTransparente;
    }

    public void setFluxoTransparente(Double fluxoTransparente) {
        this.fluxoTransparente = fluxoTransparente;
    }

    public Double getCargaFluxoOpaco() {
        return cargaFluxoOpaco;
    }

    public void setCargaFluxoOpaco(Double cargaFluxoOpaco) {
        this.cargaFluxoOpaco = cargaFluxoOpaco;
    }

    public Double getCargaFluxoTransparente() {
        return cargaFluxoTransparente;
    }

    public void setCargaFluxoTransparente(Double cargaFluxoTransparente) {
        this.cargaFluxoTransparente = cargaFluxoTransparente;
    }

    public ComodosProjetos getRefComodo() {
        return refComodo;
    }

    public void setRefComodo(ComodosProjetos refComodo) {
        this.refComodo = refComodo;
    }
    
    public Double getAbsorvidadeTinta()
    {
        return absorvidadeTinta;
    }

    public void setAbsorvidadeTinta(Double absorvidadeTinta)
    {
        this.absorvidadeTinta = absorvidadeTinta;
    }
    
    public Double getTransmitancia() {
        return transmitancia;
    }

    public void setTransmitancia(Double transmitancia) {
        this.transmitancia = transmitancia;
    }
    
    public Double getFatorSolar() {
        return fatorSolar;
    }

    public void setFatorSolar(Double fatorSolar) {
        this.fatorSolar = fatorSolar;
    }
    
    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }
    
    public Double getRadiacaoSolarIns() {
        return radiacaoSolarIns;
    }

    public void setRadiacaoSolarIns(Double radiacaoSolarIns) {
        this.radiacaoSolarIns = radiacaoSolarIns;
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
        if (!(object instanceof Faces)) {
            return false;
        }
        Faces other = (Faces) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Faces[ id=" + id + " ]";
    }
    
}
