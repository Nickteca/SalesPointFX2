package com.salespointfx2.www.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.Sucursal;

public interface SucursalRepo extends JpaRepository<Sucursal, Short> {
	Optional<Sucursal> findByEstatusSucursalTrue();
}
