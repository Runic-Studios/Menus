package com.runicrealms.menus.menu

import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * Shop condition that checks if the player has items totalling to a number of a specific material.
 *
 * @property material Material to check
 * @property amount Amount of items required
 * @constructor Create Material shop condition
 * @see com.runicrealms.menus.menu.ShopCondition
 * @see com.runicrealms.menus.menu.ShopTransaction
 * @see com.runicrealms.menus.menu.ShopMenuItem
 */
class MaterialShopCondition(val material: Material, val amount: Int) : ShopCondition {

    override fun checkCondition(viewer: Player, menu: Menu) =
        viewer.inventory.contains(material, amount)
}
