/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.client.renderer;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.*;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.luis.xsurvive.XSurvive;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author Luis-St
 *
 */

public abstract class XSurviveRenderType extends RenderType { // ToDo: Remove comment after testing
	
	public static List<RenderType> glint = newRenderList(XSurviveRenderType::glintRenderType);
	public static List<RenderType> glintTranslucent = newRenderList(XSurviveRenderType::glintTranslucentRenderType);
	public static List<RenderType> entityGlint = newRenderList(XSurviveRenderType::entityGlintRenderType);
	//public static List<RenderType> glintDirect = newRenderList(XSurviveRenderType::glintDirectRenderType);
	public static List<RenderType> entityGlintDirect = newRenderList(XSurviveRenderType::entityGlintDriectRenderType);
	//public static List<RenderType> armorGlint = newRenderList(XSurviveRenderType::armorGlintRenderType);
	public static List<RenderType> armorEntityGlint = newRenderList(XSurviveRenderType::armorEntityGlintRenderType);
	
	private XSurviveRenderType(@NotNull String name, @NotNull VertexFormat format, @NotNull Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, @NotNull Runnable setupState, @NotNull Runnable clearState) {
		super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
	}
	
	public static void addGlintTypes(@NotNull Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map) {
		addGlintTypes(map, glint);
		addGlintTypes(map, glintTranslucent);
		addGlintTypes(map, entityGlint);
		//addGlintTypes(map, glintDirect);
		addGlintTypes(map, entityGlintDirect);
		//addGlintTypes(map, armorGlint);
		addGlintTypes(map, armorEntityGlint);
	}
	
	private static @NotNull List<RenderType> newRenderList(@NotNull Function<String, RenderType> function) {
		ArrayList<RenderType> list = Lists.newArrayList();
		for (DyeColor color : DyeColor.values()) {
			list.add(function.apply(color.getName()));
		}
		list.add(function.apply("rainbow"));
		return list;
	}
	
	private static void addGlintTypes(@NotNull Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map, @NotNull List<RenderType> renderTypes) {
		for (RenderType renderType : renderTypes) {
			if (!map.containsKey(renderType)) {
				map.put(renderType, new ByteBufferBuilder(renderType.bufferSize()));
			}
		}
	}
	
	private static @NotNull RenderType glintRenderType(@NotNull String name) {
		return RenderType.create("glint_" + name, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
			CompositeState.builder().setShaderState(RenderStateShard.RENDERTYPE_GLINT_SHADER).setTextureState(new TextureStateShard(texture(name), true, false)).setWriteMaskState(RenderStateShard.COLOR_WRITE)
				.setCullState(RenderStateShard.NO_CULL)
				.setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST).setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY).setOutputState(RenderStateShard.ITEM_ENTITY_TARGET).setTexturingState(RenderStateShard.GLINT_TEXTURING)
				.createCompositeState(false));
	}
	
	private static @NotNull RenderType glintTranslucentRenderType(@NotNull String name) {
		return RenderType.create("glint_translucent_" + name, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
			CompositeState.builder().setShaderState(RenderStateShard.RENDERTYPE_GLINT_TRANSLUCENT_SHADER).setTextureState(new TextureStateShard(texture(name), true, false)).setWriteMaskState(RenderStateShard.COLOR_WRITE)
				.setCullState(RenderStateShard.NO_CULL).setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST).setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY).setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
				.setTexturingState(RenderStateShard.GLINT_TEXTURING).createCompositeState(false));
	}
	
	private static @NotNull RenderType entityGlintRenderType(@NotNull String name) {
		return RenderType.create("entity_glint_" + name, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
			CompositeState.builder().setShaderState(RenderStateShard.RENDERTYPE_ENTITY_GLINT_SHADER).setTextureState(new TextureStateShard(texture(name), true, false)).setWriteMaskState(RenderStateShard.COLOR_WRITE)
				.setCullState(RenderStateShard.NO_CULL).setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST).setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY).setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
				.setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING).createCompositeState(false));
	}
	
	//private static @NotNull RenderType glintDirectRenderType(@NotNull String name) {
	//	return RenderType.create("glint_direct_" + name, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
	//		CompositeState.builder().setShaderState(RenderStateShard.RENDERTYPE_GLINT_DIRECT_SHADER).setTextureState(new TextureStateShard(texture(name), true, false)).setWriteMaskState(RenderStateShard.COLOR_WRITE)
	//			.setCullState(RenderStateShard.NO_CULL).setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST).setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY).setTexturingState(RenderStateShard.GLINT_TEXTURING)
	//			.createCompositeState(false));
	//}
	
	private static @NotNull RenderType entityGlintDriectRenderType(@NotNull String name) {
		return RenderType.create("entity_glint_direct_" + name, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
			CompositeState.builder().setShaderState(RenderStateShard.RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER).setTextureState(new TextureStateShard(texture(name), true, false)).setWriteMaskState(RenderStateShard.COLOR_WRITE)
				.setCullState(RenderStateShard.NO_CULL).setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST).setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY).setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
				.createCompositeState(false));
	}
	
	//private static @NotNull RenderType armorGlintRenderType(@NotNull String name) {
	//	return RenderType.create("armor_glint_" + name, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
	//		CompositeState.builder().setShaderState(RenderStateShard.RENDERTYPE_ARMOR_GLINT_SHADER).setTextureState(new TextureStateShard(texture(name), true, false)).setWriteMaskState(RenderStateShard.COLOR_WRITE)
	//			.setCullState(RenderStateShard.NO_CULL).setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST).setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY).setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
	//			.setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING).createCompositeState(false));
	//}
	
	private static @NotNull RenderType armorEntityGlintRenderType(@NotNull String name) {
		return RenderType.create("armor_entity_glint_" + name, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
			CompositeState.builder().setShaderState(RenderStateShard.RENDERTYPE_ARMOR_ENTITY_GLINT_SHADER).setTextureState(new TextureStateShard(texture(name), true, false)).setWriteMaskState(RenderStateShard.COLOR_WRITE)
				.setCullState(RenderStateShard.NO_CULL).setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST).setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY).setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
				.setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING).createCompositeState(false));
	}
	
	private static @NotNull ResourceLocation texture(@NotNull String name) {
		return ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "textures/glint/enchanted_item_glint_" + name + ".png");
	}
}
