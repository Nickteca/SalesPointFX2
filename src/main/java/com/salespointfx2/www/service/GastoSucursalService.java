package com.salespointfx2.www.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespointfx2.www.model.GastoSucursal;
import com.salespointfx2.www.repository.GastoSucursalRepo;

@Service
public class GastoSucursalService {
	@Autowired
	private GastoSucursalRepo gsr;
	@Autowired
	private SucursalService ss;
	
	public List<GastoSucursal> getAllGastosSucursalBySucursal(){
		return gsr.findBySucursalIdSucursal(ss.getSucursalActive().get());
	}
	
	public GastoSucursal saveGastoSucursal(GastoSucursal gs) {
		
		return gsr.save(gs);
	}
}
