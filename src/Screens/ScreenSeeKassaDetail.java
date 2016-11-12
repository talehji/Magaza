/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Entity.Depo;
import Entity.Kassa;
import Entity.Satis;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pallas
 */
public class ScreenSeeKassaDetail extends javax.swing.JDialog {

    private final EntityManager em;

    /**
     * Creates new form ScreenSeeKassaDetail
     */
    Kassa SelectedKassa;

    public ScreenSeeKassaDetail(java.awt.Frame parent, boolean modal, Kassa k) {
        super(parent, modal);
        initComponents();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagazaPU");
        em = emf.createEntityManager();
        this.SelectedKassa = k;
        FillTheTableDepo();
    }

    private void FillTheTableDepo() {

        DefaultTableModel tmodel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        tmodel.addColumn("İD");
        tmodel.addColumn("Barkod");
        tmodel.addColumn("Kateqoriya");
        tmodel.addColumn("Ad");
        tmodel.addColumn("Müştəri");
        tmodel.addColumn("Tarix");
        tmodel.addColumn("Saat");

        jTable1.setModel(tmodel);

        List<Satis> ListOfDepo = em.createNamedQuery("Satis.findByIdKassa", Satis.class)
                .setParameter("idKassa", em.find(Kassa.class, SelectedKassa.getIdKassa()))
                .getResultList();

        for (Satis b : ListOfDepo) {
            SimpleDateFormat t = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
            tmodel.insertRow(jTable1.getRowCount(), new Object[]{
                b.getIdSatis(),
                b.getIdMallar().getBarkod(),
                b.getIdMallar().getIdKateqoriya().getAd(),
                b.getIdMallar().getAd(),
                b.getIdMusteri().getAdSoyad(),
                t.format(b.getIdKassa().getTarix()),
                s.format(b.getIdKassa().getTarix()),
            });
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
