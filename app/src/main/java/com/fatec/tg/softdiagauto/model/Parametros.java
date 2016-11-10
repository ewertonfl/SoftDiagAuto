package com.fatec.tg.softdiagauto.model;

/**
 * Created by Erick on 09/11/2016.
 */

public class Parametros {
    private Sensor rotacao;
    private Sensor temperaturaAgua;
    private Sensor tensaoBateria;

    public Parametros(){

    }

    public Parametros(Sensor rotacao, Sensor temperaturaAgua, Sensor tensaoBateria){
        this.setRotacao(rotacao);
        this.setTemperaturaAgua(temperaturaAgua);
        this.setTensaoBateria(tensaoBateria);
    }

    public Sensor getRotacao() {
        return rotacao;
    }

    public void setRotacao(Sensor rotacao) {
        this.rotacao = rotacao;
    }

    public Sensor getTemperaturaAgua() {
        return temperaturaAgua;
    }

    public void setTemperaturaAgua(Sensor temperaturaAgua) {
        this.temperaturaAgua = temperaturaAgua;
    }

    public Sensor getTensaoBateria() {
        return tensaoBateria;
    }

    public void setTensaoBateria(Sensor tensaoBateria) {
        this.tensaoBateria = tensaoBateria;
    }

}
