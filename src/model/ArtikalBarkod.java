/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Pionir SU
 */
public class ArtikalBarkod {
    
    private Integer id;
    private String tippak;
    private String sifra_artikla;
    private String ean;
    private Double netojm;
    private Double brutojm;
    private Double sirina;
    private Double duzina;
    private Double visina;
    private Double komada;
    private String sifra_ambalaze;
    private Integer aktivan;

    public String getSifra_artikla() {
        return sifra_artikla;
    }

    public void setSifra_artikla(String sifra_artikla) {
        this.sifra_artikla = sifra_artikla;
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

    public Double getNetojm() {
        return netojm;
    }

    public void setNetojm(Double netojm) {
        this.netojm = netojm;
    }

    public Double getBrutojm() {
        return brutojm;
    }

    public void setBrutojm(Double brutojm) {
        this.brutojm = brutojm;
    }

    public Double getSirina() {
        return sirina;
    }

    public void setSirina(Double sirina) {
        this.sirina = sirina;
    }

    public Double getDuzina() {
        return duzina;
    }

    public void setDuzina(Double duzina) {
        this.duzina = duzina;
    }

    public Double getVisina() {
        return visina;
    }

    public void setVisina(Double visina) {
        this.visina = visina;
    }

    public Double getKomada() {
        return komada;
    }

    public void setKomada(Double komada) {
        this.komada = komada;
    }

    public String getSifra_ambalaze() {
        return sifra_ambalaze;
    }

    public void setSifra_ambalaze(String sifra_ambalaze) {
        this.sifra_ambalaze = sifra_ambalaze;
    }

    public Integer getAktivan() {
        return aktivan;
    }

    public void setAktivan(Integer aktivan) {
        this.aktivan = aktivan;
    }

    public ArtikalBarkod(Integer id, String sifra_artikla, String tippak, String ean, 
            Double netojm, Double brutojm, Double sirina, Double duzina, 
            Double visina, Double komada, String sifra_ambalaze, Integer aktivan) {
        
        this.id = id;
        this.tippak = tippak;
        this.sifra_artikla = sifra_artikla;
        this.ean = ean;
        this.netojm = netojm;
        this.brutojm = brutojm;
        this.sirina = sirina;
        this.duzina = duzina;
        this.visina = visina;
        this.komada = komada;
        this.sifra_ambalaze = sifra_ambalaze;
        this.aktivan = aktivan;
    }

    
}
