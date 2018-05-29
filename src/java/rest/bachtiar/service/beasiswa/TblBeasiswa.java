/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.beasiswa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "tbl_beasiswa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblBeasiswa.findAll", query = "SELECT t FROM TblBeasiswa t"),
    @NamedQuery(name = "TblBeasiswa.findById", query = "SELECT t FROM TblBeasiswa t WHERE t.id = :id"),
    @NamedQuery(name = "TblBeasiswa.findByDate", query = "SELECT t FROM TblBeasiswa t WHERE t.date = :date"),
    @NamedQuery(name = "TblBeasiswa.findByDoc", query = "SELECT t FROM TblBeasiswa t WHERE t.doc = :doc"),
    @NamedQuery(name = "TblBeasiswa.findByNmAnak", query = "SELECT t FROM TblBeasiswa t WHERE t.nmAnak = :nmAnak"),
    @NamedQuery(name = "TblBeasiswa.findByStatus", query = "SELECT t FROM TblBeasiswa t WHERE t.status = :status"),
    @NamedQuery(name = "TblBeasiswa.findByTtl", query = "SELECT t FROM TblBeasiswa t WHERE t.ttl = :ttl")})
public class TblBeasiswa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 20)
    @Column(name = "date", length = 20)
    private String date;
    @Size(max = 500)
    @Column(name = "doc", length = 500)
    private String doc;
    @Size(max = 50)
    @Column(name = "nm_anak", length = 50)
    private String nmAnak;
    @Size(max = 10)
    @Column(name = "status", length = 10)
    private String status;
    @Lob
    @Size(max = 65535)
    @Column(name = "note", length = 65535)
    private String note;
    @Size(max = 20)
    @Column(name = "ttl", length = 20)
    private String ttl;
    @JoinColumn(name = "nik", referencedColumnName = "nik", nullable = false)
    @ManyToOne(optional = false)
    private TblUser nik;

    public TblBeasiswa() {
    }

    public TblBeasiswa(Integer id) {
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

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getNmAnak() {
        return nmAnak;
    }

    public void setNmAnak(String nmAnak) {
        this.nmAnak = nmAnak;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
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
        if (!(object instanceof TblBeasiswa)) {
            return false;
        }
        TblBeasiswa other = (TblBeasiswa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.bachtiar.service.beasiswa.TblBeasiswa[ id=" + id + " ]";
    }
    
}
