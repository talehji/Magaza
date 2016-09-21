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
@Table(name = "musteri")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Musteri.findAll", query = "SELECT m FROM Musteri m"),
    @NamedQuery(name = "Musteri.findByIdMusteri", query = "SELECT m FROM Musteri m WHERE m.idMusteri = :idMusteri"),
    @NamedQuery(name = "Musteri.findByAd", query = "SELECT m FROM Musteri m WHERE m.ad = :ad"),
    @NamedQuery(name = "Musteri.findByQeyd", query = "SELECT m FROM Musteri m WHERE m.qeyd = :qeyd"),
    @NamedQuery(name = "Musteri.findBySoyad", query = "SELECT m FROM Musteri m WHERE m.soyad = :soyad"),
    @NamedQuery(name = "Musteri.findByTelefon", query = "SELECT m FROM Musteri m WHERE m.telefon = :telefon")})
public class Musteri implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMusteri")
    private Integer idMusteri;
    @Column(name = "Ad")
    private String ad;
    @Column(name = "Qeyd")
    private String qeyd;
    @Column(name = "Soyad")
    private String soyad;
    @Column(name = "Telefon")
    private String telefon;
    @OneToMany(mappedBy = "idMusteri")
    private Collection<Satis> satisCollection;
    @OneToMany(mappedBy = "idMusteri")
    private Collection<Kassa> kassaCollection;

    public Musteri() {
    }

    public Musteri(Integer idMusteri) {
        this.idMusteri = idMusteri;
    }

    public Integer getIdMusteri() {
        return idMusteri;
    }

    public void setIdMusteri(Integer idMusteri) {
        this.idMusteri = idMusteri;
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

    public String getQeyd() {
        return qeyd;
    }

    public void setQeyd(String qeyd) {
        this.qeyd = qeyd;
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
    public Collection<Satis> getSatisCollection() {
        return satisCollection;
    }

    public void setSatisCollection(Collection<Satis> satisCollection) {
        this.satisCollection = satisCollection;
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
        hash += (idMusteri != null ? idMusteri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Musteri)) {
            return false;
        }
        Musteri other = (Musteri) object;
        if ((this.idMusteri == null && other.idMusteri != null) || (this.idMusteri != null && !this.idMusteri.equals(other.idMusteri))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ad+" "+soyad;
    }
    
}
