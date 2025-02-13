package com.salespointfx2.www.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespointfx2.www.model.MovimientoCaja;
import com.salespointfx2.www.model.Sucursal;
import com.salespointfx2.www.repository.MovimientoCajaRepo;

@Service
public class MovimientoCajaService {
	@Autowired
	private MovimientoCajaRepo mcr;

	public Optional<MovimientoCaja> getLastMovimientoCaja(Optional<Sucursal> sucursal) {
		return mcr.findFirstBySucursalIdSucursalOrderByIdMovimientoCajaDesc(sucursal.get());
	}
}
