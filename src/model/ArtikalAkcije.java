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
public class ArtikalAkcije {
    
    
    private Integer id;
    private String sifra;
    private Double mpcena;
    private Double mpcena_akcijska;
    private String datum_akcije_od;
    private String datum_akcije_do;
    private int aktivan;
    private String slike;
    private String zvuk;
    private String video;

    public ArtikalAkcije(int aInt, String string, String string0, String string1, double aDouble, double aDouble0, String string2, String string3, int aInt0, String string4, String string5, String string6) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public Double getMpcena() {
        return mpcena;
    }

    public void setMpcena(Double mpcena) {
        this.mpcena = mpcena;
    }

    public Double getMpcena_akcijska() {
        return mpcena_akcijska;
    }

    public void setMpcena_akcijska(Double mpcena_akcijska) {
        this.mpcena_akcijska = mpcena_akcijska;
    }

    public String getDatum_akcije_od() {
        return datum_akcije_od;
    }

    public void setDatum_akcije_od(String datum_akcije_od) {
        this.datum_akcije_od = datum_akcije_od;
    }

    public String getDatum_akcije_do() {
        return datum_akcije_do;
    }

    public void setDatum_akcije_do(String datum_akcije_do) {
        this.datum_akcije_do = datum_akcije_do;
    }

    public int getAktivan() {
        return aktivan;
    }

    public void setAktivan(int aktivan) {
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

    // public ArtikalAkcije(int aInt, String string, String string0, String string1, double aDouble, double aDouble0, String string2, String string3, int aInt0, String string4, String string5, String string6) {    
    // public ArtikalAkcije(Integer id, String sifra, Double mpcena, Double mpcena_akcijska, String datum_akcije_od, String datum_akcije_do, int aktivan, String slike, String zvuk, String video) {
    public ArtikalAkcije(int id, String sifra, Double mpcena, Double mpcena_akcijska, String datum_akcije_od, String datum_akcije_do, int aktivan, String slike, String zvuk, String video) {        
    
        this.id = id;
        this.sifra = sifra;
        this.mpcena = mpcena;
        this.mpcena_akcijska = mpcena_akcijska;
        this.datum_akcije_od = datum_akcije_od;
        this.datum_akcije_do = datum_akcije_do;
        this.aktivan = aktivan;
        this.slike = slike;
        this.zvuk = zvuk;
        this.video = video;
    }

    
}
