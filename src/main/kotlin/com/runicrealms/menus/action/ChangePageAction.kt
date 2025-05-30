package com.runicrealms.menus.action

import com.runicrealms.menus.menu.Menu
import com.runicrealms.menus.menu.PagedMenu
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * Action for MenuItems that closes the menu the player is viewing upon execute. Actions are fired
 * on menu item click.
 *
 * @property type The type of page change that should be executed for this action
 * @constructor Construct a ChangePageAction with a given Type
 * @see com.runicrealms.menus.action.MenuAction
 * @see com.runicrealms.menus.menu.MenuItem
 * @see com.runicrealms.menus.menu.PagedMenu
 */
class ChangePageAction(val type: Type) : MenuAction {

    companion object {
        /** Constant ChangePageAction for changing to the next page */
        @JvmStatic val NEXT_PAGE = ChangePageAction(Type.NEXT_PAGE)
        /** Constant ChangePageAction for changing to the previous page */
        @JvmStatic val PREVIOUS_PAGE = ChangePageAction(Type.PREVIOUS_PAGE)
        /** Constant ChangePageAction for changing to the first page */
        @JvmStatic val FIRST_PAGE = ChangePageAction(Type.FIRST_PAGE)
        /** Constant ChangePageAction for changing to the last page */
        @JvmStatic val LAST_PAGE = ChangePageAction(Type.LAST_PAGE)
    }

    /**
     * Handles this action executing upon the click of a MenuItem icon in a Menu. Any changes made
     * to the InventoryClickEvent parameter will be applied to the bukkit event.
     *
     * This changes the player's open PagedMenu's page, depending on the provided
     * ChangePaceAction.Type for this object.
     *
     * @param event The bukkit event that is calling for the execute of this action
     * @param menu The menu that is executing this action
     */
    override fun execute(event: InventoryClickEvent, menu: Menu) {
        // Player object of who clicked
        val player = event.whoClicked as? Player ?: return
        // Cast to custom Menu
        val pagedMenu = event.inventory.holder as? PagedMenu ?: return

        // Open menu depending on action type
        when (type) {
            Type.NEXT_PAGE -> pagedMenu.openNextPage(player)
            Type.PREVIOUS_PAGE -> pagedMenu.openPreviousPage(player)
            Type.FIRST_PAGE -> pagedMenu.openMenu(player)
            Type.LAST_PAGE -> pagedMenu.openMenuPage(player, pagedMenu.size() - 1)
        }
    }

    /** The type of page change to execute upon ChangePageAction fire. */
    enum class Type {

        /** Turn to the next page in the menu, or current page if this is the last page */
        NEXT_PAGE,
        /** Turn to the previous page in the menu, or current page if this is the last page */
        PREVIOUS_PAGE,
        /** Turn to the first page in the menu */
        FIRST_PAGE,
        /** Turn to the last page in the menu */
        LAST_PAGE,
    }
}
