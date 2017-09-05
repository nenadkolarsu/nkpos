/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ms
 */
@Entity
@Table(name = "ptt_brojevi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PttBrojevi.findAll", query = "SELECT p FROM PttBrojevi p"),
    @NamedQuery(name = "PttBrojevi.findById", query = "SELECT p FROM PttBrojevi p WHERE p.id = :id"),
    @NamedQuery(name = "PttBrojevi.findBySifra", query = "SELECT p FROM PttBrojevi p WHERE p.sifra = :sifra"),
    @NamedQuery(name = "PttBrojevi.findByNaziv", query = "SELECT p FROM PttBrojevi p WHERE p.naziv = :naziv"),
    @NamedQuery(name = "PttBrojevi.findByDatumVreme", query = "SELECT p FROM PttBrojevi p WHERE p.datumVreme = :datumVreme"),
    @NamedQuery(name = "PttBrojevi.findByAktivan", query = "SELECT p FROM PttBrojevi p WHERE p.aktivan = :aktivan")})
public class MaticniPodaci implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "sifra")
    private String sifra;
    @Basic(optional = false)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @Column(name = "datum_vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @Basic(optional = false)
    @Column(name = "aktivan")
    private boolean aktivan;

    public MaticniPodaci() {
    }

    public MaticniPodaci(Integer id) {
        this.id = id;
    }
    public MaticniPodaci(Integer id, String sifra, String naziv) {
        this.id = id;
        this.sifra = sifra;
        this.naziv = naziv;
        this.datumVreme = datumVreme;
        this.aktivan = aktivan;
    }
    
    public MaticniPodaci(Integer id, String sifra, String naziv, Date datumVreme, boolean aktivan) {
        this.id = id;
        this.sifra = sifra;
        this.naziv = naziv;
        this.datumVreme = datumVreme;
        this.aktivan = aktivan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaticniPodaci)) {
            return false;
        }
        MaticniPodaci other = (MaticniPodaci) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PttBrojevi[ id=" + id + " ]";
    }
    
}
