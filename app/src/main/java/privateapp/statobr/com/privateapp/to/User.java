package privateapp.statobr.com.privateapp.to;

/**
 * Created by Thaisa on 06/07/2017.
 */

public class User {
    private Integer id;
    private String nome;
    private String email;
    private String pass;
    private Integer telefone;
    private String cpf;
    private String rg;


    public User(Integer id, String nome, String email, String pass, Integer telefone, String cpf, String rg) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.pass = pass;
        this.telefone = telefone;
        this.cpf = cpf;
        this.rg = rg;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

}
