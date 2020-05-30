package me.dablakbandit.minescape.threader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.packets.PacketHandler;
import me.dablakbandit.core.players.packets.PacketInfo;
import me.dablakbandit.core.utils.NMSUtils;

public class MSPacketUtils{
	
	private static MineScapeThreader	threader				= MineScapeThreader.getInstance();
	
	public static Class<?>				classEntityPlayer		= NMSUtils.getNMSClass("EntityPlayer");
	public static Method				getHandle				= NMSUtils.getMethod(NMSUtils.getOBCClass("entity.CraftPlayer"), "getHandle", new Class[0]);
	public static Field					fieldPlayerConnection	= NMSUtils.getFieldSilent(classEntityPlayer, "playerConnection");
	public static Class<?>				classPlayerConnection	= NMSUtils.getNMSClassSilent("PlayerConnection");
	public static Class<?>				classPacket				= NMSUtils.getNMSClassSilent("Packet");
	public static Method				methodSendPacket		= NMSUtils.getMethodSilent(classPlayerConnection, "sendPacket", new Class[]{ classPacket });;
	
	public static Object getHandle(Entity entity) throws Exception{
		return getHandle.invoke(entity);
	}
	
	public static Object getPlayerConnection(Player player) throws Exception{
		Object entityplayer = getHandle(player);
		return fieldPlayerConnection.get(entityplayer);
	}
	
	public static void dispatchPacket(Collection<CorePlayers> targets, Object... packets){
		dispatchPacket(targets, 0, false, packets);
	}
	
	public static void dispatchPacket(Collection<CorePlayers> targets, Boolean bypass, Object... packets){
		dispatchPacket(targets, 0, bypass, packets);
	}
	
	public static void dispatchPacket(Collection<CorePlayers> targets, int delay, Object... packets){
		dispatchPacket(targets, delay, false, packets);
	}
	
	public static void dispatchPacket(Collection<CorePlayers> targets, int delay, Boolean bypass, Object... packets){
		Collection colPackets = Arrays.stream(packets).collect(Collectors.toList());
		dispatchPacket(targets, delay, bypass, colPackets);
	}
	
	public static void dispatchPacket(Collection<CorePlayers> targets, Collection packets){
		dispatchPacket(targets, 0, false, packets);
	}
	
	public static void dispatchPacket(Collection<CorePlayers> targets, int delay, Collection packets){
		dispatchPacket(targets, delay, false, packets);
	}
	
	public static void dispatchPacket(Collection<CorePlayers> targets, Boolean bypass, Collection packets){
		dispatchPacket(targets, 0, bypass, packets);
	}
	
	public static void dispatchPacket(Collection<CorePlayers> targets, int delay, Boolean bypass, Collection packets){
		targets.forEach(target -> dispatchPacket(target, delay, bypass, packets));
	}
	
	public static void dispatchPacket(CorePlayers target, Object... packets){
		dispatchPacket(target, 0, false, packets);
	}
	
	public static void dispatchPacket(CorePlayers target, Boolean bypass, Object... packets){
		dispatchPacket(target, 0, bypass, packets);
	}
	
	public static void dispatchPacket(CorePlayers target, int delay, Object... packets){
		dispatchPacket(target, delay, false, packets);
	}
	
	public static void dispatchPacket(CorePlayers target, int delay, Boolean bypass, Object... packets){
		dispatchPacket(target, delay, bypass, Arrays.asList(packets));
	}
	
	public static void dispatchPacket(CorePlayers target, Collection packets){
		dispatchPacket(target, 0, false, packets);
	}
	
	public static void dispatchPacket(CorePlayers target, Boolean bypass, Collection packets){
		dispatchPacket(target, 0, bypass, packets);
	}
	
	public static void dispatchPacket(CorePlayers target, int delay, Collection packets){
		dispatchPacket(target, delay, false, packets);
	}
	
	public static void dispatchPacket(CorePlayers target, int delay, Boolean bypass, Collection packets){
		if(!Bukkit.getServer().isPrimaryThread()){
			threader.runTaskWithDelay(() -> {
				safeSend(target, bypass, packets);
			});
		}else{
			safeSend(target, bypass, packets);
		}
	}
	
	private static void safeSend(CorePlayers target, Boolean bypass, Collection packets){
		if(packets.size() == 0){ return; }
		if(target == null || target.getPlayer() == null){ return; }
		try{
			if(bypass){
				PacketHandler handler = target.getInfo(PacketInfo.class).getHandler();
				for(Object packet : packets){
					handler.bypassWrite(packet, true);
				}
			}else{
				Object ppco = getPlayerConnection(target.getPlayer());
				for(Object packet : packets){
					methodSendPacket.invoke(ppco, packet);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
