package plugin.micra_firework;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Main extends JavaPlugin implements Listener {

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  /**
   * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。 for-eachを使って、1秒ごとに順番に花火を打ち上げる。
   *
   * @param e イベント
   */
  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
    Player player = e.getPlayer(); // プレイヤー取得
    World world = player.getWorld(); // ワールド取得

    // 花火の色を格納するリスト
    List<Color> fireworkColors = Arrays.asList(
        Color.AQUA, Color.LIME, Color.PURPLE, Color.WHITE, Color.OLIVE
    );

    int delay = 0; // 初期遅延

    // for-eachを使って、1秒ごとに順番に花火を打ち上げる文
    for (Color color : fireworkColors) {
      // BukkitRunnableとは非同期処理や遅延実行、定期実行などを簡単に行うための仕組みです。
      new BukkitRunnable() {
        @Override
        public void run() {
          // 遅延実行したい処理

          // 花火を生成
          Firework firework = world.spawn(player.getLocation(), Firework.class);
          FireworkMeta fireworkMeta = firework.getFireworkMeta();
          fireworkMeta.addEffect(
              FireworkEffect.builder()
                  .withColor(color) // 各色を適用
                  .with(Type.BALL_LARGE)
                  .withFlicker()
                  .build()
          );
          fireworkMeta.setPower(1);
          firework.setFireworkMeta(fireworkMeta);

          // メッセージ表示
          player.sendMessage("花火の色: " + color.toString());
        }
      }.runTaskLater(this, delay); // 指定した時間後に実行

      delay += 20; // 1秒(20tick)ずつ遅らせる
    }
  }
}
