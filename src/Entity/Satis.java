/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pallas
 */
@Entity
@Table(name = "satis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Satis.findAll", query = "SELECT s FROM Satis s"),
    @NamedQuery(name = "Satis.findByIdMallar", query = "SELECT s FROM Satis s WHERE s.idMallar = :idMallar"),
    @NamedQuery(name = "Satis.findByIdSatis", query = "SELECT s FROM Satis s WHERE s.idSatis = :idSatis")})
public class Satis implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSatis")
    private Integer idSatis;
    @JoinColumn(name = "idMallar", referencedColumnName = "idMallar")
    @ManyToOne(optional = false)
    private Mallar idMallar;
    @JoinColumn(name = "idKassa", referencedColumnName = "idKassa")
    @ManyToOne(optional = false)
    private Kassa idKassa;
    @JoinColumn(name = "idMusteri", referencedColumnName = "idMusteri")
    @ManyToOne(optional = false)
    private Musteri idMusteri;

    public Satis() {
    }

    public Satis(Integer idSatis) {
        this.idSatis = idSatis;
    }

    public Integer getIdSatis() {
        return idSatis;
    }

    public void setIdSatis(Integer idSatis) {
        Integer oldIdSatis = this.idSatis;
        this.idSatis = idSatis;
        changeSupport.firePropertyChange("idSatis", oldIdSatis, idSatis);
    }

    public Mallar getIdMallar() {
        return idMallar;
    }

    public void setIdMallar(Mallar idMallar) {
        Mallar oldIdMallar = this.idMallar;
        this.idMallar = idMallar;
        changeSupport.firePropertyChange("idMallar", oldIdMallar, idMallar);
    }

    public Kassa getIdKassa() {
        return idKassa;
    }

    public void setIdKassa(Kassa idKassa) {
        Kassa oldIdKassa = this.idKassa;
        this.idKassa = idKassa;
        changeSupport.firePropertyChange("idKassa", oldIdKassa, idKassa);
    }

    public Musteri getIdMusteri() {
        return idMusteri;
    }

    public void setIdMusteri(Musteri idMusteri) {
        Musteri oldIdMusteri = this.idMusteri;
        this.idMusteri = idMusteri;
        changeSupport.firePropertyChange("idMusteri", oldIdMusteri, idMusteri);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSatis != null ? idSatis.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Satis)) {
            return false;
        }
        Satis other = (Satis) object;
        if ((this.idSatis == null && other.idSatis != null) || (this.idSatis != null && !this.idSatis.equals(other.idSatis))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Connection.Satis[ idSatis=" + idSatis + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
