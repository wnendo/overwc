package privateapp.statobr.com.privateapp.bd;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import privateapp.statobr.com.privateapp.ex.StatusIndicacoes;
import privateapp.statobr.com.privateapp.to.Ind;
import privateapp.statobr.com.privateapp.to.User;

/**
 * Created by Wendel on 06/07/2017.
 */

public class BancoDeDados{

    public Connection conexao;
    public User user;
    Context context;


    public void init() {
        conexao = CONN("root", "", "", "127.0.0.1");

    }

    private Connection CONN(String _user, String _pass, String _DB, String _server )
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL;
        try {
            Log.i("Login", "BANCO DE DADOS");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + _server + ";" + "databaseName=" + _DB + ";user=" + _user + ";password=" + _pass + ";";
            conn = DriverManager.getConnection(ConnURL);
            System.out.println("connected");
        } catch (SQLException se) {
            Log.e("ERRO",se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO",e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO",e.getMessage());
        }

        return conn;
    }

    public Cursor QueryInd (Cursor select){
        ResultSet rs;
        try {
            Statement statement = conexao.createStatement();
            rs = statement.executeQuery("Select * from tbUsuarios");

            if (rs.next()){

                user.setNome(rs.getString("Nome"));
                user.setTelefone(rs.getInt("Telefone"));
                user.setRg(rs.getString("RG"));
                user.setCpf(rs.getString("CPF"));
                user.setEmail(rs.getString("Email"));
            }
            else{
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            Log.e("ERRO",e.getMessage());
        }
        return select;
    };

    public Boolean QuerySelect(String usuario, String senha){
        boolean retorno = false;
        ResultSet rs;
        try {
            Statement statement = conexao.createStatement();
            rs = statement.executeQuery("Select * from tbUsuarios where Email='" + usuario + "' AND Senha='" + senha + "';");

            if (rs.next()){

                retorno = true;

                user.setNome(rs.getString("Nome"));
                user.setTelefone(rs.getInt("Telefone"));
                user.setRg(rs.getString("RG"));
                user.setCpf(rs.getString("CPF"));
                user.setEmail(rs.getString("Email"));
            }
            else{
                retorno = false;
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            Log.e("ERRO",e.getMessage());
        }
        return retorno;
    }

    public Ind montaUsuario(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        String nome = cursor.getString(cursor.getColumnIndex("nome"));
        String telefone = cursor.getString(cursor.getColumnIndex("telefone"));
        String email = cursor.getString(cursor.getColumnIndex("email"));
        String cargo = cursor.getString(cursor.getColumnIndex("cargo"));
        String empresa = cursor.getString(cursor.getColumnIndex("empresa"));
        return new Ind(1,nome,telefone, email, cargo, empresa);
    }
/*
    public Cursor carregaDados() {
         db;
        BancoDeDados banco = new BancoDeDados();
        Cursor cursor;
        String[] campos = {"Nome", "Igreja"};
        cursor = db.query("Pessoa", campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
*/
    public void QuerySQL(String comandoSQL){
        try {


            Statement statement = conexao.createStatement();
            statement.executeUpdate(comandoSQL);
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            Log.e("ERRO",e.getMessage());
        }
    }

}
