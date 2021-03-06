/*
 * -------------------------------------------------------------------------------------------------------
 * Class: ArmorPotionEffect
 * This class is part of Metallurgy 4 Reforged
 * Complete source code is available at: https://github.com/Davoleo/Metallurgy-4-Reforged
 * This code is licensed under GNU GPLv3
 * Authors: Davoleo, ItHurtsLikeHell, PierKnight100
 * Copyright (c) 2020.
 * --------------------------------------------------------------------------------------------------------
 */

package it.hurts.metallurgy_reforged.effect;

import it.hurts.metallurgy_reforged.material.Metal;
import it.hurts.metallurgy_reforged.model.EnumTools;
import it.hurts.metallurgy_reforged.util.EventUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nullable;

public abstract class ArmorPotionEffect extends BaseMetallurgyEffect {

	private final Potion potion;
	private int amplifier;

	public ArmorPotionEffect(Metal metal, Potion potion, int amplifier)
	{
		super(metal);
		this.potion = potion;
		this.amplifier = amplifier;
	}

	@Override
	public abstract boolean isEnabled();

	@Override
	public boolean isToolEffect()
	{
		return false;
	}

	@Nullable
	@Override
	public EnumTools getToolClass()
	{
		return null;
	}

	@Override
	public void onPlayerTick(EntityPlayer player)
	{
		if (EventUtils.isPlayerWearingArmor(player, metal))
		{
			player.addPotionEffect(new PotionEffect(potion, 60, amplifier, false, false));
		}
	}

}
