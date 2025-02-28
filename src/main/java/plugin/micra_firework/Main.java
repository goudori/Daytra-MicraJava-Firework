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

  private BigInteger num = BigInteger.valueOf(1); // 初期値を1に設定

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
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
    Player player = e.getPlayer(); // プレイヤー取得
    World world = player.getWorld(); // ワールド取得

    // 現在の素数よりも大きい次の素数を取得
    num = num.nextProbablePrime();

    // 素数かどうかを判定する
    if (num.isProbablePrime(1500)) {

      // 花火を出す
      Firework firework = world.spawn(player.getLocation(), Firework.class);
      FireworkMeta fireworkMeta = firework.getFireworkMeta();
      fireworkMeta.addEffect(
          FireworkEffect.builder()
              .withColor(Color.BLUE, Color.MAROON, Color.GREEN)
              .with(Type.STAR)
              .withFlicker()
              .build()
      );
      fireworkMeta.setPower(1);
      firework.setFireworkMeta(fireworkMeta);

      // プレイヤーに素数メッセージを送信
      player.sendMessage("次の素数 " + num + " です。");
    }
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
