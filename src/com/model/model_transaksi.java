
package com.model;

import com.controller.controller_transaksi;
import java.sql.Connection;
import com.koneksi.koneksi;
import com.view.transaksi;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class model_transaksi implements controller_transaksi {

    public void Simpan(transaksi ts) throws SQLException {
        
        try {
            
            Connection con = (Connection) koneksi.getKoneksi();
            String sql = "insert transaksi values(?,?,?,?,?)";
            
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setString(1, ts.id_transaksi.getText());
            prep.setString(2, ts.produk.getSelectedItem().toString());
            prep.setString(3, ts.jumlah.getText());
            prep.setString(4, ts.tanggal.getText());
            
            
            
            String data_harga = "select * from produk where nama_produk = ? ";
            PreparedStatement prep2 = con.prepareStatement(data_harga);
            prep2.setString(1, ts.produk.getSelectedItem().toString());
            ResultSet rs = prep2.executeQuery();
            while(rs.next()){
                String estUntung = rs.getString("untung");
                String stk = rs.getString("stok");
                String jmlh = ts.jumlah.getText();
                int sisa = Integer.parseInt(stk) - Integer.parseInt(jmlh);
                int total_untung = Integer.parseInt(estUntung) * Integer.parseInt(jmlh);
                String ttl_untung = Integer.toString(total_untung);
                prep.setString(5,ttl_untung);
                    String sql3 = "update produk set stok =? "
                            +"where nama_produk = ? ";
                try (PreparedStatement prep3 = con.prepareStatement(sql3)) {
                    prep3.setInt(1, sisa);
                    prep3.setString(2, ts.produk.getSelectedItem().toString());
                    prep3.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
                }
            }
            prep.executeUpdate();
           
        } catch (Exception e) {
            System.out.print(e);
        } finally{
            Tampil(ts);
            ts.setLebarKolom();
            Bersih(ts);
        }
    }

    @Override
    public void Ubah(transaksi ts) throws SQLException {
        try {
            Connection con = (Connection) koneksi.getKoneksi();
            String sql = "update transaksi set produk= ?, "
                    + "jumlah = ?, "
                    + "tanggal_transaksi = ?, "
                    + "keuntungan = ? "
                    +"where kode_rental = ?";
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setString(1, ts.produk.getSelectedItem().toString());
            prep.setString(2, ts.jumlah.getText());
            prep.setString(3, ts.tanggal_transaksi.getDate().toString());
            prep.setString(4, ts.untung.getText());
            prep.setString(5, ts.id_transaksi.getText());
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!");
            prep.close();
        } catch(Exception e) {
            System.out.print(e);
        } finally {
            Tampil(ts);
            ts.setLebarKolom();
            Bersih(ts);
        }
    }

    @Override
    public void Hapus(transaksi ts) throws SQLException {
        try {
        Connection con = (Connection) koneksi.getKoneksi();
        String sql = "delete from transaksi where id_transaksi = ? ";
        PreparedStatement prepHa = con.prepareStatement(sql);
        prepHa.setString(1, ts.id_transaksi.getText());
        String data_harga = "select * from produk where nama_produk = ? ";
            PreparedStatement prep2 = con.prepareStatement(data_harga);
            prep2.setString(1, ts.produk.getSelectedItem().toString());
            ResultSet rs = prep2.executeQuery();
            while(rs.next()){
                String estUntung = rs.getString("untung");
                String stk = rs.getString("stok");
                String jmlh = ts.jumlah.getText();
                int sisa = Integer.parseInt(stk) + Integer.parseInt(jmlh);
                int total_untung = Integer.parseInt(estUntung) * Integer.parseInt(jmlh);
                String ttl_untung = Integer.toString(total_untung);
                    String sql3 = "update produk set stok =? "
                            +"where nama_produk = ? ";
                try (PreparedStatement prep3 = con.prepareStatement(sql3)) {
                    prep3.setInt(1, sisa);
                    prep3.setString(2, ts.produk.getSelectedItem().toString());
                    prep3.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
                }
            }
        prepHa.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus!");
        prepHa.close();
    } catch (Exception e) {
        System.out.print(e);    
    } finally {
        Tampil(ts);
        ts.setLebarKolom();
        Bersih(ts);
        }
    }

    @Override
    public void Tampil(transaksi ts) throws SQLException {
        try {
            ts.tbl.getDataVector().removeAllElements();
            ts.tbl.fireTableDataChanged();
            try {
                Connection con = (Connection) koneksi.getKoneksi();
                Statement st = con.createStatement();
                String Sql = "select * from transaksi order by id_transaksi asc";
                ResultSet rs = st.executeQuery(Sql);
                while (rs.next()) {
                    Object[] ob = new Object[7];
                    ob[0] = rs.getString(1);
                    ob[1] = rs.getString(2);
                    ob[2] = rs.getString(3);
                    ob[3] = rs.getString(4);
                    String untng = "Rp "+rs.getString(5);
                    ob[4] = untng;
                    ts.tbl.addRow(ob);
                }
            } catch (Exception e) {
                System.out.print(e);
            }
        } catch (Exception e) {
            
        }
    }

    @Override
    public void Bersih(transaksi ts) throws SQLException {
        ts.id_transaksi.setText(null);
        ts.jumlah.setText(null);
        ts.produk.setSelectedItem(null);
        ts.untung.setText(null);
        ts.tanggal_transaksi.setDate(null);
    }

    @Override
    public void KlikTabel(transaksi ts) throws SQLException {
        try {
            int pilih = ts.tbl_transaksi.getSelectedRow();
            String s = (String) ts.tbl_transaksi.getModel().getValueAt(pilih, 4);
            ts.id_transaksi.setText(ts.tbl_transaksi.getValueAt(pilih, 0).toString());
            ts.produk.setSelectedItem(ts.tbl_transaksi.getValueAt(pilih, 1).toString());
            ts.jumlah.setText(ts.tbl_transaksi.getValueAt(pilih, 2).toString());
            Date date = new SimpleDateFormat().parse(ts.tbl_transaksi.getValueAt(pilih, 3).toString());
            ts.tanggal_transaksi.setDate(date);
            ts.untung.setText(ts.tbl_transaksi.getValueAt(pilih, 4).toString());
            
        } catch (Exception e) {
        }
    }

    @Override
    public void TambahProduk(transaksi ts) throws SQLException {
        try {
            Connection con = (Connection) koneksi.getKoneksi();
            String data_harga = "select * from produk where nama_produk = ? ";
            PreparedStatement prep2 = con.prepareStatement(data_harga);
            prep2.setString(1, ts.produk.getSelectedItem().toString());
            ResultSet rs = prep2.executeQuery();
            while(rs.next()){
                String stk = rs.getString("stok");
                String jmlh = ts.jumlah.getText();
                int sisa = Integer.parseInt(stk) + Integer.parseInt(jmlh);
                    String sql3 = "update produk set stok =? "
                            +"where nama_produk = ? ";
                try (PreparedStatement prep3 = con.prepareStatement(sql3)) {
                    prep3.setInt(1, sisa);
                    prep3.setString(2, ts.produk.getSelectedItem().toString());
                    prep3.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
                }
            }
        } catch(Exception e) {
            System.out.print(e);
        } finally {
            Tampil(ts);
            ts.setLebarKolom();
            Bersih(ts);
        }
    }
    
}

