package privateapp.statobr.com.privateapp.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import privateapp.statobr.com.privateapp.R;
import privateapp.statobr.com.privateapp.bd.BancoDeDados;
import privateapp.statobr.com.privateapp.mask.Mask;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class Registrar extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    public EditText nomeR, telefoneR, rgR, cpfR, emailR, emailConfR, senhaR;
    private Button registrarUsuario, voltarReg;
    public String nomeConfirmacao, telefoneConfirmacao, rgConfirmacao, cpfConfirmacao,
            emailConfirmacao, emailConfirmacaoConf, senhaConfirmacao, comm;
    public  AlertDialog dialog;
    public AlertDialog.Builder builder;
    public BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        registrarUsuario = (Button) findViewById(R.id.registrar);
        voltarReg = (Button) findViewById(R.id.voltarRegistro);

        nomeR = (EditText) findViewById(R.id.nome);
        telefoneR  = (EditText) findViewById(R.id.telefone);
        rgR = (EditText) findViewById(R.id.rg);
        cpfR = (EditText) findViewById(R.id.cpf);
        emailR = (EditText) findViewById(R.id.emailRegistro);
        emailConfR = (EditText) findViewById(R.id.emailConf);
        senhaR = (EditText) findViewById(R.id.senhaRegistro);


        Mask maskCPF = new Mask("###.###.###-##", cpfR);
        Mask maskTEL = new Mask("(##)#####-####", telefoneR);
        Mask maskRG = new Mask("##.###.###-#", rgR);

        cpfR.addTextChangedListener(maskCPF);
        telefoneR.addTextChangedListener(maskTEL);
        rgR.addTextChangedListener(maskRG);

        inicializar();

        nomeR.setFocusable(true);
        nomeR.requestFocus();

        registrarUsuario.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });

        voltarReg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar();
            }
        });

    }

    private void inicializar () {
        try {
            bancoDeDados = new BancoDeDados();
            bancoDeDados.init();

        }catch (Exception e1){
            e1.printStackTrace();
        }
    }


    public boolean emailVal(String email){
        String regEx =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence emailValidacao = email;

        Pattern pattern = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailValidacao);

        if(matcher.matches()) {
            return true;
        }else {
            return false;
        }
    }

    public void registrar() {
        Log.i("Registrar", "Pegando Dados");

        boolean validacaoEmail;

        nomeConfirmacao = nomeR.getText().toString();
        telefoneConfirmacao = telefoneR.getText().toString();
        rgConfirmacao = rgR.getText().toString();
        cpfConfirmacao = cpfR.getText().toString();
        emailConfirmacao = emailR.getText().toString();
        emailConfirmacaoConf = emailConfR.getText().toString();
        senhaConfirmacao = senhaR.getText().toString();

        validacaoEmail = emailVal(emailConfirmacao);

        if (validacaoEmail == true) {
            if (emailConfirmacao.equals(emailConfirmacaoConf)) {
                if (nomeConfirmacao.isEmpty() || telefoneConfirmacao.isEmpty() ||
                        rgConfirmacao.isEmpty() || cpfConfirmacao.isEmpty() || emailConfirmacao.isEmpty() || senhaConfirmacao.isEmpty()) {

                    builder = new AlertDialog.Builder(Registrar.this);
                    builder.setMessage("Verifique as informações e tente novamente").setTitle("Erro").setPositiveButton("OK", null);
                    builder.show();
                } else {
                    Log.i("Registrar", "Realizando Insert" + nomeConfirmacao + telefoneConfirmacao + emailConfirmacao + senhaConfirmacao);
                    comm = "insert into tbUsuarios values ('" + nomeConfirmacao.toString() + "', " +
                            "'" + telefoneConfirmacao.toString() + "', " +
                            "'" + rgConfirmacao.toString() + "', " +
                            "'" + cpfConfirmacao.toString() + "', " +
                            "'" + emailConfirmacao.toString() + "', " +
                            "'" + senhaConfirmacao.toString() + "')";
                    bancoDeDados.QuerySQL(comm);
                    Toast.makeText(Registrar.this, "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    voltar();
                }
            } else {
                emailConfR.setError("Email digitado inválido");
            }
        } else {
            emailR.setError("Email digitado inválido");
        }
    }

    public void voltar(){
        Intent a = new Intent(getApplicationContext(), Login.class);
        startActivity(a);
    }
}