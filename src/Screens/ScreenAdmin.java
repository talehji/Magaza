/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Entity.Depo;
import Entity.Kassa;
import Entity.Mallar;
import Entity.Musteri;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pallas
 */
public class ScreenAdmin extends javax.swing.JFrame {

    private final EntityManager em;
    private List<Kassa> ListOfKassa;
    private List<Musteri> ListOfBorclar;
    public Kassa selectedKassa;

    /**
     * Creates new form ScreenAdmin
     */
    public ScreenAdmin() {
        initComponents();
        this.setExtendedState(ScreenAdmin.MAXIMIZED_BOTH);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MagazaPU");
        em = emf.createEntityManager();
//        Object a = em.createQuery("SELECT k.Borc, s.idSatis from Satis s inner join Kassa k on k.idKassa=s.idKassa", Object.class)
//                  .getResultList();
//        System.out.println(a);
        FillTheTableMallar();
        FillTheTableDepo();
        FillTheTableKassa(false, null, null);
        FillTheTableBorclar();
    }

    private void FillTheTableMallar() {
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
        tmodel.addColumn("Çəkisi");
        tmodel.addColumn("Alış Qiyməti");
        tmodel.addColumn("Satış Qiyməti");

        jTableMallar.setModel(tmodel);

        List<Mallar> ListOfMallar = em.createNamedQuery("Mallar.findAll", Mallar.class).getResultList();

        for (Mallar b : ListOfMallar) {
            tmodel.insertRow(jTableMallar.getRowCount(), new Object[]{
                b.getIdMallar(),
                b.getBarkod(),
                b.getIdKateqoriya().getAd(),
                b.getAd(),
                b.getCekisi(),
                b.getAlisQiymeti(),
                b.getSatisQiymeti()
            });
        }
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
        tmodel.addColumn("Ümumi daxil Sayı");
        tmodel.addColumn("Satış Sayı");
        tmodel.addColumn("Cari Sayı");

        jTableDepo.setModel(tmodel);

        List<Depo> ListOfDepo = em.createNamedQuery("Depo.findAll", Depo.class).getResultList();

        for (Depo b : ListOfDepo) {
            Object a;
            a = em.createQuery("SELECT count(u.idSatis) from Satis u where u.idMallar = :idMallar", String.class).setParameter("idMallar", b.getIdMallar()).getSingleResult();
            int d;

            tmodel.insertRow(jTableDepo.getRowCount(), new Object[]{
                b.getIdDepo(),
                b.getIdMallar().getBarkod(),
                b.getIdMallar().getIdKateqoriya().getAd(),
                b.getIdMallar().getAd(),
                b.getSayi(),
                d = Integer.parseInt(a.toString()),
                Integer.parseInt(b.getSayi()) - d
            });
        }

    }

    private void FillTheTableKassa(boolean d, Calendar startDate, Calendar endDate) {

        DefaultTableModel tmodel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        tmodel.addColumn("İD");
        tmodel.addColumn("Satış növü");
        tmodel.addColumn("Mədaxil");
        tmodel.addColumn("Borc");
        tmodel.addColumn("Tarix");
        tmodel.addColumn("Saat");
        tmodel.addColumn("Müştəri");
        tmodel.addColumn("Satıcı");

        jTableKassa.setModel(tmodel);
        if (d == true) {
            ListOfKassa = em.createQuery(
                    "SELECT e FROM Kassa e WHERE e.tarix BETWEEN :startDate AND :endDate", Kassa.class)
                    .setParameter("startDate", startDate, TemporalType.DATE)
                    .setParameter("endDate", endDate, TemporalType.DATE)
                    .getResultList();
        } else {
            ListOfKassa = em.createNamedQuery("Kassa.findAll", Kassa.class).getResultList();
        }

        for (Kassa b : ListOfKassa) {
            SimpleDateFormat t = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
            tmodel.insertRow(jTableKassa.getRowCount(), new Object[]{
                b.getIdKassa(),
                b.getIdSatisNovu().getAd(),
                b.getMedaxil(),
                b.getBorc(),
                t.format(b.getTarix()),
                s.format(b.getTarix()),
                b.getIdMusteri().getAdSoyad(),
                b.getIdMember().getAdSoyad(),});
        }

    }

    private void FillTheTableBorclar() {

        DefaultTableModel tmodel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        tmodel.addColumn("İD");
        tmodel.addColumn("Müştəri");
        tmodel.addColumn("Telefon");
        tmodel.addColumn("Qeyd");
        tmodel.addColumn("Borc");

        jTableBorclar.setModel(tmodel);

        ListOfBorclar = em.createNamedQuery("Musteri.findAll", Musteri.class).getResultList();

        for (Musteri b : ListOfBorclar) {

            List<Kassa> GetBorc = em.createNamedQuery("Kassa.findByIdMusteri", Kassa.class)
                    .setParameter("idMusteri", em.find(Musteri.class, b.getIdMusteri()))
                    .getResultList();
            int i;
            double sum = 0;
            for (i = 0; i < GetBorc.size(); i++) {
                sum += GetBorc.get(i).getBorc();
            }

            tmodel.insertRow(jTableBorclar.getRowCount(), new Object[]{
                b.getIdMusteri(),
                b.getAdSoyad(),
                b.getTelefon(),
                b.getQeyd(),
                sum
            });
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        MagazaPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("MagazaPU").createEntityManager();
        mallarQuery = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT m FROM Mallar m");
        mallarList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : mallarQuery.getResultList();
        kateqoriyaQuery = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT k FROM Kateqoriya k");
        kateqoriyaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : kateqoriyaQuery.getResultList();
        mallarQuery1 = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT m FROM Mallar m");
        mallarList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : mallarQuery1.getResultList();
        mallarQuery2 = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT m FROM Mallar m");
        mallarList2 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : mallarQuery2.getResultList();
        mallarQuery3 = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT m FROM Mallar m");
        mallarList3 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : mallarQuery3.getResultList();
        mallarQuery4 = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT m FROM Mallar m");
        mallarList4 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : mallarQuery4.getResultList();
        kassaQuery = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT k FROM Kassa k");
        kassaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : kassaQuery.getResultList();
        satisQuery = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT s FROM Satis s");
        satisList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : satisQuery.getResultList();
        kassaQuery1 = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT k FROM Kassa k");
        kassaList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : kassaQuery1.getResultList();
        satisQuery1 = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT s FROM Satis s");
        satisList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : satisQuery1.getResultList();
        musteriQuery = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT m FROM Musteri m");
        musteriList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : musteriQuery.getResultList();
        satisnovuQuery = java.beans.Beans.isDesignTime() ? null : MagazaPUEntityManager.createQuery("SELECT s FROM Satisnovu s");
        satisnovuList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : satisnovuQuery.getResultList();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMallar = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDepo = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelMedaxil = new javax.swing.JLabel();
        jLabelBorc = new javax.swing.JLabel();
        jLabelUmumiGelir = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableKassa = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableBorclar = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jTableMallar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableMallar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTableMallar);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Daxil Et");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("   Mallar   ", jPanel2);

        jTableDepo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableDepo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTableDepo);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Daxil et");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Barkod");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Sayı");

        jComboBox2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBox2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mallarList4, jComboBox2);
        bindingGroup.addBinding(jComboBoxBinding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("   Depo   ", jPanel1);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Hesabat");

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton5.setText("Filter");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Nəğd alış");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Borc");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Ümumi gəlir");

        jLabelMedaxil.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelMedaxil.setText(" ");

        jLabelBorc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelBorc.setText(" ");

        jLabelUmumiGelir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelUmumiGelir.setText(" ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelMedaxil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelBorc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelUmumiGelir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabelMedaxil))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabelBorc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabelUmumiGelir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addGap(44, 44, 44))
        );

        jTableKassa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableKassa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableKassa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableKassaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTableKassa);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("   Kassa   ", jPanel3);

        jTableBorclar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableBorclar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jTableBorclar);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("   Borclar   ", jPanel4);

        jMenu3.setText("Fayl");
        jMenuBar1.add(jMenu3);

        jMenu1.setText("Düzəlt");

        jMenuItem2.setText("Kateqoriya");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setText("Müştərilər");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Yardım");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        ScreenKateqoriya d = new ScreenKateqoriya(this, rootPaneCheckingEnabled);
        d.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        jTextField1.requestFocusInWindow();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        Mallar m = em.createNamedQuery("Mallar.findByBarkod", Mallar.class)
                .setParameter("barkod", (Object) jTextField1.getText())
                .getSingleResult();
        jComboBox2.setSelectedIndex(m.getIdMallar() - 1);
        jComboBox2.requestFocus();
        jComboBox2.requestFocusInWindow();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Depo d = em.createNamedQuery("Depo.findByIdMallar", Depo.class)
                    .setParameter("idMallar", (Mallar) jComboBox2.getSelectedItem())
                    .getSingleResult();
            Entity.Depo f = new Depo(d.getIdDepo());
            f.setSayi(jTextField3.getText());
            f.setIdMallar(d.getIdMallar());
            int a = Integer.parseInt(d.getSayi());
            int b = Integer.parseInt(jTextField3.getText());
            String c = Integer.toString(a + b);
            f.setSayi(c);
            em.merge(f);
            em.getTransaction().begin();
            em.getTransaction().commit();
            FillTheTableDepo();
        } catch (Exception e) {
            Entity.Depo d = new Depo(0);
            d.setSayi(jTextField3.getText());
            d.setIdMallar((Mallar) jComboBox2.getSelectedItem());
            em.persist(d);
            em.getTransaction().begin();
            em.getTransaction().commit();
            FillTheTableDepo();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ScreenMalDaxilET d = new ScreenMalDaxilET(this, rootPaneCheckingEnabled);
        d.setVisible(rootPaneCheckingEnabled);
        FillTheTableMallar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        FillTheTableKassa(true, jDateChooser2.getCalendar(), jDateChooser1.getCalendar());
        Double medaxil = 0.00;
        Double borc = 0.00;
        for (Kassa kassa : ListOfKassa) {
            medaxil = medaxil + kassa.getMedaxil();
            borc = borc + kassa.getBorc();
        }
        jLabelMedaxil.setText("" + medaxil);
        jLabelBorc.setText("" + borc);
        jLabelUmumiGelir.setText("" + (medaxil - borc));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        ScreenMusteri d = new ScreenMusteri(this, rootPaneCheckingEnabled);
        d.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTableKassaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableKassaMouseClicked
        if (evt.getClickCount() == 2) {
            DefaultTableModel model = (DefaultTableModel) jTableKassa.getModel();
            int index = jTableKassa.getSelectedRow();
            selectedKassa = ListOfKassa.get(index);
            ScreenSeeKassaDetail d = new ScreenSeeKassaDetail(this, rootPaneCheckingEnabled, selectedKassa);
            d.setVisible(rootPaneCheckingEnabled); 
            
        }
    }//GEN-LAST:event_jTableKassaMouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ScreenAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> {
//            new ScreenAdmin().setVisible(true);
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager MagazaPUEntityManager;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelBorc;
    private javax.swing.JLabel jLabelMedaxil;
    private javax.swing.JLabel jLabelUmumiGelir;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableBorclar;
    private javax.swing.JTable jTableDepo;
    private javax.swing.JTable jTableKassa;
    private javax.swing.JTable jTableMallar;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private java.util.List<Entity.Kassa> kassaList;
    private java.util.List<Entity.Kassa> kassaList1;
    private javax.persistence.Query kassaQuery;
    private javax.persistence.Query kassaQuery1;
    private java.util.List<Entity.Kateqoriya> kateqoriyaList;
    private javax.persistence.Query kateqoriyaQuery;
    private java.util.List<Entity.Mallar> mallarList;
    private java.util.List<Entity.Mallar> mallarList1;
    private java.util.List<Entity.Mallar> mallarList2;
    private java.util.List<Entity.Mallar> mallarList3;
    private java.util.List<Entity.Mallar> mallarList4;
    private javax.persistence.Query mallarQuery;
    private javax.persistence.Query mallarQuery1;
    private javax.persistence.Query mallarQuery2;
    private javax.persistence.Query mallarQuery3;
    private javax.persistence.Query mallarQuery4;
    private java.util.List<Entity.Musteri> musteriList;
    private javax.persistence.Query musteriQuery;
    private java.util.List<Entity.Satis> satisList;
    private java.util.List<Entity.Satis> satisList1;
    private javax.persistence.Query satisQuery;
    private javax.persistence.Query satisQuery1;
    private java.util.List<Entity.Satisnovu> satisnovuList;
    private javax.persistence.Query satisnovuQuery;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
