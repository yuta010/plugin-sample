package org.example.plugin.pluginsample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin_sample extends JavaPlugin implements Listener {

  private int count = 0;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  /**
   * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
   *
   * @param e イベント
   */

  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) throws IOException {
    // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
    Player player = e.getPlayer();
    World world = player.getWorld();

    List<Color> colorList = List.of(Color.RED, Color.OLIVE, Color.BLACK, Color.ORANGE, Color.LIME, Color.PURPLE);

    if (count % 2 == 1) {
      for (Color color : colorList) {
        // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
        Firework firework = world.spawn(player.getLocation(), Firework.class);

        // 花火オブジェクトが持つメタ情報を取得。
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // メタ情報に対して設定を追加したり、値の上書きを行う。
        // 今回は青色で星型の花火を打ち上げる。
        fireworkMeta.addEffect(
            FireworkEffect.builder()
                .withColor(color)
                .with(Type.STAR)
                .withFlicker()
                .build());
        fireworkMeta.setPower(2);

        // 追加した情報で再設定する。
        firework.setFireworkMeta(fireworkMeta);
      }
      Path path = Path.of("firework.txt");
      Files.writeString(path, "たーまやー");
      player.sendMessage(Files.readString(path));
    }
    count++;
  }

  @EventHandler
  public void playerDropItemSetFlyEvent(PlayerDropItemEvent e) {
    Player player = e.getPlayer();

    player.setAllowFlight(true);
    player.setFlySpeed(1);
  }


  @EventHandler
  public void onPlayerMoveEvent(PlayerMoveEvent e) {
    Player player = e.getPlayer();
    ItemStack[] itemStacks = player.getInventory().getContents();
    for (ItemStack item : itemStacks) {
      if (!Objects.isNull(item) && item.getMaxStackSize() == 64 && item.getAmount() < 64) {
        item.setAmount(64);
      }
    }
    player.getInventory().setContents(itemStacks);
  }

  @EventHandler
  public void BedEnterEvent(PlayerBedEnterEvent e) {
    Player player = e.getPlayer();
    ItemStack[] itemStacks = player.getInventory().getContents();
    for (ItemStack item : itemStacks) {
      if (!Objects.isNull(item) && item.getAmount() < 64 && item.getMaxStackSize() == 64) {
        item.setAmount(0);
      }
    }
  }
}