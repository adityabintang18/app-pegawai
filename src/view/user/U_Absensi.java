/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.user;

import connection.koneksi;
import data.Users;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pc
 */
public class U_Absensi extends javax.swing.JFrame {

    private final Connection kon = new koneksi().connect();
    private Statement st;
    private ResultSet rs;
    private String query = "";
    private String Username, Id_Pegawai, Nama, Tgl, Jam_Masuk, Jam_Pulang, masuk, pulang, user, pass, name;

    /**
     * Creates new form Profile
     */
    public U_Absensi(Users users) {
        initComponents();
        updateData(users);
        DataAbsensi();
    }

    private void updateData(Users users) {
        user = users.getUsername();
        pass = users.getPassword();
        name = users.getName();

        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ambil waktu sekarang
                Calendar cal = Calendar.getInstance();
                int jam = cal.get(Calendar.HOUR_OF_DAY);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String time = sdf.format(new Date());
                // Tampilkan waktu pada label
                jL_waktu.setText(time);
//                }
            }
        });
        timer.start(); // Mulai timer
        try {
            st = kon.createStatement();
            rs = st.executeQuery("SELECT id_pegawai from users where username='" + users.getUsername() + "'");
            if (rs.next()) {
                Id_Pegawai = rs.getString(1);
            }
            System.out.print(users.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jL_nama.setText(users.getName());
        jL_id.setText(Id_Pegawai);

    }

    private void AbsenMasuk(Users users) {
        int simpan = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Melakukan Absen Masuk?", "save", JOptionPane.YES_NO_OPTION);
        if (simpan == 0) {
            SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat j = new SimpleDateFormat("HH:mm:ss.SSS");

            Username = users.getUsername();
            Nama = users.getName();
            Tgl = String.valueOf(t.format(new Date()));
            Jam_Masuk = String.valueOf(j.format(new Date()));

            System.out.print(users.getUsername());

            try {
                st = kon.createStatement();
                rs = st.executeQuery("SELECT id_pegawai from users where username='" + Username + "'");
                if (rs.next()) {
                    Id_Pegawai = rs.getString(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                query = "INSERT INTO absensi (id_pegawai,tanggal,jam_masuk) VALUES "
                        + "('" + Id_Pegawai + "',"
                        + "'" + Tgl + "',"
                        + "'" + Jam_Masuk + "')";
//            System.out.print(query);
                st = kon.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "ABSENSI MASUK BERHASIL");
                DataAbsensi();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ABSENSI GAGAL DISIMPAN, SILAHKAN MASUKKAN KEMBALI DATA DENGAN BENAR");
                e.printStackTrace();
            }
        }
    }

    private void AbsenPulang(Users users) {
        int simpan = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Melakukan Absen Pulang?", "save", JOptionPane.YES_NO_OPTION);
        if (simpan == 0) {
            SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat j = new SimpleDateFormat("HH:mm:ss.SSS");

            Username = users.getUsername();
            Nama = users.getName();
            Jam_Pulang = String.valueOf(j.format(new Date()));
            Tgl = t.format(new Date());

            try {
                st = kon.createStatement();
                rs = st.executeQuery("SELECT id_pegawai from users where username='" + Username + "'");
                if (rs.next()) {
                    Id_Pegawai = rs.getString(1);
                }
                st = kon.createStatement();
                rs = st.executeQuery("SELECT id_pegawai,tanggal, jam_masuk from absensi where id_pegawai='" + Id_Pegawai + "' and tanggal = '" + Tgl + "'");
                if (rs.next()) {
                    Id_Pegawai = rs.getString(1);
                    Tgl = rs.getString(2);
                    Jam_Masuk = rs.getString(3);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {

                query = "UPDATE absensi SET "
                        + " jam_pulang='" + Jam_Pulang
                        + "'WHERE id_pegawai = '" + Id_Pegawai + "' and "
                        + "tanggal = '" + Tgl + "' and "
                        + "jam_masuk = '" + Jam_Masuk + "'";
                st = kon.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "ABSENSI PULANG BERHASIL");
                DataAbsensi();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ABSENSI GAGAL DISIMPAN, SILAHKAN MASUKKAN KEMBALI DATA DENGAN BENAR");
                e.printStackTrace();
            }
        }
    }

    public void DataAbsensi() {

        DefaultTableModel dataabsensi = new DefaultTableModel();
        dataabsensi.addColumn("ID Pegawai"); //1
        dataabsensi.addColumn("Tanggal"); //2
        dataabsensi.addColumn("Jam Masuk"); //3
        dataabsensi.addColumn("Jam Pulang"); //4

        SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd");

        try {
            st = kon.createStatement();
            rs = st.executeQuery("SELECT * FROM absensi where tanggal ='" + t.format(new Date()) + "'");
            while (rs.next()) {
                if (rs.getTime(4) != null) {
                    masuk = String.valueOf(rs.getTime(4));

                } else {
                    masuk = "Tidak Ada";
                }
                if (rs.getTime(5) != null) {
                    pulang = String.valueOf(rs.getTime(5));

                } else {
                    pulang = "Tidak Ada";
                }
                dataabsensi.addRow(new Object[]{
                    rs.getString(2),
                    rs.getDate(3),
                    masuk,
                    rs.getTime(4),
                    pulang
                }
                );
            }
            jTb_absen.setModel(dataabsensi);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal Tampil \n" + e.getMessage());
        }
    }
    
    private void Kembali() {
        int close = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Kembali?", "select", JOptionPane.YES_NO_OPTION);
        if (close == 0) {
            Users users = new Users(" ", " ", " ");
            new U_Home(users).setVisible(true);
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
        jL_waktu = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTb_absen = new javax.swing.JTable();
        jB_masuk = new javax.swing.JButton();
        jB_keluar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jL_id = new javax.swing.JLabel();
        jL_nama = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Data Absensi Pegawai");

        jLabel2.setText("Waktu Saat Ini");

        jL_waktu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jL_waktu.setText("Waktu");

        jTb_absen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pegawai", "Tanggal", "Jam Masuk", "Jam Pulang"
            }
        ));
        jScrollPane1.setViewportView(jTb_absen);

        jB_masuk.setText("Absensi Masuk");
        jB_masuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_masukActionPerformed(evt);
            }
        });

        jB_keluar.setText("Absensi Pulang");
        jB_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_keluarActionPerformed(evt);
            }
        });

        jLabel4.setText("Daftar Absensi");

        jLabel3.setText("ID Pegawai  ");

        jLabel5.setText("Nama");

        jL_id.setText("id");

        jL_nama.setText("nama");

        jLabel6.setText(":");

        jLabel7.setText(":");

        jButton1.setText("Kembali");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jL_id)
                            .addComponent(jL_nama))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(278, 278, 278))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_masuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_keluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(280, 280, 280)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jL_waktu, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jL_id)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jL_nama)
                            .addComponent(jLabel7))
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jL_waktu)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_masuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jB_keluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jB_masukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_masukActionPerformed
        // TODO add your handling code here:
        Users users = new Users(user, pass, name);
        AbsenMasuk(users);
    }//GEN-LAST:event_jB_masukActionPerformed

    private void jB_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_keluarActionPerformed
        // TODO add your handling code here:
        Users users = new Users(user, pass, name);
        AbsenPulang(users);
    }//GEN-LAST:event_jB_keluarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Kembali();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(U_Absensi.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(U_Absensi.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(U_Absensi.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(U_Absensi.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Users users = new Users(" ", " ", " ");
                new U_Absensi(users).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_keluar;
    private javax.swing.JButton jB_masuk;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jL_id;
    private javax.swing.JLabel jL_nama;
    private javax.swing.JLabel jL_waktu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTb_absen;
    // End of variables declaration//GEN-END:variables
}
