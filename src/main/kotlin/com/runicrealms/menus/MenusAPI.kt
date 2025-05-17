package com.runicrealms.menus

import com.runicrealms.menus.builder.BasicMenuBuilder
import com.runicrealms.menus.builder.MenuBuilder
import com.runicrealms.menus.builder.PagedMenuBuilder
import com.runicrealms.menus.menu.Menu
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

/**
 * Menus Library
 *
 * To create an API instance, create an instance of this class. Each project depending on this
 * library is recommended to have a separate instance of this class. Menus instances are tied to a
 * bukkit plugin, and register their listeners and timers using it.
 *
 * Use BasicMenuBuilder, PagedMenuBuilder, MenuItemBuilder to create menus. To display a menu to a
 * player, use Menu#openPlayer(Player).
 */
interface MenusAPI {

    /** Plugin this API is bound to */
    val plugin: Plugin

    /**
     * Open a menu GUI for one viewer.
     *
     * @param player Menu viewer
     * @param menu Menu to open
     */
    fun openMenu(player: Player, menu: Menu)

    /**
     * Open a menu GUI for one viewer. The MenuBuilder is used immediately to build a new Menu
     * instance.
     *
     * @param player Menu viewer
     * @param menu Menu to open
     */
    fun openMenu(player: Player, menu: MenuBuilder<*>) = openMenu(player, menu.build())

    /**
     * Creates a basic menu builder, for building new menus.
     *
     * @param title Title of the menu
     * @param size Size of the menu
     * @return Basic menu builder
     */
    fun basicMenuBuilder(title: TextComponent, size: Int) = BasicMenuBuilder(this, title, size)

    /**
     * Creates a basic menu builder, for building new menus.
     *
     * @param size Size of the menu
     * @return Basic menu builder
     */
    fun basicMenuBuilder(size: Int) = BasicMenuBuilder(this, size)

    /**
     * Creates a paged menu builder, for building new menus with multiple pages.
     *
     * @param title Title of the menu
     * @param defaultSize Default size of pages in the menu
     * @return Paged menu builder
     */
    fun pagedMenuBuilder(title: TextComponent, defaultSize: Int) =
        PagedMenuBuilder(this, title, defaultSize)
}
