package com.salespointfx2.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.Venta;
import com.salespointfx2.www.model.VentaDetalle;

public interface VentaDetalleRepo extends JpaRepository<VentaDetalle, Integer> {
	List<VentaDetalle> findByVentaIdVenta(Venta ventaIdVenta);
}
