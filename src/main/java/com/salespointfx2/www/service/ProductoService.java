package com.salespointfx2.www.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespointfx2.www.model.Producto;
import com.salespointfx2.www.repository.ProductoRepo;

@Service
public class ProductoService {
	@Autowired private ProductoRepo pr;
	
	public Producto getProductoById(int idProducto) {
		return pr.findById(idProducto).get();
	}
}
