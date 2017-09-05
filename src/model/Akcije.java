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
@Table(name = "akcije")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Akcije.findAll", query = "SELECT a FROM Akcije a")
    , @NamedQuery(name = "Akcije.findById", query = "SELECT a FROM Akcije a WHERE a.id = :id")
    , @NamedQuery(name = "Akcije.findByArtikalId", query = "SELECT a FROM Akcije a WHERE a.artikalId = :artikalId")
    , @NamedQuery(name = "Akcije.findByArtikalSifra", query = "SELECT a FROM Akcije a WHERE a.artikalSifra = :artikalSifra")
    , @NamedQuery(name = "Akcije.findByMpcena", query = "SELECT a FROM Akcije a WHERE a.mpcena = :mpcena")
    , @NamedQuery(name = "Akcije.findByMpcenaAkcijska", query = "SELECT a FROM Akcije a WHERE a.mpcenaAkcijska = :mpcenaAkcijska")
    , @NamedQuery(name = "Akcije.findByDatumAkcijeOd", query = "SELECT a FROM Akcije a WHERE a.datumAkcijeOd = :datumAkcijeOd")
    , @NamedQuery(name = "Akcije.findByDatumAkcijeDo", query = "SELECT a FROM Akcije a WHERE a.datumAkcijeDo = :datumAkcijeDo")
    , @NamedQuery(name = "Akcije.findByAktivan", query = "SELECT a FROM Akcije a WHERE a.aktivan = :aktivan")
    , @NamedQuery(name = "Akcije.findBySlike", query = "SELECT a FROM Akcije a WHERE a.slike = :slike")
    , @NamedQuery(name = "Akcije.findByZvuk", query = "SELECT a FROM Akcije a WHERE a.zvuk = :zvuk")
    , @NamedQuery(name = "Akcije.findByVideo", query = "SELECT a FROM Akcije a WHERE a.video = :video")
    , @NamedQuery(name = "Akcije.findByDatumVreme", query = "SELECT a FROM Akcije a WHERE a.datumVreme = :datumVreme")})
public class Akcije implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "artikal_id")
    private Integer artikalId;
    @Column(name = "artikal_sifra")
    private String artikalSifra;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mpcena")
    private BigDecimal mpcena;
    @Column(name = "mpcena_akcijska")
    private BigDecimal mpcenaAkcijska;
    @Column(name = "datum_akcije_od")
    @Temporal(TemporalType.DATE)
    private Date datumAkcijeOd;
    @Column(name = "datum_akcije_do")
    @Temporal(TemporalType.DATE)
    private Date datumAkcijeDo;
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

    public Akcije() {
    }

    public Akcije(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getMpcena() {
        return mpcena;
    }

    public void setMpcena(BigDecimal mpcena) {
        this.mpcena = mpcena;
    }

    public BigDecimal getMpcenaAkcijska() {
        return mpcenaAkcijska;
    }

    public void setMpcenaAkcijska(BigDecimal mpcenaAkcijska) {
        this.mpcenaAkcijska = mpcenaAkcijska;
    }

    public Date getDatumAkcijeOd() {
        return datumAkcijeOd;
    }

    public void setDatumAkcijeOd(Date datumAkcijeOd) {
        this.datumAkcijeOd = datumAkcijeOd;
    }

    public Date getDatumAkcijeDo() {
        return datumAkcijeDo;
    }

    public void setDatumAkcijeDo(Date datumAkcijeDo) {
        this.datumAkcijeDo = datumAkcijeDo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Akcije)) {
            return false;
        }
        Akcije other = (Akcije) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Akcije[ id=" + id + " ]";
    }
    
}
