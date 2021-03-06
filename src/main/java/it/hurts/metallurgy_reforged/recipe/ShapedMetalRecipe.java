/*
 * -------------------------------------------------------------------------------------------------------
 * Class: ShapedMetalRecipe
 * This class is part of Metallurgy 4 Reforged
 * Complete source code is available at: https://github.com/Davoleo/Metallurgy-4-Reforged
 * This code is licensed under GNU GPLv3
 * Authors: Davoleo, ItHurtsLikeHell, PierKnight100
 * Copyright (c) 2020.
 * --------------------------------------------------------------------------------------------------------
 */

package it.hurts.metallurgy_reforged.recipe;

import com.google.gson.JsonObject;
import it.hurts.metallurgy_reforged.block.BlockTypes;
import it.hurts.metallurgy_reforged.config.RegistrationConfig;
import it.hurts.metallurgy_reforged.material.Metal;
import it.hurts.metallurgy_reforged.model.EnumTools;
import it.hurts.metallurgy_reforged.util.ItemUtils;
import it.hurts.metallurgy_reforged.util.Utils;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

public class ShapedMetalRecipe extends ShapedOreRecipe implements IRecipeMetal {

	private String resultType;

	public ShapedMetalRecipe(CraftingHelper.ShapedPrimer primer, String resultType)
	{
		super(null, ItemStack.EMPTY, primer);
		this.resultType = resultType;

		if (isRecipeEnabled(primer.input))
			ModRecipes.shapedMetalRecipes.add(this);
	}

	protected static boolean isRecipeEnabled(NonNullList<Ingredient> inputs)
	{

		boolean valid = true;

		for (Ingredient ingredient : inputs)
		{
			if (ingredient instanceof IngredientMetal)
			{
				if (valid)
				{
					switch (((IngredientMetal) ingredient).type)
					{
						case "dust":
							valid = RegistrationConfig.categoryItems.enableMetalDusts;
							break;
						case "nugget":
							valid = RegistrationConfig.categoryItems.enableMetalNuggets;
							break;
						case "block":
							valid = RegistrationConfig.categoryBlocks.enableRawMetalBlocks;
							break;
					}
				}
				else
					return false;
			}
		}

		return valid;
	}

	// TODO: 15/05/2020 comment this method
	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting crafting)
	{
		Metal metalModel = null;

		for (int i = 0; i < crafting.getSizeInventory(); i++)
		{
			ItemStack stack = crafting.getStackInSlot(i);

			Metal otherMetal = ItemUtils.getMetalFromOreDictStack(stack);
			if (otherMetal != null)
			{
				if (metalModel == null)
					metalModel = otherMetal;
				else if (metalModel != otherMetal)
					return ItemStack.EMPTY;
			}
		}

		if (metalModel == null)
			return ItemStack.EMPTY;

		return getOutputFromMetal(metalModel);

	}

	@Override
	public boolean isDynamic()
	{
		return true;
	}

	public ItemStack getOutputFromMetal(Metal metal)
	{
		switch (resultType)
		{
			//Tools
			case "axe":
				return metal.hasToolSet() && RegistrationConfig.categoryItems.enableMetalAxes ?
						new ItemStack(metal.getTool(EnumTools.AXE)) : ItemStack.EMPTY;
			case "hoe":
				return metal.hasToolSet() && RegistrationConfig.categoryItems.enableMetalAxes ?
						new ItemStack(metal.getTool(EnumTools.HOE)) : ItemStack.EMPTY;
			case "pickaxe":
				return metal.hasToolSet() && RegistrationConfig.categoryItems.enableMetalAxes ?
						new ItemStack(metal.getTool(EnumTools.PICKAXE)) : ItemStack.EMPTY;
			case "shovel":
				return metal.hasToolSet() && RegistrationConfig.categoryItems.enableMetalAxes ?
						new ItemStack(metal.getTool(EnumTools.SHOVEL)) : ItemStack.EMPTY;
			case "sword":
				return metal.hasToolSet() && RegistrationConfig.categoryItems.enableMetalAxes ?
						new ItemStack(metal.getTool(EnumTools.SWORD)) : ItemStack.EMPTY;
			//Armor
			case "helmet":
				return metal.hasArmorSet() && RegistrationConfig.categoryItems.enableMetalArmorSets ?
						new ItemStack(metal.getArmorPiece(EntityEquipmentSlot.HEAD)) : ItemStack.EMPTY;
			case "chestplate":
				return metal.hasArmorSet() && RegistrationConfig.categoryItems.enableMetalArmorSets ?
						new ItemStack(metal.getArmorPiece(EntityEquipmentSlot.CHEST)) : ItemStack.EMPTY;
			case "leggings":
				return metal.hasArmorSet() && RegistrationConfig.categoryItems.enableMetalArmorSets ?
						new ItemStack(metal.getArmorPiece(EntityEquipmentSlot.LEGS)) : ItemStack.EMPTY;
			case "boots":
				return metal.hasArmorSet() && RegistrationConfig.categoryItems.enableMetalArmorSets ?
						new ItemStack(metal.getArmorPiece(EntityEquipmentSlot.FEET)) : ItemStack.EMPTY;
			//Blocks
			case "block":
				return RegistrationConfig.categoryBlocks.enableRawMetalBlocks ? new ItemStack(metal.getBlock(BlockTypes.BLOCK)) : ItemStack.EMPTY;
			case "bricks":
				return RegistrationConfig.categoryBlocks.enableBricksMetalBlocks ? new ItemStack(metal.getBlock(BlockTypes.BRICKS), 6) : ItemStack.EMPTY;
			case "large_bricks":
				return RegistrationConfig.categoryBlocks.enableLargeBricksMetalBlocks ? new ItemStack(metal.getBlock(BlockTypes.LARGE_BRICKS), 4) : ItemStack.EMPTY;
			case "crystals":
				return RegistrationConfig.categoryBlocks.enableCrystalMetalBlocks ? new ItemStack(metal.getBlock(BlockTypes.CRYSTAL), 4) : ItemStack.EMPTY;
			case "engraved_block":
				return RegistrationConfig.categoryBlocks.enableEngravedMetalBlocks ? new ItemStack(metal.getBlock(BlockTypes.ENGRAVED_BLOCK), 8) : ItemStack.EMPTY;
			case "hazard_block":
				return RegistrationConfig.categoryBlocks.enableHazardMetalBlocks ? new ItemStack(metal.getBlock(BlockTypes.HAZARD_BLOCK), 4) : ItemStack.EMPTY;
			case "reinforced_glass":
				return RegistrationConfig.categoryBlocks.enableReinforcedGlassBlocks ? new ItemStack(metal.getBlock(BlockTypes.GLASS), 5) : ItemStack.EMPTY;
			//Ingot
			case "ingot":
				return new ItemStack(metal.getIngot());
			default:
				return ItemStack.EMPTY;
		}
	}

	@SuppressWarnings("unused")
	public static class Factory implements IRecipeFactory {

		@Override
		public IRecipe parse(JsonContext context, JsonObject json)
		{
			CraftingHelper.ShapedPrimer primer = Utils.parseShapedRecipe(context, json);
			return new ShapedMetalRecipe(primer, JsonUtils.getString(json, "result"));
		}

	}

}
