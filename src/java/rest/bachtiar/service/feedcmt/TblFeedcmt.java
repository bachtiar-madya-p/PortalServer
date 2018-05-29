/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.feedcmt;

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
@Table(name = "tbl_feedcmt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblFeedcmt.findAll", query = "SELECT t FROM TblFeedcmt t"),
    @NamedQuery(name = "TblFeedcmt.findById", query = "SELECT t FROM TblFeedcmt t WHERE t.id = :id"),
    @NamedQuery(name = "TblFeedcmt.findByCmtcontent", query = "SELECT t FROM TblFeedcmt t WHERE t.cmtcontent = :cmtcontent"),
    @NamedQuery(name = "TblFeedcmt.findByCmtposdate", query = "SELECT t FROM TblFeedcmt t WHERE t.cmtposdate = :cmtposdate"),
    @NamedQuery(name = "TblFeedcmt.findByRoomId", query = "SELECT t FROM TblFeedcmt t WHERE t.roomId = :roomId")})
public class TblFeedcmt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
     private String count;
    @Size(max = 500)
    @Column(name = "cmtcontent", length = 500)
    private String cmtcontent;
    @Size(max = 20)
    @Column(name = "cmtposdate", length = 20)
    private String cmtposdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "room_id", nullable = false)
    private int roomId;
    @JoinColumn(name = "nik", referencedColumnName = "nik", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TblUser nik;

    public TblFeedcmt() {
    }

     public String getCount()
    {
        return count;
    }
    public void setCount(String count)
    {
       this.count = count;
    }
    public TblFeedcmt(Integer id) {
        this.id = id;
    }

    public TblFeedcmt(Integer id, int roomId) {
        this.id = id;
        this.roomId = roomId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCmtcontent() {
        return cmtcontent;
    }

    public void setCmtcontent(String cmtcontent) {
        this.cmtcontent = cmtcontent;
    }

    public String getCmtposdate() {
        return cmtposdate;
    }

    public void setCmtposdate(String cmtposdate) {
        this.cmtposdate = cmtposdate;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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
        if (!(object instanceof TblFeedcmt)) {
            return false;
        }
        TblFeedcmt other = (TblFeedcmt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.bachtiar.service.feedcmt.TblFeedcmt[ id=" + id + " ]";
    }
    
}
