package com.cobbleworldmmo.gui

import ca.landonjw.gooeylibs2.api.UIManager
import ca.landonjw.gooeylibs2.api.button.GooeyButton
import ca.landonjw.gooeylibs2.api.page.GooeyPage
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate
import com.cobblemon.mod.common.item.PokemonItem
import com.cobbleworldmmo.cwmmoutils.utils.PlayerHeadUtils
import com.cobbleworldmmo.gui.components.ConfirmSelectionButton
import com.cobbleworldmmo.gui.components.PokemonSelectButton
import com.cobbleworldmmo.util.BattlePreviewUitls
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import kotlin.collections.remove
import kotlin.text.compareTo
import kotlin.text.get
import kotlin.text.set

class BattlePreviewMenu(
  private val confirmSelection: Boolean = false,
  private val maxPokemonSelect: Int = 1,
) {
  companion object {
    private val rows = 6
    private val title = "Battle Preview"
    private val senderSlots = listOf(10, 11, 12, 19, 20, 21)
    private val opponentsSlots = listOf(14, 15, 16, 23, 24, 25)
    private val senderHeadSlot = 2
    private val opponentHeadSlot = 6
    private val toggleConfirmSlot = 40
  }

  private val selectedSlots = mutableListOf<Int>()

  fun open(
    sender: ServerPlayerEntity,
    opponent: ServerPlayerEntity,
  ) {
    val template = ChestTemplate.builder(rows).build()
    val (senderTeam, opponentTeam) = BattlePreviewUitls.getBattleTeams(sender, opponent)


    // sender team
    val playerSenderHead = GooeyButton.builder()
      .display(PlayerHeadUtils.getHead(sender.uuid).left)
      .build()

    for (index in senderSlots.indices) {
      val pokemon = senderTeam.getOrNull(index)?.second
      val pokemonItem: ItemStack? = if (pokemon != null) PokemonItem.from(pokemon) else null
      val slot = senderSlots[index]
      val pokemonButton = PokemonSelectButton(pokemonItem, slot, false) { isSelected: Boolean ->
        when {
          isSelected && selectedSlots.size < maxPokemonSelect -> selectedSlots.add(slot)
          !isSelected -> selectedSlots.remove(slot)
        }
        !isSelected || selectedSlots.size <= maxPokemonSelect
      }
      template.set(slot, pokemonButton)
    }

    // opponent team
    val playerOpponentHead = GooeyButton.builder()
      .display(PlayerHeadUtils.getHead(opponent.uuid).left)
      .build()

    for (index in opponentsSlots.indices) {
      val pokemon = opponentTeam.getOrNull(index)?.second
      val pokemonItem: ItemStack? = if (pokemon != null) PokemonItem.from(pokemon) else null
      val slot = opponentsSlots[index]
      val pokemonButton = PokemonSelectButton(pokemonItem, slot, true)
      template.set(slot, pokemonButton)
    }

    val toggleSelection = ConfirmSelectionButton(toggleConfirmSlot, confirmSelection)
    template.set(toggleConfirmSlot, toggleSelection)

    template.set(senderHeadSlot, playerSenderHead)
    template.set(opponentHeadSlot, playerOpponentHead)

    val page = GooeyPage.builder()
      .title(title)
      .template(template)
      .build()

    UIManager.openUIForcefully(sender, page)
  }
}
