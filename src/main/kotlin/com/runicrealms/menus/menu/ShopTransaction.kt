package com.runicrealms.menus.menu

import org.bukkit.entity.Player

/**
 * Transaction that is executed for purchases in a shop menu.
 *
 * @see com.runicrealms.menus.menu.GrantItemsShopTransaction
 * @see com.runicrealms.menus.menu.TakeItemsShopTransaction
 * @see com.runicrealms.menus.menu.TakeMaterialShopTransaction
 * @see com.runicrealms.menus.menu.MaterialShopCondition
 * @see com.runicrealms.menus.menu.ItemShopCondition
 */
interface ShopTransaction {

    /**
     * Execute this transaction.
     *
     * @param viewer Player clicking on the menu item
     * @param menu Shop menu
     * @return Success
     */
    fun applyTransaction(viewer: Player, menu: Menu): Boolean
}
