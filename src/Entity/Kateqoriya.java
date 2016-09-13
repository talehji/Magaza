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
@Table(name = "kateqoriya")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kateqoriya.findAll", query = "SELECT k FROM Kateqoriya k"),
    @NamedQuery(name = "Kateqoriya.findByIdKateqoriya", query = "SELECT k FROM Kateqoriya k WHERE k.idKateqoriya = :idKateqoriya"),
    @NamedQuery(name = "Kateqoriya.findByAd", query = "SELECT k FROM Kateqoriya k WHERE k.ad = :ad")})
public class Kateqoriya implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKateqoriya")
    private Collection<Mallar> mallarCollection;

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKateqoriya")
    private Integer idKateqoriya;
    @Basic(optional = false)
    @Column(name = "Ad")
    private String ad;

    public Kateqoriya() {
    }

    public Kateqoriya(Integer idKateqoriya) {
        this.idKateqoriya = idKateqoriya;
    }

    public Kateqoriya(Integer idKateqoriya, String ad) {
        this.idKateqoriya = idKateqoriya;
        this.ad = ad;
    }

    public Integer getIdKateqoriya() {
        return idKateqoriya;
    }

    public void setIdKateqoriya(Integer idKateqoriya) {
        Integer oldIdKateqoriya = this.idKateqoriya;
        this.idKateqoriya = idKateqoriya;
        changeSupport.firePropertyChange("idKateqoriya", oldIdKateqoriya, idKateqoriya);
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        String oldAd = this.ad;
        this.ad = ad;
        changeSupport.firePropertyChange("ad", oldAd, ad);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKateqoriya != null ? idKateqoriya.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kateqoriya)) {
            return false;
        }
        Kateqoriya other = (Kateqoriya) object;
        if ((this.idKateqoriya == null && other.idKateqoriya != null) || (this.idKateqoriya != null && !this.idKateqoriya.equals(other.idKateqoriya))) {
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
    public Collection<Mallar> getMallarCollection() {
        return mallarCollection;
    }

    public void setMallarCollection(Collection<Mallar> mallarCollection) {
        this.mallarCollection = mallarCollection;
    }
    
}
