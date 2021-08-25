package com.loucaskreger.helmetoverlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = HelmetOverlay.MOD_ID, value = Dist.CLIENT)
public class EventSubscriber {

    private static final Minecraft mc = Minecraft.getInstance();
    private static final int HELMET_SLOT = 3;

    @SubscribeEvent
    public static void onHudRender(final RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
            ItemStack stack = mc.player.inventory.getArmor(HELMET_SLOT);
            if (stack.getItem() instanceof ArmorItem) {
                ArmorItem armorItem = (ArmorItem) stack.getItem();
                if (armorItem.getSlot() == EquipmentSlotType.HEAD) {
                    ResourceLocation texture = getTextureForHelmetType(armorItem);
                    if (texture != null) {
                        RenderSystem.disableDepthTest();
                        RenderSystem.depthMask(false);
                        RenderSystem.defaultBlendFunc();
                        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.disableAlphaTest();
                        mc.getTextureManager().bind(texture);
                        Tessellator tessellator = Tessellator.getInstance();
                        BufferBuilder bufferbuilder = tessellator.getBuilder();
                        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                        bufferbuilder.vertex(0.0D, (double) mc.getWindow().getGuiScaledHeight(), -90.0D).uv(0.0F, 1.0F).endVertex();
                        bufferbuilder.vertex((double) mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(), -90.0D).uv(1.0F, 1.0F).endVertex();
                        bufferbuilder.vertex((double) mc.getWindow().getGuiScaledWidth(), 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
                        bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
                        tessellator.end();
                        RenderSystem.depthMask(true);
                        RenderSystem.enableDepthTest();
                        RenderSystem.enableAlphaTest();
                        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @Nullable
    private static ResourceLocation getTextureForHelmetType(ArmorItem helmet) {
        String location = "textures/misc/";
        String baseTextureName = "helmetoverlay.png";
        if (helmet.getMaterial() instanceof ArmorMaterial) {
            switch ((ArmorMaterial) helmet.getMaterial()) {
                case LEATHER:
                    location += "leather" + baseTextureName;
                    break;
                case CHAIN:
                    location += "chain" + baseTextureName;
                    break;
                case IRON:
                    location += "iron" + baseTextureName;
                    break;
                case GOLD:
                    location += "gold" + baseTextureName;
                    break;
                case DIAMOND:
                    location += "diamond" + baseTextureName;
                    break;
                case TURTLE:
                    location += "turtle" + baseTextureName;
                    break;
                case NETHERITE:
                    location += "netherite" + baseTextureName;
                    break;
            }
            return new ResourceLocation(HelmetOverlay.MOD_ID, location);
        } else {
            return null;
        }
    }
}
