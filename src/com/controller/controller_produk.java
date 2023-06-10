/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.sql.SQLException;
import com.view.Utama;

/**
 *
 * @author HP
 */
public interface controller_produk {
    public void Simpan (Utama fm) throws SQLException;
    public void Ubah(Utama fm) throws SQLException;
    public void Hapus(Utama fm) throws SQLException;
    public void Tampil (Utama fm) throws SQLException;
    public void Bersih (Utama fm) throws SQLException;
    public void KlikTabel (Utama fm) throws SQLException;
    public void refeshData (Utama fm) throws SQLException;
    public void qcari (Utama fm) throws SQLException;
    
}
