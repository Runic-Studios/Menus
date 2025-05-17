package com.runicrealms.menus.menu

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Shop condition that checks if the player has a number of different types of items.
 *
 * @property items Pair of item and amount vararg, checks that player has all
 * @constructor Create Item shop condition
 * @see com.runicrealms.menus.menu.ShopCondition
 * @see com.runicrealms.menus.menu.ShopTransaction
 * @see com.runicrealms.menus.menu.ShopMenuItem
 */
class ItemShopCondition constructor(vararg val items: Pair<ItemStack, Int>) : ShopCondition {

    override fun checkCondition(viewer: Player, menu: Menu): Boolean {
        items@ for (pair in items) {
            var contains = 0
            contents@ for (target in (viewer.inventory.contents ?: continue).filterNotNull()) {
                if (target.isSimilar(pair.first)) {
                    contains += target.amount
                    if (contains >= pair.second) break@contents
                }
            }
            if (contains > 0) return false
        }
        return true
    }
}
