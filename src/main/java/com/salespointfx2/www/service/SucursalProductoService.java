package com.salespointfx2.www.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespointfx2.www.model.Categoria;
import com.salespointfx2.www.model.SucursalProducto;
import com.salespointfx2.www.repository.SucursalProductoRepo;

@Service
public class SucursalProductoService {
	@Autowired
	private SucursalProductoRepo spr;
	@Autowired
	private SucursalService ss;

	/* SOLO QUE SON PRODUCTOS */
	public List<SucursalProducto> getAllProductosOnly() {
		return spr.findBySucursalIdSucursalAndProductoIdProductoEsPaqueteFalse(ss.getSucursalActive().get());
	}

	public List<SucursalProducto> getAllProductosVendibles() {
		return spr.findBySucursalIdSucursalAndVendibleTrue(ss.getSucursalActive().get());
	}

	public List<SucursalProducto> getAllProductosVendibleXCategpria(Categoria categoria) {
		return spr.findBySucursalIdSucursalAndVendibleTrueAndCategoriaIdCategoria(ss.getSucursalActive().get(), categoria);
	}

	public SucursalProducto getSucursalProductoById(Integer id) {
		return spr.findById(id).get();
	}

	// CON ESTA CON SULGA LO QUE HACEMOS ES OLVIDARNO DEL FETCH EAGER, YA QUE NO
	// CARGA AUTOMATOCAMENTE TODO
	public SucursalProducto getByIdWithProducto(int id) {
		return spr.findByIdWithProducto(id).get();
	}

	public void updateInventory(SucursalProducto sp) {
		spr.save(sp);
	}

}
