/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

/**
 *
 * @author rrs
 */
@Embeddable
public class MateriaisFacePK implements Serializable {

        @Basic(optional = false)
        @Column(name = "faces_id")
        private int facesId;

        @Basic(optional = false)
        @Column(name = "materiais_id")
        private int materiaisId;
    
//      @ManyToOne(fetch = FetchType.EAGER)
//      @JoinColumn(name = "faces_id", nullable = false, insertable = false, updatable = false)
//      private Faces facesId;
//      
//      @ManyToOne(fetch = FetchType.EAGER)
//      @JoinColumn(name = "materiais_id", nullable = false, insertable = false, updatable = false)
//      private Materiais materiaisId;
    
    public MateriaisFacePK() {
    }

    public MateriaisFacePK(int facesId, int materiaisId) {
        this.facesId = facesId;
        this.materiaisId = materiaisId;
    }

    public int getFacesId() {
        return facesId;
    }

    public void setFacesId(int facesId) {
        this.facesId = facesId;
    }

    public int getMateriaisId() {
        return materiaisId;
    }

    public void setMateriaisId(int materiaisId) {
        this.materiaisId = materiaisId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) facesId;
        hash += (int) materiaisId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MateriaisFacePK)) {
            return false;
        }
        MateriaisFacePK other = (MateriaisFacePK) object;
        if (this.facesId != other.facesId) {
            return false;
        }
        if (this.materiaisId != other.materiaisId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.MateriaisFacePK[ facesId=" + facesId + ", materiaisId=" + materiaisId + " ]";
    }

    /**
     * @return the faces
     */
//    public Faces getFaces() {
//        return faces;
//    }
//
//    /**
//     * @param faces the faces to set
//     */
//    public void setFaces(Faces faces) {
//        this.faces = faces;
//    }
//
//    /**
//     * @return the materiais
//     */
//    public Materiais getMateriais() {
//        return materiais;
//    }
//
//    /**
//     * @param materiais the materiais to set
//     */
//    public void setMateriais(Materiais materiais) {
//        this.materiais = materiais;
//    }
    
}
