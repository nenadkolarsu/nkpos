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
@Table(name = "mpr_razduzenje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MprRazduzenje.findAll", query = "SELECT m FROM MprRazduzenje m")
    , @NamedQuery(name = "MprRazduzenje.findById", query = "SELECT m FROM MprRazduzenje m WHERE m.id = :id")
    , @NamedQuery(name = "MprRazduzenje.findByBrojdok", query = "SELECT m FROM MprRazduzenje m WHERE m.brojdok = :brojdok")
    , @NamedQuery(name = "MprRazduzenje.findByDatdok", query = "SELECT m FROM MprRazduzenje m WHERE m.datdok = :datdok")
    , @NamedQuery(name = "MprRazduzenje.findByOblast", query = "SELECT m FROM MprRazduzenje m WHERE m.oblast = :oblast")
    , @NamedQuery(name = "MprRazduzenje.findByKategorija", query = "SELECT m FROM MprRazduzenje m WHERE m.kategorija = :kategorija")
    , @NamedQuery(name = "MprRazduzenje.findByBrojnal", query = "SELECT m FROM MprRazduzenje m WHERE m.brojnal = :brojnal")
    , @NamedQuery(name = "MprRazduzenje.findByDuguje", query = "SELECT m FROM MprRazduzenje m WHERE m.duguje = :duguje")
    , @NamedQuery(name = "MprRazduzenje.findByGrupamsg", query = "SELECT m FROM MprRazduzenje m WHERE m.grupamsg = :grupamsg")
    , @NamedQuery(name = "MprRazduzenje.findByVeza", query = "SELECT m FROM MprRazduzenje m WHERE m.veza = :veza")
    , @NamedQuery(name = "MprRazduzenje.findByIntdok", query = "SELECT m FROM MprRazduzenje m WHERE m.intdok = :intdok")
    , @NamedQuery(name = "MprRazduzenje.findByPotpisnik", query = "SELECT m FROM MprRazduzenje m WHERE m.potpisnik = :potpisnik")
    , @NamedQuery(name = "MprRazduzenje.findByPublickey", query = "SELECT m FROM MprRazduzenje m WHERE m.publickey = :publickey")
    , @NamedQuery(name = "MprRazduzenje.findByDatalen", query = "SELECT m FROM MprRazduzenje m WHERE m.datalen = :datalen")
    , @NamedQuery(name = "MprRazduzenje.findByOpis", query = "SELECT m FROM MprRazduzenje m WHERE m.opis = :opis")
    , @NamedQuery(name = "MprRazduzenje.findByMagIzdatnice", query = "SELECT m FROM MprRazduzenje m WHERE m.magIzdatnice = :magIzdatnice")
    , @NamedQuery(name = "MprRazduzenje.findByVremeizlaza", query = "SELECT m FROM MprRazduzenje m WHERE m.vremeizlaza = :vremeizlaza")
    , @NamedQuery(name = "MprRazduzenje.findByIznos", query = "SELECT m FROM MprRazduzenje m WHERE m.iznos = :iznos")
    , @NamedQuery(name = "MprRazduzenje.findByVezaUlaz", query = "SELECT m FROM MprRazduzenje m WHERE m.vezaUlaz = :vezaUlaz")
    , @NamedQuery(name = "MprRazduzenje.findByVezazapocet", query = "SELECT m FROM MprRazduzenje m WHERE m.vezazapocet = :vezazapocet")
    , @NamedQuery(name = "MprRazduzenje.findByMagacioner", query = "SELECT m FROM MprRazduzenje m WHERE m.magacioner = :magacioner")
    , @NamedQuery(name = "MprRazduzenje.findBySemaKnjizenja", query = "SELECT m FROM MprRazduzenje m WHERE m.semaKnjizenja = :semaKnjizenja")
    , @NamedQuery(name = "MprRazduzenje.findByCommitNumber", query = "SELECT m FROM MprRazduzenje m WHERE m.commitNumber = :commitNumber")
    , @NamedQuery(name = "MprRazduzenje.findByKupac", query = "SELECT m FROM MprRazduzenje m WHERE m.kupac = :kupac")
    , @NamedQuery(name = "MprRazduzenje.findByIdKupac", query = "SELECT m FROM MprRazduzenje m WHERE m.idKupac = :idKupac")
    , @NamedQuery(name = "MprRazduzenje.findByBrojFiskalnogIsecka", query = "SELECT m FROM MprRazduzenje m WHERE m.brojFiskalnogIsecka = :brojFiskalnogIsecka")
    , @NamedQuery(name = "MprRazduzenje.findByBrojFaktureMp", query = "SELECT m FROM MprRazduzenje m WHERE m.brojFaktureMp = :brojFaktureMp")
    , @NamedQuery(name = "MprRazduzenje.findByRedniBrojRacuna", query = "SELECT m FROM MprRazduzenje m WHERE m.redniBrojRacuna = :redniBrojRacuna")
    , @NamedQuery(name = "MprRazduzenje.findByOj", query = "SELECT m FROM MprRazduzenje m WHERE m.oj = :oj")
    , @NamedQuery(name = "MprRazduzenje.findByIdOj", query = "SELECT m FROM MprRazduzenje m WHERE m.idOj = :idOj")
    , @NamedQuery(name = "MprRazduzenje.findByIdTransakcija", query = "SELECT m FROM MprRazduzenje m WHERE m.idTransakcija = :idTransakcija")
    , @NamedQuery(name = "MprRazduzenje.findByIdKasa", query = "SELECT m FROM MprRazduzenje m WHERE m.idKasa = :idKasa")
    , @NamedQuery(name = "MprRazduzenje.findByKasa", query = "SELECT m FROM MprRazduzenje m WHERE m.kasa = :kasa")
    , @NamedQuery(name = "MprRazduzenje.findByIdSredstvoPlacanja", query = "SELECT m FROM MprRazduzenje m WHERE m.idSredstvoPlacanja = :idSredstvoPlacanja")
    , @NamedQuery(name = "MprRazduzenje.findBySredstvoPlacanja", query = "SELECT m FROM MprRazduzenje m WHERE m.sredstvoPlacanja = :sredstvoPlacanja")
    , @NamedQuery(name = "MprRazduzenje.findByIdBanka", query = "SELECT m FROM MprRazduzenje m WHERE m.idBanka = :idBanka")
    , @NamedQuery(name = "MprRazduzenje.findByBanka", query = "SELECT m FROM MprRazduzenje m WHERE m.banka = :banka")
    , @NamedQuery(name = "MprRazduzenje.findByBrojCekaKartice1", query = "SELECT m FROM MprRazduzenje m WHERE m.brojCekaKartice1 = :brojCekaKartice1")
    , @NamedQuery(name = "MprRazduzenje.findByBrojCekaKartice2", query = "SELECT m FROM MprRazduzenje m WHERE m.brojCekaKartice2 = :brojCekaKartice2")
    , @NamedQuery(name = "MprRazduzenje.findByBrojCekaKartice3", query = "SELECT m FROM MprRazduzenje m WHERE m.brojCekaKartice3 = :brojCekaKartice3")
    , @NamedQuery(name = "MprRazduzenje.findByTipPopusta", query = "SELECT m FROM MprRazduzenje m WHERE m.tipPopusta = :tipPopusta")
    , @NamedQuery(name = "MprRazduzenje.findByIznosPopusta", query = "SELECT m FROM MprRazduzenje m WHERE m.iznosPopusta = :iznosPopusta")
    , @NamedQuery(name = "MprRazduzenje.findByIdCiklus", query = "SELECT m FROM MprRazduzenje m WHERE m.idCiklus = :idCiklus")
    , @NamedQuery(name = "MprRazduzenje.findByStampacIbfm", query = "SELECT m FROM MprRazduzenje m WHERE m.stampacIbfm = :stampacIbfm")
    , @NamedQuery(name = "MprRazduzenje.findByVazeci", query = "SELECT m FROM MprRazduzenje m WHERE m.vazeci = :vazeci")
    , @NamedQuery(name = "MprRazduzenje.findBySlike", query = "SELECT m FROM MprRazduzenje m WHERE m.slike = :slike")
    , @NamedQuery(name = "MprRazduzenje.findByZvuk", query = "SELECT m FROM MprRazduzenje m WHERE m.zvuk = :zvuk")
    , @NamedQuery(name = "MprRazduzenje.findByVideo", query = "SELECT m FROM MprRazduzenje m WHERE m.video = :video")
    , @NamedQuery(name = "MprRazduzenje.findByUsername", query = "SELECT m FROM MprRazduzenje m WHERE m.username = :username")
    , @NamedQuery(name = "MprRazduzenje.findByTs", query = "SELECT m FROM MprRazduzenje m WHERE m.ts = :ts")
    , @NamedQuery(name = "MprRazduzenje.findByAktivan", query = "SELECT m FROM MprRazduzenje m WHERE m.aktivan = :aktivan")})
public class MprRazduzenje implements Serializable {

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
    @Column(name = "datdok")
    @Temporal(TemporalType.DATE)
    private Date datdok;
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
    @Column(name = "veza")
    private String veza;
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
    @Column(name = "opis")
    private String opis;
    @Basic(optional = false)
    @Column(name = "mag_izdatnice")
    private String magIzdatnice;
    @Basic(optional = false)
    @Column(name = "vremeizlaza")
    @Temporal(TemporalType.TIME)
    private Date vremeizlaza;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "iznos")
    private BigDecimal iznos;
    @Basic(optional = false)
    @Column(name = "veza_ulaz")
    private String vezaUlaz;
    @Basic(optional = false)
    @Column(name = "vezazapocet")
    private String vezazapocet;
    @Basic(optional = false)
    @Column(name = "magacioner")
    private String magacioner;
    @Basic(optional = false)
    @Column(name = "sema_knjizenja")
    private String semaKnjizenja;
    @Basic(optional = false)
    @Column(name = "commit_number")
    private long commitNumber;
    @Basic(optional = false)
    @Column(name = "kupac")
    private String kupac;
    @Basic(optional = false)
    @Column(name = "id_kupac")
    private long idKupac;
    @Basic(optional = false)
    @Column(name = "broj_fiskalnog_isecka")
    private String brojFiskalnogIsecka;
    @Basic(optional = false)
    @Column(name = "broj_fakture_mp")
    private String brojFaktureMp;
    @Basic(optional = false)
    @Column(name = "redni_broj_racuna")
    private String redniBrojRacuna;
    @Basic(optional = false)
    @Column(name = "oj")
    private String oj;
    @Basic(optional = false)
    @Column(name = "id_oj")
    private long idOj;
    @Basic(optional = false)
    @Column(name = "id_transakcija")
    private long idTransakcija;
    @Basic(optional = false)
    @Column(name = "id_kasa")
    private long idKasa;
    @Basic(optional = false)
    @Column(name = "kasa")
    private String kasa;
    @Basic(optional = false)
    @Column(name = "id_sredstvo_placanja")
    private long idSredstvoPlacanja;
    @Basic(optional = false)
    @Column(name = "sredstvo_placanja")
    private String sredstvoPlacanja;
    @Basic(optional = false)
    @Column(name = "id_banka")
    private long idBanka;
    @Basic(optional = false)
    @Column(name = "banka")
    private String banka;
    @Basic(optional = false)
    @Column(name = "broj_ceka_kartice1")
    private String brojCekaKartice1;
    @Basic(optional = false)
    @Column(name = "broj_ceka_kartice2")
    private String brojCekaKartice2;
    @Basic(optional = false)
    @Column(name = "broj_ceka_kartice3")
    private String brojCekaKartice3;
    @Basic(optional = false)
    @Column(name = "tip_popusta")
    private String tipPopusta;
    @Basic(optional = false)
    @Column(name = "iznos_popusta")
    private BigDecimal iznosPopusta;
    @Basic(optional = false)
    @Column(name = "id_ciklus")
    private long idCiklus;
    @Basic(optional = false)
    @Column(name = "stampac_ibfm")
    private String stampacIbfm;
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

    public MprRazduzenje() {
    }

    public MprRazduzenje(Long id) {
        this.id = id;
    }

    public MprRazduzenje(Long id, String brojdok, Date datdok, String oblast, String kategorija, String brojnal, boolean duguje, String grupamsg, String veza, String intdok, String potpisnik, String publickey, int datalen, byte[] signature, String opis, String magIzdatnice, Date vremeizlaza, BigDecimal iznos, String vezaUlaz, String vezazapocet, String magacioner, String semaKnjizenja, long commitNumber, String kupac, long idKupac, String brojFiskalnogIsecka, String brojFaktureMp, String redniBrojRacuna, String oj, long idOj, long idTransakcija, long idKasa, String kasa, long idSredstvoPlacanja, String sredstvoPlacanja, long idBanka, String banka, String brojCekaKartice1, String brojCekaKartice2, String brojCekaKartice3, String tipPopusta, BigDecimal iznosPopusta, long idCiklus, String stampacIbfm, boolean vazeci, String slike, String zvuk, String video, String username, Date ts, Object host, boolean aktivan) {
        this.id = id;
        this.brojdok = brojdok;
        this.datdok = datdok;
        this.oblast = oblast;
        this.kategorija = kategorija;
        this.brojnal = brojnal;
        this.duguje = duguje;
        this.grupamsg = grupamsg;
        this.veza = veza;
        this.intdok = intdok;
        this.potpisnik = potpisnik;
        this.publickey = publickey;
        this.datalen = datalen;
        this.signature = signature;
        this.opis = opis;
        this.magIzdatnice = magIzdatnice;
        this.vremeizlaza = vremeizlaza;
        this.iznos = iznos;
        this.vezaUlaz = vezaUlaz;
        this.vezazapocet = vezazapocet;
        this.magacioner = magacioner;
        this.semaKnjizenja = semaKnjizenja;
        this.commitNumber = commitNumber;
        this.kupac = kupac;
        this.idKupac = idKupac;
        this.brojFiskalnogIsecka = brojFiskalnogIsecka;
        this.brojFaktureMp = brojFaktureMp;
        this.redniBrojRacuna = redniBrojRacuna;
        this.oj = oj;
        this.idOj = idOj;
        this.idTransakcija = idTransakcija;
        this.idKasa = idKasa;
        this.kasa = kasa;
        this.idSredstvoPlacanja = idSredstvoPlacanja;
        this.sredstvoPlacanja = sredstvoPlacanja;
        this.idBanka = idBanka;
        this.banka = banka;
        this.brojCekaKartice1 = brojCekaKartice1;
        this.brojCekaKartice2 = brojCekaKartice2;
        this.brojCekaKartice3 = brojCekaKartice3;
        this.tipPopusta = tipPopusta;
        this.iznosPopusta = iznosPopusta;
        this.idCiklus = idCiklus;
        this.stampacIbfm = stampacIbfm;
        this.vazeci = vazeci;
        this.slike = slike;
        this.zvuk = zvuk;
        this.video = video;
        this.username = username;
        this.ts = ts;
        this.host = host;
        this.aktivan = aktivan;
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

    public Date getDatdok() {
        return datdok;
    }

    public void setDatdok(Date datdok) {
        this.datdok = datdok;
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

    public String getVeza() {
        return veza;
    }

    public void setVeza(String veza) {
        this.veza = veza;
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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getMagIzdatnice() {
        return magIzdatnice;
    }

    public void setMagIzdatnice(String magIzdatnice) {
        this.magIzdatnice = magIzdatnice;
    }

    public Date getVremeizlaza() {
        return vremeizlaza;
    }

    public void setVremeizlaza(Date vremeizlaza) {
        this.vremeizlaza = vremeizlaza;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public String getVezaUlaz() {
        return vezaUlaz;
    }

    public void setVezaUlaz(String vezaUlaz) {
        this.vezaUlaz = vezaUlaz;
    }

    public String getVezazapocet() {
        return vezazapocet;
    }

    public void setVezazapocet(String vezazapocet) {
        this.vezazapocet = vezazapocet;
    }

    public String getMagacioner() {
        return magacioner;
    }

    public void setMagacioner(String magacioner) {
        this.magacioner = magacioner;
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

    public String getKupac() {
        return kupac;
    }

    public void setKupac(String kupac) {
        this.kupac = kupac;
    }

    public long getIdKupac() {
        return idKupac;
    }

    public void setIdKupac(long idKupac) {
        this.idKupac = idKupac;
    }

    public String getBrojFiskalnogIsecka() {
        return brojFiskalnogIsecka;
    }

    public void setBrojFiskalnogIsecka(String brojFiskalnogIsecka) {
        this.brojFiskalnogIsecka = brojFiskalnogIsecka;
    }

    public String getBrojFaktureMp() {
        return brojFaktureMp;
    }

    public void setBrojFaktureMp(String brojFaktureMp) {
        this.brojFaktureMp = brojFaktureMp;
    }

    public String getRedniBrojRacuna() {
        return redniBrojRacuna;
    }

    public void setRedniBrojRacuna(String redniBrojRacuna) {
        this.redniBrojRacuna = redniBrojRacuna;
    }

    public String getOj() {
        return oj;
    }

    public void setOj(String oj) {
        this.oj = oj;
    }

    public long getIdOj() {
        return idOj;
    }

    public void setIdOj(long idOj) {
        this.idOj = idOj;
    }

    public long getIdTransakcija() {
        return idTransakcija;
    }

    public void setIdTransakcija(long idTransakcija) {
        this.idTransakcija = idTransakcija;
    }

    public long getIdKasa() {
        return idKasa;
    }

    public void setIdKasa(long idKasa) {
        this.idKasa = idKasa;
    }

    public String getKasa() {
        return kasa;
    }

    public void setKasa(String kasa) {
        this.kasa = kasa;
    }

    public long getIdSredstvoPlacanja() {
        return idSredstvoPlacanja;
    }

    public void setIdSredstvoPlacanja(long idSredstvoPlacanja) {
        this.idSredstvoPlacanja = idSredstvoPlacanja;
    }

    public String getSredstvoPlacanja() {
        return sredstvoPlacanja;
    }

    public void setSredstvoPlacanja(String sredstvoPlacanja) {
        this.sredstvoPlacanja = sredstvoPlacanja;
    }

    public long getIdBanka() {
        return idBanka;
    }

    public void setIdBanka(long idBanka) {
        this.idBanka = idBanka;
    }

    public String getBanka() {
        return banka;
    }

    public void setBanka(String banka) {
        this.banka = banka;
    }

    public String getBrojCekaKartice1() {
        return brojCekaKartice1;
    }

    public void setBrojCekaKartice1(String brojCekaKartice1) {
        this.brojCekaKartice1 = brojCekaKartice1;
    }

    public String getBrojCekaKartice2() {
        return brojCekaKartice2;
    }

    public void setBrojCekaKartice2(String brojCekaKartice2) {
        this.brojCekaKartice2 = brojCekaKartice2;
    }

    public String getBrojCekaKartice3() {
        return brojCekaKartice3;
    }

    public void setBrojCekaKartice3(String brojCekaKartice3) {
        this.brojCekaKartice3 = brojCekaKartice3;
    }

    public String getTipPopusta() {
        return tipPopusta;
    }

    public void setTipPopusta(String tipPopusta) {
        this.tipPopusta = tipPopusta;
    }

    public BigDecimal getIznosPopusta() {
        return iznosPopusta;
    }

    public void setIznosPopusta(BigDecimal iznosPopusta) {
        this.iznosPopusta = iznosPopusta;
    }

    public long getIdCiklus() {
        return idCiklus;
    }

    public void setIdCiklus(long idCiklus) {
        this.idCiklus = idCiklus;
    }

    public String getStampacIbfm() {
        return stampacIbfm;
    }

    public void setStampacIbfm(String stampacIbfm) {
        this.stampacIbfm = stampacIbfm;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MprRazduzenje)) {
            return false;
        }
        MprRazduzenje other = (MprRazduzenje) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "forms_pos.MprRazduzenje[ id=" + id + " ]";
    }
    
}
