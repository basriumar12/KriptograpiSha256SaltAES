package com.example.sha256withsalt

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jaredrummler.android.processes.AndroidProcesses
import kotlinx.android.synthetic.main.activity_register.*
import java.security.MessageDigest


class RegisterActivity : AppCompatActivity(),InterfaceRegister {
    var presenterRegister : PresenterRegister = PresenterRegister(this,this)
    var dialogProgressDialog : ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenterRegister = PresenterRegister(this,this)
        val processes =
            AndroidProcesses.getRunningAppProcesses()

        for (process in processes) {
            // Get some information about the process
            val processName = process.name
            val stat = process.stat()
            val pid = stat.pid
            val parentProcessId = stat.ppid()
            val startTime = stat.stime()
            val policy = stat.policy()
            val state = stat.state()
            val statm = process.statm()
            val totalSizeOfProcess = statm.size
            val residentSetSize = statm.residentSetSize
            val packageInfo = process.getPackageInfo(this, 0)
           // val appName = packageInfo.applicationInfo.loadLabel(pm).toString()

            test.text = "$pid $policy $totalSizeOfProcess" +
                    " $residentSetSize $packageInfo $parentProcessId $startTime $state $statm"
        }

       // test.text = processes.name

        btn_submit.setOnClickListener {

            if (

                edt_nama.text.toString().isNullOrEmpty() ||
                edt_alamat.text.toString().isNullOrEmpty() ||
                edt_email.text.toString().isNullOrEmpty() ||
                edt_username.text.toString().isNullOrEmpty() ||
                edt_nohp.text.toString().isNullOrEmpty() ||
                edt_tanggal_lahir.text.toString().isNullOrEmpty() ||
                edt_pass.text.toString().isNullOrEmpty()){
                    Toast.makeText(this,"Data harus di isi semua",Toast.LENGTH_LONG).show()
                } else{
                progress_circular.visibility = View.VISIBLE
                var nama = edt_nama.text.toString()
                var email = edt_email.text.toString()
                var alamat = edt_alamat.text.toString()
                var username = edt_username.text.toString()
                var noHp =edt_nohp.text.toString()
                var tglLahir = edt_tanggal_lahir.text.toString()
                var pass = edt_pass.text.toString()
                presenterRegister.saveDataKombinasi(nama,email,alamat,username,noHp,tglLahir,pass)
                

            }
            
        }
        btn_submit_sha256.setOnClickListener {

            if (

                edt_nama.text.toString().isNullOrEmpty() ||
                edt_alamat.text.toString().isNullOrEmpty() ||
                edt_email.text.toString().isNullOrEmpty() ||
                edt_username.text.toString().isNullOrEmpty() ||
                edt_nohp.text.toString().isNullOrEmpty() ||
                edt_tanggal_lahir.text.toString().isNullOrEmpty() ||
                edt_pass.text.toString().isNullOrEmpty()){
                    Toast.makeText(this,"Data harus di isi semua",Toast.LENGTH_LONG).show()
                } else{
                progress_circular.visibility = View.VISIBLE
                var nama = edt_nama.text.toString()
                var email = edt_email.text.toString()
                var alamat = edt_alamat.text.toString()
                var username = edt_username.text.toString()
                var noHp =edt_nohp.text.toString()
                var tglLahir = edt_tanggal_lahir.text.toString()
                var pass = edt_pass.text.toString()
                presenterRegister.saveData256(nama,email,alamat,username,noHp,tglLahir,pass)


            }

        }
        btn_submit_normal.setOnClickListener {

            if (

                edt_nama.text.toString().isNullOrEmpty() ||
                edt_alamat.text.toString().isNullOrEmpty() ||
                edt_email.text.toString().isNullOrEmpty() ||
                edt_username.text.toString().isNullOrEmpty() ||
                edt_nohp.text.toString().isNullOrEmpty() ||
                edt_tanggal_lahir.text.toString().isNullOrEmpty() ||
                edt_pass.text.toString().isNullOrEmpty()){
                    Toast.makeText(this,"Data harus di isi semua",Toast.LENGTH_LONG).show()
                } else{
                progress_circular.visibility = View.VISIBLE
                var nama = edt_nama.text.toString()
                var email = edt_email.text.toString()
                var alamat = edt_alamat.text.toString()
                var username = edt_username.text.toString()
                var noHp =edt_nohp.text.toString()
                var tglLahir = edt_tanggal_lahir.text.toString()
                var pass = edt_pass.text.toString()
                presenterRegister.saveDataNormal(nama,email,alamat,username,noHp,tglLahir,pass)


            }

        }

        btn_submit_reset.setOnClickListener {
            edt_nama.setText("")
            edt_alamat.setText("")
            edt_email.setText("")
            edt_username.setText("")
            edt_nohp.setText("")
            edt_pass.setText("")
            edt_tanggal_lahir.setText("")
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
//        Log.e("TAG","$name ")
//        Log.e("TAG","$email ")
//        Log.e("TAG","$alamat ")
//        Log.e("TAG","$ktp ")
//        Log.e("TAG","$noHp ")
//        Log.e("TAG","$tglLahir ")
//        Log.e("TAG","$pass ")
        Toast.makeText(this,"Berhasil insert ",Toast.LENGTH_LONG).show()

        progress_circular.visibility = View.GONE
    }
}