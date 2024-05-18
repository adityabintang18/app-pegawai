/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.user;

import connection.koneksi;
import data.Users;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import view.admin.Home;

/**
 *
 * @author pc
 */
public class U_Profile extends javax.swing.JFrame {

    private final Connection kon = new koneksi().connect();
    private Statement st;
    private ResultSet rs, rst;
    private String query = "";
    private String Id_Pegawai, Nama, JK, Tempat_lahir, Tanggal_lahir, Alamat, Kelurahan, Kecamatan, Kota, Email, No_Hp, JB, Jabatan, A, GD, SK, Created_At, Created_By, Updated_At, Updated_By,user,pass,name;
    private int Jenis_Kelamin, Agama, Golongan_Darah, Status;

    /**
     * Creates new form Absensi
     */
    public U_Profile(Users users) {
        initComponents();
        OptionJabatan();
        OptionAgama();
        OptionGoldarah();
        OptionStatkawin();
        DataProfile(users);
    }

    public void OptionJabatan() {
        try {
            query = "SELECT * FROM ref_jabatan";
            st = kon.createStatement();
            rs = st.executeQuery(query);

            List<String> items = new ArrayList<>();
            while (rs.next()) {
                items.add(rs.getString("nama_jabatan")); // Ganti column_name dengan nama kolom yang sesuai
            }

            // Memasukkan data dari ArrayList ke dalam JComboBox
            for (String item : items) {
                jC_jabatan.addItem(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void OptionAgama() {
        try {
            query = "SELECT * FROM ref_agama";
            st = kon.createStatement();
            rs = st.executeQuery(query);

            List<String> items = new ArrayList<>();
            while (rs.next()) {
                items.add(rs.getString("Agama")); // Ganti column_name dengan nama kolom yang sesuai
            }

            // Memasukkan data dari ArrayList ke dalam JComboBox
            for (String item : items) {
                jC_agama.addItem(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void OptionGoldarah() {
        try {
            query = "SELECT * FROM ref_goldarah";
            st = kon.createStatement();
            rs = st.executeQuery(query);

            List<String> items = new ArrayList<>();
            while (rs.next()) {
                items.add(rs.getString("Golongan_Darah")); // Ganti column_name dengan nama kolom yang sesuai
            }

            // Memasukkan data dari ArrayList ke dalam JComboBox
            for (String item : items) {
                jC_goldarah.addItem(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void OptionStatkawin() {
        try {
            query = "SELECT * FROM ref_statkawin";
            st = kon.createStatement();
            rs = st.executeQuery(query);

            List<String> items = new ArrayList<>();
            while (rs.next()) {
                items.add(rs.getString("Stat_Kawin")); // Ganti column_name dengan nama kolom yang sesuai
            }

            // Memasukkan data dari ArrayList ke dalam JComboBox
            for (String item : items) {
                jC_status.addItem(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void DataProfile(Users users) {
        user = users.getUsername();
        pass = users.getPassword();
        name = users.getName();
        try {
            System.out.print(users.getUsername());
            st = kon.createStatement();
            rs = st.executeQuery("SELECT id_pegawai FROM users where username ='" + users.getUsername() + "'");
            if (rs.next()) {
                Id_Pegawai = rs.getString(1);
            }
            st = kon.createStatement();
            rs = st.executeQuery("SELECT * FROM profil_pegawai where id_pegawai ='" + Id_Pegawai + "'");
            while (rs.next()) {
                try {
                    Jenis_Kelamin = rs.getInt(3);
                    Jabatan = rs.getString(12);
                    Agama = rs.getInt(13);
                    Golongan_Darah = rs.getInt(14);
                    Status = rs.getInt(15);
                    rst = st.executeQuery("SELECT Gender from ref_gender where id='" + Jenis_Kelamin + "'");
                    if (rst.next()) {
                        JK = rst.getString(1);
                    }
                    rst = st.executeQuery("SELECT nama_jabatan from ref_jabatan where id_jabatan='" + Jabatan + "'");
                    if (rst.next()) {
                        JB = rst.getString(1);
                    }
                    rst = st.executeQuery("SELECT Agama from ref_agama where id='" + Agama + "'");
                    if (rst.next()) {
                        A = rst.getString(1);
                    }
                    rst = st.executeQuery("SELECT Golongan_Darah from ref_goldarah where id='" + Golongan_Darah + "'");
                    if (rst.next()) {
                        GD = rst.getString(1);
                    }
                    rst = st.executeQuery("SELECT Stat_Kawin from ref_statkawin where id='" + Status + "'");
                    if (rst.next()) {
                        SK = rst.getString(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                rs = st.executeQuery("SELECT * FROM profil_pegawai where id_pegawai ='" + Id_Pegawai + "'");
                if (rs.next()) {

                    jC_agama.setSelectedItem(A);
                    jC_goldarah.setSelectedItem(GD);
                    jC_jabatan.setSelectedItem(JB);
                    jC_status.setSelectedItem(SK);
                    jD_tgl_lahir.setDate(rs.getDate(5));
                    jF_email.setText(rs.getString(10));
                    jF_telp.setText(rs.getString(11));
                    if (JK.equals("Laki-Laki")) {
                        jR_1.setSelected(true);
                    } else {
                        jR_2.setSelected(true);
                    }
                    jT_alamat.setText(rs.getString(6));
                    jT_kec.setText(rs.getString(8));
                    jT_kel.setText(rs.getString(7));
                    jT_kota.setText(rs.getString(9));
                    jT_kota_lahir.setText(rs.getString(4));
                    jT_nama.setText(rs.getString(2));
                    jT_id.setText(rs.getString(1));

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal Tampil \n" + e.getMessage());
        }
    }

    private void UbahPegawai() {
        int edit = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Mengubah Data Ini?", "edit", JOptionPane.YES_NO_OPTION);
        if (edit == 0) {
            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Id_Pegawai = String.valueOf(jT_id.getText());
            Nama = String.valueOf(jT_nama.getText());
            if (jR_1.isSelected()) {
                JK = String.valueOf(jR_1.getText());
            } else {
                JK = String.valueOf(jR_2.getText());
            }
            Tempat_lahir = String.valueOf(jT_kota_lahir.getText());
            Tanggal_lahir = fd.format(jD_tgl_lahir.getDate());
            Alamat = String.valueOf(jT_alamat.getText());
            Kelurahan = String.valueOf(jT_kel.getText());
            Kecamatan = String.valueOf(jT_kec.getText());
            Kota = String.valueOf(jT_kota.getText());
            Email = String.valueOf(jF_email.getText());
            No_Hp = String.valueOf(jF_telp.getText());
            JB = String.valueOf(jC_jabatan.getSelectedItem());
            A = String.valueOf(jC_agama.getSelectedItem());
            GD = String.valueOf(jC_goldarah.getSelectedItem());
            SK = String.valueOf(jC_status.getSelectedItem());
            Updated_At = String.valueOf(fd.format(new java.util.Date()));
            Updated_By = String.valueOf(jT_id.getText());
            try {
                rs = st.executeQuery("SELECT id from ref_gender where Gender='" + JK + "'");
                if (rs.next()) {
                    Jenis_Kelamin = rs.getInt(1);
                }
                rs = st.executeQuery("SELECT id_jabatan from ref_jabatan where nama_jabatan='" + JB + "'");
                if (rs.next()) {
                    Jabatan = rs.getString(1);
                }
                rs = st.executeQuery("SELECT id from ref_agama where Agama='" + A + "'");
                if (rs.next()) {
                    Agama = rs.getInt(1);
                }
                rs = st.executeQuery("SELECT id from ref_goldarah where Golongan_Darah='" + GD + "'");
                if (rs.next()) {
                    Golongan_Darah = rs.getInt(1);
                }
                rs = st.executeQuery("SELECT id from ref_statkawin where Stat_Kawin='" + SK + "'");
                if (rs.next()) {
                    Status = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                query = "UPDATE profil_pegawai SET "
                        + "Nama ='" + Nama + "', "
                        + "Jenis_Kelamin='" + Jenis_Kelamin + "', "
                        + "Tempat_lahir ='" + Tempat_lahir + "', "
                        + "Tanggal_lahir='" + Tanggal_lahir + "', "
                        + "Alamat='" + Alamat + "', "
                        + "Kelurahan='" + Kelurahan + "', "
                        + "Kecamatan ='" + Kecamatan + "', "
                        + "kota='" + Kota + "', "
                        + "Email ='" + Email + "', "
                        + "No_Hp='" + No_Hp + "', "
                        + "Jabatan ='" + Jabatan + "', "
                        + "Agama='" + Agama + "', "
                        + "Golongan_Darah ='" + Golongan_Darah + "', "
                        + "Status='" + Status + "', "
                        + "Updated_At='" + Updated_At + "', "
                        + "Updated_By='" + Updated_By + "' "
                        + "WHERE Id_Pegawai = '" + Id_Pegawai + "'";
                st = kon.createStatement();
                st.execute(query);

                JOptionPane.showMessageDialog(null, "DATA BERHASIL DIUBAH");
//                DataProfile(users);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "DATA GAGAL DIUBAH, SILAHKAN UBAH DATA DENGAN BENAR \n " + e.getMessage());
            }
            jT_nama.requestFocus();
        }
    }

    private void Kembali() {
        int close = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Kembali?", "select", JOptionPane.YES_NO_OPTION);
        if (close == 0) {
           Users users = new Users(user, pass, name);
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
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jT_nama = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jT_kota_lahir = new javax.swing.JTextField();
        jC_jabatan = new javax.swing.JComboBox<>();
        jT_kel = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jT_alamat = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jT_kec = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jT_kota = new javax.swing.JTextField();
        jF_telp = new javax.swing.JFormattedTextField();
        jF_email = new javax.swing.JFormattedTextField();
        jC_agama = new javax.swing.JComboBox<>();
        jC_goldarah = new javax.swing.JComboBox<>();
        jC_status = new javax.swing.JComboBox<>();
        jR_1 = new javax.swing.JRadioButton();
        jR_2 = new javax.swing.JRadioButton();
        jD_tgl_lahir = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jT_id = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jB_ubah = new javax.swing.JButton();
        jB_kembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Profile User");

        jLabel8.setText("Kota");

        jLabel9.setText("No. Telpon");

        jT_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jT_namaActionPerformed(evt);
            }
        });

        jLabel10.setText("Email");

        jC_jabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- pilih --" }));

        jLabel12.setText("Jabatan");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jT_alamat.setColumns(20);
        jT_alamat.setRows(5);
        jScrollPane1.setViewportView(jT_alamat);

        jLabel11.setText("Agama");

        jLabel13.setText("Gol Darah");

        jLabel14.setText("Status "); // NOI18N

        jC_agama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- pilih --" }));
        jC_agama.setToolTipText("Pilih");

        jC_goldarah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- pilih --" }));

        jC_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih--" }));

        jR_1.setText("Laki-Laki");

        jR_2.setText("Perempuan");

        jLabel2.setText("Nama Lengkap");

        jLabel3.setText("Gender");

        jLabel15.setText("ID Pegawai");

        jT_id.setEditable(false);

        jLabel4.setText("TTL");

        jLabel16.setText(",");

        jLabel5.setText("Alamat");

        jLabel6.setText("Kelurahan");

        jLabel7.setText("Kecamatan");

        jB_ubah.setText("Ubah");
        jB_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ubahActionPerformed(evt);
            }
        });

        jB_kembali.setText("Kembali");
        jB_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_kembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jC_jabatan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jR_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jT_kel)
                                    .addComponent(jT_kec)
                                    .addComponent(jT_kota, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jT_kota_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jD_tgl_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jR_1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jC_goldarah, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jC_agama, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jF_email)
                            .addComponent(jC_status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jT_nama)
                            .addComponent(jF_telp)
                            .addComponent(jT_id, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_ubah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jT_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jT_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jR_1)
                            .addComponent(jR_2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jT_kota_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(7, 7, 7)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jT_kel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jT_kec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jT_kota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jF_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(jC_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jF_telp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12))))
                            .addComponent(jD_tgl_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jC_agama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jC_goldarah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jC_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_kembali)
                    .addComponent(jB_ubah))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jB_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ubahActionPerformed
        // TODO add your handling code here:
        UbahPegawai();
    }//GEN-LAST:event_jB_ubahActionPerformed

    private void jB_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_kembaliActionPerformed
        // TODO add your handling code here:
        Kembali();
    }//GEN-LAST:event_jB_kembaliActionPerformed

    private void jT_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jT_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jT_namaActionPerformed

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
            java.util.logging.Logger.getLogger(U_Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(U_Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(U_Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(U_Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Users users = new Users(" ", " ", " ");
                new U_Profile(users).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_kembali;
    private javax.swing.JButton jB_ubah;
    private javax.swing.JComboBox<String> jC_agama;
    private javax.swing.JComboBox<String> jC_goldarah;
    private javax.swing.JComboBox<String> jC_jabatan;
    private javax.swing.JComboBox<String> jC_status;
    private com.toedter.calendar.JDateChooser jD_tgl_lahir;
    private javax.swing.JFormattedTextField jF_email;
    private javax.swing.JFormattedTextField jF_telp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jR_1;
    private javax.swing.JRadioButton jR_2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jT_alamat;
    private javax.swing.JTextField jT_id;
    private javax.swing.JTextField jT_kec;
    private javax.swing.JTextField jT_kel;
    private javax.swing.JTextField jT_kota;
    private javax.swing.JTextField jT_kota_lahir;
    private javax.swing.JTextField jT_nama;
    // End of variables declaration//GEN-END:variables
}
