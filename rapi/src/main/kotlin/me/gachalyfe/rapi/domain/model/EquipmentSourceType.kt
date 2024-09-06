package me.gachalyfe.rapi.domain.model

enum class EquipmentSourceType(val code: Int) {
    SI_DROPS(1),
    AI_DROPS(2),
    GEARS(3),
    FURNACE(4),
    ;

    companion object {
        private val MAP: Map<Int, EquipmentSourceType> = entries.associateBy { it.code }

        infix fun from(code: Int) = MAP[code] ?: AI_DROPS
    }
}
