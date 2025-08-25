package com.cobbleworldmmo.util

import ca.landonjw.gooeylibs2.api.button.ButtonBase
import ca.landonjw.gooeylibs2.api.button.GooeyButton
import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.battles.ChallengeManager.BattleChallenge
import com.cobblemon.mod.common.battles.ShowdownPokemon
import com.cobblemon.mod.common.pokemon.Pokemon
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
}