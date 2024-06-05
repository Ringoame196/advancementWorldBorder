package com.github.Ringoame196.managers

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player

class WorldBorderManager(private val config: FileConfiguration) {
    private val world = acquisitionWorld()
    private val worldBorder = world?.worldBorder
    private val size = acquisitionSize()
    private val additionValue = config.getDouble("additionValue")
    private val time = config.getLong("time")
    private fun acquisitionWorld(): World? {
        val worldName = config.getString("worldName") ?: "world"
        return Bukkit.getWorld(worldName)
    }
    private fun acquisitionSize(): Double {
        return worldBorder?.size ?: 0.0
    }
    fun spreadSize() {
        val newSize = size + additionValue
        worldBorder?.setSize(newSize, time)
    }
    fun sendSpreadMessage(player: Player) {
        Bukkit.broadcastMessage("${ChatColor.YELLOW}[通知] ${player.displayName}が実績達成！\nワールドボーダーが拡大した。 ($size -> ${size + additionValue})")

        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            playSpreadSound(onlinePlayer)
        }
    }
    private fun playSpreadSound(player: Player) {
        val sound = Sound.ENTITY_PLAYER_LEVELUP
        player.playSound(player, sound, 1f, 1f)
    }
}
