package com.grupoagil.proyectoagil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupoagil.proyectoagil.model.Producto;
import com.grupoagil.proyectoagil.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        return ResponseEntity.ok(productos);
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.createProducto(producto);
        return ResponseEntity.status(201).body(nuevoProducto); // Retorna el producto creado con código 201
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long idProducto) {
        productoService.deleteProducto(idProducto);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Actualizar un producto por su nombre
    @PutMapping("/{nombre}")
    public ResponseEntity<Producto> updateProducto(@PathVariable String nombre, @RequestBody Producto productoActualizado) {
        Producto producto = productoService.updateProductoByNombre(nombre, productoActualizado);
        
        if (producto != null) {
            return ResponseEntity.ok(producto); // Si se encuentra y actualiza el producto
        } else {
            return ResponseEntity.notFound().build(); // Si no se encuentra el producto
        }
    }
}
