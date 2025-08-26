package com.cobbleworldmmo.adapters

import com.cobbleworldmmo.cwmmoutils.utils.ItemModelUtils

/**
 * Helper para crear ItemModelUtils con parámetros opcionales.
 */
fun itemModelAdapter(
    slot: Int = 0,
    item: String,
    name: String = "",
    lore: List<String>? = null,
    customModelData: Int = 0,
    hideFlags: Boolean = false
): ItemModelUtils {
    return ItemModelUtils(
        slot,
        item,
        name,
        lore ?: emptyList(),
        customModelData,
        hideFlags
    )
}

