/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.plant;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "tbl_plant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblPlant.findAll", query = "SELECT t FROM TblPlant t"),
    @NamedQuery(name = "TblPlant.findByPlant", query = "SELECT t FROM TblPlant t WHERE t.plant = :plant"),
    @NamedQuery(name = "TblPlant.findByIdPlant", query = "SELECT t FROM TblPlant t WHERE t.idPlant = :idPlant")})
public class TblPlant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "plant", nullable = false, length = 30)
    private String plant;
    @Column(name = "id_plant")
    private Integer idPlant;

    public TblPlant() {
    }

    public TblPlant(String plant) {
        this.plant = plant;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public Integer getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(Integer idPlant) {
        this.idPlant = idPlant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (plant != null ? plant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPlant)) {
            return false;
        }
        TblPlant other = (TblPlant) object;
        if ((this.plant == null && other.plant != null) || (this.plant != null && !this.plant.equals(other.plant))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.bachtiar.entity.TblPlant[ plant=" + plant + " ]";
    }
    
}
