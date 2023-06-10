/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.sql.SQLException;
import com.view.transaksi;

/**
 *
 * @author HP
 */
public interface controller_transaksi {
    public void Simpan (transaksi ts) throws SQLException;
    public void Ubah(transaksi ts) throws SQLException;
    public void Hapus(transaksi ts) throws SQLException;
    public void Tampil (transaksi ts) throws SQLException;
    public void Bersih (transaksi ts) throws SQLException;
    public void KlikTabel (transaksi ts) throws SQLException;
    public void TambahProduk (transaksi ts) throws SQLException;

    
}
