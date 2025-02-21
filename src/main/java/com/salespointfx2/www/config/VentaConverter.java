package com.salespointfx2.www.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.salespointfx2.www.dto.VentaDetalleTabla;
import com.salespointfx2.www.model.SucursalProducto;
import com.salespointfx2.www.model.Venta;
import com.salespointfx2.www.model.VentaDetalle;
import com.salespointfx2.www.service.SucursalProductoService;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Component
public class VentaConverter {
	private final SucursalProductoService sucursalProductoService;

    public VentaConverter(SucursalProductoService sucursalProductoService) {
        this.sucursalProductoService = sucursalProductoService;
    }
    /**
     * Convierte una lista de VentaDetalleTabla (DTO) a una lista de VentaDetalle (Entidad),
     * asignando la venta correspondiente y buscando la entidad SucursalProducto según el ID.
     *
     * @param dtos  Lista de DTOs de venta.
     * @param venta La venta que se está registrando.
     * @return Lista de entidades VentaDetalle listas para ser persistidas.
     */
    public List<VentaDetalle> convertToVentaDetalle(List<VentaDetalleTabla> dtos, Venta venta) {
        List<VentaDetalle> ventaDetalleList = new ArrayList<>();
        for (VentaDetalleTabla dto : dtos) {
            // Buscar la entidad SucursalProducto usando el ID del producto contenido en el DTO.
            int idProducto = dto.getIdProducto().get();
            SucursalProducto sp = sucursalProductoService.getSucursalProductoById(idProducto);

            // Crear la entidad VentaDetalle y asignar los valores
            VentaDetalle detalle = new VentaDetalle();
            detalle.setCantidad((short) dto.getCantidad().get());
            detalle.setPrecio(dto.getPrecioUnitario().get());
            detalle.setSubTotal(dto.getSubtotal().get());
            detalle.setSucursalProductoIdSucursalProducto(new SucursalProducto(dto.getIdProducto().get()));
            detalle.setVentaIdVenta(new Venta(venta.getIdVenta()));
            

            ventaDetalleList.add(detalle);
        }
        return ventaDetalleList;
    }
}
