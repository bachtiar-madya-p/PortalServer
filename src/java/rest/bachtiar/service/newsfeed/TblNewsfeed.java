/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.newsfeed;

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
@Table(name = "tbl_newsfeed")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblNewsfeed.findAll", query = "SELECT t FROM TblNewsfeed t"),
    @NamedQuery(name = "TblNewsfeed.findByRoomId", query = "SELECT t FROM TblNewsfeed t WHERE t.roomId = :roomId"),
    @NamedQuery(name = "TblNewsfeed.findByImgContent", query = "SELECT t FROM TblNewsfeed t WHERE t.imgContent = :imgContent"),
    @NamedQuery(name = "TblNewsfeed.findByPosdate", query = "SELECT t FROM TblNewsfeed t WHERE t.posdate = :posdate")})
public class TblNewsfeed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "room_id", nullable = false)
    private Integer roomId;
    @Lob
    @Size(max = 65535)
    @Column(name = "content", length = 65535)
    private String content;
    @Size(max = 255)
    @Column(name = "img_content", length = 255)
    private String imgContent;
    @Size(max = 20)
    @Column(name = "posdate", length = 20)
    private String posdate;
    @JoinColumn(name = "nik", referencedColumnName = "nik", nullable = false)
    @ManyToOne(optional = false)
    private String count;
    private TblUser nik;

    public TblNewsfeed() {
    }

    public TblNewsfeed(Integer roomId) {
        this.roomId = roomId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgContent() {
        return imgContent;
    }

    public void setImgContent(String imgContent) {
        this.imgContent = imgContent;
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
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblNewsfeed)) {
            return false;
        }
        TblNewsfeed other = (TblNewsfeed) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.bachtiar.service.newsfeed.TblNewsfeed[ roomId=" + roomId + " ]";
    }

}
