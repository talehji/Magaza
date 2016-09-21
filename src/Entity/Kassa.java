/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Kassa.findByBorc", query = "SELECT k FROM Kassa k WHERE k.borc = :borc"),
    @NamedQuery(name = "Kassa.findByMedaxil", query = "SELECT k FROM Kassa k WHERE k.medaxil = :medaxil"),
    @NamedQuery(name = "Kassa.findByIdMusteri", query = "SELECT k FROM Kassa k WHERE k.idMusteri = :idMusteri"),
    @NamedQuery(name = "Kassa.findByTarix", query = "SELECT k FROM Kassa k WHERE k.tarix = :tarix")})
public class Kassa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKassa")
    private Integer idKassa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Borc")
    private Double borc;
    @Column(name = "Medaxil")
    private Double medaxil;
    @Column(name = "Tarix")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tarix;
    @OneToMany(mappedBy = "idKassa")
    private Collection<Satis> satisCollection;
    @JoinColumn(name = "idMusteri", referencedColumnName = "idMusteri")
    @ManyToOne
    private Musteri idMusteri;
    @JoinColumn(name = "idMember", referencedColumnName = "idMember")
    @ManyToOne
    private Member idMember;
    @JoinColumn(name = "idSatisNovu", referencedColumnName = "idSatisNovu")
    @ManyToOne
    private Satisnovu idSatisNovu;

    public Kassa() {
    }

    public Kassa(Integer idKassa) {
        this.idKassa = idKassa;
    }

    public Integer getIdKassa() {
        return idKassa;
    }

    public void setIdKassa(Integer idKassa) {
        this.idKassa = idKassa;
    }

    public Double getBorc() {
        return borc;
    }

    public void setBorc(Double borc) {
        this.borc = borc;
    }

    public Double getMedaxil() {
        return medaxil;
    }

    public void setMedaxil(Double medaxil) {
        this.medaxil = medaxil;
    }

    public Date getTarix() {
        return tarix;
    }

    public void setTarix(Date tarix) {
        this.tarix = tarix;
    }

    @XmlTransient
    public Collection<Satis> getSatisCollection() {
        return satisCollection;
    }

    public void setSatisCollection(Collection<Satis> satisCollection) {
        this.satisCollection = satisCollection;
    }

    public Musteri getIdMusteri() {
        return idMusteri;
    }

    public void setIdMusteri(Musteri idMusteri) {
        this.idMusteri = idMusteri;
    }

    public Member getIdMember() {
        return idMember;
    }

    public void setIdMember(Member idMember) {
        this.idMember = idMember;
    }

    public Satisnovu getIdSatisNovu() {
        return idSatisNovu;
    }

    public void setIdSatisNovu(Satisnovu idSatisNovu) {
        this.idSatisNovu = idSatisNovu;
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
        return "Entity.Kassa[ idKassa=" + idKassa + " ]";
    }
    
}
