package com.grupoagil.proyectoagil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoagil.proyectoagil.model.Producto;
import com.grupoagil.proyectoagil.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Obtener todos los productos
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Crear un nuevo producto
    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Eliminar un producto por su ID
    public void deleteProducto(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }

    // Actualizar un producto por nombre (esto lo haremos a través de una búsqueda por nombre)
    public Producto updateProductoByNombre(String nombre, Producto productoActualizado) {
        // Buscar el producto por nombre
        Optional<Producto> productoExistente = productoRepository.findAll().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst();

        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            // Actualizamos los campos
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setCategoria(productoActualizado.getCategoria());

            // Guardamos el producto actualizado
            return productoRepository.save(producto);
        } else {
            return null; // Si no se encuentra el producto, retornamos null
        }
    }
}
