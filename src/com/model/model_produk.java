
package com.model;

import com.controller.controller_produk;
import java.sql.Connection;
import com.koneksi.koneksi;
import com.view.Utama;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class model_produk implements controller_produk {

    @Override
    public void Simpan(Utama fm) throws SQLException {
        try {
            Connection con = (Connection) koneksi.getKoneksi();
            String sql = "insert produk values(?,?,?,?,?,?)";
            PreparedStatement prep = con.prepareStatement(sql);
            
            prep.setString(1, fm.kode_produk.getText());
            prep.setString(2, fm.nama_produk.getText());
            prep.setString(3, fm.stok.getText());
            prep.setString(4, fm.modal_beli.getText());
            prep.setString(5, fm.harga_jual.getText());
            int modal = Integer.parseInt(fm.modal_beli.getText());
            int jual = Integer.parseInt(fm.harga_jual.getText());
            int keuntungan = jual-modal;
            String untungFix = Integer.toString(keuntungan);
            prep.setString(6, untungFix);
            prep.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
            prep.close();
        } catch (Exception e) {
            System.out.print(e);
        } finally{
            Tampil(fm);
            fm.setLebarKolom();
            Bersih(fm);
        }
    }

    @Override
    public void Ubah(Utama fm) throws SQLException {
        try {
            Connection con = (Connection) koneksi.getKoneksi();
            String sql = "update produk set nama_produk= ?, "
                    + "stok = ?, "
                    + "modal_beli = ?, "
                    + "harga_jual = ?, "
                    + "untung = ? "
                    +"where kode_produk = ?";
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setString(1, fm.nama_produk.getText());
            prep.setString(2, fm.stok.getText());
            prep.setString(3, fm.modal_beli.getText());
            prep.setString(4, fm.harga_jual.getText());
            int modal = Integer.parseInt(fm.modal_beli.getText());
            int jual = Integer.parseInt(fm.harga_jual.getText());
            int keuntungan = jual-modal;
            String untungFix = Integer.toString(keuntungan);
            prep.setString(5, untungFix);
            prep.setString(6, fm.kode_produk.getText());
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!");
            prep.close();
        } catch(Exception e) {
            System.out.print(e);
        } finally {
            Tampil(fm);
            fm.setLebarKolom();
            Bersih(fm);
        }
    }

    @Override
    public void Hapus(Utama fm) throws SQLException {
        try {
        Connection con = (Connection) koneksi.getKoneksi();
        String sql = "delete from produk where kode_produk = ? ";
        PreparedStatement prep = con.prepareStatement(sql);
        prep.setString(1, fm.kode_produk.getText());
        prep.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus!");
        prep.close();
    } catch (Exception e) {
        System.out.print(e);    
    } finally {
        Tampil(fm);
        fm.setLebarKolom();
        Bersih(fm);
        }
    }

    @Override
    public void Tampil(Utama fm) throws SQLException {
        try {
            fm.tbl.getDataVector().removeAllElements();
            fm.tbl.fireTableDataChanged();
            try {
                Connection con = (Connection) koneksi.getKoneksi();
                Statement st = con.createStatement();
                String Sql = "select * from produk order by nama_produk asc";
                ResultSet rs = st.executeQuery(Sql);
                while (rs.next()) {
                    Object[] ob = new Object[8];
                    ob[0] = rs.getString(1);
                    ob[1] = rs.getString(2);
                    ob[2] = rs.getString(3);
                    ob[3] = "Rp "+rs.getString(4);
                    ob[4] = "Rp "+rs.getString(5);
                    ob[5] = "Rp "+rs.getString(6);
                    fm.tbl.addRow(ob);
                }
            } catch (Exception e) {
                System.out.print(e);
            }
        } catch (Exception e) {
            
        }
    }

    @Override
    public void Bersih(Utama fm) throws SQLException {
        fm.kode_produk.setText(null);
        fm.nama_produk.setText(null);
        fm.stok.setText(null);
        fm.modal_beli.setText(null);
        fm.harga_jual.setText(null);
        fm.untung.setText(null);
    }

    @Override
    public void KlikTabel(Utama fm) throws SQLException {
        try {
            int pilih = fm.tbl_produk.getSelectedRow();
            String s = (String) fm.tbl_produk.getModel().getValueAt(pilih, 5);
            fm.kode_produk.setText(fm.tbl_produk.getValueAt(pilih, 0).toString());
            fm.nama_produk.setText(fm.tbl_produk.getValueAt(pilih, 1).toString());
            fm.stok.setText(fm.tbl_produk.getValueAt(pilih, 2).toString());
            String kasar_modal_beli = fm.tbl_produk.getValueAt(pilih, 3).toString();
            String bersih_modal_beli = kasar_modal_beli.replaceAll("[^\\d]", "");
            fm.modal_beli.setText(bersih_modal_beli);
//            fm.modal_beli.setText(fm.tbl_produk.getValueAt(pilih, 3).toString());
            String kasar_harga_jual = fm.tbl_produk.getValueAt(pilih, 4).toString();
            String bersih_harga_jual = kasar_harga_jual.replaceAll("[^\\d]", "");
            fm.harga_jual.setText(bersih_harga_jual);
//            fm.harga_jual.setText(fm.tbl_produk.getValueAt(pilih, 4).toString());
            String nilai = fm.tbl_produk.getValueAt(pilih, 5).toString();
            String nilaiBersih = nilai.replaceAll("[^\\d]", ""); // Menghapus semua karakter selain angka
            fm.untung.setText(nilaiBersih);
//            fm.untung.setText(fm.tbl_produk.getValueAt(pilih, 5).toString());
        } catch (Exception e) {
        }
    }

    @Override
    public void refeshData(Utama fm) throws SQLException {
        Tampil(fm);
        fm.setLebarKolom();
        Bersih(fm);
    }

    @Override
   public void qcari(Utama fm) throws SQLException {
    try {
        fm.tbl.getDataVector().removeAllElements();
        fm.tbl.fireTableDataChanged();
        try {
            Connection con = (Connection) koneksi.getKoneksi();
            String searchTerm = fm.qcari.getText(); // store the search term in a variable
            String cariProduk = "SELECT * FROM produk where nama_produk LIKE ?";
            PreparedStatement prep2 = con.prepareStatement(cariProduk);
            prep2.setString(1, "%" + searchTerm + "%");
            ResultSet rs = prep2.executeQuery();
            while (rs.next()) {
                Object[] ob = new Object[6];
                ob[0] = rs.getString("kode_produk");
                ob[1] = rs.getString("nama_produk");
                ob[2] = rs.getString("stok");
                ob[3] = "Rp "+rs.getString("modal_beli");
                ob[4] = "Rp "+rs.getString("harga_jual");
                ob[5] = "Rp "+rs.getString("untung");
                fm.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    } catch (Exception e) {
        
    }
}
//    public void qcari(Utama fm) throws SQLException {
//        try {
//            fm.tbl.getDataVector().removeAllElements();
//            fm.tbl.fireTableDataChanged();
//            try {
//                Connection con = (Connection) koneksi.getKoneksi();
//                Statement st = con.createStatement();
////                String Sql = "select * from produk where nama_produk = '"+fm.qcari.getText()+"'";
////                ResultSet rs = st.executeQuery(Sql);
////                com.mysql.jdbc.Connection con = (com.mysql.jdbc.Connection) koneksi.getKoneksi();
//                String cariHarian = "SELECT * FROM produk where nama_produk = ? ";
//                PreparedStatement prep2 = con.prepareStatement(cariHarian);
//                prep2.setString(1, fm.qcari.getText());
//                ResultSet rs = prep2.executeQuery();
//                System.out.println(rs);
//                while (rs.next()) {
//                    Object[] ob = new Object[8];
//                    ob[0] = rs.getString(1);
//                    ob[1] = rs.getString(2);
//                    ob[2] = rs.getString(3);
//                    ob[3] = rs.getString(4);
//                    ob[4] = rs.getString(5);
//                    ob[5] = rs.getString(6);
//                    fm.tbl.addRow(ob);
//                }
//            } catch (Exception e) {
//                System.out.print(e);
//            }
//        } catch (Exception e) {
//            
//        }
//    }
    
}

