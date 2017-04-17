package Reika.DragonAPI.Instantiable.Event;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.Event.HasResult;

@HasResult
/** For grass and mycelium */
public class BlockSpreadEvent extends Event {

	public final World world;
	public final int x;
	public final int y;
	public final int z;
	public final Block spreadingBlock;
	public final boolean defaultValue;

	public BlockSpreadEvent(World world, int x, int y, int z, Block b, boolean flag) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		spreadingBlock = b;
		defaultValue = flag;
	}

	private static boolean shouldDie(World world, int x, int y, int z, Block b) {
		boolean DEFAULT = world.getBlockLightValue(x, y+1, z) < 4 && world.getBlockLightOpacity(x, y+1, z) > 2;
		BlockDeathEvent evt = new BlockDeathEvent(world, x, y, z, b, DEFAULT);
		MinecraftForge.EVENT_BUS.post(evt);
		switch(evt.getResult()) {
			case ALLOW:
				return true;
			case DENY:
				return false;
			default:
			case DEFAULT:
				return DEFAULT;
		}
	}

	private static boolean shouldSpreadTo(World world, int x, int y, int z, Block b) {
		boolean DEFAULT = world.getBlock(x, y, z) == Blocks.dirt && world.getBlockMetadata(x, y, z) == 0 && world.getBlockLightValue(x, y+1, z) >= 4 && world.getBlockLightOpacity(x, y+1, z) <= 2;
		BlockSpreadEvent evt = new BlockSpreadEvent(world, x, y, z, b, DEFAULT);
		MinecraftForge.EVENT_BUS.post(evt);
		switch(evt.getResult()) {
			case ALLOW:
				return true;
			case DENY:
				return false;
			default:
			case DEFAULT:
				return DEFAULT;
		}
	}

	public static void fire(World world, int x, int y, int z, Random rand, Block b) {
		if (!world.isRemote) {
			if (shouldDie(world, x, y, z, b)) {
				world.setBlock(x, y, z, Blocks.dirt);
			}
			else if (world.getBlockLightValue(x, y+1, z) >= 9) {
				for (int i = 0; i < 4; i++)  {
					int dx = x+rand.nextInt(3)-1;
					int dy = y+rand.nextInt(5)-3;
					int dz = z+rand.nextInt(3)-1;
					if (shouldSpreadTo(world, dx, dy, dz, b)) {
						world.setBlock(dx, dy, dz, b);
					}
				}
			}
		}
	}

	@HasResult
	public static class BlockDeathEvent extends BlockSpreadEvent {

		public BlockDeathEvent(World world, int x, int y, int z, Block b, boolean flag) {
			super(world, x, y, z, b, flag);
		}
	}

}