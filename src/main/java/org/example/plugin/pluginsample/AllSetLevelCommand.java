package org.example.plugin.pluginsample;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AllSetLevelCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(sender instanceof Player player){
      player.sendMessage("実行できません！");
    } else {
      for(Player player :sender.getServer().getOnlinePlayers()) {
        player.setLevel(Integer.parseInt(args[0]));
        player.sendMessage("プレイヤー全員のレベルが" + args[0] + "になりました！");
        System.out.println("プレイヤー全員のレベルが" + args[0] + "になりました！");
      }
    }
    return false;
  }
}
