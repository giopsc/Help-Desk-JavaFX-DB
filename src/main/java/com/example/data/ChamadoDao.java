package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Chamado;

public class ChamadoDao {

    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "rm98837";
    private static final String PASS = "280101";
    
    public void inserir(Chamado chamado) throws SQLException {
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        
        var sql = "INSERT INTO chamados (id, usuario, cod_equip, tipo_equip, atividade) VALUES (?, ?, ?, ?, ?)";
        var comando = conexao.prepareStatement(sql);
        comando.setInt(1, chamado.id());
        comando.setString(2, chamado.user());
        comando.setString(3, chamado.codEquip());
        comando.setString(4, chamado.categoria());
        comando.setString(5, chamado.atividades());
        comando.executeUpdate();
        comando.close();
    }
    
    public List<Chamado> listarTodos() throws SQLException {
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        
        var chamados = new ArrayList<Chamado>();
        
        var resultado = conexao.createStatement().executeQuery("SELECT * FROM chamados");
        
        while (resultado.next()) {
            chamados.add(new Chamado(
                resultado.getInt("id"),
                resultado.getString("usuario"),
                resultado.getString("cod_equip"),
                resultado.getString("tipo_equip"),
                resultado.getString("atividade")
                ));
            }
            
            conexao.close();
            return chamados;
        }
        
        public void apagar(Chamado chamado) throws SQLException{
            var conexao = DriverManager.getConnection(URL, USER, PASS);
            var sql = "DELETE FROM chamados WHERE id = ?";
            var comando = conexao.prepareStatement(sql);
            comando.setInt(1, chamado.id());   
            comando.executeUpdate();
            comando.close();
        }
        
    }