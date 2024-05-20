abstract class Metric(
        val name: String,
        val help: String,
) {
    abstract val getValues: () -> List<MetricValue>
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("# HELP $name $help\n")
        builder.append("# TYPE $name gauge\n")
        getValues().forEach {
            builder.append("$name")
            if (it.labels.isNotEmpty()) {
                builder.append("{")
                builder.append(it.labels.entries.joinToString(",") { "${it.key}=\"${it.value}\"" })
                builder.append("}")
            }
            builder.append(" ${it.value}\n")
        }
        return builder.toString()
    }
}

data class MetricValue(val value: Double, val labels: Map<String, String>)
