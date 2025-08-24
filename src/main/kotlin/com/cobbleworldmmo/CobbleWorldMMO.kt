package com.cobbleworldmmo

import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class CobbleWorldMMO : ModInitializer {
  val MOD_ID = "cmmo-battle-preview"

  @JvmField
  val LOGGER: Logger = LogManager.getLogger(MOD_ID)

  override fun onInitialize() {
    LOGGER.info("Hello, World!")
  }
}
