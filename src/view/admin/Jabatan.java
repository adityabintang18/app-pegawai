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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author adity
 */
public class Jabatan extends javax.swing.JFrame {

    private final Connection kon = new koneksi().connect();
    private Statement st;
    private ResultSet rsjabatan;
    private String query = "";
    private String id_jabatan, nama_jabatan, tgl_awal, tgl_akhir;
    private int id;

    /**
     * Creates new form Jabatan
     */
    public Jabatan() {
        initComponents();
        LebarKolom();
        DataJabatan();
        IdJabatan();

    }

    private void LebarKolom() {
        TableColumn column;
        jTb_jabatan.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTb_jabatan.getColumnModel().getColumn(1).setPreferredWidth(70);
        jTb_jabatan.getColumnModel().getColumn(2).setPreferredWidth(70);
    }

    public void DataJabatan() {
        DefaultTableModel datajabatan = new DefaultTableModel();
        datajabatan.addColumn("Id Jabatan");
        datajabatan.addColumn("Nama Jabatan");
        datajabatan.addColumn("Tanggal Awal Berlaku");
        datajabatan.addColumn("Tanggal Akhir Berlaku");

        try {
            st = kon.createStatement();
            rsjabatan = st.executeQuery("SELECT * FROM ref_jabatan");
            while (rsjabatan.next()) {
                datajabatan.addRow(new Object[]{
                    rsjabatan.getString(2),
                    rsjabatan.getString(3),
                    rsjabatan.getDate(4),
                    rsjabatan.getDate(5)
                });
            }
            jTb_jabatan.setModel(datajabatan);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal Tampil \n" + e.getMessage());
        }
    }

    private void SimpanJabatan() {
        int simpan = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Menyimpan Data Ini?", "save", JOptionPane.YES_NO_OPTION);
        if (simpan == 0) {
            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            id_jabatan = String.valueOf(jT_id.getText());
            nama_jabatan = String.valueOf(jT_jabatan.getText());
            tgl_awal = String.valueOf(fd.format(jD_awal.getDate()));
            tgl_akhir = String.valueOf(fd.format(jD_akhir.getDate()));

            try {
                query = "INSERT INTO ref_jabatan (id_jabatan,nama_jabatan, tgl_awal_berlaku, tgl_akhir_berlaku) VALUES "
                        + "('" + id_jabatan + "',"
                        + "'" + nama_jabatan + "',"
                        + "'" + tgl_awal + "',"
                        + "'" + tgl_akhir + "')";
//            System.out.print(query);
                st = kon.createStatement();
                st.executeUpdate(query);
                IdJabatan();
                BatalJabatan();
                DataJabatan();
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DISIMPAN");

                jT_jabatan.setText("");
                jD_awal.setCalendar(null);
                jD_akhir.setCalendar(null);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "DATA GAGAL DISIMPAN, SILAHKAN MASUKKAN KEMBALI DATA DENGAN BENAR");
                e.printStackTrace();
            }
            jT_jabatan.requestFocus();
        }
    }

    private void UbahJabatan() {
        int edit = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Mengubah Data Ini?", "edit", JOptionPane.YES_NO_OPTION);
        if (edit == 0) {
            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            id_jabatan = String.valueOf(jT_id.getText());
            nama_jabatan = String.valueOf(jT_jabatan.getText());
            tgl_awal = String.valueOf(fd.format(jD_awal.getDate()));
            tgl_akhir = String.valueOf(fd.format(jD_akhir.getDate()));

            try {
                query = "UPDATE ref_jabatan SET nama_jabatan='" + nama_jabatan + "', tgl_awal_berlaku='" + tgl_awal + "', tgl_akhir_berlaku='" + tgl_akhir + "'WHERE id_jabatan = '" + id_jabatan + "'";
                st = kon.createStatement();
                st.execute(query);
                IdJabatan();
                BatalJabatan();
                DataJabatan();
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DIUBAH");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "DATA GAGAL DIUBAH, SILAHKAN UBAH DATA DENGAN BENAR \n " + e.getMessage());
            }
            jT_jabatan.requestFocus();
        }
    }

    ;
     
     private void BatalJabatan() {
        jT_jabatan.setText("");
        jD_awal.setCalendar(null);
        jD_akhir.setCalendar(null);
    }

    ;
     
     private void HapusJabatan() {
        DefaultTableModel model = (DefaultTableModel) jTb_jabatan.getModel();
        int row = jTb_jabatan.getSelectedRow();
        if (row >= 0) {
            int delete = JOptionPane.showConfirmDialog(null, "Apakah Yakin ingin menghapus data ini?", "delete", JOptionPane.YES_NO_OPTION);
            if (delete == 0) {
                model.removeRow(row);
                try {

                    query = "DELETE FROM ref_jabatan WHERE id_jabatan ='" + jT_id.getText() + "'";
                    st = kon.createStatement();
                    st.execute(query);
                    JOptionPane.showMessageDialog(null, "DATA BERHASIL DIHAPUS");
                    BatalJabatan();
                    DataJabatan();
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, "DATA GAGAL DIHAPUS, SILAHKAN COBA KEMBALI \n" + e.getMessage());
                }
                jT_jabatan.requestFocus();
            }
        }
    }

    ;
     
     private void TabelJabatanSelect() {
        try {

            jT_id.setText("");
            jT_jabatan.setText("");
            jD_awal.setCalendar(null);
            jD_akhir.setCalendar(null);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            int baris = jTb_jabatan.getSelectedRow();

            String id_jabatan = jTb_jabatan.getValueAt(baris, 0).toString();
            jT_id.setText(id_jabatan);
            String nama_jabatan = jTb_jabatan.getValueAt(baris, 1).toString();
            jT_jabatan.setText(nama_jabatan);
            Date tglAwal = (Date) jTb_jabatan.getValueAt(baris, 2);
            jD_awal.setDate(tglAwal);
            Date tglAkhir = (Date) jTb_jabatan.getValueAt(baris, 3);
            jD_akhir.setDate(tglAkhir);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void IdJabatan() {
        try {
            query = "SELECT * FROM ref_jabatan ORDER BY id_jabatan desc";
            st = kon.createStatement();
            rsjabatan = st.executeQuery(query);
            if (rsjabatan.next()) {
                String idjb = rsjabatan.getString("id_jabatan").substring(4);
                String AN = "" + (Integer.parseInt(idjb) + 1);
                String Nol = "";

                if (AN.length() == 1) {
                    Nol = "00";
                } else if (AN.length() == 2) {
                    Nol = "0";
                } else if (AN.length() == 3) {
                    Nol = "";
                }

                jT_id.setText("JBT" + Nol + AN);
            } else {
                jT_id.setText("JBT001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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
        jT_jabatan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jB_Simpan = new javax.swing.JButton();
        jB_Ubah = new javax.swing.JButton();
        jB_Hapus = new javax.swing.JButton();
        jB_Kembali = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTb_jabatan = new javax.swing.JTable();
        jD_awal = new com.toedter.calendar.JDateChooser();
        jD_akhir = new com.toedter.calendar.JDateChooser();
        jB_batal = new javax.swing.JButton();
        jT_id = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Data Jabatan Pegawai");

        jLabel2.setText("Nama Jabatan");

        jT_jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jT_jabatanActionPerformed(evt);
            }
        });

        jLabel3.setText("Tanggal Awal Berlaku");

        jLabel4.setText("Tanggal Akhir Berlaku");

        jB_Simpan.setText("Simpan");
        jB_Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SimpanActionPerformed(evt);
            }
        });

        jB_Ubah.setText("Ubah");
        jB_Ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_UbahActionPerformed(evt);
            }
        });

        jB_Hapus.setText("Hapus");
        jB_Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_HapusActionPerformed(evt);
            }
        });

        jB_Kembali.setText("Kembali");
        jB_Kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_KembaliActionPerformed(evt);
            }
        });

        jTb_jabatan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Jabatan", "Nama Jabatan", "Tanggal Awal Berlaku", "Tanggal Akhir Berlaku"
            }
        ));
        jTb_jabatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTb_jabatanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTb_jabatan);

        jD_awal.setDateFormatString("dd MMMM yyyy");

        jD_akhir.setDateFormatString("dd MMMM yyyy");

        jB_batal.setText("Batal");
        jB_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_batalActionPerformed(evt);
            }
        });

        jT_id.setEditable(false);

        jLabel5.setText("ID Jabatan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jD_awal, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                .addComponent(jD_akhir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jT_id, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                .addComponent(jT_jabatan, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_Simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Ubah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Hapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_batal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Kembali)))))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jT_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jT_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jD_awal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Kembali)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jB_Simpan)
                                .addComponent(jB_Ubah)
                                .addComponent(jB_Hapus)
                                .addComponent(jB_batal))))
                    .addComponent(jD_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jT_jabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jT_jabatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jT_jabatanActionPerformed

    private void jB_SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SimpanActionPerformed
        // TODO add your handling code here:
        SimpanJabatan();
    }//GEN-LAST:event_jB_SimpanActionPerformed

    private void jTb_jabatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTb_jabatanMouseClicked
        // TODO add your handling code here:
        TabelJabatanSelect();
    }//GEN-LAST:event_jTb_jabatanMouseClicked

    private void jB_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_batalActionPerformed
        // TODO add your handling code here:
        BatalJabatan();
    }//GEN-LAST:event_jB_batalActionPerformed

    private void jB_UbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_UbahActionPerformed
        // TODO add your handling code here:
        UbahJabatan();
    }//GEN-LAST:event_jB_UbahActionPerformed

    private void jB_HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_HapusActionPerformed
        // TODO add your handling code here:
        HapusJabatan();
    }//GEN-LAST:event_jB_HapusActionPerformed

    private void jB_KembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_KembaliActionPerformed
        // TODO add your handling code here:
        Kembali();
    }//GEN-LAST:event_jB_KembaliActionPerformed

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
            java.util.logging.Logger.getLogger(Jabatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Jabatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Jabatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Jabatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Jabatan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_Hapus;
    private javax.swing.JButton jB_Kembali;
    private javax.swing.JButton jB_Simpan;
    private javax.swing.JButton jB_Ubah;
    private javax.swing.JButton jB_batal;
    private com.toedter.calendar.JDateChooser jD_akhir;
    private com.toedter.calendar.JDateChooser jD_awal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jT_id;
    private javax.swing.JTextField jT_jabatan;
    private javax.swing.JTable jTb_jabatan;
    // End of variables declaration//GEN-END:variables

}
