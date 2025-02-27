package com.salespointfx2.www.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespointfx2.www.repository.GastoRepo;

@Service
public class GastoService {
	@Autowired private GastoRepo gr;
}
