/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Classes.Baza;
import Classes.TableAdd;
import Entity.Kassa;
import Entity.Mallar;
import Entity.Menber;
import Entity.Musteri;
import Entity.Satis;
import java.awt.Toolkit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pallas
 */
public class ScreenSatici extends javax.swing.JDialog {

    private final EntityManager em;
    private List<TableAdd> listofbaza;
    private final Menber menber;

    public ScreenSatici(java.awt.Frame parent, boolean modal, Menber menber) {
        super(parent, modal);
        initComponents();
        this.menber = menber;
        jLabelIshchiAdi.setText(menber.getAd() + " " + menber.getSoyad());
//        this.setExtendedState(ScreenSatici.MAXIMIZED_BOTH);
        Toolkit d = Toolkit.getDefaultToolkit();
        int x = (int) d.getScreenSize().getWidth();
        int y = (int) d.getScreenSize().getHeight();
        this.setSize(x, y);
        this.setAlwaysOnTop(true);
        jTextFieldBarkod.requestFocusInWindow();
        FillTheTableDaxil();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagazaPU");
        em = emf.createEntityManager();
    }

    private void FillTheTableDaxil() {
        DefaultTableModel tmodel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        tmodel.addColumn("Id");
        tmodel.addColumn("Ad");
        tmodel.addColumn("Qiyməti");

        jTable1.setModel(tmodel);

        listofbaza = Baza.ListOfTable;

        for (TableAdd b : listofbaza) {
            tmodel.insertRow(jTable1.getRowCount(), new Object[]{
                b.id,
                b.Ad,
                b.Qiymet
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jTextFieldCemQiymet = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldBarkod = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabelIshchiAdi = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);

        jTextFieldCemQiymet.setEditable(false);
        jTextFieldCemQiymet.setBackground(new java.awt.Color(0, 0, 0));
        jTextFieldCemQiymet.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jTextFieldCemQiymet.setForeground(new java.awt.Color(0, 204, 51));
        jTextFieldCemQiymet.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldCemQiymet.setText("0.00");
        jTextFieldCemQiymet.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldCemQiymet.setSelectedTextColor(new java.awt.Color(0, 194, 16));
        jTextFieldCemQiymet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCemQiymetActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Barkod");

        jTextFieldBarkod.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldBarkod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBarkodActionPerformed(evt);
            }
        });

        jButton1.setText("Çıxış");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabelIshchiAdi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelIshchiAdi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIshchiAdi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldBarkod, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelIshchiAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTextFieldCemQiymet, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextFieldBarkod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1)
                        .addComponent(jLabelIshchiAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldCemQiymet, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCemQiymetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCemQiymetActionPerformed

    }//GEN-LAST:event_jTextFieldCemQiymetActionPerformed


    private void jTextFieldBarkodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBarkodActionPerformed
        ScreenMusteri r = new ScreenMusteri(null, rootPaneCheckingEnabled);
        if (jTextFieldBarkod.getText().equals("")) {
            ScreenChekout d = new ScreenChekout(null, rootPaneCheckingEnabled, menber);
            this.setAlwaysOnTop(false);

            d.setVisible(rootPaneCheckingEnabled);
            this.setAlwaysOnTop(true);
            Object a = em.createQuery("select max(u.idKassa) from Kassa u", Integer.class).getSingleResult();
            System.out.println(r.selectedMushteri);

            switch (d.Status) {
                case 3:
                    System.out.println("");
                    break;
                case 1:
                    for (TableAdd t : listofbaza) {
                        Entity.Satis f = new Satis(0);
                        f.setIdKassa(em.find(Kassa.class, a));
                        f.setIdMallar(em.find(Mallar.class, t.id));
                        f.setIdMusteri(em.find(Musteri.class, 1));
                        System.out.println("alindi negd");
                        em.persist(f);
                        em.getTransaction().begin();
                        em.getTransaction().commit();
                    }   break;
                case 2:
                    for (TableAdd t : listofbaza) {
                        Entity.Satis f = new Satis(0);
                        f.setIdKassa(em.find(Kassa.class, a));
                        f.setIdMallar(em.find(Mallar.class, t.id));
                        f.setIdMusteri(em.find(Musteri.class, r.selectedMushteri));
                        System.out.println("alindi nisye");
                        em.persist(f);
                        em.getTransaction().begin();
                        em.getTransaction().commit();
                    }   break;
                default:
                    break;
            }

            Baza.ListOfTable.removeAll(listofbaza);
            jTextFieldCemQiymet.setText("0.00");
            FillTheTableDaxil();

        } else {
            List< Mallar> ListOfMallar = em.createNamedQuery("Mallar.findAll", Mallar.class).getResultList();
            for (Mallar mallar : ListOfMallar) {
                if (mallar.getBarkod().equals(jTextFieldBarkod.getText())) {
                    Baza.ListOfTable.add(new TableAdd(mallar.getIdMallar(), mallar.getAd(), mallar.getQiymeti()));
                }
            }
            int i;
            double sum = 0;
            for (i = 0; i < Baza.ListOfTable.size(); i++) {
                sum += Baza.ListOfTable.get(i).Qiymet;
            }
            jTextFieldCemQiymet.setText("" + sum);
            FillTheTableDaxil();
            jTextFieldBarkod.setText(null);
        }
    }//GEN-LAST:event_jTextFieldBarkodActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ScreenLogin d = new ScreenLogin(null, rootPaneCheckingEnabled);
        this.dispose();
        d.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabelIshchiAdi;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldBarkod;
    private javax.swing.JTextField jTextFieldCemQiymet;
    // End of variables declaration//GEN-END:variables

}