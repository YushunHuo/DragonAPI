/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.DragonAPI.Interfaces;

import net.minecraft.entity.player.EntityPlayer;

public interface XPProducer {

	public void clearXP();

	public float getXP();

	public void addXPToPlayer(EntityPlayer ep);

}