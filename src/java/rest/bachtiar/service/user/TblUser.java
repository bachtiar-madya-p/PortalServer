/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.bachtiar.service.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import rest.bachtiar.service.beasiswa.TblBeasiswa;
import rest.bachtiar.service.newsfeed.TblNewsfeed;

/**
 *
 * @author root
 */
@Entity
@Table(name = "tbl_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblUser.findAll", query = "SELECT t FROM TblUser t"),
    @NamedQuery(name = "TblUser.findByNik", query = "SELECT t FROM TblUser t WHERE t.nik = :nik"),
    @NamedQuery(name = "TblUser.findByDept", query = "SELECT t FROM TblUser t WHERE t.dept = :dept"),
    @NamedQuery(name = "TblUser.findByEmail", query = "SELECT t FROM TblUser t WHERE t.email = :email"),
    @NamedQuery(name = "TblUser.findByImgUser", query = "SELECT t FROM TblUser t WHERE t.imgUser = :imgUser"),
    @NamedQuery(name = "TblUser.findByName", query = "SELECT t FROM TblUser t WHERE t.name = :name"),
    @NamedQuery(name = "TblUser.findByPhone", query = "SELECT t FROM TblUser t WHERE t.phone = :phone"),
    @NamedQuery(name = "TblUser.findByPlant", query = "SELECT t FROM TblUser t WHERE t.plant = :plant"),
    @NamedQuery(name = "TblUser.findByPwd", query = "SELECT t FROM TblUser t WHERE t.pwd = :pwd"),
    @NamedQuery(name = "TblUser.findByRegDate", query = "SELECT t FROM TblUser t WHERE t.regDate = :regDate"),
    @NamedQuery(name = "TblUser.findByTag", query = "SELECT t FROM TblUser t WHERE t.tag = :tag")})
public class TblUser implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nik")
    private Collection<TblBeasiswa> tblBeasiswaCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nik")
    private List<TblNewsfeed> tblNewsfeedList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "nik", nullable = false)
    private Integer nik;
    @Size(max = 30)
    @Column(name = "dept", length = 30)
    private String dept;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;
    @Size(max = 255)
    @Column(name = "img_user", length = 255)
    private String imgUser;
    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;
    @Size(max = 30)
    @Column(name = "plant", length = 30)
    private String plant;
    @Size(max = 25)
    @Column(name = "pwd", length = 25)
    private String pwd;
    @Size(max = 20)
    @Column(name = "reg_date", length = 20)
    private String regDate;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "gcm_token", nullable = false, length = 65535)
    private String gcmToken;
    @Size(max = 10)
    @Column(name = "tag", length = 10)
    private String tag;

    public TblUser() {
    }

    public TblUser(Integer nik) {
        this.nik = nik;
    }

    public TblUser(Integer nik, String gcmToken) {
        this.nik = nik;
        this.gcmToken = gcmToken;
    }

    public Integer getNik() {
        return nik;
    }

    public void setNik(Integer nik) {
        this.nik = nik;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nik != null ? nik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblUser)) {
            return false;
        }
        TblUser other = (TblUser) object;
        if ((this.nik == null && other.nik != null) || (this.nik != null && !this.nik.equals(other.nik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.bachtiar.service.user.TblUser[ nik=" + nik + " ]";
    }

    @XmlTransient
    public List<TblNewsfeed> getTblNewsfeedList() {
        return tblNewsfeedList;
    }

    public void setTblNewsfeedList(List<TblNewsfeed> tblNewsfeedList) {
        this.tblNewsfeedList = tblNewsfeedList;
    }

    @XmlTransient
    public Collection<TblBeasiswa> getTblBeasiswaCollection() {
        return tblBeasiswaCollection;
    }

    public void setTblBeasiswaCollection(Collection<TblBeasiswa> tblBeasiswaCollection) {
        this.tblBeasiswaCollection = tblBeasiswaCollection;
    }
    
}
