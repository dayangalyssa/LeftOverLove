package com.bignerdranch.leftoverlove

class MakananDetail(
    val idMakanan: String ="",
    val namaMakanan: String="",
    val harga: String="",
    val waktuPengambilan: String="",
    val alamatPengambilan: String="",
    val deskripsi: String="",
    val rating: String="",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as MakananDetail

        return idMakanan == other.idMakanan
    }

    override fun hashCode(): Int {
        return idMakanan.hashCode()
    }
}