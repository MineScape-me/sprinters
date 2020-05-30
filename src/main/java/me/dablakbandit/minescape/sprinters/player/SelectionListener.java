package me.dablakbandit.minescape.sprinters.player;

import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.chatapi.ChatAPIInfo;
import me.dablakbandit.minescape.sprinters.chat.ui.base.ChatSelectionListener;
import me.dablakbandit.minescape.threader.MineScapeThreader;

public class SelectionListener extends me.dablakbandit.core.players.selection.SelectionListener{
	@Override
	public void onSelectPoint1(CorePlayers pl){
		update(pl);
	}
	
	@Override
	public void onSelectPoint2(CorePlayers pl){
		update(pl);
	}
	
	private void update(CorePlayers pl){
		ChatAPIInfo cai = pl.getInfo(ChatAPIInfo.class);
		if(cai.getOpenChat() instanceof ChatSelectionListener){
			MineScapeThreader.getInstance().runTaskWithDelay(() -> {
				cai.refresh();
			});
		}
	}
}
