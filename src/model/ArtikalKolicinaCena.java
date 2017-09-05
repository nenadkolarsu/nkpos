/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ms
 */
public class ArtikalKolicinaCena {

    public ArtikalKolicinaCena() {
    }

    public ArtikalKolicinaCena(Integer id, String sifra, String naziv, Double kolicina, Double maloprodajna_cena, Integer aktivan) {
        this.id = id;
        this.sifra = sifra;
        this.naziv = naziv;
        this.kolicina = kolicina;
        this.maloprodajna_cena = maloprodajna_cena;
        this.aktivan = aktivan;
    }

    private Integer id;
    private String sifra;
    private String naziv;
    private Double kolicina;
    private Double maloprodajna_cena;
    private Integer aktivan;

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

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Double getKolicina() {
        return kolicina;
    }

    public void setKolicina(Double kolicina) {
        this.kolicina = kolicina;
    }

    public Double getMaloprodajna_cena() {
        return maloprodajna_cena;
    }

    public void setMaloprodajna_cena(Double maloprodajna_cena) {
        this.maloprodajna_cena = maloprodajna_cena;
    }

    public Integer getAktivan() {
        return aktivan;
    }

    public void setAktivan(Integer aktivan) {
        this.aktivan = aktivan;
    }
    
}
