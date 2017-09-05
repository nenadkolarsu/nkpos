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
public class ArtikalCene {
    
    private Integer id;
    private String artikal;
    private String datum;
    private Double nabavna_cena;
    private Double planska_cena;
    private Double prodajna_cena_sa_pdv;
    private Double prodajna_cena;
    private Double maloprodajna_cena_bez_pdv;
    private Double maloprodajna_cena;
    private Integer aktivan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArtikal() {
        return artikal;
    }

    public void setArtikal(String artikal) {
        this.artikal = artikal;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Double getNabavna_cena() {
        return nabavna_cena;
    }

    public void setNabavna_cena(Double nabavna_cena) {
        this.nabavna_cena = nabavna_cena;
    }

    public Double getPlanska_cena() {
        return planska_cena;
    }

    public void setPlanska_cena(Double planska_cena) {
        this.planska_cena = planska_cena;
    }

    public Double getProdajna_cena_sa_pdv() {
        return prodajna_cena_sa_pdv;
    }

    public void setProdajna_cena_sa_pdv(Double prodajna_cena_sa_pdv) {
        this.prodajna_cena_sa_pdv = prodajna_cena_sa_pdv;
    }

    public Double getProdajna_cena() {
        return prodajna_cena;
    }

    public void setProdajna_cena(Double prodajna_cena) {
        this.prodajna_cena = prodajna_cena;
    }

    public Double getMaloprodajna_cena_bez_pdv() {
        return maloprodajna_cena_bez_pdv;
    }

    public void setMaloprodajna_cena_bez_pdv(Double maloprodajna_cena_bez_pdv) {
        this.maloprodajna_cena_bez_pdv = maloprodajna_cena_bez_pdv;
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

    public ArtikalCene(Integer id, String artikal, String datum, Double nabavna_cena, Double planska_cena, Double prodajna_cena_sa_pdv, Double prodajna_cena, Double maloprodajna_cena_sa_pdv, Double maloprodajna_cena, Integer aktivan) {
        this.id = id;
        this.artikal = artikal;
        this.datum = datum;
        this.nabavna_cena = nabavna_cena;
        this.planska_cena = planska_cena;
        this.prodajna_cena_sa_pdv = prodajna_cena_sa_pdv;
        this.prodajna_cena = prodajna_cena;
        this.maloprodajna_cena_bez_pdv = maloprodajna_cena_bez_pdv;
        this.maloprodajna_cena = maloprodajna_cena;
        this.aktivan = aktivan;
    }
 
    
}
