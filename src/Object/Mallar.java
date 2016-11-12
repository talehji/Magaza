/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

/**
 *
 * @author Pallas
 */
public class Mallar {

    public int idMallar;
    public String Ad;
    public String Barkod;
    public String Cekisi;
    public double AlisQiymeti;
    public double SatisQiymeti;
    public String isActive;
    public int idKateqoriya;

    public Mallar() {
    }

    public Mallar(int idMallar, String Ad, String Barkod, String Cekisi, double AlisQiymeti, double SatisQiymeti, String isActive, int idKateqoriya) {
        this.idMallar = idMallar;
        this.Ad = Ad;
        this.Barkod = Barkod;
        this.Cekisi = Cekisi;
        this.AlisQiymeti = AlisQiymeti;
        this.SatisQiymeti = SatisQiymeti;
        this.isActive = isActive;
        this.idKateqoriya = idKateqoriya;
    }

}
