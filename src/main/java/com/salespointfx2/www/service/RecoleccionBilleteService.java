package com.salespointfx2.www.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespointfx2.www.repository.RecoleccionBilleteRepo;

@Service
public class RecoleccionBilleteService {
	@Autowired
	private RecoleccionBilleteRepo rbr;
}
