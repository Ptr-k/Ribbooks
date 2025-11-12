package com.ribbooks.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ribbooks.MainActivity
import com.ribbooks.R

class LoginScreen : AppCompatActivity() {
    private lateinit var emailTextView : EditText
    private lateinit var passwordTextView : EditText
    private lateinit var button : Button
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // toma la view de activity_login.xml de layout en res
        setContentView(R.layout.activity_login)

        // Se crea la instancia para la base de datos
        auth = FirebaseAuth.getInstance()

        // Igual se inicializan las fields donde se tomarán
        // los datos para el login. Se toman del campo de la view
        emailTextView = findViewById(R.id.password_input)
        passwordTextView = findViewById(R.id.email_input)
        button = findViewById(R.id.loginBtn)

        button.setOnClickListener {
            loginUser()
        }
    }

    /**
     * loginUser() toma la información de los campos en los que se introduce
     * información en la view y las valida para poder intentar contectarse
     * a la base de datos.
     */
    private fun loginUser() {
        // toma los textos introducidos en los cuadtros de texto
        // y los pasa a variables que se pueden utilizar para validar
        // los datos.
        val email = emailTextView.text.toString()
        val password = passwordTextView.text.toString()

        // Validaciones para los inputs
        if(email.isEmpty() || password.isEmpty()) {
            // Toast es como una pequeña notifiación que sale en el pie de la aplicación
            Toast.makeText(this, "Introduce las credenciales", Toast.LENGTH_LONG).show()
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    // en el caso que se haya podido loggear, te lleva a la aplicación
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    // salta una notificación en el caso que no se haya loggeado
                    Toast.makeText(this, "Crecenciales incorrectas", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}