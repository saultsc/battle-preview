package com.cobbleworldmmo.gui.components

import ca.landonjw.gooeylibs2.api.button.ButtonAction
import ca.landonjw.gooeylibs2.api.button.ButtonBase
import com.cobbleworldmmo.adapters.itemModelAdapter
import net.minecraft.item.ItemStack

class PokemonSelectButton(private val pokemonItem: ItemStack?, slot: Int, private val isLocked: Boolean = false) :
  ButtonBase(
    pokemonItem ?: itemModelAdapter(
      slot = slot,
      item = "minecraft:gray_wool",
      name = "",
      listOf(),
      customModelData = 0,
      hideFlags = true,
    ).createItem()
  ) {
  private var active = false
  private val selectedName: String = pokemonItem?.let { "§aSelected: ${it.name.string}" } ?: ""

  val greenWool = itemModelAdapter(
    slot = slot,
    item = "minecraft:green_wool",
    name = selectedName.takeIf { it.isNotEmpty() } ?: "",
  ).createItem()

  override fun onClick(action: ButtonAction) {
    if (isLocked || pokemonItem == null) return

    if (active) {
      setDisplay(pokemonItem)
    } else {
      setDisplay(greenWool)
    }
    active = !active
  }
}