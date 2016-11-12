/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import java.sql.Date;

/**
 *
 * @author Pallas
 */
public class Kassa {
    public int idKassa;
    public double Borc;
    public Date Tarix;
    public int idMember;
    public int idSatisNovu;
    public int idMusteri;

    public Kassa() {
    }

    public Kassa(int idKassa, double Borc, Date Tarix, int idMember, int idSatisNovu, int idMusteri) {
        this.idKassa = idKassa;
        this.Borc = Borc;
        this.Tarix = Tarix;
        this.idMember = idMember;
        this.idSatisNovu = idSatisNovu;
        this.idMusteri = idMusteri;
    }
}
