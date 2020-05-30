package me.dablakbandit.minescape.sprinters.game;

import me.dablakbandit.minescape.gamemanager.game.GameState;

public abstract class SprintersState extends GameState<SprintersGame>{
	
	public abstract SprintersState getNext();
}
