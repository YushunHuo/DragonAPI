package Reika.DragonAPI;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ReikaEntityHelper {
	
	private static int[] mobColorArray = new int[201]; //Highest Entity ID (endercrystal)+1
	
	private static void setMobColors() {
		mobColorArray[50] = ReikaGuiAPI.RGBtoHex(65, 183, 54);
		mobColorArray[51] = ReikaGuiAPI.RGBtoHex(207); //Skeleton
		mobColorArray[52] = ReikaGuiAPI.RGBtoHex(90, 71, 43); //Spider
		mobColorArray[53] = ReikaGuiAPI.RGBtoHex(67, 109, 53); //Giant
		mobColorArray[54] = ReikaGuiAPI.RGBtoHex(67, 109, 53); //Zombie
		mobColorArray[55] = ReikaGuiAPI.RGBtoHex(90, 162, 68); //Slime
		mobColorArray[56] = ReikaGuiAPI.RGBtoHex(240); //Ghast
		mobColorArray[57] = ReikaGuiAPI.RGBtoHex(181, 131, 131); //PigZombie
		mobColorArray[58] = ReikaGuiAPI.RGBtoHex(204, 15, 248); //Enderman
		mobColorArray[59] = ReikaGuiAPI.RGBtoHex(18, 77, 90); //Cave Spider
		mobColorArray[60] = ReikaGuiAPI.RGBtoHex(140); //Silverfish
		mobColorArray[61] = ReikaGuiAPI.RGBtoHex(235, 180, 26); //Blaze
		mobColorArray[62] = ReikaGuiAPI.RGBtoHex(84, 14, 0); //LavaSlime
		mobColorArray[63] = ReikaGuiAPI.RGBtoHex(224, 121, 250); //Dragon
		mobColorArray[64] = ReikaGuiAPI.RGBtoHex(79); //Wither
		mobColorArray[65] = ReikaGuiAPI.RGBtoHex(118, 100, 61); //Bat
		mobColorArray[66] = ReikaGuiAPI.RGBtoHex(163, 148, 131); //Witch
		
		mobColorArray[90] = ReikaGuiAPI.RGBtoHex(238, 158, 158); //Pig
		mobColorArray[91] = ReikaGuiAPI.RGBtoHex(214); //Sheep
		mobColorArray[92] = ReikaGuiAPI.RGBtoHex(67, 53, 37); //Cow
		mobColorArray[93] = ReikaGuiAPI.RGBtoHex(193, 147, 67); //Chicken
		mobColorArray[94] = ReikaGuiAPI.RGBtoHex(83, 108, 127); //Squid
		mobColorArray[95] = ReikaGuiAPI.RGBtoHex(183, 179, 180); //Wolf
		mobColorArray[96] = ReikaGuiAPI.RGBtoHex(151, 3, 4); //Mooshroom
		mobColorArray[97] = ReikaGuiAPI.RGBtoHex(226, 143, 34); //Snow Golem
		mobColorArray[98] = ReikaGuiAPI.RGBtoHex(242, 197, 110); //Ocelot
		mobColorArray[99] = ReikaGuiAPI.RGBtoHex(208, 185, 168); //Iron Golem
		
		mobColorArray[120] = ReikaGuiAPI.RGBtoHex(178, 122, 98); //Villager
		
		
	}
	
	/** Converts a string mobname to its respective id. Args: Name, world */
	public static int mobNameToID(String name, World world) {
		Entity ent = EntityList.createEntityByName(name, world);
		int id = ent.entityId;
		return id;
	}
	
	/** Converts a string mobname to its respective class file. Args: Name, world */
	public static Class mobNameToClass(String name, World world) {
		Entity ent = EntityList.createEntityByName(name, world);
		return ent.getClass();
	}
	
	/** Returns the number of mob-type entities in MineCraft. Args: World */
	public static int getNumberMobsInMC(World world) {
		int highestid = 0;
		int numberentities = EntityList.IDtoClassMapping.size();
		Object[] entityKeys = EntityList.IDtoClassMapping.keySet().toArray();
		int[] entityIDs = new int[entityKeys.length];
		for (int i = 0; i < entityKeys.length; i++) {
			entityIDs[i] = Integer.valueOf(String.valueOf(entityKeys[i]));
		}
		for (int i = 0; i < entityIDs.length; i++) {
			if (entityIDs[i] > highestid) {
				highestid = entityIDs[i];
			}
		}
		int numbermobs = 0;
		for (int id = 0; id <= highestid; id++) {
			if (EntityList.IDtoClassMapping.containsKey(id) && id != 48 && id != 49) { //ID 48,49 is "Mob","Monster" -> EntityLiving.class, EntityMob.class
				Entity ent = EntityList.createEntityByID(id, world);
				if (ent instanceof EntityLiving)
					numbermobs++;
			}
		}
		return numbermobs;
	}
	
	/** Returns the mass (in kg) of the entity. Args: Entity */
    public static double getEntityMass(Entity ent) {
    	if (ent instanceof EntityItem || ent instanceof EntityXPOrb)
    		return 0.25;
    	if (ent instanceof EntityCreeper)
    		return 100; //220 lbs; TNT is heavy
    	if (ent instanceof EntitySkeleton)
    		return 30;	//66 lbs
    	if (ent instanceof EntityZombie || ent instanceof EntityPlayer || ent instanceof EntityVillager || ent instanceof EntityWitch)
    		return 70; // 180 lbs
    	if (ent instanceof EntityPigZombie)
    		return 90;
    	if (ent instanceof EntitySpider)
    		return 60;	//
    	if (ent instanceof EntityPig)
    		return 100;
    	if (ent instanceof EntityCow || ent instanceof EntityMooshroom)
    		return 350;
    	if (ent instanceof EntityGhast)
    		return 20; //spirit creature
    	if (ent instanceof EntityBlaze)
    		return 300;
    	if (ent instanceof EntityMagmaCube) {
    		EntityMagmaCube cube = (EntityMagmaCube)ent;
    		return 400*cube.getSlimeSize();
    	}
    	if (ent instanceof EntitySlime) {
    		EntitySlime cube = (EntitySlime)ent;
    		return 200*cube.getSlimeSize();
    	}
    	if (ent instanceof EntityEnderman)
    		return 40;
    	if (ent instanceof EntitySilverfish)
    		return 1;
    	if (ent instanceof EntityChicken)
    		return 2;
    	if (ent instanceof EntityCaveSpider)
    		return 30;
    	if (ent instanceof EntityDragon)
    		return 2000; //really conjectural
    	if (ent instanceof EntityWolf)
    		return 50;
    	if (ent instanceof EntityOcelot)
    		return 15;
    	if (ent instanceof EntityIronGolem)
    		return 32000; //iron = 8g/cc, 4m^3 of it
    	if (ent instanceof EntityGolem)
    		return 100;
    	if (ent instanceof EntitySheep)
    		return 150;
    	if (ent instanceof EntitySquid)
    		return 120;
    	if (ent instanceof EntityBat)
    		return 0.5;
    	if (ent instanceof EntityMinecart)
    		return 400;
    	if (ent instanceof EntityBoat)
    		return 70;
    	if (ent instanceof EntityTNTPrimed)
    		return 2700; //2.7 g/cc
    	if (ent instanceof EntityFallingSand)
    		return 2000; //2 g/cc
    	return 0.1;
    }
    
    /** Returns an itemstack (size 1 item) of the entity's breeding item. Args: Entity */
    public static ItemStack getBreedItem(EntityAnimal ent) {
    	int id;
    	int meta;
    	ItemStack item = null;
    	for (id = 256; id < Item.itemsList.length; id++) {
    		if (ReikaItemHelper.hasMetadata(id)) {
    			for (meta = 0; meta < 15; meta++) {
    				item = new ItemStack(id, 1, meta);
    				if (ent.isBreedingItem(item))
    					return item;
    			}
    		}
    		else {
				item = new ItemStack(id, 1, 0);
				if (ent.isBreedingItem(item))
					return item;
    		}
    	}
    	return null;
    }
    
    /** Converts a mob ID to a color, based off the mob's color. Players return bright red.
     * Args: Mob ID */
    public static int mobToColor(int id) {
    	if (EntityList.createEntityByID(id, null) instanceof EntityPlayer)
    		return 0xffff0000;
    	setMobColors();
    	return mobColorArray[id];
    }
	
}
