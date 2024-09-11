package me.gachalyfe.rapi.domain.service.csv

interface CsvHandler<T> {
    fun import(data: List<T>): Int

    fun export(): List<T>
}
