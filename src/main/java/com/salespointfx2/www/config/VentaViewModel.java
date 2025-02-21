package com.salespointfx2.www.config;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.dto.VentaDetalleTabla;
import com.salespointfx2.www.model.Folio;
import com.salespointfx2.www.service.FolioService;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component
public class VentaViewModel {
	@Autowired private FolioService fs;
	private final ObservableList<VentaDetalleTabla> productos = FXCollections.observableArrayList();
	private final DoubleProperty total = new SimpleDoubleProperty(0.0);
    private final StringProperty totalFormatted = new SimpleStringProperty("$0.00");
    
 // Formateador para el total (por ejemplo: $1,000 o $1,000.1)
    private final DecimalFormat formatter = new DecimalFormat("$#,##0.0#");
    
 // Propiedad para el folio actual
    private final StringProperty folio = new SimpleStringProperty("Sin Folio");
    
    public VentaViewModel() {
        // Listener que actualiza el total formateado cuando cambia el total numérico
        total.addListener((observable, oldValue, newValue) -> 
            totalFormatted.set(formatter.format(newValue))
        );
    }
    // Getters y setters para el folio
    public StringProperty folioProperty() {
        return folio;
    }
    
    public String getFolio() {
        return folio.get();
    }
    
    public void setFolio(String nuevoFolio) {
        folio.set(nuevoFolio);
    }
 // Método para actualizar el folio en el ViewModel
    public void actualizarFolio() {
        // Obtiene el folio actual (o el siguiente) del servicio
        Folio folioActual = fs.getFolioVenta();
        // Formatear el folio según el formato deseado, por ejemplo "VEN-11-1"
        String folioFormateado = String.format("%s-%d-%d",
                folioActual.getAcronimoFolio().replace("-", ""), 
                folioActual.getSucursalIdSucursal().getIdSucursal(),
                folioActual.getNumeroFolio());
        // Actualiza la propiedad
        folio.set(folioFormateado);
    }

	public ObservableList<VentaDetalleTabla> getProductos() {
		return productos;
	}

	public DoubleProperty totalProperty() {
		return total;
	}

	public void cobrar() {
		productos.clear();
		total.set(0.0);
	}

	public void agregarProducto(VentaDetalleTabla producto) {
		productos.add(producto);
		calcularTotal();
	}

	public void calcularTotal() {
		double totalCalculado = productos.stream().mapToDouble(v -> v.getSubtotal().get()).sum();
		total.set(totalCalculado);
	}

	public void eliminarProducto(VentaDetalleTabla producto) {
		if (productos.contains(producto)) {
			productos.remove(producto);
			calcularTotal(); // Recalcular total después de eliminar
		}
	}

}
