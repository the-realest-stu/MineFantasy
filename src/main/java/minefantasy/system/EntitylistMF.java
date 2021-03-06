package minefantasy.system;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import minefantasy.MineFantasyBase;
import minefantasy.entity.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.BiomeDictionary.Type;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntitylistMF {

	public static int IDBase;

	public static void init() {
		biomeSpawnBlacklist.add("MooMooFarm");
		
		IDBase = cfg.entityIDBase;
		addEntity(EntityHound.class, "HoundMF", Color.WHITE.hashCode(), MineFantasyBase.getColourForRGB(128, 64, 0));
		addEntity(EntityMinotaur.class, "Minotaur", MineFantasyBase.getColourForRGB(70, 50, 28),
				Color.BLACK.hashCode());
		addEntity(EntitySkeletalKnight.class, "SkeletalKnight", Color.GRAY.hashCode(),
				MineFantasyBase.getColourForRGB(100, 70, 70));
		addEntity(EntityDragonSmall.class, "SmallDragon", Color.RED.hashCode(),
				MineFantasyBase.getColourForRGB(221, 218, 164));
		addEntity(EntityDrake.class, "Drake", Color.GREEN.hashCode(), MineFantasyBase.getColourForRGB(221, 218, 164));
		addEntity(EntityBasilisk.class, "Basilisk", Color.BLUE.hashCode(),
				MineFantasyBase.getColourForRGB(221, 218, 164));

		EntityRegistry.registerModEntity(EntityBombThrown.class, "MFBomb", IDBase, MineFantasyBase.instance, 64, 1,
				true);
		IDBase++;
		EntityRegistry.registerModEntity(EntityFirebreath.class, "fireBreath", IDBase, MineFantasyBase.instance, 64, 20,
				true);
		IDBase++;
		EntityRegistry.registerModEntity(EntityArrowMF.class, "arrowMF", IDBase, MineFantasyBase.instance, 64, 20,
				false);
		IDBase++;
		EntityRegistry.registerModEntity(EntityBoltMF.class, "boltMF", IDBase, MineFantasyBase.instance, 64, 20, false);
		IDBase++;
		EntityRegistry.registerModEntity(EntityThrownSpear.class, "MFSpear", IDBase, MineFantasyBase.instance, 64, 20,
				false);
		EntityRegistry.registerModEntity(EntityRockSling.class, "MFRock", IDBase, MineFantasyBase.instance, 64, 1,
				false);
		IDBase++;
		EntityRegistry.registerModEntity(EntityShrapnel.class, "shrapnelMF", IDBase, MineFantasyBase.instance, 64, 1,
				false);
		IDBase++;
		
	}
	
	public static void initEntitySpawns() {
	
		addSpawn(EntityHound.class, cfg.houndSpawnrate, 3, 5, EnumCreatureType.creature, Type.FOREST);
		addSpawn(EntityHound.class, cfg.houndSpawnrate / 3, 3, 5, EnumCreatureType.creature, Type.PLAINS);
		addSpawn(EntityDrake.class, cfg.drakeSpawnrate, 1, 3, EnumCreatureType.creature, Type.PLAINS);
		addSpawn(EntityDrake.class, cfg.drakeSpawnrate, 1, 3, EnumCreatureType.creature, Type.MOUNTAIN);
		
		addSpawn(EntitySkeletalKnight.class, cfg.knightSpawnrate, 1, 1, EnumCreatureType.monster);
		addSpawn(EntityMinotaur.class, cfg.minotaurSpawnrate, 1, 1, EnumCreatureType.monster);
		addSpawn(EntityBasilisk.class, cfg.basilSpawnrate, 1, 1, EnumCreatureType.monster);
		
		addSpawn(EntityMinotaur.class, cfg.minotaurSpawnrate * 5, 2, 7, EnumCreatureType.monster, Type.NETHER);
		addSpawn(EntityBasilisk.class, cfg.basilSpawnrateNether, 1, 1, EnumCreatureType.monster, Type.NETHER);
		addSpawn(EntityDragonSmall.class, cfg.dragonSpawnrateNether, 1, 2, EnumCreatureType.monster, Type.NETHER);

	}

	private static void addEntity(Class<? extends Entity> entityClass, String entityName, int eggColor,
			int eggDotsColor) {
		if (MineFantasyBase.isDebug()) {
			System.out.println("MineFantasy: registerEntity " + entityClass + " with Mod ID " + IDBase);
		}
		EntityRegistry.registerModEntity(entityClass, entityName, IDBase, MineFantasyBase.instance, 128, 1, true);
		EntityList.entityEggs.put(Integer.valueOf(IDBase), new EntityEggInfo(IDBase, eggColor, eggDotsColor));

		EntityList.addMapping(entityClass, entityName, IDBase);
		IDBase++;
	}

	public static List<String> biomeSpawnBlacklist = new ArrayList<String>();
	
	public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max,
			EnumCreatureType typeOfCreature) {
		for (BiomeGenBase biome : BiomeGenBase.biomeList) {
			if (biome != null) {
				if (BiomeDictionary.isBiomeRegistered(biome)) {
					if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.END)) {
						continue;
					}
					if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER)) {
						continue;
					}
					if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MUSHROOM)) {
						continue;
					}
				}
				if (biome.getClass().toString().contains("twilightforest")) {
					continue;
				}
				if (biomeSpawnBlacklist.contains(biome.biomeName)) {
					continue;
				}
				EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biome);
				// System.out.println("Adding " + entityClass.toString() + " to biome " + biome.getClass() + " : " + biome.biomeName);
			}
		}
	}

	public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max,
			EnumCreatureType typeOfCreature, BiomeDictionary.Type type) {
		for (BiomeGenBase biome : BiomeGenBase.biomeList) {
			if (biome != null) {
				boolean addSpawn = false;
				if (biome.getClass().toString().contains("twilightforest")) {
					continue;
				}
				if (biomeSpawnBlacklist.contains(biome.biomeName)) {
					continue;
				}
				if (BiomeDictionary.isBiomeRegistered(biome)) {
					if (BiomeDictionary.isBiomeOfType(biome, type)) {
						addSpawn = true;
					}
				}
				if (type == Type.MOUNTAIN && (biome.biomeName.contains("Highlands") || biome.biomeName.contains("Mountains"))) {
					addSpawn = true;
				}
				if (addSpawn) {
					EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biome);
					// System.out.println("Adding " + entityClass.toString() + " to biome " + biome.getClass() + " : " + biome.biomeName);
				}
			}
		}
	}
}
