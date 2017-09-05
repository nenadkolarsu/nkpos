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
@Table(name = "partneri_racuni")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PartneriRacuni.findAll", query = "SELECT p FROM PartneriRacuni p"),
    @NamedQuery(name = "PartneriRacuni.findById", query = "SELECT p FROM PartneriRacuni p WHERE p.id = :id"),
    @NamedQuery(name = "PartneriRacuni.findByBankaId", query = "SELECT p FROM PartneriRacuni p WHERE p.bankaId = :bankaId"),
    @NamedQuery(name = "PartneriRacuni.findByBankaSifra", query = "SELECT p FROM PartneriRacuni p WHERE p.bankaSifra = :bankaSifra"),
    @NamedQuery(name = "PartneriRacuni.findByTekuciRacun", query = "SELECT p FROM PartneriRacuni p WHERE p.tekuciRacun = :tekuciRacun"),
    @NamedQuery(name = "PartneriRacuni.findByAktivan", query = "SELECT p FROM PartneriRacuni p WHERE p.aktivan = :aktivan"),
    @NamedQuery(name = "PartneriRacuni.findByDatumVreme", query = "SELECT p FROM PartneriRacuni p WHERE p.datumVreme = :datumVreme"),
    @NamedQuery(name = "PartneriRacuni.findBySlika", query = "SELECT p FROM PartneriRacuni p WHERE p.slika = :slika"),
    @NamedQuery(name = "PartneriRacuni.findByVideo", query = "SELECT p FROM PartneriRacuni p WHERE p.video = :video"),
    @NamedQuery(name = "PartneriRacuni.findByZvuk", query = "SELECT p FROM PartneriRacuni p WHERE p.zvuk = :zvuk")})
public class PartneriRacuniModel implements Serializable {
    @Basic(optional = false)
    @Column(name = "partner_id")
    private int partnerId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "banka_id")
    private int bankaId;
    @Basic(optional = false)
    @Column(name = "banka_sifra")
    private String bankaSifra;
    @Basic(optional = false)
    @Column(name = "tekuci_racun")
    private String tekuciRacun;
    @Basic(optional = false)
    @Column(name = "aktivan")
    private boolean aktivan;
    @Basic(optional = false)
    @Column(name = "datum_vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @Basic(optional = false)
    @Column(name = "slika")
    private String slika;
    @Basic(optional = false)
    @Column(name = "video")
    private String video;
    @Basic(optional = false)
    @Column(name = "zvuk")
    private String zvuk;

    public PartneriRacuniModel() {
    }

    public PartneriRacuniModel(Integer id) {
        this.id = id;
    }

    public PartneriRacuniModel(Integer id, int bankaId, String bankaSifra, String tekuciRacun, boolean aktivan, Date datumVreme, String slika, String video, String zvuk) {
        this.id = id;
        this.bankaId = bankaId;
        this.bankaSifra = bankaSifra;
        this.tekuciRacun = tekuciRacun;
        this.aktivan = aktivan;
        this.datumVreme = datumVreme;
        this.slika = slika;
        this.video = video;
        this.zvuk = zvuk;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getBankaId() {
        return bankaId;
    }

    public void setBankaId(int bankaId) {
        this.bankaId = bankaId;
    }

    public String getBankaSifra() {
        return bankaSifra;
    }

    public void setBankaSifra(String bankaSifra) {
        this.bankaSifra = bankaSifra;
    }

    public String getTekuciRacun() {
        return tekuciRacun;
    }

    public void setTekuciRacun(String tekuciRacun) {
        this.tekuciRacun = tekuciRacun;
    }

    public boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getZvuk() {
        return zvuk;
    }

    public void setZvuk(String zvuk) {
        this.zvuk = zvuk;
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
        if (!(object instanceof PartneriRacuniModel)) {
            return false;
        }
        PartneriRacuniModel other = (PartneriRacuniModel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PartneriRacuni[ id=" + id + " ]";
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }
    
}
