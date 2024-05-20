import org.bukkit.Bukkit

class TickTime : Metric("tick_time", "The time it takes to process a tick in milliseconds") {
    override val getValues: () -> List<MetricValue> = {
        listOf(MetricValue(Bukkit.getServer().averageTickTime, emptyMap()))
    }
}
