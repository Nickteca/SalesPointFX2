package com.salespointfx2.www.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespointfx2.www.model.PaqueteProducto;
import com.salespointfx2.www.model.Producto;
import com.salespointfx2.www.repository.PaqueteProductoRepo;

@Service
public class PaqueteProductoService {
	@Autowired private PaqueteProductoRepo ppr;
	
	public List<PaqueteProducto> getPaqueteProductoById(Producto  paquete){
		return ppr.findByPaqueteIdPaquete(paquete);
	}
}
