package com.cobbleworldmmo.gui

import ca.landonjw.gooeylibs2.api.button.ButtonAction
import ca.landonjw.gooeylibs2.api.button.ButtonBase
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

class SelectablePokemonButton(private val pokemonItem: ItemStack) : ButtonBase(pokemonItem) {
  private var active = false
  private val greenWool = ItemStack(Items.GREEN_WOOL)

  override fun onClick(action: ButtonAction) {
    if (active) {
      setDisplay(pokemonItem)
    } else {
      setDisplay(greenWool)
    }
    active = !active
  }
}
