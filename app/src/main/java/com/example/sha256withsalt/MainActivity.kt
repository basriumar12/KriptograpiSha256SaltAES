package com.example.sha256withsalt

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val kunci = encryptStringTosha256("basriumarbasri")





        val AES = doEncrypt("basriumar12@gmail.com", kunci.substring(0, kunci.length - 48))
        val salt = AES?.let { salt(it) }

        val aesdecrypt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            doDecrypt(AES.toString(),kunci.substring(0,kunci.length-48))
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val saltDecrypt = saltDecode(aesdecrypt.toString())




        Log.e("HASIL"," enkripsi kunci $kunci")
        Log.e("HASIL"," enkripsi aes $AES")
        Log.e("HASIL"," enkripsi salt $salt")
        Log.e("HASIL"," decrypt $saltDecrypt")

        btn_enkrip.setOnClickListener {
            var aesenkrip = doEncrypt(tv_name.text.toString(), kunci.substring(0,kunci.length-48))
            val aesdekrip = aesenkrip.let { doDecrypt(aesenkrip.toString(),kunci.substring(0,kunci.length-48)) }

            aesenkrip = aesenkrip.let { salt(it.toString()) }
            tv_result.text = "Hasil : $aesenkrip"
            tv_result_decrypt.text = "Hasil decyrpt $aesdekrip"
        }


    }

    private fun encryptStringTosha256(email: String): String {
        val charset = Charsets.UTF_8
        val byteArray = email.toByteArray(charset)
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(byteArray)
        return hash.fold("", { str, it -> str + "%02x".format(it) })
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

    fun saltDecode(string: String): String {
        var encode = string
        var tokens = listOf('6', '7', '8', '9', '0', 'z', 'y', 'x')
        var replacers = listOf('1', '2', '3', '4', '5', 'a', 'b', 'c')

        for (a in 0 until encode.length) {
            for (b in 0 until tokens.size) {

                if (encode.get(a).toString() == tokens.get(b).toString()) {
                    encode = encode.replace(tokens[b].toString(), replacers[b].toString())


                }
            }
        }

        Log.e("tag", "after encode $encode")
        return encode


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(Exception::class)
    fun doDecrypt(cipher: String, kunci: String): String? {
        val keySpec =
            SecretKeySpec(kunci.toByteArray(), "AES")
        val pair = cipher.split(":".toRegex()).toTypedArray()
        var bIv: ByteArray? = ByteArray(0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bIv = Base64.getDecoder().decode(pair[0])
        }
        val bEnc = Base64.getDecoder().decode(pair[1])
        val ivSpec = IvParameterSpec(bIv)
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        c.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val bDec = c.doFinal(bEnc)
        return String(bDec)
    }

}