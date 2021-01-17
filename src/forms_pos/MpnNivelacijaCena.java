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
@Table(name = "mpn_nivelacija_cena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MpnNivelacijaCena.findAll", query = "SELECT m FROM MpnNivelacijaCena m")
    , @NamedQuery(name = "MpnNivelacijaCena.findById", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.id = :id")
    , @NamedQuery(name = "MpnNivelacijaCena.findByBrojdok", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.brojdok = :brojdok")
    , @NamedQuery(name = "MpnNivelacijaCena.findByOblast", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.oblast = :oblast")
    , @NamedQuery(name = "MpnNivelacijaCena.findByKategorija", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.kategorija = :kategorija")
    , @NamedQuery(name = "MpnNivelacijaCena.findByBrojnal", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.brojnal = :brojnal")
    , @NamedQuery(name = "MpnNivelacijaCena.findByDuguje", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.duguje = :duguje")
    , @NamedQuery(name = "MpnNivelacijaCena.findByGrupamsg", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.grupamsg = :grupamsg")
    , @NamedQuery(name = "MpnNivelacijaCena.findByOpis", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.opis = :opis")
    , @NamedQuery(name = "MpnNivelacijaCena.findByDatdok", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.datdok = :datdok")
    , @NamedQuery(name = "MpnNivelacijaCena.findByIznos", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.iznos = :iznos")
    , @NamedQuery(name = "MpnNivelacijaCena.findByVezazapocet", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.vezazapocet = :vezazapocet")
    , @NamedQuery(name = "MpnNivelacijaCena.findByVazeci", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.vazeci = :vazeci")
    , @NamedQuery(name = "MpnNivelacijaCena.findBySlike", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.slike = :slike")
    , @NamedQuery(name = "MpnNivelacijaCena.findByZvuk", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.zvuk = :zvuk")
    , @NamedQuery(name = "MpnNivelacijaCena.findByVideo", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.video = :video")
    , @NamedQuery(name = "MpnNivelacijaCena.findByUsername", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.username = :username")
    , @NamedQuery(name = "MpnNivelacijaCena.findByTs", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.ts = :ts")
    , @NamedQuery(name = "MpnNivelacijaCena.findByAktivan", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.aktivan = :aktivan")
    , @NamedQuery(name = "MpnNivelacijaCena.findByIntdok", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.intdok = :intdok")
    , @NamedQuery(name = "MpnNivelacijaCena.findByPotpisnik", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.potpisnik = :potpisnik")
    , @NamedQuery(name = "MpnNivelacijaCena.findByPublickey", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.publickey = :publickey")
    , @NamedQuery(name = "MpnNivelacijaCena.findByDatalen", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.datalen = :datalen")
    , @NamedQuery(name = "MpnNivelacijaCena.findBySemaKnjizenja", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.semaKnjizenja = :semaKnjizenja")
    , @NamedQuery(name = "MpnNivelacijaCena.findByCommitNumber", query = "SELECT m FROM MpnNivelacijaCena m WHERE m.commitNumber = :commitNumber")})
public class MpnNivelacijaCena implements Serializable {

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
    @Column(name = "grupamsg")
    private String grupamsg;
    @Basic(optional = false)
    @Column(name = "opis")
    private String opis;
    @Basic(optional = false)
    @Column(name = "datdok")
    @Temporal(TemporalType.DATE)
    private Date datdok;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "iznos")
    private BigDecimal iznos;
    @Basic(optional = false)
    @Column(name = "vezazapocet")
    private String vezazapocet;
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
    @Column(name = "intdok")
    private String intdok;
    @Basic(optional = false)
    @Column(name = "potpisnik")
    private String potpisnik;
    @Basic(optional = false)
    @Column(name = "publickey")
    private String publickey;
    @Basic(optional = false)
    @Column(name = "datalen")
    private int datalen;
    @Basic(optional = false)
    @Lob
    @Column(name = "signature")
    private byte[] signature;
    @Basic(optional = false)
    @Column(name = "sema_knjizenja")
    private String semaKnjizenja;
    @Basic(optional = false)
    @Column(name = "commit_number")
    private long commitNumber;

    public MpnNivelacijaCena() {
    }

    public MpnNivelacijaCena(Long id) {
        this.id = id;
    }

    public MpnNivelacijaCena(Long id, String brojdok, String oblast, String kategorija, String brojnal, boolean duguje, String grupamsg, String opis, Date datdok, BigDecimal iznos, String vezazapocet, boolean vazeci, String slike, String zvuk, String video, String username, Date ts, Object host, boolean aktivan, String intdok, String potpisnik, String publickey, int datalen, byte[] signature, String semaKnjizenja, long commitNumber) {
        this.id = id;
        this.brojdok = brojdok;
        this.oblast = oblast;
        this.kategorija = kategorija;
        this.brojnal = brojnal;
        this.duguje = duguje;
        this.grupamsg = grupamsg;
        this.opis = opis;
        this.datdok = datdok;
        this.iznos = iznos;
        this.vezazapocet = vezazapocet;
        this.vazeci = vazeci;
        this.slike = slike;
        this.zvuk = zvuk;
        this.video = video;
        this.username = username;
        this.ts = ts;
        this.host = host;
        this.aktivan = aktivan;
        this.intdok = intdok;
        this.potpisnik = potpisnik;
        this.publickey = publickey;
        this.datalen = datalen;
        this.signature = signature;
        this.semaKnjizenja = semaKnjizenja;
        this.commitNumber = commitNumber;
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

    public String getGrupamsg() {
        return grupamsg;
    }

    public void setGrupamsg(String grupamsg) {
        this.grupamsg = grupamsg;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Date getDatdok() {
        return datdok;
    }

    public void setDatdok(Date datdok) {
        this.datdok = datdok;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public String getVezazapocet() {
        return vezazapocet;
    }

    public void setVezazapocet(String vezazapocet) {
        this.vezazapocet = vezazapocet;
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

    public String getIntdok() {
        return intdok;
    }

    public void setIntdok(String intdok) {
        this.intdok = intdok;
    }

    public String getPotpisnik() {
        return potpisnik;
    }

    public void setPotpisnik(String potpisnik) {
        this.potpisnik = potpisnik;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public int getDatalen() {
        return datalen;
    }

    public void setDatalen(int datalen) {
        this.datalen = datalen;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSemaKnjizenja() {
        return semaKnjizenja;
    }

    public void setSemaKnjizenja(String semaKnjizenja) {
        this.semaKnjizenja = semaKnjizenja;
    }

    public long getCommitNumber() {
        return commitNumber;
    }

    public void setCommitNumber(long commitNumber) {
        this.commitNumber = commitNumber;
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
        if (!(object instanceof MpnNivelacijaCena)) {
            return false;
        }
        MpnNivelacijaCena other = (MpnNivelacijaCena) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "forms_pos.MpnNivelacijaCena[ id=" + id + " ]";
    }
    
}
