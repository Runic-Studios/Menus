package com.runicrealms.menus.handle

import com.runicrealms.menus.MenusAPI
import com.runicrealms.menus.menu.Menu
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

/**
 * Bukkit event listener that handles click events and close events for Menu library menus.
 *
 * Each Menus API instance will have a different listener registered for it. This listener doesn't
 * execute much logic rather delegates it to the menu to handle.
 *
 * @property menusAPI Encompassing Menus API registering this listener
 * @constructor Create a Menus listener for registering with Bukkit
 */
class MenusListener(val menusAPI: MenusAPI) : Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    fun onInventoryClick(event: InventoryClickEvent) {
        // Cast to custom Menu, if !is Menu then return
        val menu = event.inventory.holder as? Menu ?: return
        // Ensure that this menu is to be handled by the plugin that registered this Menus API.
        // This is significant because multiple Menus APIs can be created by different plugins,
        // and without this check each menu click would be handled several times.
        if (menu.menusAPI.plugin != menusAPI.plugin) return
        // Ensure that the slot clicked is not empty
        if (event.currentItem?.type ?: return == Material.AIR) return

        event.isCancelled = menu.interactionsBlocked

        // Fire the on click event for the menu
        menu.onClick(event)
    }

    @EventHandler(priority = EventPriority.NORMAL)
    fun onInventoryClose(event: InventoryCloseEvent) {
        // Cast to custom Menu, if !is Menu then return
        val menu = event.inventory.holder as? Menu ?: return
        // Ensure that this menu is to be handled by the plugin that registered this Menus API.
        // This is significant because multiple Menus APIs can be created by different plugins,
        // and without this check each menu click would be handled several times.
        if (menu.menusAPI.plugin != menusAPI.plugin) return

        // Fire the on close event for the menu
        menu.onClose(event)
    }
}
