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
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pallas
 */
@Entity
@Table(name = "kassa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kassa.findAll", query = "SELECT k FROM Kassa k"),
    @NamedQuery(name = "Kassa.findByIdKassa", query = "SELECT k FROM Kassa k WHERE k.idKassa = :idKassa"),
    @NamedQuery(name = "Kassa.findByMedaxil", query = "SELECT k FROM Kassa k WHERE k.medaxil = :medaxil"),
    @NamedQuery(name = "Kassa.findByBorc", query = "SELECT k FROM Kassa k WHERE k.borc = :borc"),
    @NamedQuery(name = "Kassa.findByTarix", query = "SELECT k FROM Kassa k WHERE k.tarix = :tarix")})
public class Kassa implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKassa")
    private Integer idKassa;
    @Basic(optional = false)
    @Column(name = "Medaxil")
    private String medaxil;
    @Basic(optional = false)
    @Column(name = "Borc")
    private String borc;
    @Basic(optional = false)
    @Column(name = "Tarix")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tarix;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKassa")
    private Collection<Satis> satisCollection;
    @JoinColumn(name = "idSatisNovu", referencedColumnName = "idSatisNovu")
    @ManyToOne(optional = false)
    private Satisnovu idSatisNovu;
    @JoinColumn(name = "idMenber", referencedColumnName = "idMenber")
    @ManyToOne(optional = false)
    private Menber idMenber;

    public Kassa() {
    }

    public Kassa(Integer idKassa) {
        this.idKassa = idKassa;
    }

    public Kassa(Integer idKassa, String medaxil, String borc, Date tarix) {
        this.idKassa = idKassa;
        this.medaxil = medaxil;
        this.borc = borc;
        this.tarix = tarix;
    }

    public Integer getIdKassa() {
        return idKassa;
    }

    public void setIdKassa(Integer idKassa) {
        Integer oldIdKassa = this.idKassa;
        this.idKassa = idKassa;
        changeSupport.firePropertyChange("idKassa", oldIdKassa, idKassa);
    }

    public String getMedaxil() {
        return medaxil;
    }

    public void setMedaxil(String medaxil) {
        String oldMedaxil = this.medaxil;
        this.medaxil = medaxil;
        changeSupport.firePropertyChange("medaxil", oldMedaxil, medaxil);
    }

    public String getBorc() {
        return borc;
    }

    public void setBorc(String borc) {
        String oldBorc = this.borc;
        this.borc = borc;
        changeSupport.firePropertyChange("borc", oldBorc, borc);
    }

    public Date getTarix() {
        return tarix;
    }

    public void setTarix(Date tarix) {
        Date oldTarix = this.tarix;
        this.tarix = tarix;
        changeSupport.firePropertyChange("tarix", oldTarix, tarix);
    }

    @XmlTransient
    public Collection<Satis> getSatisCollection() {
        return satisCollection;
    }

    public void setSatisCollection(Collection<Satis> satisCollection) {
        this.satisCollection = satisCollection;
    }

    public Satisnovu getIdSatisNovu() {
        return idSatisNovu;
    }

    public void setIdSatisNovu(Satisnovu idSatisNovu) {
        Satisnovu oldIdSatisNovu = this.idSatisNovu;
        this.idSatisNovu = idSatisNovu;
        changeSupport.firePropertyChange("idSatisNovu", oldIdSatisNovu, idSatisNovu);
    }

    public Menber getIdMenber() {
        return idMenber;
    }

    public void setIdMenber(Menber idMenber) {
        Menber oldIdMenber = this.idMenber;
        this.idMenber = idMenber;
        changeSupport.firePropertyChange("idMenber", oldIdMenber, idMenber);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKassa != null ? idKassa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kassa)) {
            return false;
        }
        Kassa other = (Kassa) object;
        if ((this.idKassa == null && other.idKassa != null) || (this.idKassa != null && !this.idKassa.equals(other.idKassa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Connection.Kassa[ idKassa=" + idKassa + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
