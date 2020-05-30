package me.dablakbandit.minescape.threader;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import me.dablakbandit.minescape.sprinters.SprintersPlugin;

public class MineScapeThreader{
	
	private static MineScapeThreader threader = new MineScapeThreader();
	
	public static MineScapeThreader getInstance(){
		return threader;
	}
	
	private static Timer				timer		= new Timer();
	private static BukkitScheduler		scheduler	= Bukkit.getScheduler();
	private static Plugin				plugin		= SprintersPlugin.getInstance();
	private static ThreadPoolExecutor	service		= (ThreadPoolExecutor)Executors.newFixedThreadPool(4);
	
	public MineScapeThreader(){
		
	}
	
	public void enable(){
		
	}
	
	public void shutdown(){
		service.shutdown();
	}
	
	public BukkitTask runTaskWithDelay(Runnable run){
		return runTaskWithDelay(run, 0);
	}
	
	public BukkitTask runTaskWithDelay(Runnable run, long ticks){
		return scheduler.runTaskLater(plugin, run, ticks);
	}
	
	public BukkitTask repeatTaskWithDelay(Runnable run, long ticks){
		return repeatTaskWithDelay(run, ticks, ticks);
	}
	
	public BukkitTask repeatTaskWithDelay(Runnable run, long delay, long ticks){
		return scheduler.runTaskTimer(plugin, run, delay, ticks);
	}
	
	public BukkitTask runAsyncTaskWithDelay(Runnable run){
		return runAsyncTaskWithDelay(run, 0);
	}
	
	public BukkitTask runAsyncTaskWithDelay(Runnable run, long ticks){
		return scheduler.runTaskLaterAsynchronously(plugin, () -> service.submit(run), ticks);
	}
	
	public BukkitTask repeatAsyncTaskWithDelay(Runnable run, long ticks){
		return repeatAsyncTaskWithDelay(run, ticks, ticks);
	}
	
	public BukkitTask repeatAsyncTaskWithDelay(Runnable run, long delay, long ticks){
		return scheduler.runTaskTimerAsynchronously(plugin, () -> service.submit(run), delay, ticks);
	}
	
	public void runTimerTaskSync(Runnable run, int milliseconds){
		TimerTask t = new TimerTask(){
			@Override
			public void run(){
				runTaskWithDelay(run);
			}
		};
		timer.schedule(t, milliseconds);
	}
	
}
