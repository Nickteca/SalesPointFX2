package com.salespointfx2.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.PaqueteProducto;
import com.salespointfx2.www.model.Producto;

public interface PaqueteProductoRepo extends JpaRepository<PaqueteProducto, Integer> {
	List<PaqueteProducto> findByPaqueteIdPaquete(Producto paqueteIdPaquete);
}
