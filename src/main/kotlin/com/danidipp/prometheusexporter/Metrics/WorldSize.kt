import org.bukkit.Bukkit
import org.bukkit.World

class WorldSize : Metric("minecraft_world_size", "The size of the world folder in bytes") {
    override val getValues: () -> List<MetricValue> = {
        val worlds = Bukkit.getServer().worlds
        worlds.map { MetricValue(getWorldSize(it), mapOf("world" to it.name)) }
    }

    private fun getWorldSize(world: World): Double {
        return world.worldFolder.getTotalSpace().toDouble()
    }
}
