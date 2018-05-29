/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.dept;

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
@Table(name = "tbl_dept")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblDept.findAll", query = "SELECT t FROM TblDept t"),
    @NamedQuery(name = "TblDept.findByDept", query = "SELECT t FROM TblDept t WHERE t.dept = :dept"),
    @NamedQuery(name = "TblDept.findByIdDept", query = "SELECT t FROM TblDept t WHERE t.idDept = :idDept")})
public class TblDept implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "dept", nullable = false, length = 30)
    private String dept;
    @Column(name = "id_dept")
    private Integer idDept;

    public TblDept() {
    }

    public TblDept(String dept) {
        this.dept = dept;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Integer getIdDept() {
        return idDept;
    }

    public void setIdDept(Integer idDept) {
        this.idDept = idDept;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dept != null ? dept.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDept)) {
            return false;
        }
        TblDept other = (TblDept) object;
        if ((this.dept == null && other.dept != null) || (this.dept != null && !this.dept.equals(other.dept))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.bachtiar.entity.TblDept[ dept=" + dept + " ]";
    }
    
}
