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
 * @author Pionir SU
 */
@Entity
@Table(name = "artikli")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artikli.findAll", query = "SELECT a FROM Artikli a")
    , @NamedQuery(name = "Artikli.findById", query = "SELECT a FROM Artikli a WHERE a.id = :id")
    , @NamedQuery(name = "Artikli.findByJedinicaMereId", query = "SELECT a FROM Artikli a WHERE a.jedinicaMereId = :jedinicaMereId")
    , @NamedQuery(name = "Artikli.findByPorukaId", query = "SELECT a FROM Artikli a WHERE a.porukaId = :porukaId")
    , @NamedQuery(name = "Artikli.findByPoreskaGrupaId", query = "SELECT a FROM Artikli a WHERE a.poreskaGrupaId = :poreskaGrupaId")
    , @NamedQuery(name = "Artikli.findByOdeljenjeId", query = "SELECT a FROM Artikli a WHERE a.odeljenjeId = :odeljenjeId")
    , @NamedQuery(name = "Artikli.findByArtikalKategorijaId", query = "SELECT a FROM Artikli a WHERE a.artikalKategorijaId = :artikalKategorijaId")
    , @NamedQuery(name = "Artikli.findByArtikalTipId", query = "SELECT a FROM Artikli a WHERE a.artikalTipId = :artikalTipId")
    , @NamedQuery(name = "Artikli.findBySifra", query = "SELECT a FROM Artikli a WHERE a.sifra = :sifra")
    , @NamedQuery(name = "Artikli.findByNaziv", query = "SELECT a FROM Artikli a WHERE a.naziv = :naziv")
    , @NamedQuery(name = "Artikli.findByAktivan", query = "SELECT a FROM Artikli a WHERE a.aktivan = :aktivan")
    , @NamedQuery(name = "Artikli.findByDatumVreme", query = "SELECT a FROM Artikli a WHERE a.datumVreme = :datumVreme")
    , @NamedQuery(name = "Artikli.findByNapomena", query = "SELECT a FROM Artikli a WHERE a.napomena = :napomena")
    , @NamedQuery(name = "Artikli.findByUserId", query = "SELECT a FROM Artikli a WHERE a.userId = :userId")
    , @NamedQuery(name = "Artikli.findByAllowPlacanjeVaucher", query = "SELECT a FROM Artikli a WHERE a.allowPlacanjeVaucher = :allowPlacanjeVaucher")
    , @NamedQuery(name = "Artikli.findByVaucherProcenat", query = "SELECT a FROM Artikli a WHERE a.vaucherProcenat = :vaucherProcenat")
    , @NamedQuery(name = "Artikli.findByVaucherIznos", query = "SELECT a FROM Artikli a WHERE a.vaucherIznos = :vaucherIznos")
    , @NamedQuery(name = "Artikli.findByVaucherIznosProcenatFlag", query = "SELECT a FROM Artikli a WHERE a.vaucherIznosProcenatFlag = :vaucherIznosProcenatFlag")
    , @NamedQuery(name = "Artikli.findByRezervav1", query = "SELECT a FROM Artikli a WHERE a.rezervav1 = :rezervav1")
    , @NamedQuery(name = "Artikli.findByRezervav2", query = "SELECT a FROM Artikli a WHERE a.rezervav2 = :rezervav2")
    , @NamedQuery(name = "Artikli.findByRezervav3", query = "SELECT a FROM Artikli a WHERE a.rezervav3 = :rezervav3")
    , @NamedQuery(name = "Artikli.findByRezervai1", query = "SELECT a FROM Artikli a WHERE a.rezervai1 = :rezervai1")
    , @NamedQuery(name = "Artikli.findByRezervai2", query = "SELECT a FROM Artikli a WHERE a.rezervai2 = :rezervai2")
    , @NamedQuery(name = "Artikli.findByRezervai3", query = "SELECT a FROM Artikli a WHERE a.rezervai3 = :rezervai3")
    , @NamedQuery(name = "Artikli.findByRezervad1", query = "SELECT a FROM Artikli a WHERE a.rezervad1 = :rezervad1")
    , @NamedQuery(name = "Artikli.findByRezervad2", query = "SELECT a FROM Artikli a WHERE a.rezervad2 = :rezervad2")
    , @NamedQuery(name = "Artikli.findByRezervad3", query = "SELECT a FROM Artikli a WHERE a.rezervad3 = :rezervad3")
    , @NamedQuery(name = "Artikli.findByArtiklicol", query = "SELECT a FROM Artikli a WHERE a.artiklicol = :artiklicol")})
public class Artikli implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "jedinica_mere_id")
    private Integer jedinicaMereId;
    @Column(name = "poruka_id")
    private Integer porukaId;
    @Column(name = "poreska_grupa_id")
    private Integer poreskaGrupaId;
    @Column(name = "odeljenje_id")
    private Integer odeljenjeId;
    @Column(name = "artikal_kategorija_id")
    private Integer artikalKategorijaId;
    @Column(name = "artikal_tip_id")
    private Integer artikalTipId;
    @Column(name = "sifra")
    private String sifra;
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "aktivan")
    private Boolean aktivan;
    @Column(name = "datum_vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @Column(name = "napomena")
    private String napomena;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "allow_placanje_vaucher")
    private Boolean allowPlacanjeVaucher;
    @Column(name = "vaucher_procenat")
    private Long vaucherProcenat;
    @Column(name = "vaucher_iznos")
    private Long vaucherIznos;
    @Column(name = "vaucher_iznos_procenat_flag")
    private String vaucherIznosProcenatFlag;
    @Column(name = "rezervav1")
    private String rezervav1;
    @Column(name = "rezervav2")
    private String rezervav2;
    @Column(name = "rezervav3")
    private String rezervav3;
    @Column(name = "rezervai1")
    private Integer rezervai1;
    @Column(name = "rezervai2")
    private Integer rezervai2;
    @Column(name = "rezervai3")
    private Integer rezervai3;
    @Column(name = "rezervad1")
    private Long rezervad1;
    @Column(name = "rezervad2")
    private Long rezervad2;
    @Column(name = "rezervad3")
    private Long rezervad3;
    @Column(name = "artiklicol")
    private String artiklicol;

    public Artikli() {
    }

    public Artikli(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJedinicaMereId() {
        return jedinicaMereId;
    }

    public void setJedinicaMereId(Integer jedinicaMereId) {
        this.jedinicaMereId = jedinicaMereId;
    }

    public Integer getPorukaId() {
        return porukaId;
    }

    public void setPorukaId(Integer porukaId) {
        this.porukaId = porukaId;
    }

    public Integer getPoreskaGrupaId() {
        return poreskaGrupaId;
    }

    public void setPoreskaGrupaId(Integer poreskaGrupaId) {
        this.poreskaGrupaId = poreskaGrupaId;
    }

    public Integer getOdeljenjeId() {
        return odeljenjeId;
    }

    public void setOdeljenjeId(Integer odeljenjeId) {
        this.odeljenjeId = odeljenjeId;
    }

    public Integer getArtikalKategorijaId() {
        return artikalKategorijaId;
    }

    public void setArtikalKategorijaId(Integer artikalKategorijaId) {
        this.artikalKategorijaId = artikalKategorijaId;
    }

    public Integer getArtikalTipId() {
        return artikalTipId;
    }

    public void setArtikalTipId(Integer artikalTipId) {
        this.artikalTipId = artikalTipId;
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

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getAllowPlacanjeVaucher() {
        return allowPlacanjeVaucher;
    }

    public void setAllowPlacanjeVaucher(Boolean allowPlacanjeVaucher) {
        this.allowPlacanjeVaucher = allowPlacanjeVaucher;
    }

    public Long getVaucherProcenat() {
        return vaucherProcenat;
    }

    public void setVaucherProcenat(Long vaucherProcenat) {
        this.vaucherProcenat = vaucherProcenat;
    }

    public Long getVaucherIznos() {
        return vaucherIznos;
    }

    public void setVaucherIznos(Long vaucherIznos) {
        this.vaucherIznos = vaucherIznos;
    }

    public String getVaucherIznosProcenatFlag() {
        return vaucherIznosProcenatFlag;
    }

    public void setVaucherIznosProcenatFlag(String vaucherIznosProcenatFlag) {
        this.vaucherIznosProcenatFlag = vaucherIznosProcenatFlag;
    }

    public String getRezervav1() {
        return rezervav1;
    }

    public void setRezervav1(String rezervav1) {
        this.rezervav1 = rezervav1;
    }

    public String getRezervav2() {
        return rezervav2;
    }

    public void setRezervav2(String rezervav2) {
        this.rezervav2 = rezervav2;
    }

    public String getRezervav3() {
        return rezervav3;
    }

    public void setRezervav3(String rezervav3) {
        this.rezervav3 = rezervav3;
    }

    public Integer getRezervai1() {
        return rezervai1;
    }

    public void setRezervai1(Integer rezervai1) {
        this.rezervai1 = rezervai1;
    }

    public Integer getRezervai2() {
        return rezervai2;
    }

    public void setRezervai2(Integer rezervai2) {
        this.rezervai2 = rezervai2;
    }

    public Integer getRezervai3() {
        return rezervai3;
    }

    public void setRezervai3(Integer rezervai3) {
        this.rezervai3 = rezervai3;
    }

    public Long getRezervad1() {
        return rezervad1;
    }

    public void setRezervad1(Long rezervad1) {
        this.rezervad1 = rezervad1;
    }

    public Long getRezervad2() {
        return rezervad2;
    }

    public void setRezervad2(Long rezervad2) {
        this.rezervad2 = rezervad2;
    }

    public Long getRezervad3() {
        return rezervad3;
    }

    public void setRezervad3(Long rezervad3) {
        this.rezervad3 = rezervad3;
    }

    public String getArtiklicol() {
        return artiklicol;
    }

    public void setArtiklicol(String artiklicol) {
        this.artiklicol = artiklicol;
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
        if (!(object instanceof Artikli)) {
            return false;
        }
        Artikli other = (Artikli) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Artikli[ id=" + id + " ]";
    }
    
}
