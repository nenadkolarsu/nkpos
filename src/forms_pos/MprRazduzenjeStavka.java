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
@Table(name = "mpr_razduzenje_stavka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MprRazduzenjeStavka.findAll", query = "SELECT m FROM MprRazduzenjeStavka m")
    , @NamedQuery(name = "MprRazduzenjeStavka.findById", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.id = :id")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByBrojdok", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.brojdok = :brojdok")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByDatdok", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.datdok = :datdok")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByOblast", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.oblast = :oblast")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByKategorija", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.kategorija = :kategorija")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByBrojnal", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.brojnal = :brojnal")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByDuguje", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.duguje = :duguje")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByRb", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.rb = :rb")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByStorno", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.storno = :storno")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByRezervacijautoku", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.rezervacijautoku = :rezervacijautoku")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByArtikal", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.artikal = :artikal")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByTipIzboraArtikla", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.tipIzboraArtikla = :tipIzboraArtikla")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByIzbornasifra", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.izbornasifra = :izbornasifra")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByPozicija", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.pozicija = :pozicija")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByOpis", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.opis = :opis")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByJm", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.jm = :jm")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByKlas", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.klas = :klas")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByMagIzdatnice", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.magIzdatnice = :magIzdatnice")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByKolicina", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.kolicina = :kolicina")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByIznos", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.iznos = :iznos")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByCena", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.cena = :cena")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByPlancena", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.plancena = :plancena")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByProdcena", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.prodcena = :prodcena")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByProdcena1", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.prodcena1 = :prodcena1")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByProdcena2", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.prodcena2 = :prodcena2")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByProdcena3", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.prodcena3 = :prodcena3")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByProscena", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.proscena = :proscena")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByKalkcena", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.kalkcena = :kalkcena")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByKontigent", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.kontigent = :kontigent")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByMpcena", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.mpcena = :mpcena")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByNabcena", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.nabcena = :nabcena")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByDatproiz", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.datproiz = :datproiz")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByDatvazido", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.datvazido = :datvazido")
    , @NamedQuery(name = "MprRazduzenjeStavka.findBySmena", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.smena = :smena")
    , @NamedQuery(name = "MprRazduzenjeStavka.findBySerod", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.serod = :serod")
    , @NamedQuery(name = "MprRazduzenjeStavka.findBySerdo", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.serdo = :serdo")
    , @NamedQuery(name = "MprRazduzenjeStavka.findBySemaKnjizenja", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.semaKnjizenja = :semaKnjizenja")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByTsProizvodnja", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.tsProizvodnja = :tsProizvodnja")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByZaUpotrebu", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.zaUpotrebu = :zaUpotrebu")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByPorstopa", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.porstopa = :porstopa")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByIdPorstopa", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.idPorstopa = :idPorstopa")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByIznospdv", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.iznospdv = :iznospdv")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByInfoPc", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.infoPc = :infoPc")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByIdTransakcija", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.idTransakcija = :idTransakcija")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByIdTransStavka", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.idTransStavka = :idTransStavka")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByMpcenaPoCenovniku", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.mpcenaPoCenovniku = :mpcenaPoCenovniku")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByIznosPopusta", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.iznosPopusta = :iznosPopusta")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByKolicinaPp", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.kolicinaPp = :kolicinaPp")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByMpcenaZaPp", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.mpcenaZaPp = :mpcenaZaPp")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByPlancenaZaPp", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.plancenaZaPp = :plancenaZaPp")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByNabcenaZaPp", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.nabcenaZaPp = :nabcenaZaPp")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByPorezPp", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.porezPp = :porezPp")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByPopustPp", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.popustPp = :popustPp")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByMpcenaZaPpPoCen", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.mpcenaZaPpPoCen = :mpcenaZaPpPoCen")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByVazeci", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.vazeci = :vazeci")
    , @NamedQuery(name = "MprRazduzenjeStavka.findBySlike", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.slike = :slike")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByZvuk", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.zvuk = :zvuk")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByVideo", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.video = :video")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByUsername", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.username = :username")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByTs", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.ts = :ts")
    , @NamedQuery(name = "MprRazduzenjeStavka.findByAktivan", query = "SELECT m FROM MprRazduzenjeStavka m WHERE m.aktivan = :aktivan")})
public class MprRazduzenjeStavka implements Serializable {

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
    @Column(name = "rb")
    private int rb;
    @Basic(optional = false)
    @Column(name = "storno")
    private boolean storno;
    @Basic(optional = false)
    @Column(name = "rezervacijautoku")
    private boolean rezervacijautoku;
    @Basic(optional = false)
    @Column(name = "artikal")
    private String artikal;
    @Basic(optional = false)
    @Column(name = "tip_izbora_artikla")
    private String tipIzboraArtikla;
    @Basic(optional = false)
    @Column(name = "izbornasifra")
    private String izbornasifra;
    @Basic(optional = false)
    @Column(name = "pozicija")
    private String pozicija;
    @Basic(optional = false)
    @Column(name = "opis")
    private String opis;
    @Basic(optional = false)
    @Column(name = "jm")
    private String jm;
    @Basic(optional = false)
    @Column(name = "klas")
    private String klas;
    @Basic(optional = false)
    @Column(name = "mag_izdatnice")
    private String magIzdatnice;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "kolicina")
    private BigDecimal kolicina;
    @Basic(optional = false)
    @Column(name = "iznos")
    private BigDecimal iznos;
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
    @Column(name = "kontigent")
    private String kontigent;
    @Basic(optional = false)
    @Column(name = "mpcena")
    private BigDecimal mpcena;
    @Basic(optional = false)
    @Column(name = "nabcena")
    private BigDecimal nabcena;
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
    @Basic(optional = false)
    @Column(name = "serod")
    private long serod;
    @Basic(optional = false)
    @Column(name = "serdo")
    private long serdo;
    @Basic(optional = false)
    @Column(name = "sema_knjizenja")
    private String semaKnjizenja;
    @Basic(optional = false)
    @Column(name = "ts_proizvodnja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsProizvodnja;
    @Basic(optional = false)
    @Column(name = "za_upotrebu")
    private String zaUpotrebu;
    @Basic(optional = false)
    @Column(name = "porstopa")
    private String porstopa;
    @Basic(optional = false)
    @Column(name = "id_porstopa")
    private long idPorstopa;
    @Basic(optional = false)
    @Column(name = "iznospdv")
    private BigDecimal iznospdv;
    @Basic(optional = false)
    @Column(name = "info_pc")
    private String infoPc;
    @Basic(optional = false)
    @Column(name = "id_transakcija")
    private long idTransakcija;
    @Basic(optional = false)
    @Column(name = "id_trans_stavka")
    private long idTransStavka;
    @Basic(optional = false)
    @Column(name = "mpcena_po_cenovniku")
    private BigDecimal mpcenaPoCenovniku;
    @Basic(optional = false)
    @Column(name = "iznos_popusta")
    private BigDecimal iznosPopusta;
    @Basic(optional = false)
    @Column(name = "kolicina_pp")
    private BigDecimal kolicinaPp;
    @Basic(optional = false)
    @Column(name = "mpcena_za_pp")
    private BigDecimal mpcenaZaPp;
    @Basic(optional = false)
    @Column(name = "plancena_za_pp")
    private BigDecimal plancenaZaPp;
    @Basic(optional = false)
    @Column(name = "nabcena_za_pp")
    private BigDecimal nabcenaZaPp;
    @Basic(optional = false)
    @Column(name = "porez_pp")
    private BigDecimal porezPp;
    @Basic(optional = false)
    @Column(name = "popust_pp")
    private BigDecimal popustPp;
    @Basic(optional = false)
    @Column(name = "mpcena_za_pp_po_cen")
    private BigDecimal mpcenaZaPpPoCen;
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

    public MprRazduzenjeStavka() {
    }

    public MprRazduzenjeStavka(Long id) {
        this.id = id;
    }

    public MprRazduzenjeStavka(Long id, String brojdok, Date datdok, String oblast, String kategorija, String brojnal, boolean duguje, int rb, boolean storno, boolean rezervacijautoku, String artikal, String tipIzboraArtikla, String izbornasifra, String pozicija, String opis, String jm, String klas, String magIzdatnice, BigDecimal kolicina, BigDecimal iznos, BigDecimal cena, BigDecimal plancena, BigDecimal prodcena, BigDecimal prodcena1, BigDecimal prodcena2, BigDecimal prodcena3, BigDecimal proscena, BigDecimal kalkcena, String kontigent, BigDecimal mpcena, BigDecimal nabcena, Date datproiz, Date datvazido, String smena, long serod, long serdo, String semaKnjizenja, Date tsProizvodnja, String zaUpotrebu, String porstopa, long idPorstopa, BigDecimal iznospdv, String infoPc, long idTransakcija, long idTransStavka, BigDecimal mpcenaPoCenovniku, BigDecimal iznosPopusta, BigDecimal kolicinaPp, BigDecimal mpcenaZaPp, BigDecimal plancenaZaPp, BigDecimal nabcenaZaPp, BigDecimal porezPp, BigDecimal popustPp, BigDecimal mpcenaZaPpPoCen, boolean vazeci, String slike, String zvuk, String video, String username, Date ts, Object host, boolean aktivan) {
        this.id = id;
        this.brojdok = brojdok;
        this.datdok = datdok;
        this.oblast = oblast;
        this.kategorija = kategorija;
        this.brojnal = brojnal;
        this.duguje = duguje;
        this.rb = rb;
        this.storno = storno;
        this.rezervacijautoku = rezervacijautoku;
        this.artikal = artikal;
        this.tipIzboraArtikla = tipIzboraArtikla;
        this.izbornasifra = izbornasifra;
        this.pozicija = pozicija;
        this.opis = opis;
        this.jm = jm;
        this.klas = klas;
        this.magIzdatnice = magIzdatnice;
        this.kolicina = kolicina;
        this.iznos = iznos;
        this.cena = cena;
        this.plancena = plancena;
        this.prodcena = prodcena;
        this.prodcena1 = prodcena1;
        this.prodcena2 = prodcena2;
        this.prodcena3 = prodcena3;
        this.proscena = proscena;
        this.kalkcena = kalkcena;
        this.kontigent = kontigent;
        this.mpcena = mpcena;
        this.nabcena = nabcena;
        this.datproiz = datproiz;
        this.datvazido = datvazido;
        this.smena = smena;
        this.serod = serod;
        this.serdo = serdo;
        this.semaKnjizenja = semaKnjizenja;
        this.tsProizvodnja = tsProizvodnja;
        this.zaUpotrebu = zaUpotrebu;
        this.porstopa = porstopa;
        this.idPorstopa = idPorstopa;
        this.iznospdv = iznospdv;
        this.infoPc = infoPc;
        this.idTransakcija = idTransakcija;
        this.idTransStavka = idTransStavka;
        this.mpcenaPoCenovniku = mpcenaPoCenovniku;
        this.iznosPopusta = iznosPopusta;
        this.kolicinaPp = kolicinaPp;
        this.mpcenaZaPp = mpcenaZaPp;
        this.plancenaZaPp = plancenaZaPp;
        this.nabcenaZaPp = nabcenaZaPp;
        this.porezPp = porezPp;
        this.popustPp = popustPp;
        this.mpcenaZaPpPoCen = mpcenaZaPpPoCen;
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

    public boolean getRezervacijautoku() {
        return rezervacijautoku;
    }

    public void setRezervacijautoku(boolean rezervacijautoku) {
        this.rezervacijautoku = rezervacijautoku;
    }

    public String getArtikal() {
        return artikal;
    }

    public void setArtikal(String artikal) {
        this.artikal = artikal;
    }

    public String getTipIzboraArtikla() {
        return tipIzboraArtikla;
    }

    public void setTipIzboraArtikla(String tipIzboraArtikla) {
        this.tipIzboraArtikla = tipIzboraArtikla;
    }

    public String getIzbornasifra() {
        return izbornasifra;
    }

    public void setIzbornasifra(String izbornasifra) {
        this.izbornasifra = izbornasifra;
    }

    public String getPozicija() {
        return pozicija;
    }

    public void setPozicija(String pozicija) {
        this.pozicija = pozicija;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getJm() {
        return jm;
    }

    public void setJm(String jm) {
        this.jm = jm;
    }

    public String getKlas() {
        return klas;
    }

    public void setKlas(String klas) {
        this.klas = klas;
    }

    public String getMagIzdatnice() {
        return magIzdatnice;
    }

    public void setMagIzdatnice(String magIzdatnice) {
        this.magIzdatnice = magIzdatnice;
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

    public String getKontigent() {
        return kontigent;
    }

    public void setKontigent(String kontigent) {
        this.kontigent = kontigent;
    }

    public BigDecimal getMpcena() {
        return mpcena;
    }

    public void setMpcena(BigDecimal mpcena) {
        this.mpcena = mpcena;
    }

    public BigDecimal getNabcena() {
        return nabcena;
    }

    public void setNabcena(BigDecimal nabcena) {
        this.nabcena = nabcena;
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

    public long getSerod() {
        return serod;
    }

    public void setSerod(long serod) {
        this.serod = serod;
    }

    public long getSerdo() {
        return serdo;
    }

    public void setSerdo(long serdo) {
        this.serdo = serdo;
    }

    public String getSemaKnjizenja() {
        return semaKnjizenja;
    }

    public void setSemaKnjizenja(String semaKnjizenja) {
        this.semaKnjizenja = semaKnjizenja;
    }

    public Date getTsProizvodnja() {
        return tsProizvodnja;
    }

    public void setTsProizvodnja(Date tsProizvodnja) {
        this.tsProizvodnja = tsProizvodnja;
    }

    public String getZaUpotrebu() {
        return zaUpotrebu;
    }

    public void setZaUpotrebu(String zaUpotrebu) {
        this.zaUpotrebu = zaUpotrebu;
    }

    public String getPorstopa() {
        return porstopa;
    }

    public void setPorstopa(String porstopa) {
        this.porstopa = porstopa;
    }

    public long getIdPorstopa() {
        return idPorstopa;
    }

    public void setIdPorstopa(long idPorstopa) {
        this.idPorstopa = idPorstopa;
    }

    public BigDecimal getIznospdv() {
        return iznospdv;
    }

    public void setIznospdv(BigDecimal iznospdv) {
        this.iznospdv = iznospdv;
    }

    public String getInfoPc() {
        return infoPc;
    }

    public void setInfoPc(String infoPc) {
        this.infoPc = infoPc;
    }

    public long getIdTransakcija() {
        return idTransakcija;
    }

    public void setIdTransakcija(long idTransakcija) {
        this.idTransakcija = idTransakcija;
    }

    public long getIdTransStavka() {
        return idTransStavka;
    }

    public void setIdTransStavka(long idTransStavka) {
        this.idTransStavka = idTransStavka;
    }

    public BigDecimal getMpcenaPoCenovniku() {
        return mpcenaPoCenovniku;
    }

    public void setMpcenaPoCenovniku(BigDecimal mpcenaPoCenovniku) {
        this.mpcenaPoCenovniku = mpcenaPoCenovniku;
    }

    public BigDecimal getIznosPopusta() {
        return iznosPopusta;
    }

    public void setIznosPopusta(BigDecimal iznosPopusta) {
        this.iznosPopusta = iznosPopusta;
    }

    public BigDecimal getKolicinaPp() {
        return kolicinaPp;
    }

    public void setKolicinaPp(BigDecimal kolicinaPp) {
        this.kolicinaPp = kolicinaPp;
    }

    public BigDecimal getMpcenaZaPp() {
        return mpcenaZaPp;
    }

    public void setMpcenaZaPp(BigDecimal mpcenaZaPp) {
        this.mpcenaZaPp = mpcenaZaPp;
    }

    public BigDecimal getPlancenaZaPp() {
        return plancenaZaPp;
    }

    public void setPlancenaZaPp(BigDecimal plancenaZaPp) {
        this.plancenaZaPp = plancenaZaPp;
    }

    public BigDecimal getNabcenaZaPp() {
        return nabcenaZaPp;
    }

    public void setNabcenaZaPp(BigDecimal nabcenaZaPp) {
        this.nabcenaZaPp = nabcenaZaPp;
    }

    public BigDecimal getPorezPp() {
        return porezPp;
    }

    public void setPorezPp(BigDecimal porezPp) {
        this.porezPp = porezPp;
    }

    public BigDecimal getPopustPp() {
        return popustPp;
    }

    public void setPopustPp(BigDecimal popustPp) {
        this.popustPp = popustPp;
    }

    public BigDecimal getMpcenaZaPpPoCen() {
        return mpcenaZaPpPoCen;
    }

    public void setMpcenaZaPpPoCen(BigDecimal mpcenaZaPpPoCen) {
        this.mpcenaZaPpPoCen = mpcenaZaPpPoCen;
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
        if (!(object instanceof MprRazduzenjeStavka)) {
            return false;
        }
        MprRazduzenjeStavka other = (MprRazduzenjeStavka) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "forms_pos.MprRazduzenjeStavka[ id=" + id + " ]";
    }
    
}
