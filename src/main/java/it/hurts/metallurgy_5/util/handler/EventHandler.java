package it.hurts.metallurgy_5.util.handler;

import it.hurts.metallurgy_5.Metallurgy_5;
import it.hurts.metallurgy_5.item.armor.ModArmors;
import it.hurts.metallurgy_5.item.tool.ModTools;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import scala.util.Random;

import java.util.List;

/***************************
 *
 * Author : ItHurtsLikeHell
 * Project: Metallurgy-5
 * Date   : 28 ago 2018
 * Time   : 18:24:07
 *
 ***************************/

@EventBusSubscriber(modid=Metallurgy_5.MODID)
public class EventHandler {
	
	private static boolean fire = false;
	private final static double speed = 0.10000000149011612D;
	
//	Mithril Armor (Ultra istinto)
	@SubscribeEvent
	public static void glowingArmorEffect(PlayerTickEvent event) {
		int radius=32;
		boolean isArmored=false;

		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.mithril_helmet
			&&event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.mithril_chest
			&&event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.mithril_legs
			&&event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.mithril_boots)
				isArmored=true;

		double xM = event.player.posX + radius, yM = event.player.posY + radius, zM = event.player.posZ + radius; //Definiamo il Massimo di X Y Z
		double xm = event.player.posX - radius, ym = event.player.posY - radius, zm = event.player.posZ - radius; //Definiamo il minimo di X Y Z
		List <Entity> list;						//Creazione lista

		list = event.player.getEntityWorld().getEntitiesWithinAABBExcludingEntity(event.player,new AxisAlignedBB(xM, yM, zM, xm, ym, zm)); //Immetiamo in lista tutte le entit� comprese da il minimo e massimo di X Y Z
		Entity a[] = new Entity [list.size()]; 	// Creiamo un array di entity grande quanto la lista

		int max;
		for(int i = 0; i<list.size(); i++) {		// For con controllo se si indossa l'armatura e se i< della grandezza della lista
			max=i;                   			//Inseriamo il valore di "I" a singola ripetizione in max
			list.toArray(a);					// Inseriamo il contenuto della lista nell'array "a"
			a[i].setGlowing(isArmored);			//Le entita'� di a che si trovano in posizione "i" riceveranno l'effetto glowing
			for(int k=0;k<=max;k++) {  			//per k=0 fino a che k non è <= del massimo della n ripetizione
				if(a[k].getDistance(event.player) > radius) { 	//controllo fra entità in posizione k e player
					a[k].setGlowing(false); 					// Rimuoviamo l'effetto Glowing all'entità in posizione "k" di "a"
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onArmorTick(PlayerTickEvent event) {
		
		EntityPlayer pl = event.player; //The Player
		
		
//		Astral Silver Armor (Jump Boost)
		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.astral_silver_helmet
				&&event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.astral_silver_chest
				&&event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.astral_silver_legs
				&&event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.astral_silver_boots){
			event.player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 100, 1));
		}
		
//		Celenegil Armor (Resistence)
		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.celenegil_helmet
				&&event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.celenegil_chest
				&&event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.celenegil_legs
				&&event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.celenegil_boots){
			event.player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 3));
		}
		
//		Deep Iron Armor (Swimming Speed when the player is in water and on ground)
		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.deep_iron_helmet
				&&event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.deep_iron_chest
				&&event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.deep_iron_legs
				&&event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.deep_iron_boots
				&&event.player.isInWater()){
			
			pl.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 230, 3));
			pl.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 230, 1));
			pl.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, 3));
			if(pl.onGround) {	//checks if the player is tourching ground
				//adds more motion in his movement 
			  if(pl.motionX <= 3D)
			    pl.motionX *= 1.1D;
			  if(pl.motionZ <= 3D)
			    pl.motionZ *= 1.1D;
			}
			else
			{
			    //stop player motion 
				pl.motionX = 0D;
				pl.motionZ = 0D;
			}
			
			//The player can no longer swim upwards
			pl.motionY = -0.3D;
			
			//when the player is in the water he can step one block height like a horse
			if(pl.stepHeight != 1.0F)
				pl.stepHeight = 1.0F;
		}
		else if(pl.stepHeight > 0.0F) //turns the stepHeight to normal if the player isn't wearing the deep iron armor or if he is not in water
			pl.stepHeight = 0.0F;
		
//		Vulcanite Armor (Fire Immunity)
        fire = event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.vulcanite_helmet
                && event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.vulcanite_chest
                && event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.vulcanite_legs
                && event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.vulcanite_boots;
		
//		Angmallen Armor (Luck I for Vampirism)
		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.angmallen_helmet
				&&event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.angmallen_chest
				&&event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.angmallen_legs
				&&event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.angmallen_boots){
			event.player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 80, 0));
		}
		
//		Kalendrite Armor (Strenght I)
		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.kalendrite_helmet
				&&event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.kalendrite_chest
				&&event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.kalendrite_legs
				&&event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.kalendrite_boots){
			event.player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 60, 0));
		}
		
//		Amordrine Armor (Strenght II)
		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.amordrine_helmet
				&&event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.amordrine_chest
				&&event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.amordrine_legs
				&&event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.amordrine_boots){
			event.player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 60, 1));
		}
		
//		Adamantine Armor (Saturation)
		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.adamantine_helmet
				&&event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.adamantine_chest
				&&event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.adamantine_legs
				&&event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.adamantine_boots){
			event.player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 60, 0));
		}
		
//		Astral Silver Armor (Jump Boost II)
		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.astral_silver_helmet
				&&event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.astral_silver_chest
				&&event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.astral_silver_legs
				&&event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.astral_silver_boots){
			event.player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 60, 1));
		}
		
//		Platinum Armor (Night Vision, Needed Vanishing Curse)
		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.platinum_helmet){
			event.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 80, 0));
		}
		
//		Carmot Armor (Haste I)
		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.carmot_helmet
				&&event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.carmot_chest
				&&event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.carmot_legs
				&&event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.carmot_boots) {
			event.player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 60, 0));
			}
		
//		Prometheum Armor (No potion, need to implement a new Effect)
		if (event.player.inventory.armorItemInSlot(3).getItem() == ModArmors.prometheum_helmet
				&&event.player.inventory.armorItemInSlot(2).getItem() == ModArmors.prometheum_chest
				&&event.player.inventory.armorItemInSlot(1).getItem() == ModArmors.prometheum_legs
				&&event.player.inventory.armorItemInSlot(0).getItem() == ModArmors.prometheum_boots){
			event.player.removePotionEffect(MobEffects.POISON);
			}
	}	

	@SubscribeEvent
	public static void onAttack(AttackEntityEvent event){

		EntityPlayer player = event.getEntityPlayer();
		if(!player.world.isRemote)
		{
		
//		Shadow Iron Sword (Blindness [cecit�])
		if (player.getHeldItemMainhand().isItemEqualIgnoreDurability(new ItemStack(ModTools.shadow_iron_sword)))
		{
			Entity foe = event.getTarget();
			EntityLivingBase foe2 = (EntityLivingBase)foe;
			
			if((int)(Math.random()*100) <= 25)
				foe2.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
		}
	
//		Vyroxeres Sword (Potion)
		if (player.getHeldItemMainhand().isItemEqualIgnoreDurability(new ItemStack(ModTools.vyroxeres_sword))){
			Entity foe = event.getTarget();
	
			if((int)(Math.random()*100) <= 25)
				((EntityLivingBase) foe).addPotionEffect(new PotionEffect(MobEffects.POISON, 100));
		}
		
//		Ignatius Sword (Fire Aspect)
		if (player.getHeldItemMainhand().isItemEqualIgnoreDurability(new ItemStack(ModTools.ignatius_sword))) {
			Entity foe = event.getTarget();
			
			if((int)(Math.random()*100) <= 15)
				foe.setFire(5);
		}
		
//		Vulcanite Sword (Fire Aspect)
		if (player.getHeldItemMainhand().isItemEqualIgnoreDurability(new ItemStack(ModTools.vulcanite_sword))) {
			Entity foe = event.getTarget();
			
			if((int)(Math.random()*100) <= 30)
				foe.setFire(5);
		}
		
//		Tartarite Sword (Withering II)
		if (player.getHeldItemMainhand().isItemEqualIgnoreDurability(new ItemStack(ModTools.tartarite_sword))) {
			Entity foe = event.getTarget();
			
			if((int)(Math.random()*100) <= 20)
				((EntityLivingBase) foe).addPotionEffect(new PotionEffect(MobEffects.WITHER, 60,1));
		}
		
		
//		kalendrite sword (Regeneration)
		if (player.getHeldItemMainhand().isItemEqualIgnoreDurability(new ItemStack(ModTools.kalendrite_sword))) {
			
			if((int)(Math.random()*100) <= 30)
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1));
		}
		}
	}
	
//	Sanguinite Sword (Vampirism)
	@SubscribeEvent
	public static void entityHurtEvent(LivingHurtEvent event)
	{
		EntityLivingBase eventEntity = event.getEntityLiving();
		//the entity that damaged the event entity
		Entity source = event.getSource().getImmediateSource();
		if(source instanceof EntityPlayer)
		{
					
			//the player that damaged the event entity
			EntityPlayer pl = (EntityPlayer) source;
					
			if(pl.getHeldItemMainhand().isItemEqualIgnoreDurability(new ItemStack(ModTools.sanguinite_sword))) {
				{		
				//check if the player is missing hearts.
			     if(pl.getHealth() < pl.getMaxHealth())
			     {
					    
			      int luck_level = Math.round(pl.getLuck());
			      //percentage to get healed based on the luck of the player (example: luck 0 = 15%,luck 1 = 20%...) 
				  int percentage = 15 + (luck_level * 5);
				  if(new Random().nextInt(100) < percentage)
				  {		
					 //the heal Amount ,that is the 10% of the damage				  
				     float healAmount = event.getAmount() * 10F / 100F;
			         if(pl.getHealth() + healAmount >= pl.getMaxHealth())
				     	healAmount = 0;      
					  //set the player health   
					   pl.setHealth(pl.getHealth() + healAmount);
				    }
				  }
				}
			}
		}
	}
//	Effects	
	
//	FireImmunity
	@SubscribeEvent
	public static void cancelFireDamage(LivingAttackEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {
			 if (event.getSource().equals (DamageSource.LAVA) 
			 ||  event.getSource().equals (DamageSource.IN_FIRE) 
			 ||  event.getSource().equals (DamageSource.ON_FIRE))
				 event.setCanceled(fire);
		}
		
	}
	

	
}
