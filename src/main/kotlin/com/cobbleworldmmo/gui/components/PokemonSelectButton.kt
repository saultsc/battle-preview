package com.cobbleworldmmo.gui.components

import ca.landonjw.gooeylibs2.api.button.ButtonAction
import ca.landonjw.gooeylibs2.api.button.ButtonBase
import com.cobbleworldmmo.adapters.itemModelAdapter
import net.minecraft.item.ItemStack

class PokemonSelectButton(
  private val pokemonItem: ItemStack?,
  slot: Int,
  private val isLocked: Boolean = false,
  private val onSelect: ((Boolean) -> Boolean)? = null
) : ButtonBase(
  pokemonItem ?: itemModelAdapter(
    slot = slot,
    item = "minecraft:gray_wool",
    name = "No Pokemon"
  ).createItem()
) {
  private var active = false
  private val selectedName = if (pokemonItem != null) "§a${pokemonItem.name.string} Selected" else "Selected"

  val greenWool = itemModelAdapter(
    slot = slot,
    item = "minecraft:lime_wool",
    name = selectedName.takeIf { it.isNotEmpty() } ?: "",
  ).createItem()

  override fun onClick(action: ButtonAction) {
    if (isLocked || pokemonItem == null) return

    val nextState = !active
    val allowed = onSelect?.invoke(nextState) ?: true
    if (!allowed) return

    setDisplay(if (nextState) greenWool else pokemonItem)
    active = nextState
  }
}