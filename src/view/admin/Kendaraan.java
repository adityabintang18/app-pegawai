/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.admin;

import connection.koneksi;
import data.Users;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adity
 */
public class Kendaraan extends javax.swing.JFrame {

    private final Connection kon = new koneksi().connect();
    private Statement st;
    private ResultSet rs, rst;
    private String query = "";
    private String Id_Pegawai, No, JK, Merk, BB, Warna_Kendaraan, S,T,cc, Created_At, Created_By, Updated_At, Updated_By;
    private int CC, Jenis_Kendaraan, Tahun, Bahan_Bakar, Status;

    /**
     * Creates new form Kendaaraan
     */
    public Kendaraan() {
        initComponents();
        DataKendaraan();
        OptionPegawai();
        OptionKendaraan();
        OptionBahanbakar();
    }

    public void OptionPegawai() {
        try {
            query = "SELECT * FROM profil_pegawai";
            st = kon.createStatement();
            rs = st.executeQuery(query);

            List<String> items = new ArrayList<>();
            while (rs.next()) {
                items.add(rs.getString("id_pegawai")); // Ganti column_name dengan nama kolom yang sesuai
            }

            // Memasukkan data dari ArrayList ke dalam JComboBox
            for (String item : items) {
                jC_id.addItem(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void OptionKendaraan() {
        try {
            query = "SELECT * FROM ref_jns_kendaraan";
            st = kon.createStatement();
            rs = st.executeQuery(query);

            List<String> items = new ArrayList<>();
            while (rs.next()) {
                items.add(rs.getString("jns_kendaraan")); // Ganti column_name dengan nama kolom yang sesuai
            }

            // Memasukkan data dari ArrayList ke dalam JComboBox
            for (String item : items) {
                jC_Jenis.addItem(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void OptionBahanbakar() {
        try {
            query = "SELECT * FROM ref_bahanbakar";
            st = kon.createStatement();
            rs = st.executeQuery(query);

            List<String> items = new ArrayList<>();
            while (rs.next()) {
                items.add(rs.getString("nm_bahanbakar")); // Ganti column_name dengan nama kolom yang sesuai
            }

            // Memasukkan data dari ArrayList ke dalam JComboBox
            for (String item : items) {
                jC_Bb.addItem(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void SimpanKendaraan() {
        int simpan = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Menyimpan Data Ini?", "save", JOptionPane.YES_NO_OPTION);
        if (simpan == 0) {
            Id_Pegawai = String.valueOf(jC_id.getSelectedItem());
            No = String.valueOf(jT_No.getText());
            JK = String.valueOf(jC_Jenis.getSelectedItem());
            Merk = String.valueOf(jT_Merk.getText());
            Tahun = (int) jS_Tahun.getValue();
            BB = String.valueOf(jC_Bb.getSelectedItem());
            CC = (int) jS_CC.getValue();
            Warna_Kendaraan = String.valueOf(jT_Warna.getText());
            S = String.valueOf(jC_Status.getSelectedItem());
//            Created_At = String.valueOf(fd.format(new Date()));
//            Created_By = String.valueOf(jT_id.getText());

            try {
                rs = st.executeQuery("SELECT id from ref_jns_kendaraan where jns_kendaraan='" + JK + "'");
                if (rs.next()) {
                    Jenis_Kendaraan = rs.getInt(1);
                }
                rs = st.executeQuery("SELECT id from ref_bahanbakar where nm_bahanbakar='" + BB + "'");
                if (rs.next()) {
                    Bahan_Bakar = rs.getInt(1);
                }
                if (S.equals("Aktif")) {
                    Status = 1;
                } else if (S.equals("Tidak Aktif")) {
                    Status = 0;
                } else {
                    Status = 2;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                query = "INSERT INTO profil_kendaraan (id_pegawai,nomor_kendaraan,jenis_kendaraan,merk,tahun_pembuatan,bahan_bakar,cc,warna,status) VALUES "
                        + "('" + Id_Pegawai + "',"
                        + "'" + No + "',"
                        + "'" + Jenis_Kendaraan + "',"
                        + "'" + Merk + "',"
                        + "'" + Tahun + "',"
                        + "'" + Bahan_Bakar + "',"
                        + "'" + CC + "',"
                        + "'" + Warna_Kendaraan + "',"
                        + "'" + Status + "')";
                st = kon.createStatement();
                st.executeUpdate(query);
                BatalKendaraan();
                DataKendaraan();
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DISIMPAN");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "DATA GAGAL DISIMPAN, SILAHKAN MASUKKAN KEMBALI DATA DENGAN BENAR");
                e.printStackTrace();
            }
            jC_id.requestFocus();
        }
    }

    private void UbahKendaraan() {
        int edit = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Mengubah Data Ini?", "edit", JOptionPane.YES_NO_OPTION);
        if (edit == 0) {
            Id_Pegawai = String.valueOf(jC_id.getSelectedItem());
            No = String.valueOf(jT_No.getText());
            JK = String.valueOf(jC_Jenis.getSelectedItem());
            Merk = String.valueOf(jT_Merk.getText());
            Tahun = (int) jS_Tahun.getValue();
            BB = String.valueOf(jC_Bb.getSelectedItem());
            CC = (int) jS_CC.getValue();
            Warna_Kendaraan = String.valueOf(jT_Warna.getText());
            S = String.valueOf(jC_Status.getSelectedItem());

            try {
                rs = st.executeQuery("SELECT id from ref_jns_kendaraan where jns_kendaraan='" + JK + "'");
                if (rs.next()) {
                    Jenis_Kendaraan = rs.getInt(1);
                }
                rs = st.executeQuery("SELECT id from ref_bahanbakar where nm_bahanbakar='" + BB + "'");
                if (rs.next()) {
                    Bahan_Bakar = rs.getInt(1);
                }
                if (S.equals("Aktif")) {
                    Status = 1;
                } else if (S.equals("Tidak Aktif")) {
                    Status = 0;
                } else {
                    Status = 2;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                query = "UPDATE profil_kendaraan SET "
                        + "id_pegawai ='" + Id_Pegawai + "', "
                        + "nomor_kendaraan ='" + No + "', "
                        + "jenis_kendaraan ='" + Jenis_Kendaraan + "', "
                        + "merk ='" + Merk + "', "
                        + "tahun_pembuatan ='" + Tahun + "', "
                        + "bahan_bakar ='" + Bahan_Bakar + "', "
                        + "cc ='" + CC + "', "
                        + "warna ='" + Warna_Kendaraan + "', "
                        + "status ='" + Status + "' "
                        + "WHERE id_pegawai = '" + Id_Pegawai + "' and "
                        + "nomor_kendaraan = '" + No + "'";
                st = kon.createStatement();
                st.execute(query);
                BatalKendaraan();
                DataKendaraan();
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DIUBAH");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "DATA GAGAL DIUBAH, SILAHKAN UBAH DATA DENGAN BENAR \n " + e.getMessage());
            }
            jC_id.requestFocus();
        }
    }

    private void HapusKendaraan() {
        DefaultTableModel model = (DefaultTableModel) jTb_kendaraan.getModel();
        int row = jTb_kendaraan.getSelectedRow();
        if (row >= 0) {
            int delete = JOptionPane.showConfirmDialog(null, "Apakah Yakin ingin menghapus data ini?", "delete", JOptionPane.YES_NO_OPTION);
            if (delete == 0) {
                model.removeRow(row);
                try {

                    query = "DELETE FROM profil_kendaraan WHERE id_pegawai ='" + jC_id.getSelectedItem() + "' and nomor_kendaraan='" + jT_No.getText() + "'";
                    st = kon.createStatement();
                    st.execute(query);
                    JOptionPane.showMessageDialog(null, "DATA BERHASIL DIHAPUS");
                    BatalKendaraan();
                    DataKendaraan();
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, "DATA GAGAL DIHAPUS, SILAHKAN COBA KEMBALI \n" + e.getMessage());
                }
                jC_id.requestFocus();
            }
        }
    }

    ;
    
    private void BatalKendaraan() {
        jC_id.setSelectedIndex(0);
        jT_No.setText("");
        jC_Jenis.setSelectedIndex(0);
        jT_Merk.setText("");
        jS_Tahun.setValue(0);
        jC_Bb.setSelectedIndex(0);
        jS_CC.setValue(0);
        jT_Warna.setText("");
        jC_Status.setSelectedIndex(0);
    }

    ;

    public void DataKendaraan() {
        DefaultTableModel datakendaraan = new DefaultTableModel();
        datakendaraan.addColumn("ID Pegawai");
        datakendaraan.addColumn("No Kendaraan");
        datakendaraan.addColumn("Jenis Kendaraan");
        datakendaraan.addColumn("Merk");
        datakendaraan.addColumn("Tahun Pembuatan");
        datakendaraan.addColumn("Bahan Bakar");
        datakendaraan.addColumn("CC");
        datakendaraan.addColumn("Warna Kendaraan");
        datakendaraan.addColumn("Status");
        try {
            st = kon.createStatement();
            rs = st.executeQuery("SELECT * FROM profil_kendaraan");
            while (rs.next()) {
                try {
                    Jenis_Kendaraan = rs.getInt(4);
                    Bahan_Bakar = rs.getInt(7);
                    Status = rs.getInt(10);
                    rst = st.executeQuery("SELECT jns_kendaraan from ref_jns_kendaraan where id='" + Jenis_Kendaraan + "'");
                    if (rst.next()) {
                        JK = rst.getString(1);
                    }
                    rst = st.executeQuery("SELECT nm_bahanbakar from ref_bahanbakar where id='" + Bahan_Bakar + "'");
                    if (rst.next()) {
                        BB = rst.getString(1);
                    }

                    if (Status == 1) {
                        S = "Aktif";
                    } else if (Status == 2) {
                        S = "Perbaikan";
                    } else {
                        S = "Tidak Aktif";
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                rs = st.executeQuery("SELECT * FROM profil_kendaraan");
                if (rs.next()) {
                    datakendaraan.addRow(new Object[]{
                        rs.getString(2),
                        rs.getString(3),
                        JK,
                        rs.getString(5),
                        rs.getString(6),
                        BB,
                        rs.getInt(8),
                        rs.getString(9),
                        S
                    });
                }
            }
            jTb_kendaraan.setModel(datakendaraan);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal Tampil \n" + e.getMessage());
        }
    }

    private void TabelKendaraanSelect() {
        try {
            BatalKendaraan();
            int baris = jTb_kendaraan.getSelectedRow();

            Id_Pegawai = jTb_kendaraan.getValueAt(baris, 0).toString();
            No = jTb_kendaraan.getValueAt(baris, 1).toString();
            JK = jTb_kendaraan.getValueAt(baris, 2).toString();
            Merk = jTb_kendaraan.getValueAt(baris, 3).toString();
            T = jTb_kendaraan.getValueAt(baris, 4).toString();
            BB = jTb_kendaraan.getValueAt(baris, 5).toString();
            cc = jTb_kendaraan.getValueAt(baris, 6).toString();
            Warna_Kendaraan = jTb_kendaraan.getValueAt(baris, 7).toString();
            S = jTb_kendaraan.getValueAt(baris, 8).toString();

            
            jC_id.setSelectedItem(Id_Pegawai);
            jT_No.setText(No);
            jC_Jenis.setSelectedItem(JK);
            jT_Merk.setText(Merk);
            jS_Tahun.setValue(Integer.valueOf(T));
            jC_Bb.setSelectedItem(BB);
            jS_CC.setValue(Integer.valueOf(cc));
            jT_Warna.setText(Warna_Kendaraan);
            jC_Status.setSelectedItem(S);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void Kembali() {
        int close = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Kembali?", "select", JOptionPane.YES_NO_OPTION);
        if (close == 0) {
            Users users = new Users(" ", " ", " ");
            new Home(users).setVisible(true);
            dispose();
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jT_No = new javax.swing.JTextField();
        jC_Bb = new javax.swing.JComboBox<>();
        jS_CC = new javax.swing.JSpinner();
        jT_Warna = new javax.swing.JTextField();
        jC_id = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTb_kendaraan = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jC_Jenis = new javax.swing.JComboBox<>();
        jT_Merk = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jC_Status = new javax.swing.JComboBox<>();
        jS_Tahun = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Data Kendaraan Pegawai");

        jLabel2.setText("ID Pegawai");

        jLabel3.setText("Jenis Kendaraan");

        jLabel4.setText("Bahan Bakar");

        jLabel5.setText("Nomor Kendaraan");

        jLabel6.setText("CC");

        jLabel7.setText("Warna Kendaraan");

        jC_Bb.setMaximumRowCount(1000);
        jC_Bb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- pilih --" }));

        jT_Warna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jT_WarnaActionPerformed(evt);
            }
        });

        jC_id.setMaximumRowCount(1000);
        jC_id.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- pilih --" }));

        jTb_kendaraan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pegawai", "No Kendaraan", "Jenis Kendaraan", "Merk", "Tahun Pembuatan", "Bahan Bakar", "CC", "Warna Kendaraan", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTb_kendaraan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTb_kendaraanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTb_kendaraan);

        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Ubah");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Batal");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Kembali");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jC_Jenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- pilih --" }));

        jLabel8.setText("Merk");

        jLabel9.setText("Status");

        jC_Status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- pilih --", "Aktif", "Tidak Aktif", "Perbaikan" }));

        jLabel10.setText("Tahun Pembuatan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jT_No)
                                .addComponent(jC_id, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jC_Bb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jS_CC)
                                .addComponent(jT_Warna)
                                .addComponent(jC_Jenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jT_Merk)
                                .addComponent(jC_Status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jS_Tahun)))))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jC_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jT_No, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jC_Jenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jT_Merk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jS_Tahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jC_Bb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jS_CC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jT_Warna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jC_Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jT_WarnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jT_WarnaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jT_WarnaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SimpanKendaraan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        UbahKendaraan();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        HapusKendaraan();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        BatalKendaraan();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Kembali();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTb_kendaraanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTb_kendaraanMouseClicked
        // TODO add your handling code here:
        TabelKendaraanSelect();
    }//GEN-LAST:event_jTb_kendaraanMouseClicked

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
            java.util.logging.Logger.getLogger(Kendaraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kendaraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kendaraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kendaraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Kendaraan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jC_Bb;
    private javax.swing.JComboBox<String> jC_Jenis;
    private javax.swing.JComboBox<String> jC_Status;
    private javax.swing.JComboBox<String> jC_id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner jS_CC;
    private javax.swing.JSpinner jS_Tahun;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jT_Merk;
    private javax.swing.JTextField jT_No;
    private javax.swing.JTextField jT_Warna;
    private javax.swing.JTable jTb_kendaraan;
    // End of variables declaration//GEN-END:variables
}
