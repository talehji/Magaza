/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Entity.Kateqoriya;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.DefaultListModel;

/**
 *
 * @author Pallas
 */
public final class ScreenKateqoriya extends javax.swing.JDialog {

    private final EntityManager em;

    /**
     * Creates new form ScreenKateqoriya
     *
     * @param parent
     * @param modal
     */
    public ScreenKateqoriya(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        buttonandtextfield(true, false);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagazaPU");
        em = emf.createEntityManager();
        FillTheTableDaxil();
    }

    public void buttonandtextfield(boolean d, boolean b) {
        jButtonYeni.setEnabled(d);
        jButtonYaddasaxla.setEnabled(b);
        jButtonLegvEt.setEnabled(b);
        jTextField1.setEnabled(b);
    }

    private void FillTheTableDaxil() {
        DefaultListModel lmodel = new DefaultListModel();
        List<Kateqoriya> ListOfKateqoriya = em.createNamedQuery("Kateqoriya.findAll", Kateqoriya.class).getResultList();
        for (Kateqoriya kateqoriya : ListOfKateqoriya) {
            lmodel.addElement(kateqoriya.getAd());
        }
//        em.getTransaction().begin();
//        em.getTransaction().commit();
        jList1.setModel(lmodel);


    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jTextField1 = new javax.swing.JTextField();
        jButtonYeni = new javax.swing.JButton();
        jButtonYaddasaxla = new javax.swing.JButton();
        jButtonLegvEt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jScrollPane1.setViewportView(jList1);

        jButtonYeni.setText("Yeni");
        jButtonYeni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonYeniActionPerformed(evt);
            }
        });

        jButtonYaddasaxla.setText("Yadda Saxla");
        jButtonYaddasaxla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonYaddasaxlaActionPerformed(evt);
            }
        });

        jButtonLegvEt.setText("Ləğv et");
        jButtonLegvEt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLegvEtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonYeni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonYaddasaxla)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonLegvEt)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonYeni)
                    .addComponent(jButtonYaddasaxla)
                    .addComponent(jButtonLegvEt))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void jButtonYeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonYeniActionPerformed
        buttonandtextfield(false, true);
    }//GEN-LAST:event_jButtonYeniActionPerformed

    private void jButtonLegvEtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLegvEtActionPerformed
        buttonandtextfield(true, false);
        this.dispose();
    }//GEN-LAST:event_jButtonLegvEtActionPerformed

    private void jButtonYaddasaxlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonYaddasaxlaActionPerformed
        buttonandtextfield(true, false);
        Entity.Kateqoriya k = new Entity.Kateqoriya(0);
        k.setAd(jTextField1.getText());
        em.persist(k);
        em.getTransaction().begin();
        em.getTransaction().commit();
        
        FillTheTableDaxil();
        jTextField1.setText(null);
        
    }//GEN-LAST:event_jButtonYaddasaxlaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ScreenKateqoriya.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ScreenKateqoriya.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ScreenKateqoriya.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScreenKateqoriya.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ScreenKateqoriya dialog = new ScreenKateqoriya(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLegvEt;
    private javax.swing.JButton jButtonYaddasaxla;
    private javax.swing.JButton jButtonYeni;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
