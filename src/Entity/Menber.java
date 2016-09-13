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
@Table(name = "menber")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menber.findAll", query = "SELECT m FROM Menber m"),
    @NamedQuery(name = "Menber.findByIdMenber", query = "SELECT m FROM Menber m WHERE m.idMenber = :idMenber"),
    @NamedQuery(name = "Menber.findByAd", query = "SELECT m FROM Menber m WHERE m.ad = :ad"),
    @NamedQuery(name = "Menber.findBySoyad", query = "SELECT m FROM Menber m WHERE m.soyad = :soyad"),
    @NamedQuery(name = "Menber.findByTelefon", query = "SELECT m FROM Menber m WHERE m.telefon = :telefon"),
    @NamedQuery(name = "Menber.findByPassword", query = "SELECT m FROM Menber m WHERE m.password = :password"),
    @NamedQuery(name = "Menber.findByUserPass", query = "SELECT m FROM Menber m WHERE m.idMenber = :idMenber and m.password = :password"),
    @NamedQuery(name = "Menber.findByIsActive", query = "SELECT m FROM Menber m WHERE m.isActive = :isActive")})
public class Menber implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMenber")
    private Collection<Kassa> kassaCollection;

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMenber")
    private Integer idMenber;
    @Basic(optional = false)
    @Column(name = "Ad")
    private String ad;
    @Basic(optional = false)
    @Column(name = "Soyad")
    private String soyad;
    @Basic(optional = false)
    @Column(name = "Telefon")
    private String telefon;
    @Basic(optional = false)
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @Column(name = "isActive")
    private String isActive;

    public Menber() {
    }

    public Menber(Integer idMenber) {
        this.idMenber = idMenber;
    }

    public Menber(Integer idMenber, String ad, String soyad, String telefon, String password, String isActive) {
        this.idMenber = idMenber;
        this.ad = ad;
        this.soyad = soyad;
        this.telefon = telefon;
        this.password = password;
        this.isActive = isActive;
    }

    public Integer getIdMenber() {
        return idMenber;
    }

    public void setIdMenber(Integer idMenber) {
        Integer oldIdMenber = this.idMenber;
        this.idMenber = idMenber;
        changeSupport.firePropertyChange("idMenber", oldIdMenber, idMenber);
    }

    public String getAd() {
        return ad;
    }
    public String getAdSoyad() {
        return ad+" "+soyad;
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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        String oldTelefon = this.telefon;
        this.telefon = telefon;
        changeSupport.firePropertyChange("telefon", oldTelefon, telefon);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String oldPassword = this.password;
        this.password = password;
        changeSupport.firePropertyChange("password", oldPassword, password);
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        String oldIsActive = this.isActive;
        this.isActive = isActive;
        changeSupport.firePropertyChange("isActive", oldIsActive, isActive);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenber != null ? idMenber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menber)) {
            return false;
        }
        Menber other = (Menber) object;
        if ((this.idMenber == null && other.idMenber != null) || (this.idMenber != null && !this.idMenber.equals(other.idMenber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ad+" "+soyad;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    @XmlTransient
    public Collection<Kassa> getKassaCollection() {
        return kassaCollection;
    }

    public void setKassaCollection(Collection<Kassa> kassaCollection) {
        this.kassaCollection = kassaCollection;
    }
    
}
