package com.example.model;

public record Chamado(int id, String user, String codEquip, String categoria, String atividades) {

    @Override
    public String toString(){
        return user + " | " + codEquip + " - " + categoria + " - " +  atividades; 
    }

}