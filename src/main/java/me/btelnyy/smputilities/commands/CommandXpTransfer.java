package me.btelnyy.smputilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.btelnyy.smputilities.SmpUtilities;
import me.btelnyy.smputilities.service.Utils;
import me.btelnyy.smputilities.service.file_manager.Configuration;
import me.btelnyy.smputilities.service.file_manager.FileID;

public class CommandXpTransfer implements CommandExecutor{
    static final Configuration language = SmpUtilities.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Utils.colored(language.getString("not_player")));
            return true;    
        }
        Player player = (Player) sender;
        if(args.length < 1){
            player.sendMessage(Utils.colored("&cError: &7Invalid Syntax. Usage: /transferxp <player> <amount>"));
            return true;
        }
        if(Bukkit.getPlayer(args[0]) == null){
            player.sendMessage(Utils.colored(language.getString("player_not_found")));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        Integer levels;
        try{
            levels = Integer.parseInt(args[1]);
        }catch(Exception ex){
            player.sendMessage(Utils.colored(language.getString("invalid_number")));
            return true;
        }
        if((player.getLevel() - levels) < 0){
            player.sendMessage(Utils.colored(language.getString("not_enough_levels")));
            return true;
        }
        target.setLevel(target.getLevel() + levels);
        player.setLevel(player.getLevel() - levels);
        player.sendMessage(Utils.colored(language.getString("transfer_succes")).replace("{levels}", levels.toString()).replace("{target}", target.getName()));
        target.sendMessage(Utils.colored(language.getString("transfer_success_target").replace("{levels}", levels.toString()).replace("{sender}", player.getName())));
        return true;
    }
}
