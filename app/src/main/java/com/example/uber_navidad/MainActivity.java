package com.example.uber_navidad;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText edtCedula, edtNombre, edtApellido,
            edtCorreo, edtPassword, edtVerifPass;
    Button btnAceptar;
    BigInteger valorMd5 = null;
    ArrayList<String> lista;

    public static final String REGEX_NUMEROS = "^[0-9]+$"; //validar numeros
    public static final String REGEX_LETRAS = "^[a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s]+$"; //validar letras
    public static final String REGEX_EMAIL ="^[a-zA-Z0-9\\._-]+@[a-zA-Z0-9-]{2,}[.][a-zA-Z]{2,4}$"; //validar correo electronico


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCedula = (EditText)findViewById(R.id.id_cedula);
        edtNombre = (EditText)findViewById(R.id.id_Nombre);
        edtApellido = (EditText)findViewById(R.id.id_Apellido);
        edtCorreo = (EditText)findViewById(R.id.id_correo);
        edtPassword = (EditText)findViewById(R.id.id_password);
        edtVerifPass = (EditText)findViewById(R.id.id_valPass);

        btnAceptar = (Button)findViewById(R.id.id_button);
        lista = new ArrayList<>();
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
            }
        });
    }

    private void validar(){
        String stCedula = edtCedula.getText().toString();
        String stNombre = edtNombre.getText().toString();
        String stApellido = edtApellido.getText().toString();
        String stCorreo = edtCorreo.getText().toString();
        String stPassword = edtPassword.getText().toString();
        String stVeriPass = edtVerifPass.getText().toString();

        if(!stCedula.isEmpty() && !stNombre.trim().isEmpty() && !stApellido.trim().isEmpty() && !stCorreo.trim().isEmpty() && !stPassword.isEmpty() && !stVeriPass.isEmpty()){
            int i=0;
            Pattern patron = Pattern.compile(REGEX_LETRAS);
            if(stCedula.length()<10 || stCedula.length()>11 ||!validaCedula(stCedula)){

                edtCedula.setError("Ingrese una cédula válida");
                edtCedula.requestFocus();
            }else{
                lista.add(stCedula);
                i++;
            }
            if(patron.matcher(stNombre).matches()){
                lista.add(stNombre);
                i++;
            }else{
                edtNombre.setError("Ingrese correctamente el nombre");
                edtNombre.requestFocus();
            }

            if(patron.matcher(stApellido).matches()){
                lista.add(stApellido);
                i++;
            }else{
                edtApellido.setError("Ingrese correctamente el apellido");
                edtApellido.requestFocus();
            }
            Pattern patron1 = Pattern.compile(REGEX_EMAIL);
            if(patron1.matcher(stCorreo).matches()){
                lista.add(stCorreo);
                i++;
            }else{
                edtCorreo.setError("Ingrese correctamente el correo");
                edtCorreo.requestFocus();
            }

            if(stPassword.compareTo(stVeriPass) == 0){
                i++;
                byte[] md5Password = stPassword.getBytes();
                try {
                    valorMd5 = new BigInteger(1,MD5.cryptMD5(md5Password));
                    lista.add(valorMd5.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                edtVerifPass.setError("Las contraseñas no son iguales");
                edtVerifPass.requestFocus();
            }

            if(i==5){

                //registrar("http://192.168.0.102/ConexionWebServices/insert.php",valorMd5.toString());
                Intent siguiente = new Intent(this, TarjetaCredito.class);
                siguiente.putExtra("Datos",lista);
                startActivity(siguiente);
            }

        }else{
            Toast.makeText(this, "Falta llenar campos, vuelva a intentarlo", Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean validaCedula(String x){
        int suma=0;
        int a[]=new int [x.length()/2];
        int b[]=new int [(x.length()/2)];
        int c=0;
        int d=1;
        for (int i = 0; i < x.length()/2; i++) {
            a[i]=Integer.parseInt(String.valueOf(x.charAt(c)));
            c=c+2;
            if (i < (x.length()/2)-1) {
                b[i]=Integer.parseInt(String.valueOf(x.charAt(d)));
                d=d+2;
            }
        }

        for (int i = 0; i < a.length; i++) {
            a[i]=a[i]*2;
            if (a[i] >9){
                a[i]=a[i]-9;
            }
            suma=suma+a[i]+b[i];
        }
        int aux=suma/10;
        int dec=(aux+1)*10;
        if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x.length()-1))))
            return true;
        else
        if(suma%10==0 && x.charAt(x.length()-1)=='0'){
            return true;
        }else{
            return false;

        }
    }

}
