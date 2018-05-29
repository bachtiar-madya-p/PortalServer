/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.rollpwd;

import rest.bachtiar.service.user.TblUser;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tbl_roolback_pwd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblRoolbackPwd.findAll", query = "SELECT t FROM TblRoolbackPwd t"),
    @NamedQuery(name = "TblRoolbackPwd.findById", query = "SELECT t FROM TblRoolbackPwd t WHERE t.id = :id"),
    @NamedQuery(name = "TblRoolbackPwd.findByDate", query = "SELECT t FROM TblRoolbackPwd t WHERE t.date = :date"),
    @NamedQuery(name = "TblRoolbackPwd.findByTag", query = "SELECT t FROM TblRoolbackPwd t WHERE t.tag = :tag")})
public class TblRoolbackPwd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 20)
    @Column(name = "date", length = 20)
    private String date;
    @Size(max = 10)
    @Column(name = "tag", length = 10)
    private String tag;
    @JoinColumn(name = "nik", referencedColumnName = "nik", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblUser nik;

    public TblRoolbackPwd() {
    }

    public TblRoolbackPwd(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public TblUser getNik() {
        return nik;
    }

    public void setNik(TblUser nik) {
        this.nik = nik;
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
        if (!(object instanceof TblRoolbackPwd)) {
            return false;
        }
        TblRoolbackPwd other = (TblRoolbackPwd) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.bachtiar.entity.TblRoolbackPwd[ id=" + id + " ]";
    }
    
}
