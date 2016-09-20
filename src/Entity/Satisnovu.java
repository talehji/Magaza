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
@Table(name = "satisnovu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Satisnovu.findAll", query = "SELECT s FROM Satisnovu s"),
    @NamedQuery(name = "Satisnovu.findByIdSatisNovu", query = "SELECT s FROM Satisnovu s WHERE s.idSatisNovu = :idSatisNovu"),
    @NamedQuery(name = "Satisnovu.findByAd", query = "SELECT s FROM Satisnovu s WHERE s.ad = :ad")})
public class Satisnovu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSatisNovu")
    private Integer idSatisNovu;
    @Column(name = "Ad")
    private String ad;
    @OneToMany(mappedBy = "idSatisNovu")
    private Collection<Kassa> kassaCollection;

    public Satisnovu() {
    }

    public Satisnovu(Integer idSatisNovu) {
        this.idSatisNovu = idSatisNovu;
    }

    public Integer getIdSatisNovu() {
        return idSatisNovu;
    }

    public void setIdSatisNovu(Integer idSatisNovu) {
        this.idSatisNovu = idSatisNovu;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
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
    
}
