/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
 * @author Pionir SU
 */
@Entity
@Table(name = "drzave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Drzave.findAll", query = "SELECT d FROM Drzave d")
    , @NamedQuery(name = "Drzave.findById", query = "SELECT d FROM Drzave d WHERE d.id = :id")
    , @NamedQuery(name = "Drzave.findBySifra", query = "SELECT d FROM Drzave d WHERE d.sifra = :sifra")
    , @NamedQuery(name = "Drzave.findByNaziv", query = "SELECT d FROM Drzave d WHERE d.naziv = :naziv")
    , @NamedQuery(name = "Drzave.findByDatumVreme", query = "SELECT d FROM Drzave d WHERE d.datumVreme = :datumVreme")
    , @NamedQuery(name = "Drzave.findByAktivan", query = "SELECT d FROM Drzave d WHERE d.aktivan = :aktivan")})
public class Drzave implements Serializable {

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
    
    private Connection conn;
    
    public Drzave() {
    }

    public Drzave(Integer id) {
        this.id = id;
    }

    public Drzave(String naziv, Connection conn) {
        this.naziv = naziv;
        this.conn = conn;
    }

    public Drzave(Integer id, String sifra, String naziv, Date datumVreme, boolean aktivan) {
        this.id = id;
        this.sifra = sifra;
        this.naziv = naziv;
        this.datumVreme = datumVreme;
        this.aktivan = aktivan;
    }

    public int puniDrzaveId(String Naziv) {
        int vrati=0;
        try {
            Statement stmt = conn.createStatement();
            String sqlQuery = " select * from drzave where aktivan=1 and naziv = '" + naziv
                    + "'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            while (rs.next()) 
                vrati = rs.getInt(1);
            }
            catch (Exception ex) {
                vrati=0;
        }
        return vrati;
    }

    public String puniDrzaveSifra(String Naziv) {
        String vrati="a";
        try {
            Statement stmt = conn.createStatement();
            String sqlQuery = " select * from drzave where aktivan=1 and naziv = '" + naziv
                    + "'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            while (rs.next()) 
                vrati = rs.getString(2);
            }
            catch (Exception ex) {
                vrati="a";
        }
        return vrati;
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
        if (!(object instanceof Drzave)) {
            return false;
        }
        Drzave other = (Drzave) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Drzave[ id=" + id + " ]";
    }

}
