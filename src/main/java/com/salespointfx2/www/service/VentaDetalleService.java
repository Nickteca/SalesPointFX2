package com.salespointfx2.www.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salespointfx2.www.model.VentaDetalle;
import com.salespointfx2.www.repository.VentaDetalleRepo;

@Service
public class VentaDetalleService {
	@Autowired
	private VentaDetalleRepo vdr;

	@Transactional
	public List<VentaDetalle> saveAll(List<VentaDetalle> vd) {
		return vdr.saveAll(vd);
	}
}
