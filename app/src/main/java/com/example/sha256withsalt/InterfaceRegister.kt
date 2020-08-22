package com.example.sha256withsalt

interface InterfaceRegister {
    fun load()
    fun hideLoad()
    fun hasil(

        name: String,
        email: String,
        alamat: String,
        ktp: String,
        noHp: String,
        tglLahir: String,
        pass: String
    )
}