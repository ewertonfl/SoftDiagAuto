package com.fatec.tg.softdiagauto.controller;

import com.fatec.tg.softdiagauto.util.Eopcao;

/**
 * Created by Gabriel Rubio on 05/12/2016.
 */



public class Controller implements Runnable {

    private Eopcao teste;

    public void run(){

        switch (teste) {

            case INICIALIZACAO:
                //iniciarComunicacao();
                // setTeste(Eopcao.TESTE_COMUNICACAO);
                break;
            case TESTE_COMUNICACAO:
                //testeComunicacao();
                break;
            case INFORMACOES:
                //informacoes();
                break;
            case PARAMETROS:
                //parametros();
                break;
            case FALHAS:
                //falhas();
                break;
            case APAGAR_FALHAS:
                //apagarFalhas();
                //setTeste(Eopcao.FALHAS);
                break;
            case FINALIZAR_COMUNICACAO:
                //finalizarComunicacao();
                break;
            default:
                break;
        }
    }

    public Eopcao getTeste() {
        return teste;
    }

    public void setTeste(Eopcao teste) {
        this.teste = teste;
    }
}
