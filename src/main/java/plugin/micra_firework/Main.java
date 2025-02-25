package plugin.micra_firework;

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
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

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
    // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
    Player player = e.getPlayer(); // イベントからプレイヤーの情報を取得する

    World world = player.getWorld();// プレイヤーがいるワールドの情報を取得する

    // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
    Firework firework = world.spawn(player.getLocation(), Firework.class);

    // 花火オブジェクトが持つメタ情報を取得。
    FireworkMeta fireworkMeta = firework.getFireworkMeta();

    // メタ情報に対して設定を追加したり、値の上書きを行う。
    // 今回は花火を打ち上げる。

    // スニークした時の花火の設定
    fireworkMeta.addEffect(
        FireworkEffect.builder()
            .withColor(Color.BLUE, Color.MAROON, Color.GREEN)
            .with(Type.STAR)
            .withFlicker()
            .build());
    fireworkMeta.setPower(1); //花火の打ち上げの高さ 

    // 追加した情報で再設定する。
    firework.setFireworkMeta(fireworkMeta);

  }

  /**
   * プレーヤーが走った時に花火を打ちあげるイベント
   *
   * @param e
   */
  @EventHandler
  public void onPlayerRun(PlayerToggleSprintEvent e) {
    // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
    Player player = e.getPlayer();
    World world = player.getWorld();

    // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
    Firework firework = world.spawn(player.getLocation(), Firework.class);

    // 花火オブジェクトが持つメタ情報を取得。
    FireworkMeta fireworkMeta = firework.getFireworkMeta();

    // 走った時の花火の設定
    // スニークした時の花火の設定
    fireworkMeta.addEffect(
        FireworkEffect.builder()
            .withColor(Color.GREEN)
            .with(Type.CREEPER)
            .withFlicker()
            .build());
    fireworkMeta.setPower(1);//花火の打ち上げの高さ

    // 追加した情報で再設定する。
    firework.setFireworkMeta(fireworkMeta);

  }

  /**
   * プレーヤー対して花火ダメージを無効化する時のイベントです。
   *
   * @param event
   */
  @EventHandler
  public void onFireworkDamage(EntityDamageByEntityEvent event) {
    Entity fireworkDamager = event.getDamager();

    // プレーヤーに対して花火によるダメージを無効化する
    if (fireworkDamager instanceof Firework) {
      event.setCancelled(true);
    }
  }

}
