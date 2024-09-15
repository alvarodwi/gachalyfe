package me.gachalyfe.rapi.utils.csv

interface CsvHandler<T> {
    fun import(data: List<T>): Int

    fun export(): List<T>
}
