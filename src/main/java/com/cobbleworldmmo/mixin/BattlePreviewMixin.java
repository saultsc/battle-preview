package com.cobbleworldmmo.mixin;

import com.cobblemon.mod.common.battles.BattleFormat;
import com.cobblemon.mod.common.battles.BattleType;
import com.cobblemon.mod.common.battles.BattleTypes;
import com.cobblemon.mod.common.battles.ChallengeManager;
import com.cobblemon.mod.common.battles.ChallengeManager.BattleChallenge;
import com.cobbleworldmmo.CobbleWorldMMO;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChallengeManager.class, remap = false)
public class BattlePreviewMixin {

    @Inject(method = "onAccept(Lcom/cobblemon/mod/common/battles/ChallengeManager$BattleChallenge;)V", at = @At("HEAD"), cancellable = true)
    private void onAccept(BattleChallenge challenge, @NotNull CallbackInfo ci) {
        String battleFormatName = challenge.getBattleFormat().getBattleType().getName();

        if (!battleFormatName.equals(BattleTypes.INSTANCE.getSINGLES().getName())) {
            return;
        }

        ci.cancel();

        CobbleWorldMMO.LOGGER.info("BattlePreviewMixin: onAccept");
    }
}
