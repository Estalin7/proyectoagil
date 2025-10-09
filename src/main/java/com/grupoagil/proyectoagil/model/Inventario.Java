package com.grupoagil.proyectoagil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventario")
public class Inventario{

    @Column(name = "cantDipo", length=3)
    private Long idInventario;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    public Inventario() {}
    public Inventario(Long idInventario, Integer cantidad) {
        this.idInventario = idInventario;
        this.cantidad = cantidad;
    }
    // Getters and Setters

    public Long getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Long idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


}