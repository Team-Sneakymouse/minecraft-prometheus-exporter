import org.bukkit.Bukkit

class TPS : Metric("minecraft_tps", "The current server TPS") {
    override val getValues: () -> List<MetricValue> = {
        var tpsWindows =
                mapOf(
                        "1m" to Bukkit.getServer().tps[0],
                        "5m" to Bukkit.getServer().tps[1],
                        "15m" to Bukkit.getServer().tps[2]
                )
        tpsWindows.map { MetricValue(it.value, mapOf("window" to it.key)) }
    }
}
