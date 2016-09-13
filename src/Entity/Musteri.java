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
@Table(name = "musteri")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Musteri.findAll", query = "SELECT m FROM Musteri m"),
    @NamedQuery(name = "Musteri.findByIdMusteri", query = "SELECT m FROM Musteri m WHERE m.idMusteri = :idMusteri"),
    @NamedQuery(name = "Musteri.findByAd", query = "SELECT m FROM Musteri m WHERE m.ad = :ad"),
    @NamedQuery(name = "Musteri.findBySoyad", query = "SELECT m FROM Musteri m WHERE m.soyad = :soyad"),
    @NamedQuery(name = "Musteri.findByQeyd", query = "SELECT m FROM Musteri m WHERE m.qeyd = :qeyd"),
    @NamedQuery(name = "Musteri.findByTelefon", query = "SELECT m FROM Musteri m WHERE m.telefon = :telefon")})
public class Musteri implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMusteri")
    private Collection<Satis> satisCollection;

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMusteri")
    private Integer idMusteri;
    @Basic(optional = false)
    @Column(name = "Ad")
    private String ad;
    @Basic(optional = false)
    @Column(name = "Soyad")
    private String soyad;
    @Basic(optional = false)
    @Column(name = "Qeyd")
    private String qeyd;
    @Basic(optional = false)
    @Column(name = "Telefon")
    private String telefon;

    public Musteri() {
    }

    public Musteri(Integer idMusteri) {
        this.idMusteri = idMusteri;
    }

    public Musteri(Integer idMusteri, String ad, String soyad, String qeyd, String telefon) {
        this.idMusteri = idMusteri;
        this.ad = ad;
        this.soyad = soyad;
        this.qeyd = qeyd;
        this.telefon = telefon;
    }

    public Integer getIdMusteri() {
        return idMusteri;
    }

    public void setIdMusteri(Integer idMusteri) {
        Integer oldIdMusteri = this.idMusteri;
        this.idMusteri = idMusteri;
        changeSupport.firePropertyChange("idMusteri", oldIdMusteri, idMusteri);
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        String oldAd = this.ad;
        this.ad = ad;
        changeSupport.firePropertyChange("ad", oldAd, ad);
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        String oldSoyad = this.soyad;
        this.soyad = soyad;
        changeSupport.firePropertyChange("soyad", oldSoyad, soyad);
    }

    public String getQeyd() {
        return qeyd;
    }

    public void setQeyd(String qeyd) {
        String oldQeyd = this.qeyd;
        this.qeyd = qeyd;
        changeSupport.firePropertyChange("qeyd", oldQeyd, qeyd);
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        String oldTelefon = this.telefon;
        this.telefon = telefon;
        changeSupport.firePropertyChange("telefon", oldTelefon, telefon);
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
        return "Connection.Musteri[ idMusteri=" + idMusteri + " ]";
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
    
}
