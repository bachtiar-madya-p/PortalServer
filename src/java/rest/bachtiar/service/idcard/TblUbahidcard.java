/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.idcard;

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
import rest.bachtiar.service.user.TblUser;

/**
 *
 * @author root
 */
@Entity
@Table(name = "tbl_ubahidcard")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblUbahidcard.findAll", query = "SELECT t FROM TblUbahidcard t"),
    @NamedQuery(name = "TblUbahidcard.findById", query = "SELECT t FROM TblUbahidcard t WHERE t.id = :id"),
    @NamedQuery(name = "TblUbahidcard.findByContent", query = "SELECT t FROM TblUbahidcard t WHERE t.content = :content"),
    @NamedQuery(name = "TblUbahidcard.findByDate", query = "SELECT t FROM TblUbahidcard t WHERE t.date = :date"),
    @NamedQuery(name = "TblUbahidcard.findByDoc", query = "SELECT t FROM TblUbahidcard t WHERE t.doc = :doc"),
    @NamedQuery(name = "TblUbahidcard.findByStatus", query = "SELECT t FROM TblUbahidcard t WHERE t.status = :status")})
public class TblUbahidcard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 500)
    @Column(name = "content", length = 500)
    private String content;
    @Size(max = 20)
    @Column(name = "date", length = 20)
    private String date;
    @Size(max = 255)
    @Column(name = "doc", length = 255)
    private String doc;
    @Size(max = 12)
    @Column(name = "status", length = 12)
    private String status;
    @JoinColumn(name = "nik", referencedColumnName = "nik", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblUser nik;

    public TblUbahidcard() {
    }

    public TblUbahidcard(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof TblUbahidcard)) {
            return false;
        }
        TblUbahidcard other = (TblUbahidcard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.bachtiar.entity.TblUbahidcard[ id=" + id + " ]";
    }
    
}
