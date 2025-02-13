package com.salespointfx2.www.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespointfx2.www.model.Sucursal;
import com.salespointfx2.www.repository.SucursalRepo;

@Service
public class SucursalService {
	@Autowired
	private SucursalRepo sr;

	public Optional<Sucursal> getSucursalActive() {
		return sr.findByEstatusSucursalTrue();
	}
}
