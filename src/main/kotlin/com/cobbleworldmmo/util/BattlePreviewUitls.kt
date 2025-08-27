package com.cobbleworldmmo.util

import ca.landonjw.gooeylibs2.api.button.ButtonAction
import ca.landonjw.gooeylibs2.api.button.ButtonBase
import ca.landonjw.gooeylibs2.api.button.GooeyButton
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate
import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.battles.ChallengeManager.BattleChallenge
import com.cobblemon.mod.common.battles.ShowdownPokemon
import com.cobblemon.mod.common.item.PokemonItem
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobbleworldmmo.gui.BattlePreviewMenu.Companion
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity

object BattlePreviewUitls {

  fun getBattleTeams(
    sender: ServerPlayerEntity,
    receiver: ServerPlayerEntity
  ): Pair<List<Pair<ShowdownPokemon, Pokemon>>, List<Pair<ShowdownPokemon, Pokemon>>> {
    val senderTeam = Cobblemon.storage.getParty(sender).map { pokemon ->
      ShowdownPokemon().apply {
        this.pokeball = pokemon.caughtBall.name.toString()
      } to pokemon
    }

    val receiverTeam = Cobblemon.storage.getParty(receiver).map { pokemon ->
      ShowdownPokemon().apply {
        this.pokeball = pokemon.caughtBall.name.toString()
      } to pokemon
    }

    return senderTeam to receiverTeam
  }

  fun getPlayerHead(player: ServerPlayerEntity): ItemStack {
    val head = ItemStack(Items.PLAYER_HEAD)
    val nbt = NbtCompound()
    val profile = player.gameProfile

    val skullOwner = NbtCompound()
    skullOwner.putString("Name", profile.name)
    skullOwner.putUuid("Id", profile.id)
    nbt.put("SkullOwner", skullOwner)

    return head
  }

  interface MaxPokemonSelector {
    fun getMaxPokemon(format: String): Int
  }

  class DefaultMaxPokemonSelector : MaxPokemonSelector {
    override fun getMaxPokemon(format: String): Int {
      return when (format.lowercase()) {
        "singles" -> 1
        "doubles" -> 2
        "triples" -> 3
        "vgc" -> 4
        else -> 1
      }
    }
  }
}