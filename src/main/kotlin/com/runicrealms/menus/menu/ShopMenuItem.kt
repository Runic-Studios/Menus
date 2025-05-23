package com.runicrealms.menus.menu

import com.runicrealms.menus.action.MenuAction
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * Special type of menu item that allows the viewer to complete some type of transaction.
 *
 * @param icon MenuItem icon ItemStack
 * @property conditions List of condition to check in order before completing the transaction
 * @property transactions List of transactions to execute on completion
 * @constructor Create a shop menu item
 * @see com.runicrealms.menus.menu.MenuItem
 * @see com.runicrealms.menus.menu.ShopCondition
 * @see com.runicrealms.menus.menu.ShopTransaction
 */
open class ShopMenuItem
constructor(
    icon: ItemStack,
    val conditions: List<ShopCondition>,
    val transactions: List<ShopTransaction>,
) : MenuItem(icon, true) {

    init {
        addAction(
            object : MenuAction {
                override fun execute(event: InventoryClickEvent, menu: Menu) {
                    val player = event.whoClicked as? Player ?: return
                    for (condition in conditions) if (!condition.checkCondition(player, menu))
                        return
                    for (transaction in transactions) {
                        val transactionSucceeded = transaction.applyTransaction(player, menu)
                        if (!transactionSucceeded) {
                            throw RuntimeException(
                                "Transaction failed: ${icon.type}, ${player.uniqueId}, ${menu.title}"
                            )
                        }
                    }
                }
            }
        )
    }
}
