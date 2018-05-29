/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.bpjs;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import rest.bachtiar.service.user.TblUser;

/**
 *
 * @author root
 */
@Entity
@Table(name = "tbl_bpjs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblBpjs.findAll", query = "SELECT t FROM TblBpjs t"),
    @NamedQuery(name = "TblBpjs.findById", query = "SELECT t FROM TblBpjs t WHERE t.id = :id"),
    @NamedQuery(name = "TblBpjs.findByDate", query = "SELECT t FROM TblBpjs t WHERE t.date = :date"),
    @NamedQuery(name = "TblBpjs.findByNokk", query = "SELECT t FROM TblBpjs t WHERE t.nokk = :nokk"),
    @NamedQuery(name = "TblBpjs.findByNoktp", query = "SELECT t FROM TblBpjs t WHERE t.noktp = :noktp"),
    @NamedQuery(name = "TblBpjs.findByDoc", query = "SELECT t FROM TblBpjs t WHERE t.doc = :doc"),
    @NamedQuery(name = "TblBpjs.findByStatus", query = "SELECT t FROM TblBpjs t WHERE t.status = :status")})
public class TblBpjs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 20)
    @Column(name = "date", length = 20)
    private String date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nokk", nullable = false, length = 20)
    private String nokk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "noktp", nullable = false, length = 20)
    private String noktp;
    @Size(max = 500)
    @Column(name = "doc", length = 500)
    private String doc;
    @Size(max = 12)
    @Column(name = "status", length = 12)
    private String status;
    @JoinColumn(name = "nik", referencedColumnName = "nik", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblUser nik;

    public TblBpjs() {
    }

    public TblBpjs(Integer id) {
        this.id = id;
    }

    public TblBpjs(Integer id, String nokk, String noktp) {
        this.id = id;
        this.nokk = nokk;
        this.noktp = noktp;
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

    public String getNokk() {
        return nokk;
    }

    public void setNokk(String nokk) {
        this.nokk = nokk;
    }

    public String getNoktp() {
        return noktp;
    }

    public void setNoktp(String noktp) {
        this.noktp = noktp;
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
        if (!(object instanceof TblBpjs)) {
            return false;
        }
        TblBpjs other = (TblBpjs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.bachtiar.service.bpjs.TblBpjs[ id=" + id + " ]";
    }
    
}
