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
@Table(name = "satisnovu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Satisnovu.findAll", query = "SELECT s FROM Satisnovu s"),
    @NamedQuery(name = "Satisnovu.findByIdSatisNovu", query = "SELECT s FROM Satisnovu s WHERE s.idSatisNovu = :idSatisNovu"),
    @NamedQuery(name = "Satisnovu.findByAd", query = "SELECT s FROM Satisnovu s WHERE s.ad = :ad")})
public class Satisnovu implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSatisNovu")
    private Collection<Kassa> kassaCollection;

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSatisNovu")
    private Integer idSatisNovu;
    @Basic(optional = false)
    @Column(name = "Ad")
    private String ad;

    public Satisnovu() {
    }

    public Satisnovu(Integer idSatisNovu) {
        this.idSatisNovu = idSatisNovu;
    }

    public Satisnovu(Integer idSatisNovu, String ad) {
        this.idSatisNovu = idSatisNovu;
        this.ad = ad;
    }

    public Integer getIdSatisNovu() {
        return idSatisNovu;
    }

    public void setIdSatisNovu(Integer idSatisNovu) {
        Integer oldIdSatisNovu = this.idSatisNovu;
        this.idSatisNovu = idSatisNovu;
        changeSupport.firePropertyChange("idSatisNovu", oldIdSatisNovu, idSatisNovu);
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
        hash += (idSatisNovu != null ? idSatisNovu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Satisnovu)) {
            return false;
        }
        Satisnovu other = (Satisnovu) object;
        if ((this.idSatisNovu == null && other.idSatisNovu != null) || (this.idSatisNovu != null && !this.idSatisNovu.equals(other.idSatisNovu))) {
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
    public Collection<Kassa> getKassaCollection() {
        return kassaCollection;
    }

    public void setKassaCollection(Collection<Kassa> kassaCollection) {
        this.kassaCollection = kassaCollection;
    }
    
}
