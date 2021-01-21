package com.example.sha256withsalt

import android.content.Context
import android.os.Build
import com.example.sha256withsalt.network.ModelRegister
import com.hairulharun.intensivecourse.network.ApiRetrofit
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class PresenterRegister(val contex: Context, val view: InterfaceRegister) {

    fun saveData256(
        name: String,
        email: String,
        alamat: String,
        ktp: String,
        noHp: String,
        tglLahir: String,
        pass: String
    ) {


        view.load()
        var nama = encryptStringTosha256(name.toString())
        var email = encryptStringTosha256(email)
        var alamat = encryptStringTosha256(alamat)
        var ktp = encryptStringTosha256(ktp)
        var noHp = encryptStringTosha256(noHp)
        var tglLahir = encryptStringTosha256(tglLahir)
        var pass = encryptStringTosha256(pass)


        ApiRetrofit.create().register256(nama, email, alamat, ktp, noHp, tglLahir, pass)
            .enqueue(object : Callback<ModelRegister> {
                override fun onFailure(call: Call<ModelRegister>?, t: Throwable?) {
                    view.hideLoad()
                }

                override fun onResponse(
                    call: Call<ModelRegister>?,
                    response: Response<ModelRegister>?
                ) {
                    response?.body()?.kode.let {
                        view.hideLoad()
                        view.hasil(nama, email, alamat, ktp, noHp, tglLahir, pass)
                    }

                }
            })


    }

 fun saveDataNormal(
        name: String,
        email: String,
        alamat: String,
        ktp: String,
        noHp: String,
        tglLahir: String,
        pass: String
    ) {


        view.load()



        ApiRetrofit.create().registernormal(name, email, alamat, ktp, noHp, tglLahir, pass)
            .enqueue(object : Callback<ModelRegister> {
                override fun onFailure(call: Call<ModelRegister>?, t: Throwable?) {
                    view.hideLoad()
                }

                override fun onResponse(
                    call: Call<ModelRegister>?,
                    response: Response<ModelRegister>?
                ) {
                    response?.body()?.kode.let {
                        view.hideLoad()
                        view.hasil(name, email, alamat, ktp, noHp, tglLahir, pass)
                    }

                }
            })


    }

  fun saveDataSalt(
        name: String,
        email: String,
        alamat: String,
        ktp: String,
        noHp: String,
        tglLahir: String,
        pass: String
    ) {


        view.load()
        var nama = salt(name.toString())
        var email = salt(email)
        var alamat = salt(alamat)
        var ktp = salt(ktp)
        var noHp = salt(noHp)
        var tglLahir = salt(tglLahir)
        var pass = salt(pass)


        ApiRetrofit.create().register(nama, email, alamat, ktp, noHp, tglLahir, pass)
            .enqueue(object : Callback<ModelRegister> {
                override fun onFailure(call: Call<ModelRegister>?, t: Throwable?) {
                    view.hideLoad()
                }

                override fun onResponse(
                    call: Call<ModelRegister>?,
                    response: Response<ModelRegister>?
                ) {
                    response?.body()?.kode.let {
                        view.hideLoad()
                        view.hasil(nama, email, alamat, ktp, noHp, tglLahir, pass)
                    }

                }
            })


    }

    fun saveDataAES(
        name: String,
        email: String,
        alamat: String,
        ktp: String,
        noHp: String,
        tglLahir: String,
        pass: String
    ) {


        view.load()
        var nama = doEncrypt(name.toString(),"kuncienambelaska").toString()
        var email = doEncrypt(email,"kuncienambelaska").toString()
        var alamat = doEncrypt(alamat,"kuncienambelaska").toString()
        var ktp = doEncrypt(ktp,"kuncienambelaska").toString()
        var noHp = doEncrypt(noHp,"kuncienambelaska").toString()
        var tglLahir = doEncrypt(tglLahir,"kuncienambelaska").toString()
        var pass = doEncrypt(pass,"kuncienambelaska").toString()


        ApiRetrofit.create().register(nama.toString(), email.toString(), alamat.toString(),
            ktp.toString(), noHp.toString(), tglLahir.toString(), pass.toString())
            .enqueue(object : Callback<ModelRegister> {
                override fun onFailure(call: Call<ModelRegister>?, t: Throwable?) {
                    view.hideLoad()
                }

                override fun onResponse(
                    call: Call<ModelRegister>?,
                    response: Response<ModelRegister>?
                ) {
                    response?.body()?.kode.let {
                        view.hideLoad()
                        view.hasil(nama, email, alamat, ktp, noHp, tglLahir, pass)
                    }

                }
            })


    }


    fun saveDataKombinasi(
        name: String,
        email: String,
        alamat: String,
        ktp: String,
        noHp: String,
        tglLahir: String,
        pass: String
    ) {


        view.load()
        val kunci = encryptStringTosha256("basriumarbasri")
        var kunciFinal = kunci.substring(0, kunci.length-48)    
        val nama = doEncrypt(name.toString(),kunciFinal)
        val email = doEncrypt(email,kunciFinal)
        val alamat = doEncrypt(alamat,kunciFinal)
        val ktp = doEncrypt(ktp,kunciFinal)
        val noHp = doEncrypt(noHp,kunciFinal)
        val tglLahir = doEncrypt(tglLahir,kunciFinal)
        val pass = doEncrypt(pass,kunciFinal)

        val namas = salt(nama.toString())
        val emails = salt(email.toString())
        val alamats = salt(alamat.toString())
        val ktps = salt(ktp.toString())
        val noHps = salt(noHp.toString())
        val tglLahirs = salt(tglLahir.toString())
        val passS = salt(pass.toString())

        ApiRetrofit.create().register(namas, emails, alamats,
            ktps, noHps, tglLahirs.toString(), passS.toString())
            .enqueue(object : Callback<ModelRegister> {
                override fun onFailure(call: Call<ModelRegister>?, t: Throwable?) {
                    view.hideLoad()
                }

                override fun onResponse(
                    call: Call<ModelRegister>?,
                    response: Response<ModelRegister>?
                ) {
                    response?.body()?.kode.let {
                        view.hideLoad()
                        view.hasil(namas, emails, alamats, ktps, noHps, tglLahirs, passS)
                    }

                }
            })


    }
    fun salt(string: String): String {
        var encode: String = string
        val tokens = listOf('1', '2', '3', '4', '5', 'a', 'b', 'c')
        val replacers = listOf('6', '7', '8', '9', '0', 'z', 'y', 'x')

        for (a in 0 until encode.length) {
            for (b in 0 until tokens.size) {

                if (encode.get(a).toString() == tokens.get(b).toString()) {
                    encode = encode.replace(tokens[b].toString(), replacers[b].toString())
                }
            }
        }

        return encode


    }

    fun doEncrypt(pesan: String, kunci: String): String? {
        val keySpec =
            SecretKeySpec(kunci.toByteArray(), "AES")
        val bIv = ByteArray(16)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SecureRandom.getInstanceStrong().nextBytes(bIv)
        }
        val ivSpec = IvParameterSpec(bIv)
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        c.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val bEnc = c.doFinal(pesan.toByteArray())
        var strEnc: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            strEnc = Base64.getEncoder().encodeToString(bEnc)
        }
        var strIv: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            strIv = Base64.getEncoder().encodeToString(bIv)
        }
        return "$strIv:$strEnc"
    }


    fun encryptStringTosha256(email: String): String {
        val charset = Charsets.UTF_8
        val byteArray = email.toByteArray(charset)
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(byteArray)
        return hash.fold("", { str, it -> str + "%02x".format(it) })
    }


}