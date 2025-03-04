package com.salespointfx2.www.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salespointfx2.www.model.MovimientoCaja;
import com.salespointfx2.www.model.Recoleccion;
import com.salespointfx2.www.model.RecoleccionBillete;
import com.salespointfx2.www.repository.RecoleccionBilleteRepo;
import com.salespointfx2.www.repository.RecoleccionRepo;

@Service
public class RecoleccionService {
	@Autowired
	private RecoleccionRepo rr;
	@Autowired
	private RecoleccionBilleteRepo rbr;
	@Autowired
	private SucursalService ss;
	@Autowired
	private MovimientoCajaService mcs;

	@Transactional
	public Recoleccion saveRecoleccion(List<RecoleccionBillete> lrb, float total) {
		MovimientoCaja mc = mcs.getLastMovimientoCaja(ss.getSucursalActive()).get();
		if (mc.getCreatedAt().isBefore(LocalDateTime.now())) {
			Recoleccion recoleccion = new Recoleccion(ss.getSucursalActive().get(), total);
			// recoleccion.calcularTotal();
			Recoleccion reco = rr.save(recoleccion);

			for (RecoleccionBillete rb : lrb) {
				rb.setRecoleccionIdRecoleccion(reco);
			}
			rbr.saveAll(lrb);
			reco.setRecoleccionBilleteList(lrb);
			return rr.save(reco);
		} else {
			return null;
		}
	}
}
