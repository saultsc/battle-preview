package com.cobbleworldmmo.gui.components

import ca.landonjw.gooeylibs2.api.button.ButtonAction
import ca.landonjw.gooeylibs2.api.button.ButtonBase
import com.cobbleworldmmo.adapters.itemModelAdapter

class ConfirmSelectionButton(
  slot: Int,
  private var active: Boolean,
  private var selectedCount: Int,
  private val maxSelect: Int
) : ButtonBase(
  itemModelAdapter(
    slot = slot,
    item = "minecraft:green_wool",
    name = "§aConfirm Selection $selectedCount/$maxSelect"
  ).createItem()
) {
  private val slot = slot

  private fun getGreenWool() = itemModelAdapter(
    slot = slot,
    item = "minecraft:green_wool",
    name = "§aConfirm Selection $selectedCount/$maxSelect"
  ).createItem()

  private fun getRedWool() = itemModelAdapter(
    slot = slot,
    item = "minecraft:red_wool",
    name = "§cCancel"
  ).createItem()

  fun updateSelection(selected: Int) {
    if (active && selected != selectedCount) active = false
    selectedCount = selected
    setDisplay(if (active) getRedWool() else getGreenWool())
  }

  override fun onClick(action: ButtonAction) {
    if(selectedCount != maxSelect && !active) return
    active = !active
    setDisplay(if (active) getRedWool() else getGreenWool())
  }
}