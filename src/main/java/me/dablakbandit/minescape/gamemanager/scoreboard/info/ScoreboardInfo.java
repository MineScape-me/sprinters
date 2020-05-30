package me.dablakbandit.minescape.gamemanager.scoreboard.info;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import me.dablakbandit.core.players.CorePlayerManager;
import me.dablakbandit.core.players.CorePlayers;
import me.dablakbandit.core.players.info.CorePlayersInfo;
import me.dablakbandit.minescape.threader.MineScapeThreader;

public class ScoreboardInfo extends CorePlayersInfo{
	
	private static MineScapeThreader	threader			= MineScapeThreader.getInstance();
	private static ScoreboardManager	scoreboardManager	= Bukkit.getScoreboardManager();
	private static String[]				colors				= new String[15];
	
	static{
		for(int i = 0; i < colors.length; i++){
			String s = ChatColor.values()[i].toString();
			colors[i] = s + s + s + s + s + s + s + ChatColor.RESET;
		}
	}
	
	public ScoreboardInfo(CorePlayers pl){
		super(pl);
	}
	
	private Scoreboard board;
	
	@Override
	public void load(){
		setup();
		setupSide();
	}
	
	public void setup(){
		board = scoreboardManager.getNewScoreboard();
		Player player = pl.getPlayer();
		if(player != null){
			player.setScoreboard(board);
		}
		for(int i = 0; i < colors.length; i++){
			Team t = board.registerNewTeam("line-" + i);
			String score = colors[i];
			t.addEntry(score);
			// lines.add(new ScoreboardLine(pl, t));
		}
		if(player != null){
			threader.runAsyncTaskWithDelay(() -> {
				delayUpdate();
			});
		}
	}
	
	public void setupSide(){
		Objective obj = board.registerNewObjective("side", "dummy");
		obj.setDisplayName(ChatColor.GOLD + "=-=-=-=[ MineScape ]=-=-=-=");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		for(int i = 0; i < colors.length; i++){
			String score = colors[i];
			obj.getScore(score).setScore(-i);
		}
	}
	
	public void delayUpdate(){
		CorePlayerManager.getInstance().getPlayers().values().forEach(pl -> {
			update(pl, "", "");
		});
	}
	
	public void update(CorePlayers other, String prefix, String suffix){
		String teamName = other.getName();
		Team team = getTeam(teamName);
		if(team == null){
			team = createTeam(teamName, ChatColor.WHITE);
		}
		if(prefix != null){
			team.setPrefix(prefix);
		}
		if(suffix != null){
			team.setSuffix(suffix);
		}
		if(other.getPlayer() != null && !team.hasEntry(other.getName())){
			team.addEntry(other.getName());
		}
	}
	
	protected Team getTeam(String name){
		return board.getTeam(name);
	}
	
	protected Team createTeam(String name){
		return createTeam(name, null);
	}
	
	protected Team createTeam(String name, ChatColor color){
		Team team = board.registerNewTeam(name);
		team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
		team.setCanSeeFriendlyInvisibles(false);
		if(color != null){
			team.setColor(color);
		}
		return team;
	}
	
	public void remove(){
		Player player = pl.getPlayer();
		CorePlayerManager.getInstance().getInfo(ScoreboardInfo.class).stream().map(si -> si.getTeam(player.getName())).filter(Objects::nonNull).forEach(Team::unregister);
	}
	
	@Override
	public void save(){
		remove();
	}
}
