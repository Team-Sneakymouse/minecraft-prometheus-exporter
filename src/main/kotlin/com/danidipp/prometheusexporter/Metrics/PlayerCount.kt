import org.bukkit.Bukkit

class PlayerCount() : Metric("minecraft_player_count", "The number of players currently online") {
    override val getValues: () -> List<MetricValue> = {
        listOf(MetricValue(Bukkit.getServer().onlinePlayers.size.toDouble(), emptyMap()))
    }
}
