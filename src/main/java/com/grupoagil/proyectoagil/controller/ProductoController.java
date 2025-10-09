package com.grupoagil.proyectoagil.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupoagil.proyectoagil.model.Inventario;
import com.grupoagil.proyectoagil.model.Producto;
import com.grupoagil.proyectoagil.service.ProductoService;
import com.grupoagil.proyectoagil.service.ProductoService.ProductoResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /** 
     * Obtener todos los productos
     */
    @GetMapping
    public List<ProductoResponse> getAllProductos() {
        return productoService.getAllProductos();
    }

    /**
     * Crear un nuevo producto
     */
    @PostMapping
    public ResponseEntity<?> createProducto(@RequestBody Map<String, Object> request) {
        try {
            String nombre = (String) request.get("nombre");
            String categoria = (String) request.get("categoria");
            Double precio = Double.valueOf(request.get("precio").toString());
            String descripcion = (String) request.get("descripcion");
            Integer cantidadDisponible = Integer.valueOf(request.get("cantidadDisponible").toString());

            Producto producto = productoService.createProducto(nombre, categoria, precio, descripcion, cantidadDisponible);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Editar un producto por su nombre
     */
    @PutMapping("/editar/{nombreExistente}")
    public ResponseEntity<?> editProducto(
            @PathVariable String nombreExistente,
            @RequestBody Map<String, Object> request) {
        try {
            String nuevoNombre = (String) request.get("nombre");
            String categoria = (String) request.get("categoria");
            Double precio = Double.valueOf(request.get("precio").toString());
            String descripcion = (String) request.get("descripcion");
            Integer cantidadDisponible = Integer.valueOf(request.get("cantidadDisponible").toString());

            Producto productoActualizado = productoService.editProducto(
                    nombreExistente,
                    nuevoNombre,
                    categoria,
                    precio,
                    descripcion,
                    cantidadDisponible
            );

            return ResponseEntity.ok(productoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Buscar un producto por su nombre
     */
    
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<?> buscarProducto(@PathVariable String nombre) {
        Optional<Producto> productoOpt = productoService.findByNombre(nombre);

        if (productoOpt.isPresent()) {
            Producto p = productoOpt.get();
            Inventario inv = productoService.getInventario(p.getIdProducto());
            ProductoResponse resp = new ProductoResponse(p, inv.getCantDispo());
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Producto no encontrado"));
        }
    }
}
