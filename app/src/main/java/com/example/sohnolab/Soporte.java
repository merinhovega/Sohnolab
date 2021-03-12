package com.example.sohnolab;

public class Soporte {
    private String solicitadoPor;
    private String fecha;
    private String tiempoEjecutado;
    private String atendidoPor;
    private String tipoServicio;
    private String medio;
    private String plataforma;
    private String casoMaximo;
    private String descripcion;
    private String id;

    public Soporte(String solicitadoPor, String fecha, String tiempoEjecutado, String atendidoPor, String tipoServicio, String medio, String plataforma, String casoMaximo, String descripcion, String id) {
        this.solicitadoPor = solicitadoPor;
        this.fecha = fecha;
        this.tiempoEjecutado = tiempoEjecutado;
        this.atendidoPor = atendidoPor;
        this.tipoServicio = tipoServicio;
        this.medio = medio;
        this.plataforma = plataforma;
        this.casoMaximo = casoMaximo;
        this.descripcion = descripcion;
        this.id = id;
    }

    public Soporte() {
    }

    public String getSolicitadoPor() {
        return solicitadoPor;
    }

    public void setSolicitadoPor(String solicitadoPor) {
        this.solicitadoPor = solicitadoPor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTiempoEjecutado() {
        return tiempoEjecutado;
    }

    public void setTiempoEjecutado(String tiempoEjecutado) {
        this.tiempoEjecutado = tiempoEjecutado;
    }

    public String getAtendidoPor() {
        return atendidoPor;
    }

    public void setAtendidoPor(String atendidoPor) {
        this.atendidoPor = atendidoPor;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getCasoMaximo() {
        return casoMaximo;
    }

    public void setCasoMaximo(String casoMaximo) {
        this.casoMaximo = casoMaximo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void guardar(){
        Datos.guardar(this);
    }

    public void eliminar(){
        Datos.eliminar(this);
    }


}
