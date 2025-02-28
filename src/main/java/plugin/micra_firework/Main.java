package plugin.micra_firework;

import java.math.BigInteger;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

  private int count;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  /**
   * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
   * <p>
   * <p>
   * countを増やして、偶数の時に花火を打ち上げる。
   * <p>
   * ※奇数の時は花火を打ち上げない
   *
   * @param e イベント
   */
  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
    Player player = e.getPlayer(); // プレイヤー取得
    World world = player.getWorld(); // ワールド取得

    // 偶数の時に花火を打ち上げる
    if (count % 2 == 0) {

      // 花火を出す
      Firework firework = world.spawn(player.getLocation(), Firework.class);
      FireworkMeta fireworkMeta = firework.getFireworkMeta();
      fireworkMeta.addEffect(
          FireworkEffect.builder()
              .withColor(Color.BLUE, Color.RED, Color.YELLOW)
              .with(Type.BALL_LARGE)
              .withFlicker()
              .build()
      );
      fireworkMeta.setPower(2);
      firework.setFireworkMeta(fireworkMeta);

      // スニークが偶数の時に花火を打ち上げているかのメッセージ確認
      player.sendMessage("偶数の花火は、" + count);

    }
    count++; // １ずつ増やす
  }

  /**
   * プレーヤー対して花火ダメージを無効化する時のイベントです。
   *
   * @param event
   */
  @EventHandler
  public void onFireworkDamage(EntityDamageByEntityEvent event) {
    Entity fireworkDamager = event.getDamager();

    // プレイヤーへの花火ダメージを無効化
    if (fireworkDamager instanceof Firework) {
      event.setCancelled(true);
    }
  }
}
