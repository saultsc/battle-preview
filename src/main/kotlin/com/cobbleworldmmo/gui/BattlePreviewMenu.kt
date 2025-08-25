package com.cobbleworldmmo.gui

import ca.landonjw.gooeylibs2.api.UIManager
import ca.landonjw.gooeylibs2.api.button.GooeyButton
import ca.landonjw.gooeylibs2.api.page.GooeyPage
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate
import com.cobblemon.mod.common.item.PokemonItem
import com.cobbleworldmmo.util.BattlePreviewUitls
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity

class BattlePreviewMenu {
  companion object {
    private val rows = 6
    private val title = "Battle Preview"
    private val senderSlots = listOf(19, 20, 21, 28, 29, 30)
    private val opponentsSlots = listOf(23, 24, 25, 32, 33, 34)
    private val senderHeadSlot = 11
    private val opponentHeadSlot = 15
  }

  fun open(
    sender: ServerPlayerEntity,
    opponent: ServerPlayerEntity
  ) {
    val template = ChestTemplate.builder(rows).build()
    val (senderTeam, opponentTeam) = BattlePreviewUitls.getBattleTeams(sender, opponent)

    val playerSenderHead = GooeyButton.builder()
      .display(BattlePreviewUitls.getPlayerHead(sender))
      .build()

    senderTeam.forEachIndexed { index, pokemonList ->
      val pokemonItem = PokemonItem.from(pokemonList.second)
      val pokemonButton = SelectablePokemonButton(pokemonItem)
      template.set(senderSlots[index], pokemonButton)
    }

    val playerOpponentHead = GooeyButton.builder()
      .display(BattlePreviewUitls.getPlayerHead(opponent))
      .build()

    opponentTeam.forEachIndexed { index, pokemonList ->
      val pokemonButton = GooeyButton.builder()
        .display(PokemonItem.from(pokemonList.second))
        .build()
      template.set(opponentsSlots[index], pokemonButton)
    }

    template.set(senderHeadSlot, playerSenderHead)
    template.set(opponentHeadSlot, playerOpponentHead)

    val page = GooeyPage.builder()
      .title(title)
      .template(template)
      .build()

    UIManager.openUIForcefully(sender, page)
  }
}