/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ms
 */
@Entity
@Table(name = "partneri")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partneri.findAll", query = "SELECT p FROM Partneri p"),
    @NamedQuery(name = "Partneri.findById", query = "SELECT p FROM Partneri p WHERE p.id = :id"),
    @NamedQuery(name = "Partneri.findBySifra", query = "SELECT p FROM Partneri p WHERE p.sifra = :sifra"),
    @NamedQuery(name = "Partneri.findByMaticniBroj", query = "SELECT p FROM Partneri p WHERE p.maticniBroj = :maticniBroj"),
    @NamedQuery(name = "Partneri.findByPib", query = "SELECT p FROM Partneri p WHERE p.pib = :pib"),
    @NamedQuery(name = "Partneri.findByJmbg", query = "SELECT p FROM Partneri p WHERE p.jmbg = :jmbg"),
    @NamedQuery(name = "Partneri.findByNaziv", query = "SELECT p FROM Partneri p WHERE p.naziv = :naziv"),
    @NamedQuery(name = "Partneri.findByKraciNaziv", query = "SELECT p FROM Partneri p WHERE p.kraciNaziv = :kraciNaziv"),
    @NamedQuery(name = "Partneri.findByAdresa", query = "SELECT p FROM Partneri p WHERE p.adresa = :adresa"),
    @NamedQuery(name = "Partneri.findByZemlja", query = "SELECT p FROM Partneri p WHERE p.zemlja = :zemlja"),
    @NamedQuery(name = "Partneri.findByZemljaId", query = "SELECT p FROM Partneri p WHERE p.zemljaId = :zemljaId"),
    @NamedQuery(name = "Partneri.findByPttBroj", query = "SELECT p FROM Partneri p WHERE p.pttBroj = :pttBroj"),
    @NamedQuery(name = "Partneri.findByPttBrojId", query = "SELECT p FROM Partneri p WHERE p.pttBrojId = :pttBrojId"),
    @NamedQuery(name = "Partneri.findByTelefon", query = "SELECT p FROM Partneri p WHERE p.telefon = :telefon"),
    @NamedQuery(name = "Partneri.findByFax", query = "SELECT p FROM Partneri p WHERE p.fax = :fax"),
    @NamedQuery(name = "Partneri.findByEmail", query = "SELECT p FROM Partneri p WHERE p.email = :email"),
    @NamedQuery(name = "Partneri.findByWeb", query = "SELECT p FROM Partneri p WHERE p.web = :web"),
    @NamedQuery(name = "Partneri.findByKategorijaPartneriId", query = "SELECT p FROM Partneri p WHERE p.kategorijaPartneriId = :kategorijaPartneriId"),
    @NamedQuery(name = "Partneri.findByKategorijaPartneri", query = "SELECT p FROM Partneri p WHERE p.kategorijaPartneri = :kategorijaPartneri"),
    @NamedQuery(name = "Partneri.findBySlike", query = "SELECT p FROM Partneri p WHERE p.slike = :slike"),
    @NamedQuery(name = "Partneri.findByZvuk", query = "SELECT p FROM Partneri p WHERE p.zvuk = :zvuk"),
    @NamedQuery(name = "Partneri.findByVideo", query = "SELECT p FROM Partneri p WHERE p.video = :video"),
    @NamedQuery(name = "Partneri.findByAktivan", query = "SELECT p FROM Partneri p WHERE p.aktivan = :aktivan"),
    @NamedQuery(name = "Partneri.findByPdv", query = "SELECT p FROM Partneri p WHERE p.pdv = :pdv")})
public class Partneri implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "sifra")
    private String sifra;
    @Column(name = "maticni_broj")
    private String maticniBroj;
    @Column(name = "pib")
    private String pib;
    @Column(name = "jmbg")
    private String jmbg;
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "kraci_naziv")
    private String kraciNaziv;
    @Column(name = "adresa")
    private String adresa;
    @Column(name = "zemlja")
    private String zemlja;
    @Column(name = "zemlja_id")
    private Integer zemljaId;
    @Column(name = "ptt_broj")
    private String pttBroj;
    @Column(name = "ptt_broj_id")
    private Integer pttBrojId;
    @Column(name = "telefon")
    private String telefon;
    @Column(name = "fax")
    private String fax;
    @Column(name = "email")
    private String email;
    @Column(name = "web")
    private String web;
    @Column(name = "kategorija_partneri_id")
    private Integer kategorijaPartneriId;
    @Column(name = "kategorija_partneri")
    private String kategorijaPartneri;
    @Column(name = "slike")
    private String slike;
    @Column(name = "zvuk")
    private String zvuk;
    @Column(name = "video")
    private String video;
    @Column(name = "aktivan")
    private Boolean aktivan;
    @Column(name = "PDV")
    private String pdv;

    public Partneri() {
    }

    public Partneri(Integer id) {
        this.id = id;
    }

    public Partneri(Integer id, String sifra, String naziv, String adresa,String pttBroj, String zemlja, Boolean aktivan) {
        this.id = id;
        this.sifra = sifra;
        this.naziv = naziv;
        this.adresa = adresa;
        this.pttBroj = pttBroj;
        this.zemlja = zemlja;
        this.aktivan = aktivan;
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

    public String getMaticniBroj() {
        return maticniBroj;
    }

    public void setMaticniBroj(String maticniBroj) {
        this.maticniBroj = maticniBroj;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getKraciNaziv() {
        return kraciNaziv;
    }

    public void setKraciNaziv(String kraciNaziv) {
        this.kraciNaziv = kraciNaziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getZemlja() {
        return zemlja;
    }

    public void setZemlja(String zemlja) {
        this.zemlja = zemlja;
    }

    public Integer getZemljaId() {
        return zemljaId;
    }

    public void setZemljaId(Integer zemljaId) {
        this.zemljaId = zemljaId;
    }

    public String getPttBroj() {
        return pttBroj;
    }

    public void setPttBroj(String pttBroj) {
        this.pttBroj = pttBroj;
    }

    public Integer getPttBrojId() {
        return pttBrojId;
    }

    public void setPttBrojId(Integer pttBrojId) {
        this.pttBrojId = pttBrojId;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Integer getKategorijaPartneriId() {
        return kategorijaPartneriId;
    }

    public void setKategorijaPartneriId(Integer kategorijaPartneriId) {
        this.kategorijaPartneriId = kategorijaPartneriId;
    }

    public String getKategorijaPartneri() {
        return kategorijaPartneri;
    }

    public void setKategorijaPartneri(String kategorijaPartneri) {
        this.kategorijaPartneri = kategorijaPartneri;
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

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    public String getPdv() {
        return pdv;
    }

    public void setPdv(String pdv) {
        this.pdv = pdv;
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
        if (!(object instanceof Partneri)) {
            return false;
        }
        Partneri other = (Partneri) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Partneri[ id=" + id + " ]";
    }
    
}
