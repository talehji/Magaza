/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pallas
 */
@Entity
@Table(name = "member")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Member.findAll", query = "SELECT m FROM Member m"),
    @NamedQuery(name = "Member.findByIdMember", query = "SELECT m FROM Member m WHERE m.idMember = :idMember"),
    @NamedQuery(name = "Member.findByAd", query = "SELECT m FROM Member m WHERE m.ad = :ad"),
    @NamedQuery(name = "Member.findByIsActive", query = "SELECT m FROM Member m WHERE m.isActive = :isActive"),
    @NamedQuery(name = "Member.findByPassword", query = "SELECT m FROM Member m WHERE m.password = :password"),
    @NamedQuery(name = "Member.findBySoyad", query = "SELECT m FROM Member m WHERE m.soyad = :soyad"),
    @NamedQuery(name = "Member.findByTelefon", query = "SELECT m FROM Member m WHERE m.telefon = :telefon")})
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMember")
    private Integer idMember;
    @Column(name = "Ad")
    private String ad;
    @Column(name = "isActive")
    private String isActive;
    @Column(name = "Password")
    private String password;
    @Column(name = "Soyad")
    private String soyad;
    @Column(name = "Telefon")
    private String telefon;
    @OneToMany(mappedBy = "idMember")
    private Collection<Kassa> kassaCollection;

    public Member() {
    }

    public Member(Integer idMember) {
        this.idMember = idMember;
    }

    public Integer getIdMember() {
        return idMember;
    }

    public void setIdMember(Integer idMember) {
        this.idMember = idMember;
    }

    public String getAd() {
        return ad;
    }

    public String getAdSoyad() {
        return ad+" "+soyad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @XmlTransient
    public Collection<Kassa> getKassaCollection() {
        return kassaCollection;
    }

    public void setKassaCollection(Collection<Kassa> kassaCollection) {
        this.kassaCollection = kassaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMember != null ? idMember.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Member)) {
            return false;
        }
        Member other = (Member) object;
        if ((this.idMember == null && other.idMember != null) || (this.idMember != null && !this.idMember.equals(other.idMember))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ad+" "+soyad;
    }
    
}
