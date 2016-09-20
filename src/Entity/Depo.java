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
@Table(name = "depo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Depo.findAll", query = "SELECT d FROM Depo d"),
    @NamedQuery(name = "Depo.findByIdDepo", query = "SELECT d FROM Depo d WHERE d.idDepo = :idDepo"),
    @NamedQuery(name = "Depo.findByIdMallar", query = "SELECT d FROM Depo d WHERE d.idMallar = :idMallar"),
    @NamedQuery(name = "Depo.findBySayi", query = "SELECT d FROM Depo d WHERE d.sayi = :sayi")})
public class Depo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDepo")
    private Integer idDepo;
    @Column(name = "Sayi")
    private String sayi;
    @JoinColumn(name = "idMallar", referencedColumnName = "idMallar")
    @ManyToOne
    private Mallar idMallar;

    public Depo() {
    }

    public Depo(Integer idDepo) {
        this.idDepo = idDepo;
    }

    public Integer getIdDepo() {
        return idDepo;
    }

    public void setIdDepo(Integer idDepo) {
        this.idDepo = idDepo;
    }

    public String getSayi() {
        return sayi;
    }

    public void setSayi(String sayi) {
        this.sayi = sayi;
    }

    public Mallar getIdMallar() {
        return idMallar;
    }

    public void setIdMallar(Mallar idMallar) {
        this.idMallar = idMallar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDepo != null ? idDepo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Depo)) {
            return false;
        }
        Depo other = (Depo) object;
        if ((this.idDepo == null && other.idDepo != null) || (this.idDepo != null && !this.idDepo.equals(other.idDepo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Classes.Depo[ idDepo=" + idDepo + " ]";
    }
    
}
