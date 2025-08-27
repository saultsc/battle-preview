package com.cobbleworldmmo.gui.components

import ca.landonjw.gooeylibs2.api.button.ButtonAction
import ca.landonjw.gooeylibs2.api.button.ButtonBase
import com.cobbleworldmmo.adapters.itemModelAdapter

class ConfirmSelectionButton(slot: Int, active: Boolean) : ButtonBase(
  itemModelAdapter(
    slot = slot,
    item = "minecraft:green_wool",
    name = "§aConfirm"
  ).createItem()
) {
  private var active = active

  private val greenWool = itemModelAdapter(
    slot = slot,
    item = "minecraft:green_wool",
    name = "§aConfirm"
  ).createItem()

  private val redWool = itemModelAdapter(
    slot = slot,
    item = "minecraft:red_wool",
    name = "§cCancel"
  ).createItem()

  override fun onClick(action: ButtonAction) {
    if (active) {
      setDisplay(greenWool)
    } else {
      setDisplay(redWool)
    }
    active = !active
  }
}