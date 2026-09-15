package ca.fxco.moreculling.mixin.blockentity;

import ca.fxco.moreculling.utils.CullingUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.blockentity.AbstractSignRenderer;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.entity.SignTextSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractSignRenderer.class)
public class SignRenderer_textMixin {

    @WrapOperation(
            method = "extractRenderState(Lnet/minecraft/world/level/block/entity/SignBlockEntity;" +
                    "Lnet/minecraft/client/renderer/blockentity/state/SignRenderState;" +
                    "FLnet/minecraft/world/phys/Vec3;" +
                    "Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/entity/SignBlockEntity;" +
                            "getText(Lnet/minecraft/world/level/block/entity/SignTextSlot;" +
                            ")Lnet/minecraft/world/level/block/entity/SignText;"
            )
    )
    private SignText moreculling$cullSignText(SignBlockEntity instance, SignTextSlot slot, Operation<SignText> original) {
        SignText text = original.call(instance, slot);
        return CullingUtils.cullSignText(instance.getBlockPos(), instance.getBlockState(), slot == SignTextSlot.FRONT, text) ? text : null;
    }
}
