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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "mallar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mallar.findAll", query = "SELECT m FROM Mallar m"),
    @NamedQuery(name = "Mallar.findByIdMallar", query = "SELECT m FROM Mallar m WHERE m.idMallar = :idMallar"),
    @NamedQuery(name = "Mallar.findByAd", query = "SELECT m FROM Mallar m WHERE m.ad = :ad"),
    @NamedQuery(name = "Mallar.findByBarkod", query = "SELECT m FROM Mallar m WHERE m.barkod = :barkod"),
    @NamedQuery(name = "Mallar.findByCekisi", query = "SELECT m FROM Mallar m WHERE m.cekisi = :cekisi"),
    @NamedQuery(name = "Mallar.findByAlisQiymeti", query = "SELECT m FROM Mallar m WHERE m.alisQiymeti = :alisQiymeti"),
    @NamedQuery(name = "Mallar.findBySatisQiymeti", query = "SELECT m FROM Mallar m WHERE m.satisQiymeti = :satisQiymeti"),
    @NamedQuery(name = "Mallar.findByIsActive", query = "SELECT m FROM Mallar m WHERE m.isActive = :isActive")})
public class Mallar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMallar")
    private Integer idMallar;
    @Basic(optional = false)
    @Column(name = "Ad")
    private String ad;
    @Basic(optional = false)
    @Column(name = "Barkod")
    private String barkod;
    @Basic(optional = false)
    @Column(name = "Cekisi")
    private String cekisi;
    @Basic(optional = false)
    @Column(name = "AlisQiymeti")
    private double alisQiymeti;
    @Basic(optional = false)
    @Column(name = "SatisQiymeti")
    private double satisQiymeti;
    @Basic(optional = false)
    @Column(name = "isActive")
    private String isActive;
    @OneToMany(mappedBy = "idMallar")
    private Collection<Satis> satisCollection;
    @JoinColumn(name = "idKateqoriya", referencedColumnName = "idKateqoriya")
    @ManyToOne(optional = false)
    private Kateqoriya idKateqoriya;
    @OneToMany(mappedBy = "idMallar")
    private Collection<Depo> depoCollection;

    public Mallar() {
    }

    public Mallar(Integer idMallar) {
        this.idMallar = idMallar;
    }

    public Mallar(Integer idMallar, String ad, String barkod, String cekisi, double alisQiymeti, double satisQiymeti, String isActive) {
        this.idMallar = idMallar;
        this.ad = ad;
        this.barkod = barkod;
        this.cekisi = cekisi;
        this.alisQiymeti = alisQiymeti;
        this.satisQiymeti = satisQiymeti;
        this.isActive = isActive;
    }

    public Integer getIdMallar() {
        return idMallar;
    }

    public void setIdMallar(Integer idMallar) {
        this.idMallar = idMallar;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getBarkod() {
        return barkod;
    }

    public void setBarkod(String barkod) {
        this.barkod = barkod;
    }

    public String getCekisi() {
        return cekisi;
    }

    public void setCekisi(String cekisi) {
        this.cekisi = cekisi;
    }

    public double getAlisQiymeti() {
        return alisQiymeti;
    }

    public void setAlisQiymeti(double alisQiymeti) {
        this.alisQiymeti = alisQiymeti;
    }

    public double getSatisQiymeti() {
        return satisQiymeti;
    }

    public void setSatisQiymeti(double satisQiymeti) {
        this.satisQiymeti = satisQiymeti;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public Collection<Satis> getSatisCollection() {
        return satisCollection;
    }

    public void setSatisCollection(Collection<Satis> satisCollection) {
        this.satisCollection = satisCollection;
    }

    public Kateqoriya getIdKateqoriya() {
        return idKateqoriya;
    }

    public void setIdKateqoriya(Kateqoriya idKateqoriya) {
        this.idKateqoriya = idKateqoriya;
    }

    @XmlTransient
    public Collection<Depo> getDepoCollection() {
        return depoCollection;
    }

    public void setDepoCollection(Collection<Depo> depoCollection) {
        this.depoCollection = depoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMallar != null ? idMallar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mallar)) {
            return false;
        }
        Mallar other = (Mallar) object;
        if ((this.idMallar == null && other.idMallar != null) || (this.idMallar != null && !this.idMallar.equals(other.idMallar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ad;
    }
    
}
