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

	public List<SucursalProducto> getAllProductosVendibles() {
		return spr.findBySucursalIdSucursalAndVendibleTrue(ss.getSucursalActive().get());
	}

	public List<SucursalProducto> getAllProductosVendibleXCategpria(Categoria categoria) {
		return spr.findBySucursalIdSucursalAndVendibleTrueAndCategoriaIdCategoria(ss.getSucursalActive().get(), categoria);
	}

}
