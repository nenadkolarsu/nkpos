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
 * @author ms
 */
@Entity
@Table(name = "cenovnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cenovnik.findAll", query = "SELECT c FROM Cenovnik c"),
    @NamedQuery(name = "Cenovnik.findById", query = "SELECT c FROM Cenovnik c WHERE c.id = :id"),
    @NamedQuery(name = "Cenovnik.findByArtikalId", query = "SELECT c FROM Cenovnik c WHERE c.artikalId = :artikalId"),
    @NamedQuery(name = "Cenovnik.findByArtikalSifra", query = "SELECT c FROM Cenovnik c WHERE c.artikalSifra = :artikalSifra"),
    @NamedQuery(name = "Cenovnik.findBySifra", query = "SELECT c FROM Cenovnik c WHERE c.sifra = :sifra"),
    @NamedQuery(name = "Cenovnik.findByDatum", query = "SELECT c FROM Cenovnik c WHERE c.datum = :datum"),
    @NamedQuery(name = "Cenovnik.findByNabavnaCena", query = "SELECT c FROM Cenovnik c WHERE c.nabavnaCena = :nabavnaCena"),
    @NamedQuery(name = "Cenovnik.findByPlanskaCena", query = "SELECT c FROM Cenovnik c WHERE c.planskaCena = :planskaCena"),
    @NamedQuery(name = "Cenovnik.findByProdajnaCena", query = "SELECT c FROM Cenovnik c WHERE c.prodajnaCena = :prodajnaCena"),
    @NamedQuery(name = "Cenovnik.findByMaloprodajnaCenaBezPdv", query = "SELECT c FROM Cenovnik c WHERE c.maloprodajnaCenaBezPdv = :maloprodajnaCenaBezPdv"),
    @NamedQuery(name = "Cenovnik.findByMaloprodajnaCena", query = "SELECT c FROM Cenovnik c WHERE c.maloprodajnaCena = :maloprodajnaCena"),
    @NamedQuery(name = "Cenovnik.findByAktivan", query = "SELECT c FROM Cenovnik c WHERE c.aktivan = :aktivan"),
    @NamedQuery(name = "Cenovnik.findByDatumVreme", query = "SELECT c FROM Cenovnik c WHERE c.datumVreme = :datumVreme"),
    @NamedQuery(name = "Cenovnik.findBySlika", query = "SELECT c FROM Cenovnik c WHERE c.slika = :slika"),
    @NamedQuery(name = "Cenovnik.findByVideo", query = "SELECT c FROM Cenovnik c WHERE c.video = :video"),
    @NamedQuery(name = "Cenovnik.findByZvuk", query = "SELECT c FROM Cenovnik c WHERE c.zvuk = :zvuk"),
    @NamedQuery(name = "Cenovnik.findByUser", query = "SELECT c FROM Cenovnik c WHERE c.user = :user"),
    @NamedQuery(name = "Cenovnik.findByProdajnaCenaSaPdv", query = "SELECT c FROM Cenovnik c WHERE c.prodajnaCenaSaPdv = :prodajnaCenaSaPdv")})
public class Cenovnik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "artikal_id")
    private int artikalId;
    @Column(name = "artikal_sifra")
    private String artikalSifra;
    @Basic(optional = false)
    @Column(name = "sifra")
    private String sifra;
    @Basic(optional = false)
    @Column(name = "datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nabavna_cena")
    private BigDecimal nabavnaCena;
    @Column(name = "planska_cena")
    private BigDecimal planskaCena;
    @Column(name = "prodajna_cena")
    private BigDecimal prodajnaCena;
    @Column(name = "maloprodajna_cena_bez_pdv")
    private BigDecimal maloprodajnaCenaBezPdv;
    @Column(name = "maloprodajna_cena")
    private BigDecimal maloprodajnaCena;
    @Basic(optional = false)
    @Column(name = "aktivan")
    private boolean aktivan;
    @Basic(optional = false)
    @Column(name = "datum_vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @Column(name = "slika")
    private String slika;
    @Column(name = "video")
    private String video;
    @Column(name = "zvuk")
    private String zvuk;
    @Column(name = "user")
    private String user;
    @Column(name = "prodajna_cena_sa_pdv")
    private BigDecimal prodajnaCenaSaPdv;

    public Cenovnik() {
    }

    public Cenovnik(Integer id) {
        this.id = id;
    }

    public Cenovnik(Integer id, int artikalId, String sifra, Date datum, boolean aktivan, Date datumVreme) {
        this.id = id;
        this.artikalId = artikalId;
        this.sifra = sifra;
        this.datum = datum;
        this.aktivan = aktivan;
        this.datumVreme = datumVreme;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getArtikalId() {
        return artikalId;
    }

    public void setArtikalId(int artikalId) {
        this.artikalId = artikalId;
    }

    public String getArtikalSifra() {
        return artikalSifra;
    }

    public void setArtikalSifra(String artikalSifra) {
        this.artikalSifra = artikalSifra;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public BigDecimal getNabavnaCena() {
        return nabavnaCena;
    }

    public void setNabavnaCena(BigDecimal nabavnaCena) {
        this.nabavnaCena = nabavnaCena;
    }

    public BigDecimal getPlanskaCena() {
        return planskaCena;
    }

    public void setPlanskaCena(BigDecimal planskaCena) {
        this.planskaCena = planskaCena;
    }

    public BigDecimal getProdajnaCena() {
        return prodajnaCena;
    }

    public void setProdajnaCena(BigDecimal prodajnaCena) {
        this.prodajnaCena = prodajnaCena;
    }

    public BigDecimal getMaloprodajnaCenaBezPdv() {
        return maloprodajnaCenaBezPdv;
    }

    public void setMaloprodajnaCenaBezPdv(BigDecimal maloprodajnaCenaBezPdv) {
        this.maloprodajnaCenaBezPdv = maloprodajnaCenaBezPdv;
    }

    public BigDecimal getMaloprodajnaCena() {
        return maloprodajnaCena;
    }

    public void setMaloprodajnaCena(BigDecimal maloprodajnaCena) {
        this.maloprodajnaCena = maloprodajnaCena;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BigDecimal getProdajnaCenaSaPdv() {
        return prodajnaCenaSaPdv;
    }

    public void setProdajnaCenaSaPdv(BigDecimal prodajnaCenaSaPdv) {
        this.prodajnaCenaSaPdv = prodajnaCenaSaPdv;
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
        if (!(object instanceof Cenovnik)) {
            return false;
        }
        Cenovnik other = (Cenovnik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cenovnik[ id=" + id + " ]";
    }
    
}
