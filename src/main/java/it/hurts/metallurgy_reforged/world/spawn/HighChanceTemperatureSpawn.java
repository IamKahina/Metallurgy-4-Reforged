package it.hurts.metallurgy_reforged.world.spawn;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/**
 * this class permits to spawn an ore more likely in a specific biome than another one.
 */
public class HighChanceTemperatureSpawn extends BaseOreSpawn
{

    private final Biome.TempCategory tempCategory;
    private final int newRarity;


    public HighChanceTemperatureSpawn(Block blockToReplace,Biome[] biomes, Biome.TempCategory tempCategory, int newRarity)
    {
        super(blockToReplace,biomes);
        this.tempCategory = tempCategory;
        this.newRarity = newRarity;
    }


    @Override
    public int getRarity(World world, int chunkX, int chunkZ,int originalRarity)
    {

        for (byte b : world.getChunk(chunkX, chunkZ).getBiomeArray())
        {
            Biome biome = Biome.getBiomeForId(b);
            if(biome != null && getTempCategory(biome) == tempCategory)
            {
                return newRarity;
            }
        }

        return originalRarity;
    }
    private Biome.TempCategory getTempCategory(Biome biome)
    {
        return (double)biome.getDefaultTemperature() >= 0.95D ? Biome.TempCategory.WARM : biome.getTempCategory();
    }
}
