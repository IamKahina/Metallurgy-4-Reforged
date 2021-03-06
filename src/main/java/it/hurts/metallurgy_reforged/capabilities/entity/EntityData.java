/*
 * -------------------------------------------------------------------------------------------------------
 * Class: EntityData
 * This class is part of Metallurgy 4 Reforged
 * Complete source code is available at: https://github.com/Davoleo/Metallurgy-4-Reforged
 * This code is licensed under GNU GPLv3
 * Authors: Davoleo, ItHurtsLikeHell, PierKnight100
 * Copyright (c) 2020.
 * --------------------------------------------------------------------------------------------------------
 */

package it.hurts.metallurgy_reforged.capabilities.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import java.util.Random;

public class EntityData {

    private static final IBlockState[] borrowableBlocks = new IBlockState[]{Blocks.DIRT.getDefaultState(),
                                                                            Blocks.IRON_BLOCK.getDefaultState(),
                                                                            Blocks.COMMAND_BLOCK.getDefaultState()};

    public boolean wasSnatched = false;
    public IBlockState snatchableBlock = null;
    public boolean initialized = false;

    public void initEnderman()
    {
        Random random = new Random();
        if (random.nextInt(4) == 0 && !initialized)
        {
            this.snatchableBlock = borrowableBlocks[random.nextInt(borrowableBlocks.length)];
            this.initialized = true;
        }
    }

}
