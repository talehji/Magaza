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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pallas
 */
@Entity
@Table(name = "setting")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Setting.findAll", query = "SELECT s FROM Setting s"),
    @NamedQuery(name = "Setting.findByIdSetting", query = "SELECT s FROM Setting s WHERE s.idSetting = :idSetting")})
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSetting")
    private Integer idSetting;
    @Basic(optional = false)
    @Lob
    @Column(name = "Data")
    private String data;

    public Setting() {
    }

    public Setting(Integer idSetting) {
        this.idSetting = idSetting;
    }

    public Setting(Integer idSetting, String data) {
        this.idSetting = idSetting;
        this.data = data;
    }

    public Integer getIdSetting() {
        return idSetting;
    }

    public void setIdSetting(Integer idSetting) {
        this.idSetting = idSetting;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSetting != null ? idSetting.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Setting)) {
            return false;
        }
        Setting other = (Setting) object;
        if ((this.idSetting == null && other.idSetting != null) || (this.idSetting != null && !this.idSetting.equals(other.idSetting))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Setting[ idSetting=" + idSetting + " ]";
    }
    
}
