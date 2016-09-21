/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

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
    @NamedQuery(name = "Satis.findByIdKassa", query = "SELECT s FROM Satis s WHERE s.idKassa = :idKassa"),
    @NamedQuery(name = "Satis.findByIdSatis", query = "SELECT s FROM Satis s WHERE s.idSatis = :idSatis")})
public class Satis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSatis")
    private Integer idSatis;
    @JoinColumn(name = "idKassa", referencedColumnName = "idKassa")
    @ManyToOne
    private Kassa idKassa;
    @JoinColumn(name = "idMallar", referencedColumnName = "idMallar")
    @ManyToOne
    private Mallar idMallar;
    @JoinColumn(name = "idMusteri", referencedColumnName = "idMusteri")
    @ManyToOne
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
        this.idSatis = idSatis;
    }

    public Kassa getIdKassa() {
        return idKassa;
    }

    public void setIdKassa(Kassa idKassa) {
        this.idKassa = idKassa;
    }

    public Mallar getIdMallar() {
        return idMallar;
    }

    public void setIdMallar(Mallar idMallar) {
        this.idMallar = idMallar;
    }

    public Musteri getIdMusteri() {
        return idMusteri;
    }

    public void setIdMusteri(Musteri idMusteri) {
        this.idMusteri = idMusteri;
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
        return "Entity.Satis[ idSatis=" + idSatis + " ]";
    }
    
}
