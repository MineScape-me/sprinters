package me.dablakbandit.minescape.sprinters.game.regions;

import me.dablakbandit.core.utils.NMSUtils;
import me.dablakbandit.minescape.json.JSON;
import me.dablakbandit.minescape.sprinters.game.regions.modifiers.*;

public enum RegionType{
	
	//@formatter:off
	SPRINT(SprintModifier.class),
	CONFUSION(ConfusionModifier.class),
	SLOW(SlowModifier.class),
	JUMP(JumpModifier.class),
	CHECKPOINT(CheckpointModifier.class),
	END(EndModifier.class),
    ;
    //@formatter:on
	
	private Class<? extends Modifier> modifier;
	
	RegionType(Class<? extends Modifier> modifier){
		this.modifier = modifier;
	}
	
	public Modifier getNew(){
		try{
			return (Modifier)NMSUtils.getConstructor(modifier).newInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Class<? extends Modifier> getModifier(){
		return modifier;
	}
	
	public Modifier fromJson(String json){
		return JSON.loadAndCopy(getNew(), json);
	}
}
