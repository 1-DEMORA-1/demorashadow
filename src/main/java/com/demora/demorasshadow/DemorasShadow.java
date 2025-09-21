package com.demora.demorasshadow;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class DemorasShadow extends JavaPlugin {
    
    private ShadowBladeManager shadowBladeManager;
    
    @Override
    public void onEnable() {

        saveDefaultConfig();
        

        shadowBladeManager = new ShadowBladeManager(this);
        

        getServer().getPluginManager().registerEvents(shadowBladeManager, this);
        

        Bukkit.getScheduler().runTaskTimer(this, shadowBladeManager::updateTimers, 0L, 1L);
        
        getLogger().info("DemorasShadow плагин успешно загружен!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("DemorasShadow плагин отключен!");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("demorashadow")) {
            return false;
        }
        
        if (args.length == 0) {
            sender.sendMessage("§cИспользование: /demorashadow <give|reload> [игрок]");
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "give":
                if (!sender.hasPermission("demorasshadow.give")) {
                    sender.sendMessage("§cУ вас нет прав для выполнения этой команды!");
                    return true;
                }
                
                Player target;
                if (args.length >= 2) {
                    target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage("§cИгрок не найден!");
                        return true;
                    }
                } else if (sender instanceof Player) {
                    target = (Player) sender;
                } else {
                    sender.sendMessage("§cВы должны указать имя игрока!");
                    return true;
                }
                
                shadowBladeManager.giveShadowBlade(target);
                sender.sendMessage("§aТеневой клинок выдан игроку " + target.getName());
                return true;
                
            case "reload":
                if (!sender.hasPermission("demorasshadow.reload")) {
                    sender.sendMessage("§cУ вас нет прав для выполнения этой команды!");
                    return true;
                }
                
                reloadConfig();
                shadowBladeManager.reloadConfig();
                sender.sendMessage("§aКонфигурация перезагружена!");
                return true;
                
            default:
                sender.sendMessage("§cНеизвестная команда! Используйте: /demorashadow <give|reload> [игрок]");
                return true;
        }
    }
    
    public ShadowBladeManager getShadowBladeManager() {
        return shadowBladeManager;
    }
}
