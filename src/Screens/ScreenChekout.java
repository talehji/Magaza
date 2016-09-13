/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Classes.Baza;
import Classes.TableAdd;
import Entity.Kassa;
import Entity.Menber;
import Entity.Satisnovu;
import java.awt.Color;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pallas
 */
public class ScreenChekout extends javax.swing.JDialog {

    private final EntityManager em;
    private List<TableAdd> listofbaza;
    private Menber menber;
    public int Status;

    /**
     * Creates new form ScreenChekout
     */
    public ScreenChekout(java.awt.Frame parent, boolean modal, Menber menber) {
        super(parent, modal);
        initComponents();
        this.menber = menber;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagazaPU");
        em = emf.createEntityManager();
        this.getContentPane().setBackground(Color.DARK_GRAY);  //Whatever color

        jButtonKassayaVur.requestFocusInWindow();
        jTextFieldNisyedenAlinanMebleg.setEnabled(false);

        int i;
        double sum = 0;
        for (i = 0; i < Baza.ListOfTable.size(); i++) {
            sum += Baza.ListOfTable.get(i).Qiymet;
        }
        jTextField1.setText("" + sum);
        FillTheTableDaxil();
    }

    private void FillTheTableDaxil() {
        DefaultTableModel tmodel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        tmodel.addColumn("Ad");
        tmodel.addColumn("Qiyməti");

        jTable1.setModel(tmodel);

        listofbaza = Baza.ListOfTable;

        for (TableAdd b : listofbaza) {
            tmodel.insertRow(jTable1.getRowCount(), new Object[]{
                b.Ad,
                b.Qiymet
            });
        }
    }

    private void InsertData() {
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.sql.Date sqlDate = new java.sql.Date(t);
        Status = 1;
        if (jComboBox1.getSelectedIndex() == 0) {
            Status = 1;
            Entity.Kassa d = new Kassa(0, jTextField1.getText(), "0", sqlDate);
            d.setIdSatisNovu((Satisnovu) jComboBox1.getSelectedItem());
            System.out.println("1");
            d.setIdMenber(em.find(Menber.class, menber.getIdMenber()));
            System.out.println("2");
            em.persist(d);
            em.getTransaction().begin();
            em.getTransaction().commit();
            this.dispose();
        } else if (jComboBox1.getSelectedIndex() == 1) {
            Status = 2;
            double borc = Double.parseDouble(jTextField1.getText()) - Double.parseDouble(jTextFieldNisyedenAlinanMebleg.getText());
            Entity.Kassa d = new Kassa(0, jTextField1.getText(), Double.toString(borc), sqlDate);
            d.setIdSatisNovu((Satisnovu) jComboBox1.getSelectedItem());
            d.setIdMenber(em.find(Menber.class, menber.getIdMenber()));
            em.persist(d);
            em.getTransaction().begin();
            em.getTransaction().commit();
            ScreenMusteri f = new ScreenMusteri(null, rootPaneCheckingEnabled);
            f.setVisible(rootPaneCheckingEnabled);
            if (f.selectedMushteri == null) {
                Status = 3;
            }
            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        MagazaPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("MagazaPU").createEntityManager();
        musteriQuery = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT m FROM Musteri m");
        musteriList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : musteriQuery.getResultList();
        satisnovuQuery = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT s FROM Satisnovu s");
        satisnovuList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : satisnovuQuery.getResultList();
        jTextField1 = new javax.swing.JTextField();
        jTextFieldNisyedenAlinanMebleg = new javax.swing.JTextField();
        jButtonKassayaVur = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonImtina = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuNegd = new javax.swing.JMenu();
        jMenuItemNegd = new javax.swing.JMenuItem();
        jMenuItemNisye = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));
        setUndecorated(true);
        setResizable(false);

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setText("0.00");

        jTextFieldNisyedenAlinanMebleg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldNisyedenAlinanMebleg.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldNisyedenAlinanMebleg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNisyedenAlinanMeblegActionPerformed(evt);
            }
        });

        jButtonKassayaVur.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButtonKassayaVur.setText("Kassaya vur");
        jButtonKassayaVur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKassayaVurActionPerformed(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, satisnovuList, jComboBox1);
        bindingGroup.addBinding(jComboBoxBinding);

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

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

        jButtonImtina.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButtonImtina.setText("İmtina");
        jButtonImtina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImtinaActionPerformed(evt);
            }
        });

        jMenuNegd.setText("File");

        jMenuItemNegd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0));
        jMenuItemNegd.setText("Negd");
        jMenuItemNegd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNegdActionPerformed(evt);
            }
        });
        jMenuNegd.add(jMenuItemNegd);

        jMenuItemNisye.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, 0));
        jMenuItemNisye.setText("Nisye");
        jMenuItemNisye.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNisyeActionPerformed(evt);
            }
        });
        jMenuNegd.add(jMenuItemNisye);

        jMenuBar1.add(jMenuNegd);

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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonKassayaVur, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldNisyedenAlinanMebleg)
                            .addComponent(jButtonImtina, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNisyedenAlinanMebleg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonKassayaVur, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(jButtonImtina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonKassayaVurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKassayaVurActionPerformed
        InsertData();
    }//GEN-LAST:event_jButtonKassayaVurActionPerformed

    private void jMenuItemNisyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNisyeActionPerformed
        jComboBox1.setSelectedIndex(1);
        jComboBox1.requestFocus();
        jComboBox1.requestFocusInWindow();
        jTextFieldNisyedenAlinanMebleg.setEnabled(true);
        jTextFieldNisyedenAlinanMebleg.requestFocusInWindow();
    }//GEN-LAST:event_jMenuItemNisyeActionPerformed

    private void jTextFieldNisyedenAlinanMeblegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNisyedenAlinanMeblegActionPerformed
        InsertData();
    }//GEN-LAST:event_jTextFieldNisyedenAlinanMeblegActionPerformed

    private void jMenuItemNegdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNegdActionPerformed
        InsertData();
    }//GEN-LAST:event_jMenuItemNegdActionPerformed

    private void jButtonImtinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImtinaActionPerformed
        Status = 3;
        Baza.ListOfTable.removeAll(listofbaza);
        this.dispose();
    }//GEN-LAST:event_jButtonImtinaActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (evt.getID() == 1) {
            jTextFieldNisyedenAlinanMebleg.setEnabled(true);
        } else {
            jTextFieldNisyedenAlinanMebleg.setEnabled(false);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager MagazaPUEntityManager;
    private javax.swing.JButton jButtonImtina;
    private javax.swing.JButton jButtonKassayaVur;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemNegd;
    private javax.swing.JMenuItem jMenuItemNisye;
    private javax.swing.JMenu jMenuNegd;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldNisyedenAlinanMebleg;
    private java.util.List<Entity.Musteri> musteriList;
    private javax.persistence.Query musteriQuery;
    private java.util.List<Entity.Satisnovu> satisnovuList;
    private javax.persistence.Query satisnovuQuery;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
