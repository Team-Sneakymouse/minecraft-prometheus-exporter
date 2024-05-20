package com.danidipp.prometheusexporter

import WebServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.plugin.java.JavaPlugin

class PrometheusExporter : JavaPlugin() {
    lateinit var webServer: WebServer

    override fun onLoad() {
        instance = this
    }
    override fun onEnable() {
        saveDefaultConfig()

        webServer = WebServer(this, config.getString("host")!!, config.getInt("port"))
    }

    companion object {
        const val IDENTIFIER = "prometheus-exporter"
        const val AUTHORS = "Team Sneakymouse"
        const val VERSION = "1.0"
        private lateinit var instance: PrometheusExporter
            private set

        fun getInstance(): PrometheusExporter {
            return instance
        }

        fun getWebServer(): WebServer {
            return instance.webServer
        }
    }
}

class PluginListener(val instance: PrometheusExporter) : Listener {
    @EventHandler
    fun onPluginDisable(event: PluginDisableEvent) {
        if (event.plugin == instance) {
            instance.webServer.stop()
        }
    }
}
