package com.grupoagil.proyectoagil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoagil.proyectoagil.model.Categoria;
import com.grupoagil.proyectoagil.model.Inventario;
import com.grupoagil.proyectoagil.model.Producto;
import com.grupoagil.proyectoagil.repository.CategoriaRepository;
import com.grupoagil.proyectoagil.repository.InventarioRepository;
import com.grupoagil.proyectoagil.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Obtener todos los productos
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findByNombre(String nombre) {
        return productoRepository.findAll().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }


    
    public Producto createProducto(String nombre, String categoriaNombre, Double precio,
                                   String descripcion, Integer cantidadDisponible) {

        // Buscar categoría existente
        Categoria categoria = categoriaRepository.findAll()
                .stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(categoriaNombre))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Crear producto
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        Producto newProducto = productoRepository.save(producto);

        // Crear inventario asociado
        Inventario inventario = new Inventario();
        inventario.setIdProducto(newProducto.getIdProducto());
        inventario.setCantDispo(cantidadDisponible);
        inventarioRepository.save(inventario);

        return newProducto;
    }

    
    public Producto editProducto(String nombreExistente, String nuevoNombre, String categoriaNombre, Double precio, String descripcion, Integer cantidadDisponible) {
        Optional<Producto> productoOpt = findByNombre(nombreExistente);
        if (productoOpt.isEmpty()) {
            throw new RuntimeException("Producto no encontrado: " + nombreExistente);
        }

        Producto producto = productoOpt.get();

        // Actualizar campos
        producto.setNombre(nuevoNombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);

        // Actualizar categoría
        Optional<Categoria> categoriaOpt = categoriaRepository.findByNombre(categoriaNombre);
        if (categoriaOpt.isEmpty()) {
            throw new RuntimeException("Categoría no encontrada: " + categoriaNombre);
        }
        producto.setCategoria(categoriaOpt.get());

        Producto productoActualizado = productoRepository.save(producto);

        // Actualizar inventario
        Inventario inventario = inventarioRepository.findById(producto.getIdProducto())
                .orElse(new Inventario(producto.getIdProducto(), cantidadDisponible));
        inventario.setCantDispo(cantidadDisponible);
        inventarioRepository.save(inventario);

        return productoActualizado;
    }
    
}
