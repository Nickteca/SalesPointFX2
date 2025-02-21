package com.salespointfx2.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.Producto;

public interface ProductoRepo extends JpaRepository<Producto, Integer> {
	
}
