/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "artikli_pakovanja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArtikliPakovanja.findAll", query = "SELECT a FROM ArtikliPakovanja a")
    , @NamedQuery(name = "ArtikliPakovanja.findById", query = "SELECT a FROM ArtikliPakovanja a WHERE a.id = :id")
    , @NamedQuery(name = "ArtikliPakovanja.findByTippak", query = "SELECT a FROM ArtikliPakovanja a WHERE a.tippak = :tippak")
    , @NamedQuery(name = "ArtikliPakovanja.findByEan", query = "SELECT a FROM ArtikliPakovanja a WHERE a.ean = :ean")
    , @NamedQuery(name = "ArtikliPakovanja.findByArtikalId", query = "SELECT a FROM ArtikliPakovanja a WHERE a.artikalId = :artikalId")
    , @NamedQuery(name = "ArtikliPakovanja.findByArtikalSifra", query = "SELECT a FROM ArtikliPakovanja a WHERE a.artikalSifra = :artikalSifra")
    , @NamedQuery(name = "ArtikliPakovanja.findByNaziv", query = "SELECT a FROM ArtikliPakovanja a WHERE a.naziv = :naziv")
    , @NamedQuery(name = "ArtikliPakovanja.findByJm", query = "SELECT a FROM ArtikliPakovanja a WHERE a.jm = :jm")
    , @NamedQuery(name = "ArtikliPakovanja.findByNetojm", query = "SELECT a FROM ArtikliPakovanja a WHERE a.netojm = :netojm")
    , @NamedQuery(name = "ArtikliPakovanja.findByBrutojm", query = "SELECT a FROM ArtikliPakovanja a WHERE a.brutojm = :brutojm")
    , @NamedQuery(name = "ArtikliPakovanja.findBySirina", query = "SELECT a FROM ArtikliPakovanja a WHERE a.sirina = :sirina")
    , @NamedQuery(name = "ArtikliPakovanja.findByVisina", query = "SELECT a FROM ArtikliPakovanja a WHERE a.visina = :visina")
    , @NamedQuery(name = "ArtikliPakovanja.findByDuzina", query = "SELECT a FROM ArtikliPakovanja a WHERE a.duzina = :duzina")
    , @NamedQuery(name = "ArtikliPakovanja.findByKomada", query = "SELECT a FROM ArtikliPakovanja a WHERE a.komada = :komada")
    , @NamedQuery(name = "ArtikliPakovanja.findByAktivan", query = "SELECT a FROM ArtikliPakovanja a WHERE a.aktivan = :aktivan")
    , @NamedQuery(name = "ArtikliPakovanja.findBySlike", query = "SELECT a FROM ArtikliPakovanja a WHERE a.slike = :slike")
    , @NamedQuery(name = "ArtikliPakovanja.findByZvuk", query = "SELECT a FROM ArtikliPakovanja a WHERE a.zvuk = :zvuk")
    , @NamedQuery(name = "ArtikliPakovanja.findByVideo", query = "SELECT a FROM ArtikliPakovanja a WHERE a.video = :video")
    , @NamedQuery(name = "ArtikliPakovanja.findByDatumVreme", query = "SELECT a FROM ArtikliPakovanja a WHERE a.datumVreme = :datumVreme")
    , @NamedQuery(name = "ArtikliPakovanja.findBySifraAmbalaze", query = "SELECT a FROM ArtikliPakovanja a WHERE a.sifraAmbalaze = :sifraAmbalaze")})
public class ArtikliPakovanja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tippak")
    private String tippak;
    @Column(name = "ean")
    private String ean;
    @Column(name = "artikal_id")
    private Integer artikalId;
    @Column(name = "artikal_sifra")
    private String artikalSifra;
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "jm")
    private String jm;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "netojm")
    private BigDecimal netojm;
    @Column(name = "brutojm")
    private BigDecimal brutojm;
    @Column(name = "sirina")
    private BigDecimal sirina;
    @Column(name = "visina")
    private BigDecimal visina;
    @Column(name = "duzina")
    private BigDecimal duzina;
    @Column(name = "komada")
    private BigDecimal komada;
    @Column(name = "aktivan")
    private Boolean aktivan;
    @Column(name = "slike")
    private String slike;
    @Column(name = "zvuk")
    private String zvuk;
    @Column(name = "video")
    private String video;
    @Column(name = "datum_vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @Column(name = "sifra_ambalaze")
    private String sifraAmbalaze;

    public ArtikliPakovanja() {
    }

    public ArtikliPakovanja(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTippak() {
        return tippak;
    }

    public void setTippak(String tippak) {
        this.tippak = tippak;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public Integer getArtikalId() {
        return artikalId;
    }

    public void setArtikalId(Integer artikalId) {
        this.artikalId = artikalId;
    }

    public String getArtikalSifra() {
        return artikalSifra;
    }

    public void setArtikalSifra(String artikalSifra) {
        this.artikalSifra = artikalSifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getJm() {
        return jm;
    }

    public void setJm(String jm) {
        this.jm = jm;
    }

    public BigDecimal getNetojm() {
        return netojm;
    }

    public void setNetojm(BigDecimal netojm) {
        this.netojm = netojm;
    }

    public BigDecimal getBrutojm() {
        return brutojm;
    }

    public void setBrutojm(BigDecimal brutojm) {
        this.brutojm = brutojm;
    }

    public BigDecimal getSirina() {
        return sirina;
    }

    public void setSirina(BigDecimal sirina) {
        this.sirina = sirina;
    }

    public BigDecimal getVisina() {
        return visina;
    }

    public void setVisina(BigDecimal visina) {
        this.visina = visina;
    }

    public BigDecimal getDuzina() {
        return duzina;
    }

    public void setDuzina(BigDecimal duzina) {
        this.duzina = duzina;
    }

    public BigDecimal getKomada() {
        return komada;
    }

    public void setKomada(BigDecimal komada) {
        this.komada = komada;
    }

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    public String getSlike() {
        return slike;
    }

    public void setSlike(String slike) {
        this.slike = slike;
    }

    public String getZvuk() {
        return zvuk;
    }

    public void setZvuk(String zvuk) {
        this.zvuk = zvuk;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public String getSifraAmbalaze() {
        return sifraAmbalaze;
    }

    public void setSifraAmbalaze(String sifraAmbalaze) {
        this.sifraAmbalaze = sifraAmbalaze;
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
        if (!(object instanceof ArtikliPakovanja)) {
            return false;
        }
        ArtikliPakovanja other = (ArtikliPakovanja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ArtikliPakovanja[ id=" + id + " ]";
    }
    
}
