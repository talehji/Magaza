/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
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
    @NamedQuery(name = "Mallar.findByCekisi", query = "SELECT m FROM Mallar m WHERE m.cekisi = :cekisi"),
    @NamedQuery(name = "Mallar.findByQiymeti", query = "SELECT m FROM Mallar m WHERE m.qiymeti = :qiymeti"),
    @NamedQuery(name = "Mallar.findByBarkod", query = "SELECT m FROM Mallar m WHERE m.barkod = :barkod"),
    @NamedQuery(name = "Mallar.findByIsActive", query = "SELECT m FROM Mallar m WHERE m.isActive = :isActive")})
public class Mallar implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMallar")
    private Collection<Satis> satisCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMallar")
    private Collection<Depo> depoCollection;

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

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
    @Column(name = "Cekisi")
    private String cekisi;
    @Basic(optional = false)
    @Column(name = "Qiymeti")
    private double qiymeti;
    @Basic(optional = false)
    @Column(name = "Barkod")
    private String barkod;
    @Basic(optional = false)
    @Column(name = "isActive")
    private String isActive;
    @JoinColumn(name = "idKateqoriya", referencedColumnName = "idKateqoriya")
    @ManyToOne(optional = false)
    private Kateqoriya idKateqoriya;

    public Mallar() {
    }

    public Mallar(Integer idMallar) {
        this.idMallar = idMallar;
    }

    public Mallar(Integer idMallar, String ad, String cekisi, double qiymeti, String barkod, String isActive) {
        this.idMallar = idMallar;
        this.ad = ad;
        this.cekisi = cekisi;
        this.qiymeti = qiymeti;
        this.barkod = barkod;
        this.isActive = isActive;
    }

    public Integer getIdMallar() {
        return idMallar;
    }

    public void setIdMallar(Integer idMallar) {
        Integer oldIdMallar = this.idMallar;
        this.idMallar = idMallar;
        changeSupport.firePropertyChange("idMallar", oldIdMallar, idMallar);
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        String oldAd = this.ad;
        this.ad = ad;
        changeSupport.firePropertyChange("ad", oldAd, ad);
    }

    public String getCekisi() {
        return cekisi;
    }

    public void setCekisi(String cekisi) {
        String oldCekisi = this.cekisi;
        this.cekisi = cekisi;
        changeSupport.firePropertyChange("cekisi", oldCekisi, cekisi);
    }

    public double getQiymeti() {
        return qiymeti;
    }

    public void setQiymeti(double qiymeti) {
        double oldQiymeti = this.qiymeti;
        this.qiymeti = qiymeti;
        changeSupport.firePropertyChange("qiymeti", oldQiymeti, qiymeti);
    }

    public String getBarkod() {
        return barkod;
    }

    public void setBarkod(String barkod) {
        String oldBarkod = this.barkod;
        this.barkod = barkod;
        changeSupport.firePropertyChange("barkod", oldBarkod, barkod);
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        String oldIsActive = this.isActive;
        this.isActive = isActive;
        changeSupport.firePropertyChange("isActive", oldIsActive, isActive);
    }

    public Kateqoriya getIdKateqoriya() {
        return idKateqoriya;
    }

    public void setIdKateqoriya(Kateqoriya idKateqoriya) {
        Kateqoriya oldIdKateqoriya = this.idKateqoriya;
        this.idKateqoriya = idKateqoriya;
        changeSupport.firePropertyChange("idKateqoriya", oldIdKateqoriya, idKateqoriya);
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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    @XmlTransient
    public Collection<Satis> getSatisCollection() {
        return satisCollection;
    }

    public void setSatisCollection(Collection<Satis> satisCollection) {
        this.satisCollection = satisCollection;
    }

    @XmlTransient
    public Collection<Depo> getDepoCollection() {
        return depoCollection;
    }

    public void setDepoCollection(Collection<Depo> depoCollection) {
        this.depoCollection = depoCollection;
    }
    
}
