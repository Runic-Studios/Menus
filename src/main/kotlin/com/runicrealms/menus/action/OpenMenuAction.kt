package com.runicrealms.menus.action

import com.runicrealms.menus.builder.MenuBuilder
import com.runicrealms.menus.generator.MenuTemplate
import com.runicrealms.menus.menu.Menu
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * Action for MenuItems that executes a menu supplier lambda and opens it upon execute. Menu can be
 * customized for a player using the InventoryClickEvent parameter in the lambda. Actions are fired
 * on menu item click.
 *
 * @param menuSupplier Lambda supplier for the menu to open
 */
class OpenMenuAction(private val menuSupplier: (InventoryClickEvent) -> Menu) : MenuAction {

    /**
     * Construct this action using an already constructed menu.
     *
     * @param menu A menu that has already been built
     */
    constructor(menu: Menu) : this({ menu })

    /**
     * Construct this action with a menu builder, which will be built on action execute.
     *
     * @param menuBuilder A menu builder which can build a menu
     */
    constructor(menuBuilder: MenuBuilder<*>) : this({ menuBuilder.build() })

    /**
     * Construct this action with a menu generator, which will be generated on action execute.
     *
     * @param menuTemplate A menu generator which can generate a menu
     */
    constructor(
        menuTemplate: com.runicrealms.menus.generator.MenuTemplate<*>
    ) : this({ menuTemplate.openMenu(it.whoClicked as Player) })

    /**
     * Handles this action executing upon the click of a MenuItem icon in a Menu. Any changes made
     * to the InventoryClickEvent parameter will be applied to the bukkit event.
     *
     * This action will open another menu for the viewer. The menu will be constructed using this
     * function's supplier on demand only.
     *
     * @param event The bukkit event that is calling for the execute of this action
     */
    override fun execute(event: InventoryClickEvent, menu: Menu) {
        if (event.whoClicked !is Player) return
        event.whoClicked.openInventory(menuSupplier(event).inventory)
    }
}
