/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.controller.controller_keuntungan;
import com.koneksi.koneksi;
import java.sql.Connection;
import com.view.keuntungan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class model_keuntungan implements controller_keuntungan  {

    @Override
    public void CariKeuntungan(keuntungan ks) throws SQLException {
                Connection con = (Connection) koneksi.getKoneksi();
                String cariHarian = "SELECT * FROM transaksi where tanggal_transaksi BETWEEN  ? AND ?";
                PreparedStatement prep2 = con.prepareStatement(cariHarian);
                prep2.setString(1, ks.tgl_awal.getText());
                prep2.setString(2, ks.tgl_akhir.getText());
                ResultSet rs2 = prep2.executeQuery();
                int totalKeuntungan = 0;
                while(rs2.next()){
                totalKeuntungan = totalKeuntungan + rs2.getInt("keuntungan");
                }
                String hari;
                ks.hasilCari.setText("Total Keuntungan Rp "+String.valueOf(totalKeuntungan)+" ,-");
    }
    
}
