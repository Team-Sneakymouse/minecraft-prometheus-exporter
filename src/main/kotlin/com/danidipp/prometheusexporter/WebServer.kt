import com.danidipp.prometheusexporter.PrometheusExporter
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

class WebServer(plugin: PrometheusExporter, private val host: String, private val port: Int) {
    private val plugin = plugin
    private val server: HttpServer

    init {
        server = HttpServer.create(InetSocketAddress(host, port), 0)
        server.createContext("/metrics") { httpExchange ->
            val metrics = plugin.webServer.getMetrics()
            val response = metrics.joinToString("\n") { it.toString() }
            PrometheusExporter.getInstance().logger.info("Metrics requested")
            httpExchange.sendResponseHeaders(200, response.length.toLong())
            val os = httpExchange.responseBody
            os.write(response.toByteArray())
            os.close()
        }
        server.executor = null
        server.start()
        plugin.logger.info("Web server started on $host:$port")
    }

    fun getMetrics(): Array<Metric> {
        val metrics = mutableListOf<Metric>()
        metrics.add(PlayerCount())
        metrics.add(TPS())
        metrics.add(TickTime())
        metrics.add(WorldSize())
        PrometheusExporter.getInstance().logger.info("Collected ${metrics.size} metrics")
        return metrics.toTypedArray()
    }

    fun stop() {
        server.stop(0)
        plugin.logger.info("Web server stopped")
    }
}
