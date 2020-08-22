package com.example.sha256withsalt

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity(),InterfaceRegister {
    var presenterRegister : PresenterRegister = PresenterRegister(this,this)
    var dialogProgressDialog : ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenterRegister = PresenterRegister(this,this)


        btn_submit.setOnClickListener {

            if (

                edt_nama.text.toString().isNullOrEmpty() ||
                edt_alamat.text.toString().isNullOrEmpty() ||
                edt_email.text.toString().isNullOrEmpty() ||
                edt_ktp.text.toString().isNullOrEmpty() ||
                edt_nohp.text.toString().isNullOrEmpty() ||
                edt_tanggal_lahir.text.toString().isNullOrEmpty() ||
                edt_pass.text.toString().isNullOrEmpty()){
                    Toast.makeText(this,"Data harus di isi semua",Toast.LENGTH_LONG).show()
                } else{
                progress_circular.visibility = View.VISIBLE
                var nama = edt_nama.text.toString()
                var email = edt_email.text.toString()
                var alamat = edt_alamat.text.toString()
                var ktp = edt_ktp.text.toString()
                var noHp =edt_nohp.text.toString()
                var tglLahir = edt_tanggal_lahir.text.toString()
                var pass = edt_pass.text.toString()
                presenterRegister.saveDataKombinasi(nama,email,alamat,ktp,noHp,tglLahir,pass)
                

            }
            
        }
    }

    fun encryptStringTosha256(email: String): String {
        val charset = Charsets.UTF_8
        val byteArray = email.toByteArray(charset)
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(byteArray)
        return hash.fold("", { str, it -> str + "%02x".format(it) })
    }

    override fun load() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideLoad() {
        progress_circular.visibility = View.GONE

    }

    override fun hasil(
        name: String,
        email: String,
        alamat: String,
        ktp: String,
        noHp: String,
        tglLahir: String,
        pass: String
    ) {
        dialogProgressDialog?.hide()
        Log.e("TAG","$name ")
        Log.e("TAG","$email ")
        Log.e("TAG","$alamat ")
        Log.e("TAG","$ktp ")
        Log.e("TAG","$noHp ")
        Log.e("TAG","$tglLahir ")
        Log.e("TAG","$pass ")
        Toast.makeText(this,"Berhasil insert ",Toast.LENGTH_LONG).show()
        edt_nama.setText("")
        edt_alamat.setText("")
        edt_email.setText("")
        edt_ktp.setText("")
        edt_nohp.setText("")
        edt_pass.setText("")
        edt_tanggal_lahir.setText("")
        progress_circular.visibility = View.GONE
    }
}