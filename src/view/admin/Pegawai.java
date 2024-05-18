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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author adity
 */
public class Pegawai extends javax.swing.JFrame {

    private final Connection kon = new koneksi().connect();
    private Statement st;
    private ResultSet rs, rst;
    private String query = "";
    private String Id_Pegawai, Nama, JK, Tempat_lahir, Tanggal_lahir, Alamat, Kelurahan, Kecamatan, Kota, Email, No_Hp, JB, Jabatan, A, GD, SK, Created_At, Created_By, Updated_At, Updated_By;
    private int Jenis_Kelamin, Agama, Golongan_Darah, Status;

    /**
     * Creates new form Pegawai
     */
    public Pegawai() {
        initComponents();
        LebarKolom();
        IdPegawai();
        OptionJabatan();
        OptionAgama();
        OptionGoldarah();
        OptionStatkawin();
        DataPegawai();
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

    private void IdPegawai() {
        try {
            query = "SELECT * FROM profil_pegawai ORDER BY id_pegawai desc";
            st = kon.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                String idjb = rs.getString("Id_Pegawai").substring(4);
                String AN = "" + (Integer.parseInt(idjb) + 1);
                String Nol = "";

                if (AN.length() == 1) {
                    Nol = "000";
                } else if (AN.length() == 2) {
                    Nol = "00";
                } else if (AN.length() == 3) {
                    Nol = "0";
                } else if (AN.length() == 4) {
                    Nol = "";
                }

                jT_id.setText("PEG" + Nol + AN);
            } else {
                jT_id.setText("PEG0001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void LebarKolom() {
        TableColumn column;
        jTb_pegawai.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTb_pegawai.getColumnModel().getColumn(2).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(3).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(1).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(4).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(5).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(6).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(7).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(8).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(9).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(10).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(11).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(12).setPreferredWidth(70);
        jTb_pegawai.getColumnModel().getColumn(13).setPreferredWidth(70);
    }

    private void BatalPegawai() {
        IdPegawai();
        jC_agama.setSelectedIndex(0);
        jC_goldarah.setSelectedIndex(0);
        jC_jabatan.setSelectedIndex(0);
        jC_status.setSelectedIndex(0);
        jD_tgl_lahir.setCalendar(null);
        jF_email.setText("");
        jF_telp.setText("");
        jR_1.setSelected(false);
        jR_2.setSelected(false);
        jT_alamat.setText("");
        jT_kec.setText("");
        jT_kel.setText("");
        jT_kota.setText("");
        jT_kota_lahir.setText("");
        jT_nama.setText("");
    }

    ;
    private void SimpanPegawai() {
        int simpan = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Menyimpan Data Ini?", "save", JOptionPane.YES_NO_OPTION);
        if (simpan == 0) {
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
            Created_At = String.valueOf(fd.format(new Date()));
            Created_By = String.valueOf(jT_id.getText());

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
                query = "INSERT INTO profil_pegawai (Id_Pegawai,Nama,Jenis_Kelamin,Tempat_lahir,Tanggal_lahir,Alamat,Kelurahan,Kecamatan,kota,Email,No_Hp,Jabatan,Agama,Golongan_Darah,Status,Created_At,Created_By) VALUES "
                        + "('" + Id_Pegawai + "',"
                        + "'" + Nama + "',"
                        + "'" + Jenis_Kelamin + "',"
                        + "'" + Tempat_lahir + "',"
                        + "'" + Tanggal_lahir + "',"
                        + "'" + Alamat + "',"
                        + "'" + Kelurahan + "',"
                        + "'" + Kecamatan + "',"
                        + "'" + Kota + "',"
                        + "'" + Email + "',"
                        + "'" + No_Hp + "',"
                        + "'" + Jabatan + "',"
                        + "'" + Agama + "',"
                        + "'" + Golongan_Darah + "',"
                        + "'" + Status + "',"
                        + "'" + Created_At + "',"
                        + "'" + Created_By + "')";
                st = kon.createStatement();
                st.executeUpdate(query);
                IdPegawai();
                BatalPegawai();
                DataPegawai();
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DISIMPAN");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "DATA GAGAL DISIMPAN, SILAHKAN MASUKKAN KEMBALI DATA DENGAN BENAR");
                e.printStackTrace();
            }
            jT_nama.requestFocus();
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
            Updated_At = String.valueOf(fd.format(new Date()));
            Updated_By = String.valueOf(jT_id.getText());

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
                BatalPegawai();
                DataPegawai();
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DIUBAH");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "DATA GAGAL DIUBAH, SILAHKAN UBAH DATA DENGAN BENAR \n " + e.getMessage());
            }
            jT_nama.requestFocus();
        }
    }

    public void DataPegawai() {
        DefaultTableModel datapegawai = new DefaultTableModel();
        datapegawai.addColumn("ID Pegawai"); //1
        datapegawai.addColumn("Nama Lengka"); //2
        datapegawai.addColumn("Gender"); //3
        datapegawai.addColumn("Tempat, Tanggal Lahir"); //4,5
        datapegawai.addColumn("Alamat"); //6
        datapegawai.addColumn("Kelurahan"); //7
        datapegawai.addColumn("Kecamatan"); //8
        datapegawai.addColumn("Kota"); //9
        datapegawai.addColumn("Email"); //10
        datapegawai.addColumn("No. Telp"); //11
        datapegawai.addColumn("Jabatan"); //12
        datapegawai.addColumn("Agama"); //13
        datapegawai.addColumn("Golongan Darah"); //14
        datapegawai.addColumn("Status Pernikahan"); //15
        try {
            st = kon.createStatement();
            rs = st.executeQuery("SELECT * FROM profil_pegawai");
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

                rs = st.executeQuery("SELECT * FROM profil_pegawai");
                if (rs.next()) {
                    datapegawai.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        JK,
                        rs.getString(4) + ", " + rs.getDate(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        JB,
                        A,
                        GD,
                        SK
                    });
                }
            }
            jTb_pegawai.setModel(datapegawai);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal Tampil \n" + e.getMessage());
        }
    }

    private void HapusPegawai() {
        DefaultTableModel model = (DefaultTableModel) jTb_pegawai.getModel();
        int row = jTb_pegawai.getSelectedRow();
        if (row >= 0) {
            int delete = JOptionPane.showConfirmDialog(null, "Apakah Yakin ingin menghapus data ini?", "delete", JOptionPane.YES_NO_OPTION);
            if (delete == 0) {
                model.removeRow(row);
                try {

                    query = "DELETE FROM profil_pegawai WHERE id_pegawai ='" + jT_id.getText() + "'";
                    st = kon.createStatement();
                    st.execute(query);
                    JOptionPane.showMessageDialog(null, "DATA BERHASIL DIHAPUS");
                    BatalPegawai();
                    DataPegawai();
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, "DATA GAGAL DIHAPUS, SILAHKAN COBA KEMBALI \n" + e.getMessage());
                }
                jT_nama.requestFocus();
            }
        }
    }

    ;

    private void TabelPegawaiSelect() {
        try {
            BatalPegawai();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern();
            int baris = jTb_pegawai.getSelectedRow();

            Id_Pegawai = jTb_pegawai.getValueAt(baris, 0).toString();
            Nama = jTb_pegawai.getValueAt(baris, 1).toString();
            JK = jTb_pegawai.getValueAt(baris, 2).toString();
            if (JK.equals("Laki-Laki")) {
                jR_1.setSelected(true);
            } else {
                jR_2.setSelected(true);
            }
            Pattern pattern = Pattern.compile("^([a-zA-Z]+), ([0-9-]+)");
            Matcher m = pattern.matcher(jTb_pegawai.getValueAt(baris, 3).toString());
            if (m.find()) {
                Tempat_lahir = m.group(1);
                Tanggal_lahir = m.group(2);
                Date TL = (Date) formatter.parse(Tanggal_lahir);
                System.out.print(TL);
                jD_tgl_lahir.setDate(TL);
            }
            Alamat = jTb_pegawai.getValueAt(baris, 4).toString();
            Kelurahan = jTb_pegawai.getValueAt(baris, 5).toString();
            Kecamatan = jTb_pegawai.getValueAt(baris, 6).toString();
            Kota = jTb_pegawai.getValueAt(baris, 7).toString();
            Email = jTb_pegawai.getValueAt(baris, 8).toString();
            No_Hp = jTb_pegawai.getValueAt(baris, 9).toString();
            JB = jTb_pegawai.getValueAt(baris, 10).toString();
            A = jTb_pegawai.getValueAt(baris, 11).toString();
            GD = jTb_pegawai.getValueAt(baris, 12).toString();
            SK = jTb_pegawai.getValueAt(baris, 13).toString();
            jC_agama.setSelectedItem(A);
            jC_goldarah.setSelectedItem(GD);
            jC_jabatan.setSelectedItem(JB);
            jC_status.setSelectedItem(SK);
            jT_kota_lahir.setText(Tempat_lahir);
            jF_email.setText(Email);
            jF_telp.setText(No_Hp);
            jT_alamat.setText(Alamat);
            jT_id.setText(Id_Pegawai);
            jT_kec.setText(Kecamatan);
            jT_kel.setText(Kelurahan);
            jT_kota.setText(Kota);
            jT_nama.setText(Nama);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jT_nama = new javax.swing.JTextField();
        jT_kota_lahir = new javax.swing.JTextField();
        jT_kel = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jT_alamat = new javax.swing.JTextArea();
        jT_kec = new javax.swing.JTextField();
        jT_kota = new javax.swing.JTextField();
        jF_email = new javax.swing.JFormattedTextField();
        jC_agama = new javax.swing.JComboBox<>();
        jC_goldarah = new javax.swing.JComboBox<>();
        jC_status = new javax.swing.JComboBox<>();
        jR_1 = new javax.swing.JRadioButton();
        jR_2 = new javax.swing.JRadioButton();
        jD_tgl_lahir = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jC_jabatan = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jF_telp = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTb_pegawai = new javax.swing.JTable();
        jB_ubah = new javax.swing.JButton();
        jB_hapus = new javax.swing.JButton();
        jB_batal = new javax.swing.JButton();
        jB_kembali = new javax.swing.JButton();
        jB_simpan = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jT_id = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Data pegawai");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jT_alamat.setColumns(20);
        jT_alamat.setRows(5);
        jScrollPane1.setViewportView(jT_alamat);

        jC_agama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- pilih --" }));
        jC_agama.setToolTipText("Pilih");

        jC_goldarah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- pilih --" }));

        jC_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih--" }));

        buttonGroup1.add(jR_1);
        jR_1.setText("Laki-Laki");

        buttonGroup1.add(jR_2);
        jR_2.setText("Perempuan");

        jLabel2.setText("Nama Lengkap");

        jLabel3.setText("Gender");

        jLabel4.setText("TTL");

        jLabel5.setText("Alamat");

        jLabel6.setText("Kelurahan");

        jLabel7.setText("Kecamatan");

        jLabel8.setText("Kota");

        jLabel9.setText("No. Telpon");

        jLabel10.setText("Email");

        jC_jabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- pilih --" }));

        jLabel12.setText("Jabatan");

        jLabel11.setText("Agama");

        jLabel13.setText("Gol Darah");

        jLabel14.setText("Status "); // NOI18N

        jScrollPane2.setToolTipText("");
        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });
        jScrollPane2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jScrollPane2KeyPressed(evt);
            }
        });

        jTb_pegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Pegawai", "Nama Lengkap", "Gender", "Tempat, Tanggal Lahir", "Alamat", "Kelurahan", "Kecamatan", "Kota", "Email", "No_HP", "Jabatan", "Agama", "Golongan Darah", "Status Pernikahan"
            }
        ));
        jTb_pegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTb_pegawaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTb_pegawai);

        jB_ubah.setText("Ubah");
        jB_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ubahActionPerformed(evt);
            }
        });

        jB_hapus.setText("Hapus");
        jB_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_hapusActionPerformed(evt);
            }
        });

        jB_batal.setText("Batal");
        jB_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_batalActionPerformed(evt);
            }
        });

        jB_kembali.setText("Kembali");
        jB_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_kembaliActionPerformed(evt);
            }
        });

        jB_simpan.setText("Simpan");
        jB_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_simpanActionPerformed(evt);
            }
        });

        jLabel15.setText("ID Pegawai");

        jT_id.setEditable(false);

        jLabel16.setText(",");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_ubah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                            .addComponent(jT_kel, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                            .addComponent(jT_kec)
                                            .addComponent(jT_kota)))
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
                                    .addComponent(jT_id)))
                            .addComponent(jB_simpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 991, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jT_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jT_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jR_1)
                            .addComponent(jR_2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jT_kota_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jF_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(28, 28, 28)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jC_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel12)))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel9)
                                                .addComponent(jF_telp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jLabel10)))
                            .addComponent(jD_tgl_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jC_agama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jC_goldarah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jC_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_simpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jB_ubah)
                            .addComponent(jB_hapus)
                            .addComponent(jB_batal)
                            .addComponent(jB_kembali)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jB_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_kembaliActionPerformed
        // TODO add your handling code here:
        Kembali();
    }//GEN-LAST:event_jB_kembaliActionPerformed

    private void jB_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_simpanActionPerformed
        // TODO add your handling code here:
        SimpanPegawai();
    }//GEN-LAST:event_jB_simpanActionPerformed

    private void jB_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_batalActionPerformed
        // TODO add your handling code here:
        BatalPegawai();
    }//GEN-LAST:event_jB_batalActionPerformed

    private void jScrollPane2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jScrollPane2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2KeyPressed

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void jTb_pegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTb_pegawaiMouseClicked
        // TODO add your handling code here:
        TabelPegawaiSelect();

    }//GEN-LAST:event_jTb_pegawaiMouseClicked

    private void jB_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ubahActionPerformed
        // TODO add your handling code here:
        UbahPegawai();
    }//GEN-LAST:event_jB_ubahActionPerformed

    private void jB_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_hapusActionPerformed
        // TODO add your handling code here:
        HapusPegawai();
    }//GEN-LAST:event_jB_hapusActionPerformed

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
            java.util.logging.Logger.getLogger(Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jB_batal;
    private javax.swing.JButton jB_hapus;
    private javax.swing.JButton jB_kembali;
    private javax.swing.JButton jB_simpan;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jT_alamat;
    private javax.swing.JTextField jT_id;
    private javax.swing.JTextField jT_kec;
    private javax.swing.JTextField jT_kel;
    private javax.swing.JTextField jT_kota;
    private javax.swing.JTextField jT_kota_lahir;
    private javax.swing.JTextField jT_nama;
    private javax.swing.JTable jTb_pegawai;
    // End of variables declaration//GEN-END:variables
}
