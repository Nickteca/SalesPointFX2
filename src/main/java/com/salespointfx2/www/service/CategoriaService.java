package com.salespointfx2.www.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespointfx2.www.model.Categoria;
import com.salespointfx2.www.repository.CategoriaRepo;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepo cr;
	@Autowired
	private SucursalService ss;

	public List<Categoria> getAllCategorias() {
		return cr.findAll();
	}

}