/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionDB;

import Object.Depo;
import Object.Kassa;
import Object.Kateqoriya;
import Object.Mallar;
import Object.Members;
import Object.Musteri;
import Object.Satis;
import Object.SatisNovu;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Pallas
 */
public class ConnMySql {

    private String Connection;
    private Connection connection;
    private Statement st;

    public ConnMySql() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("conn.txt"))) {

                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                    Connection = sCurrentLine;
                }
            } catch (IOException e) {
            }
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + Connection + ":3306/magaza";
            String user = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, user, password);
            st = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1000");
        }
    }

//-------------------------------I-N-S-E-R-T------------------------------------
    public void DepoInsertUpdate(Depo f) {
        try {
            String SqlEmri;
            if (f.idDepo == 0) {
                SqlEmri = "INSERT into Depo (Sayi, idMallar, idMember) values("
                        + "'" + f.Sayi + "',"
                        + "'" + f.idMallar + "',"
                        + "'" + f.idMember + "')";
            } else {
                SqlEmri = "UPDATE Depo SET "
                        + "Sayi='" + f.Sayi + "',"
                        + "idMallar='" + f.idMallar + "',"
                        + "idMember='" + f.idMember + "'"
                        + "WHERE idDepo='" + f.idDepo + "'";
            }

            st.execute(SqlEmri);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1100");
        }
    }

    public void KassaInsertUpdate(Kassa f) {
        try {
            String SqlEmri;
            if (f.idKassa == 0) {
                SqlEmri = "INSERT into Kassa (Borc, Tarix, idMember, idSatisNovu ,idMusteri) values("
                        + "'" + f.Borc + "',"
                        + "'" + f.Tarix + "',"
                        + "'" + f.idMember + "',"
                        + "'" + f.idSatisNovu + "',"
                        + "'" + f.idMusteri + "')";
            } else {
                SqlEmri = "UPDATE Kassa SET "
                        + "Borc='" + f.Borc + "',"
                        + "Tarix='" + f.Tarix + "',"
                        + "idMember='" + f.idMember + "',"
                        + "idSatisNovu='" + f.idSatisNovu + "',"
                        + "idMusteri='" + f.idMusteri + "'"
                        + "WHERE idKassa='" + f.idKassa + "'";
            }

            st.execute(SqlEmri);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1200");
        }
    }

    public void KateqoriyaInsertUpdate(Kateqoriya f) {
        try {
            String SqlEmri;
            if (f.idKateqoriya == 0) {
                SqlEmri = "INSERT into Kateqoriya (Ad) values("
                        + "'" + f.Ad + "')";
            } else {
                SqlEmri = "UPDATE Kateqoriya SET "
                        + "Ad='" + f.Ad + "'"
                        + "WHERE idKateqoriya='" + f.idKateqoriya + "'";
            }

            st.execute(SqlEmri);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1300");
        }
    }

    public void MallarInsertUpdate(Mallar f) {
        try {
            String SqlEmri;
            if (f.idMallar == 0) {
                SqlEmri = "INSERT into Mallar (Ad, Barkod, Cekisi, AlisQiymeti, SatisQiymeti, isActive, idKateqoriya) values("
                        + "'" + f.Ad + "',"
                        + "'" + f.Barkod + "',"
                        + "'" + f.Cekisi + "',"
                        + "'" + f.AlisQiymeti + "',"
                        + "'" + f.SatisQiymeti + "',"
                        + "'" + f.isActive + "',"
                        + "'" + f.idKateqoriya + "')";
            } else {
                SqlEmri = "UPDATE Mallar SET "
                        + "Ad='" + f.Ad + "',"
                        + "Barkod='" + f.Barkod + "',"
                        + "Cekisi='" + f.Cekisi + "',"
                        + "AlisQiymeti='" + f.AlisQiymeti + "',"
                        + "SatisQiymeti='" + f.SatisQiymeti + "',"
                        + "isActive='" + f.isActive + "',"
                        + "idKateqoriya='" + f.idKateqoriya + "'"
                        + "WHERE idMallar='" + f.idMallar + "'";
            }

            st.execute(SqlEmri);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1400");
        }
    }

    public void MembersInsertUpdate(Members f) {
        try {
            String SqlEmri;
            if (f.idMember == 0) {
                SqlEmri = "INSERT into Member (Ad, isActive, Password, Soyad, Telefon) values("
                        + "'" + f.Ad + "',"
                        + "'" + f.isActive + "',"
                        + "'" + f.Password + "',"
                        + "'" + f.Soyad + "',"
                        + "'" + f.Telefon + "')";
            } else {
                SqlEmri = "UPDATE Member SET "
                        + "Ad='" + f.Ad + "',"
                        + "isActive='" + f.isActive + "',"
                        + "Password='" + f.Password + "',"
                        + "Soyad='" + f.Soyad + "',"
                        + "Telefon='" + f.Telefon + "'"
                        + "WHERE idMember='" + f.idMember + "'";
            }

            st.execute(SqlEmri);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1500");
        }
    }

    public void MusteriInsertUpdate(Musteri f) {
        try {
            String SqlEmri;
            if (f.idMusteri == 0) {
                SqlEmri = "INSERT into Musteri (Ad, Qeyd, Soyad, Telefon) values("
                        + "'" + f.Ad + "',"
                        + "'" + f.Qeyd + "',"
                        + "'" + f.Soyad + "',"
                        + "'" + f.Telefon + "')";
            } else {
                SqlEmri = "UPDATE Musteri SET "
                        + "Ad='" + f.Ad + "',"
                        + "Qeyd='" + f.Qeyd + "',"
                        + "Soyad='" + f.Soyad + "',"
                        + "Telefon='" + f.Telefon + "'"
                        + "WHERE idMusteri='" + f.idMusteri + "'";
            }

            st.execute(SqlEmri);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1600");
        }
    }

    public void SatisInsertUpdate(Satis f) {
        try {
            String SqlEmri;
            if (f.idMusteri == 0) {
                SqlEmri = "INSERT into Satis (idKassa, idMallar, idMusteri) values("
                        + "'" + f.idKassa + "',"
                        + "'" + f.idMallar + "',"
                        + "'" + f.idMusteri + "')";
            } else {
                SqlEmri = "UPDATE Satis SET "
                        + "idKassa='" + f.idKassa + "',"
                        + "idMallar='" + f.idMallar + "',"
                        + "idMusteri='" + f.idMusteri + "'"
                        + "WHERE idSatis='" + f.idSatis + "'";
            }

            st.execute(SqlEmri);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1700");
        }
    }

    public void SatisNovuInsertUpdate(SatisNovu f) {
        try {
            String SqlEmri;
            if (f.idSatisNovu == 0) {
                SqlEmri = "INSERT into SatisNovu (Ad) values("
                        + "'" + f.Ad + "')";
            } else {
                SqlEmri = "UPDATE SatisNovu SET "
                        + "Ad='" + f.Ad + "'"
                        + "WHERE idSatisNovu='" + f.idSatisNovu + "'";
            }

            st.execute(SqlEmri);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1800");
        }
    }
    
//-----------------------S-E-L-E-C-T---F-O-R------------------------------------
    
    private List<Depo> Depo(String where) {
        try {
            String SqlEmri = "SELECT * from Depo " + where;
            List<Depo> list = new ArrayList<>();
            ResultSet rs = st.executeQuery(SqlEmri);
            while (rs.next()) {

                list.add(new Depo(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));

            }
            return list;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1110");
            return null;
        }
    }
    
    private List<Kassa> Kassa(String where) {
        try {
            String SqlEmri = "SELECT * from Kassa " + where;
            List<Kassa> list = new ArrayList<>();
            ResultSet rs = st.executeQuery(SqlEmri);
            while (rs.next()) {

                list.add(new Kassa(rs.getInt(1), rs.getDouble(2), rs.getDate(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));

            }
            return list;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1210");
            return null;
        }
    }
    
    private List<Kateqoriya> Kateqoriya(String where) {
        try {
            String SqlEmri = "SELECT * from Kateqoriya " + where;
            List<Kateqoriya> list = new ArrayList<>();
            ResultSet rs = st.executeQuery(SqlEmri);
            while (rs.next()) {

                list.add(new Kateqoriya(rs.getInt(1), rs.getString(2)));

            }
            return list;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1310");
            return null;
        }
    }
    
    private List<Mallar> Mallar(String where) {
        try {
            String SqlEmri = "SELECT * from Mallar " + where;
            List<Mallar> list = new ArrayList<>();
            ResultSet rs = st.executeQuery(SqlEmri);
            while (rs.next()) {

                list.add(new Mallar(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getInt(8)));

            }
            return list;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1410");
            return null;
        }
    }
    
    private List<Members> Members(String where) {
        try {
            String SqlEmri = "SELECT * from Member " + where;
            List<Members> list = new ArrayList<>();
            ResultSet rs = st.executeQuery(SqlEmri);
            while (rs.next()) {

                list.add(new Members(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

            }
            return list;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1510");
            return null;
        }
    }
    
    private List<Musteri> Musteri(String where) {
        try {
            String SqlEmri = "SELECT * from Musteri " + where;
            List<Musteri> list = new ArrayList<>();
            ResultSet rs = st.executeQuery(SqlEmri);
            while (rs.next()) {

                list.add(new Musteri(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));

            }
            return list;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1610");
            return null;
        }
    }
    
    private List<Satis> Satis(String where) {
        try {
            String SqlEmri = "SELECT * from Satis " + where;
            List<Satis> list = new ArrayList<>();
            ResultSet rs = st.executeQuery(SqlEmri);
            while (rs.next()) {

                list.add(new Satis(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)));

            }
            return list;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1710");
            return null;
        }
    }
    
    private List<SatisNovu> SatisNovu(String where) {
        try {
            String SqlEmri = "SELECT * from SatisNovu " + where;
            List<SatisNovu> list = new ArrayList<>();
            ResultSet rs = st.executeQuery(SqlEmri);
            while (rs.next()) {

                list.add(new SatisNovu(rs.getInt(1), rs.getString(2)));

            }
            return list;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: 1810");
            return null;
        }
    }
    
//-------------S-E-L-E-C-T----W-I-T-H----P-A-R-A-M-E-T-R------------------------

    public List<Depo> DepoFindAll() {
        return Depo("");
    }

    public Depo DepoFindByIdDepo(int ID) {
        return Depo("where idDepo=" + ID).get(0);
    }
    
    public List<Kassa> KassaFindAll() {
        return Kassa("");
    }

    public Kassa KassaFindByIdKassa(int ID) {
        return Kassa("where idKassa=" + ID).get(0);
    }
    
    public List<Kateqoriya> KateqoriyaFindAll() {
        return Kateqoriya("");
    }

    public Kateqoriya KateqoriyaFindByIdKateqoriya(int ID) {
        return Kateqoriya("where idKateqoriya=" + ID).get(0);
    }
    
    public List<Mallar> MallarFindAll() {
        return Mallar("");
    }

    public Mallar MallarFindByIdMallar(int ID) {
        return Mallar("where idMallar=" + ID).get(0);
    }
    
    public List<Members> MembersFindAll() {
        return Members("");
    }

    public Members MembersFindByIdMembers(int ID) {
        return Members("where idMembers=" + ID).get(0);
    }
    
    public List<Musteri> MusteriFindAll() {
        return Musteri("");
    }

    public Musteri MusteriFindByIdMusteri(int ID) {
        return Musteri("where idMusteri=" + ID).get(0);
    }
    
    public List<Satis> SatisFindAll() {
        return Satis("");
    }

    public Satis SatisFindByIdSatis(int ID) {
        return Satis("where idSatis=" + ID).get(0);
    }
    
    public List<SatisNovu> SatisNovuFindAll() {
        return SatisNovu("");
    }

    public SatisNovu SatisNovuFindByIdSatisNovu(int ID) {
        return SatisNovu("where idSatis=" + ID).get(0);
    }

}
