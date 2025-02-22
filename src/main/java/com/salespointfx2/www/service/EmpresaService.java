package com.salespointfx2.www.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespointfx2.www.model.Empresa;
import com.salespointfx2.www.repository.EmpresaRepo;

@Service
public class EmpresaService {
	@Autowired
	private EmpresaRepo er;

	public Empresa getEmpresa() {
		return er.findById((short) 1).get();
	}
}
