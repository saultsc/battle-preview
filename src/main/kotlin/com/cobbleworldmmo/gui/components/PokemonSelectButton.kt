package com.cobbleworldmmo.gui.components

import ca.landonjw.gooeylibs2.api.button.ButtonAction
import ca.landonjw.gooeylibs2.api.button.ButtonBase
import com.cobbleworldmmo.adapters.itemModelAdapter
import net.minecraft.item.ItemStack

class PokemonSelectButton(
  private val pokemonItem: ItemStack?,
  private val slot: Int,
  private val selectedSlots: MutableList<Int>? = null,
  private val maxSelect: Int? = null,
  private val onSelectionCountChanged: ((Int) -> Unit)? = null,
  private val isLocked: Boolean = false,
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
    if (selectedSlots == null || maxSelect == null || onSelectionCountChanged == null) return

    val nextState = !active
    if (nextState && selectedSlots.size >= maxSelect) return
    if (nextState) selectedSlots.add(slot) else selectedSlots.remove(slot)

    setDisplay(if (nextState) greenWool else pokemonItem)
    active = nextState
    onSelectionCountChanged(selectedSlots.size)
  }
}