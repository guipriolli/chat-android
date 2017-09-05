package br.com.gui.chat.model;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

//Mensagem
public class Mensagem implements Serializable {

    @JsonProperty("text")
    private String texto;
    private int id;

    public Mensagem() {

    }

    public Mensagem(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

}
