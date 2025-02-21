package com.salespointfx2.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.Venta;

public interface VentaRepo extends JpaRepository<Venta,Integer> {

}