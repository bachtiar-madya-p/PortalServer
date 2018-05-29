/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.sa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "tbl_sa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblSa.findAll", query = "SELECT t FROM TblSa t"),
    @NamedQuery(name = "TblSa.findById", query = "SELECT t FROM TblSa t WHERE t.id = :id"),
    @NamedQuery(name = "TblSa.findByPwd", query = "SELECT t FROM TblSa t WHERE t.pwd = :pwd"),
    @NamedQuery(name = "TblSa.findByUname", query = "SELECT t FROM TblSa t WHERE t.uname = :uname")})
public class TblSa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 15)
    @Column(name = "pwd", length = 15)
    private String pwd;
    @Size(max = 15)
    @Column(name = "uname", length = 15)
    private String uname;

    public TblSa() {
    }

    public TblSa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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
        if (!(object instanceof TblSa)) {
            return false;
        }
        TblSa other = (TblSa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.bachtiar.entity.TblSa[ id=" + id + " ]";
    }
    
}
