/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.feedback;

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
@Table(name = "tbl_feedback")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblFeedback.findAll", query = "SELECT t FROM TblFeedback t"),
    @NamedQuery(name = "TblFeedback.findById", query = "SELECT t FROM TblFeedback t WHERE t.id = :id"),
    @NamedQuery(name = "TblFeedback.findByContent", query = "SELECT t FROM TblFeedback t WHERE t.content = :content"),
    @NamedQuery(name = "TblFeedback.findByPosdate", query = "SELECT t FROM TblFeedback t WHERE t.posdate = :posdate")})
public class TblFeedback implements Serializable {

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
    @Column(name = "posdate", length = 20)
    private String posdate;
    @JoinColumn(name = "nik", referencedColumnName = "nik", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblUser nik;

    public TblFeedback() {
    }

    public TblFeedback(Integer id) {
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

    public String getPosdate() {
        return posdate;
    }

    public void setPosdate(String posdate) {
        this.posdate = posdate;
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
        if (!(object instanceof TblFeedback)) {
            return false;
        }
        TblFeedback other = (TblFeedback) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.bachtiar.entity.TblFeedback[ id=" + id + " ]";
    }
    
}
