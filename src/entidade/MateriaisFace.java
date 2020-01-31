/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.lang.Double;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
@Table(name = "materiais_face")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MateriaisFace.findAll", query = "SELECT m FROM MateriaisFace m")
    , @NamedQuery(name = "MateriaisFace.findByFacesId", query = "SELECT m FROM MateriaisFace m WHERE m.materiaisFacePK.facesId = :faces")
    , @NamedQuery(name = "MateriaisFace.findByMateriaisId", query = "SELECT m FROM MateriaisFace m WHERE m.materiaisFacePK.materiaisId = :materiais")
    , @NamedQuery(name = "MateriaisFace.findByResistencia", query = "SELECT m FROM MateriaisFace m WHERE m.resistencia = :resistencia")
    , @NamedQuery(name = "MateriaisFace.findByEspessura", query = "SELECT m FROM MateriaisFace m WHERE m.espessura = :espessura")
//    , @NamedQuery(name = "MateriaisFace.findByFace", query = "SELECT m FROM MateriaisFace m WHERE m.faces:face")
    , @NamedQuery(name = "MateriaisFace.findByMateriaisFace", query = "SELECT mf FROM MateriaisFace mf, Faces f WHERE f.id = :face")})
public class MateriaisFace implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String montaMateriaisFace = "MateriaisFace.findByMateriaisFace";
//    public static final String procuraMateriaisFace = "MateriaisFace.findByFace";
    
//  @GeneratedValue 
    @EmbeddedId
    protected MateriaisFacePK materiaisFacePK;
    
    @JoinColumn(name = "faces_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Faces faces;
    
    @JoinColumn(name = "materiais_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materiais materiais;
    
     @Column(name = "resistencia")
    private Double resistencia;
    
    @Column(name = "espessura")
    private Double espessura;

    public MateriaisFace() {
    }

    public MateriaisFace(MateriaisFacePK materiaisFacePK) {
        this.materiaisFacePK = materiaisFacePK;
    }

     public MateriaisFace(Faces faces, Materiais materiais) {
        this.materiaisFacePK = new MateriaisFacePK();
    }

    public MateriaisFacePK getMateriaisFacePK() {
        return materiaisFacePK;
    }

    public void setMateriaisFacePK(MateriaisFacePK materiaisFacePK) {
        this.materiaisFacePK = materiaisFacePK;
    }

    public Faces getFaces() {
        return faces;
    }

    public void setFaces(Faces faces) {
        this.faces = faces;
    }

    public Materiais getMateriais() {
        return materiais;
    }

    public void setMateriais(Materiais materiais) {
        this.materiais = materiais;
    }
    
    public Double getResistencia() {
        return resistencia;
    }

    public void setResistencia(Double resistencia) {
        this.resistencia = resistencia;
    }

    public Double getEspessura() {
        return espessura;
    }

    public void setEspessura(Double espessura) {
        this.espessura = espessura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materiaisFacePK != null ? materiaisFacePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MateriaisFace)) {
            return false;
        }
        MateriaisFace other = (MateriaisFace) object;
        if ((this.materiaisFacePK == null && other.materiaisFacePK != null) || (this.materiaisFacePK != null && !this.materiaisFacePK.equals(other.materiaisFacePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.MateriaisFace[ materiaisFacePK=" + materiaisFacePK + " ]";
    }
    
}
