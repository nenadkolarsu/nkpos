/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms_pos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ms
 */
@Entity
@Table(name = "mpn_nivelacija_cena_stavka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MpnNivelacijaCenaStavka.findAll", query = "SELECT m FROM MpnNivelacijaCenaStavka m")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findById", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.id = :id")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByBrojdok", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.brojdok = :brojdok")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByOblast", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.oblast = :oblast")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByKategorija", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.kategorija = :kategorija")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByBrojnal", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.brojnal = :brojnal")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByDuguje", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.duguje = :duguje")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByRb", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.rb = :rb")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByStorno", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.storno = :storno")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findBySifra", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.sifra = :sifra")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByJm", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.jm = :jm")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByKolicina", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.kolicina = :kolicina")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByIznos", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.iznos = :iznos")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByMpcenaStara", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.mpcenaStara = :mpcenaStara")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByCena", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.cena = :cena")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByPlancena", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.plancena = :plancena")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByProdcena", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.prodcena = :prodcena")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByProdcena1", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.prodcena1 = :prodcena1")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByProdcena2", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.prodcena2 = :prodcena2")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByProdcena3", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.prodcena3 = :prodcena3")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByProscena", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.proscena = :proscena")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByKalkcena", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.kalkcena = :kalkcena")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByNabcena", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.nabcena = :nabcena")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByVazeci", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.vazeci = :vazeci")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findBySlike", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.slike = :slike")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByZvuk", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.zvuk = :zvuk")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByVideo", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.video = :video")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByUsername", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.username = :username")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByTs", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.ts = :ts")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByAktivan", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.aktivan = :aktivan")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByDatdok", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.datdok = :datdok")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findBySemaKnjizenja", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.semaKnjizenja = :semaKnjizenja")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByZaUpotrebu", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.zaUpotrebu = :zaUpotrebu")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByDatproiz", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.datproiz = :datproiz")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findByDatvazido", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.datvazido = :datvazido")
    , @NamedQuery(name = "MpnNivelacijaCenaStavka.findBySmena", query = "SELECT m FROM MpnNivelacijaCenaStavka m WHERE m.smena = :smena")})
public class MpnNivelacijaCenaStavka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "brojdok")
    private String brojdok;
    @Basic(optional = false)
    @Column(name = "oblast")
    private String oblast;
    @Basic(optional = false)
    @Column(name = "kategorija")
    private String kategorija;
    @Basic(optional = false)
    @Column(name = "brojnal")
    private String brojnal;
    @Basic(optional = false)
    @Column(name = "duguje")
    private boolean duguje;
    @Basic(optional = false)
    @Column(name = "rb")
    private int rb;
    @Basic(optional = false)
    @Column(name = "storno")
    private boolean storno;
    @Basic(optional = false)
    @Column(name = "sifra")
    private String sifra;
    @Basic(optional = false)
    @Column(name = "jm")
    private String jm;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "kolicina")
    private BigDecimal kolicina;
    @Basic(optional = false)
    @Column(name = "iznos")
    private BigDecimal iznos;
    @Basic(optional = false)
    @Column(name = "mpcena_stara")
    private BigDecimal mpcenaStara;
    @Basic(optional = false)
    @Column(name = "cena")
    private BigDecimal cena;
    @Basic(optional = false)
    @Column(name = "plancena")
    private BigDecimal plancena;
    @Basic(optional = false)
    @Column(name = "prodcena")
    private BigDecimal prodcena;
    @Basic(optional = false)
    @Column(name = "prodcena1")
    private BigDecimal prodcena1;
    @Basic(optional = false)
    @Column(name = "prodcena2")
    private BigDecimal prodcena2;
    @Basic(optional = false)
    @Column(name = "prodcena3")
    private BigDecimal prodcena3;
    @Basic(optional = false)
    @Column(name = "proscena")
    private BigDecimal proscena;
    @Basic(optional = false)
    @Column(name = "kalkcena")
    private BigDecimal kalkcena;
    @Basic(optional = false)
    @Column(name = "nabcena")
    private BigDecimal nabcena;
    @Basic(optional = false)
    @Column(name = "vazeci")
    private boolean vazeci;
    @Basic(optional = false)
    @Column(name = "slike")
    private String slike;
    @Basic(optional = false)
    @Column(name = "zvuk")
    private String zvuk;
    @Basic(optional = false)
    @Column(name = "video")
    private String video;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "ts")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ts;
    @Basic(optional = false)
    @Lob
    @Column(name = "host")
    private Object host;
    @Basic(optional = false)
    @Column(name = "aktivan")
    private boolean aktivan;
    @Basic(optional = false)
    @Column(name = "datdok")
    @Temporal(TemporalType.DATE)
    private Date datdok;
    @Basic(optional = false)
    @Column(name = "sema_knjizenja")
    private String semaKnjizenja;
    @Basic(optional = false)
    @Column(name = "za_upotrebu")
    private String zaUpotrebu;
    @Basic(optional = false)
    @Column(name = "datproiz")
    @Temporal(TemporalType.DATE)
    private Date datproiz;
    @Basic(optional = false)
    @Column(name = "datvazido")
    @Temporal(TemporalType.DATE)
    private Date datvazido;
    @Basic(optional = false)
    @Column(name = "smena")
    private String smena;

    public MpnNivelacijaCenaStavka() {
    }

    public MpnNivelacijaCenaStavka(Long id) {
        this.id = id;
    }

    public MpnNivelacijaCenaStavka(Long id, String brojdok, String oblast, String kategorija, String brojnal, boolean duguje, int rb, boolean storno, String sifra, String jm, BigDecimal kolicina, BigDecimal iznos, BigDecimal mpcenaStara, BigDecimal cena, BigDecimal plancena, BigDecimal prodcena, BigDecimal prodcena1, BigDecimal prodcena2, BigDecimal prodcena3, BigDecimal proscena, BigDecimal kalkcena, BigDecimal nabcena, boolean vazeci, String slike, String zvuk, String video, String username, Date ts, Object host, boolean aktivan, Date datdok, String semaKnjizenja, String zaUpotrebu, Date datproiz, Date datvazido, String smena) {
        this.id = id;
        this.brojdok = brojdok;
        this.oblast = oblast;
        this.kategorija = kategorija;
        this.brojnal = brojnal;
        this.duguje = duguje;
        this.rb = rb;
        this.storno = storno;
        this.sifra = sifra;
        this.jm = jm;
        this.kolicina = kolicina;
        this.iznos = iznos;
        this.mpcenaStara = mpcenaStara;
        this.cena = cena;
        this.plancena = plancena;
        this.prodcena = prodcena;
        this.prodcena1 = prodcena1;
        this.prodcena2 = prodcena2;
        this.prodcena3 = prodcena3;
        this.proscena = proscena;
        this.kalkcena = kalkcena;
        this.nabcena = nabcena;
        this.vazeci = vazeci;
        this.slike = slike;
        this.zvuk = zvuk;
        this.video = video;
        this.username = username;
        this.ts = ts;
        this.host = host;
        this.aktivan = aktivan;
        this.datdok = datdok;
        this.semaKnjizenja = semaKnjizenja;
        this.zaUpotrebu = zaUpotrebu;
        this.datproiz = datproiz;
        this.datvazido = datvazido;
        this.smena = smena;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrojdok() {
        return brojdok;
    }

    public void setBrojdok(String brojdok) {
        this.brojdok = brojdok;
    }

    public String getOblast() {
        return oblast;
    }

    public void setOblast(String oblast) {
        this.oblast = oblast;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String getBrojnal() {
        return brojnal;
    }

    public void setBrojnal(String brojnal) {
        this.brojnal = brojnal;
    }

    public boolean getDuguje() {
        return duguje;
    }

    public void setDuguje(boolean duguje) {
        this.duguje = duguje;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public boolean getStorno() {
        return storno;
    }

    public void setStorno(boolean storno) {
        this.storno = storno;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getJm() {
        return jm;
    }

    public void setJm(String jm) {
        this.jm = jm;
    }

    public BigDecimal getKolicina() {
        return kolicina;
    }

    public void setKolicina(BigDecimal kolicina) {
        this.kolicina = kolicina;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public BigDecimal getMpcenaStara() {
        return mpcenaStara;
    }

    public void setMpcenaStara(BigDecimal mpcenaStara) {
        this.mpcenaStara = mpcenaStara;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public BigDecimal getPlancena() {
        return plancena;
    }

    public void setPlancena(BigDecimal plancena) {
        this.plancena = plancena;
    }

    public BigDecimal getProdcena() {
        return prodcena;
    }

    public void setProdcena(BigDecimal prodcena) {
        this.prodcena = prodcena;
    }

    public BigDecimal getProdcena1() {
        return prodcena1;
    }

    public void setProdcena1(BigDecimal prodcena1) {
        this.prodcena1 = prodcena1;
    }

    public BigDecimal getProdcena2() {
        return prodcena2;
    }

    public void setProdcena2(BigDecimal prodcena2) {
        this.prodcena2 = prodcena2;
    }

    public BigDecimal getProdcena3() {
        return prodcena3;
    }

    public void setProdcena3(BigDecimal prodcena3) {
        this.prodcena3 = prodcena3;
    }

    public BigDecimal getProscena() {
        return proscena;
    }

    public void setProscena(BigDecimal proscena) {
        this.proscena = proscena;
    }

    public BigDecimal getKalkcena() {
        return kalkcena;
    }

    public void setKalkcena(BigDecimal kalkcena) {
        this.kalkcena = kalkcena;
    }

    public BigDecimal getNabcena() {
        return nabcena;
    }

    public void setNabcena(BigDecimal nabcena) {
        this.nabcena = nabcena;
    }

    public boolean getVazeci() {
        return vazeci;
    }

    public void setVazeci(boolean vazeci) {
        this.vazeci = vazeci;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public Object getHost() {
        return host;
    }

    public void setHost(Object host) {
        this.host = host;
    }

    public boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public Date getDatdok() {
        return datdok;
    }

    public void setDatdok(Date datdok) {
        this.datdok = datdok;
    }

    public String getSemaKnjizenja() {
        return semaKnjizenja;
    }

    public void setSemaKnjizenja(String semaKnjizenja) {
        this.semaKnjizenja = semaKnjizenja;
    }

    public String getZaUpotrebu() {
        return zaUpotrebu;
    }

    public void setZaUpotrebu(String zaUpotrebu) {
        this.zaUpotrebu = zaUpotrebu;
    }

    public Date getDatproiz() {
        return datproiz;
    }

    public void setDatproiz(Date datproiz) {
        this.datproiz = datproiz;
    }

    public Date getDatvazido() {
        return datvazido;
    }

    public void setDatvazido(Date datvazido) {
        this.datvazido = datvazido;
    }

    public String getSmena() {
        return smena;
    }

    public void setSmena(String smena) {
        this.smena = smena;
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
        if (!(object instanceof MpnNivelacijaCenaStavka)) {
            return false;
        }
        MpnNivelacijaCenaStavka other = (MpnNivelacijaCenaStavka) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "forms_pos.MpnNivelacijaCenaStavka[ id=" + id + " ]";
    }
    
}
