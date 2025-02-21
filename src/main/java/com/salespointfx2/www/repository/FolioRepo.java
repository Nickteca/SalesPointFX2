package com.salespointfx2.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salespointfx2.www.model.Folio;
import com.salespointfx2.www.model.Sucursal;

public interface FolioRepo extends JpaRepository<Folio, Short> {
	Folio findByAcronimoFolioAndSucursalIdSucursal(String acronomimo, Sucursal sucursal);
	
	@Modifying
    @Query("UPDATE Folio f SET f.numeroFolio = f.numeroFolio + 1 WHERE f.idFolio = :idFolio")
    int incrementarNumeroFolio(@Param("idFolio") Short idFolio);
}
