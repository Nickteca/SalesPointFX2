package com.salespointfx2.www.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespointfx2.www.model.Folio;
import com.salespointfx2.www.repository.FolioRepo;

import org.springframework.transaction.annotation.Transactional;

@Service
public class FolioService {
	@Autowired
	private FolioRepo fr;
	@Autowired
	private SucursalService ss;

	public Folio getFolioVenta() {
		return fr.findByAcronimoFolioAndSucursalIdSucursal("VEN-", ss.getSucursalActive().get());
	}

	@Transactional
	public int updateFolioVenta(Folio folio) {
		return fr.incrementarNumeroFolio(folio.getIdFolio());
	}
}
