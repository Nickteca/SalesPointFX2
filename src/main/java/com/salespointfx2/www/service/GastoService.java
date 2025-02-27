package com.salespointfx2.www.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salespointfx2.www.model.Gasto;
import com.salespointfx2.www.repository.GastoRepo;

@Service
public class GastoService {
	@Autowired
	private GastoRepo gr;

	@Transactional
	public List<Gasto> getGastos() {
		List<Gasto> lg = gr.findAll();
		for (Gasto gasto : lg) {
			gasto.getGastoSucursalList().size();
		}
		return lg;
	}
}
