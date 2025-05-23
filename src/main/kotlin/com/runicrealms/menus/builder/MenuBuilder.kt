package com.runicrealms.menus.builder

import com.runicrealms.menus.animation.MenuAnimation
import com.runicrealms.menus.menu.Menu
import com.runicrealms.menus.menu.MenuItem
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

/**
 * Interface representing a Builder Pattern for creating a Menu. Can be cloned and will create
 * another menu builder with the same items and animation objects.
 *
 * Implementation Specification: Implementations of this interface should have generic type
 * MenuBuilder<MyMenuBuilderClass>. Doing otherwise will result in casting errors.
 *
 * @see com.runicrealms.menus.builder.BasicMenuBuilder
 * @see com.runicrealms.menus.builder.PagedMenuBuilder
 */
interface MenuBuilder<T : MenuBuilder<T>> : Cloneable {

    val size: Int

    /**
     * Set an item with a specific slot and MenuItem.
     *
     * @param slot The slot to insert the icon at
     * @param menuItem The MenuItem to represent this slot
     * @return This builder for use in the builder pattern
     */
    fun setItem(slot: Int, menuItem: MenuItem): T

    /**
     * Set an item with a specific slot and ItemStack. The ItemStack icon is immediately used to
     * generate a MenuItem without any actions.
     *
     * @param slot The slot to insert the icon at
     * @param item The ItemStack icon to represent this slot
     * @return This builder for use in the builder pattern
     */
    fun setItem(slot: Int, item: ItemStack) = setItem(slot, MenuItem(item))

    /**
     * Set an item with a specific slot and MenuItem. The MenuItemBuilder is immediately used to
     * build a MenuItem.
     *
     * @param slot The slot to insert the icon at
     * @param itemBuilder The MenuItemBuilder used to create an icon
     * @return This builder for use in the builder pattern
     */
    fun setItem(slot: Int, itemBuilder: MenuItemBuilder): T = setItem(slot, itemBuilder.build())

    /**
     * Add an item to the first available slot in the menu.
     *
     * @param menuItem The MenuItem to represent this slot
     * @return This builder for use in the builder pattern
     */
    fun addItem(menuItem: MenuItem): T

    /**
     * Add an item to the first available slot in the menu. The ItemStack icon is immediately used
     * to generate a MenuItem without any actions.
     *
     * @param item The ItemStack icon to represent this slot
     * @return This builder for use in the builder pattern
     */
    fun addItem(item: ItemStack): T = addItem(MenuItem(item))

    /**
     * Remove an item from a specific slot. If the slot is not populated, nothing will happen.
     *
     * @param slot The slot to remove the item at
     * @return This builder for use in the builder pattern
     */
    fun removeItem(slot: Int): T

    /**
     * Add a menu animation for this menu.
     *
     * @param menuAnimation The animation to add
     * @return This builder for use in the builder pattern
     */
    fun addAnimation(menuAnimation: MenuAnimation): T

    /**
     * Set whether or not this menu should allow players to take and put items inside it. This
     * setting can also be overridden by every MenuItem added to the menu, but it defaults to the
     * menu's setting, which defaults to true.
     *
     * @param interactionsBlocked Boolean setting the default setting for interactions blocked
     * @return This builder for use in the builder pattern
     */
    fun setInteractionsBlocked(interactionsBlocked: Boolean): T

    /**
     * Sets the lambda consumer which handles the InventoryCloseEvent fired when the menu is closed.
     *
     * @param onClose The consumer which handles the event
     * @return This builder for use in the builder pattern
     */
    fun setOnClose(onClose: (InventoryCloseEvent) -> Unit): T

    /**
     * Sets the lambda consumer which handles the InventoryClickEvent fired when any slot in the
     * menu is clicked. This will fire in addition (before) the individual menu's on click handler.
     *
     * @param onClick
     * @return This builder for use in the builder pattern
     */
    fun setOnClick(onClick: (InventoryClickEvent) -> Unit): T

    /**
     * When generating a border, use this enum to determine which sides of the menu the border will
     * be on.
     */
    enum class MenuBuilderBorder {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
    }

    /**
     * Add a border around the outside of this menu with a select item.
     *
     * @param borderItem MenuItem to use to populate border slots
     * @param borders Vararg of border sides of this menu inventory
     * @return This builder for use in the builder pattern
     */
    fun addBorder(borderItem: MenuItem, vararg borders: MenuBuilderBorder): T {
        for (borderSide in borders) {
            when (borderSide) {
                // TODO needs updating when dropper menus exist
                MenuBuilderBorder.TOP -> for (i in 0 until 9) setItem(i, borderItem)
                MenuBuilderBorder.BOTTOM -> for (i in 0 until 9) setItem(size - i - 1, borderItem)
                MenuBuilderBorder.LEFT -> for (i in 0 until size step 9) setItem(i, borderItem)
                MenuBuilderBorder.RIGHT -> for (i in 0 until size step 9) setItem(i + 8, borderItem)
            }
        }
        // Read implementation specification for this interface, this cast should be fine
        @Suppress("UNCHECKED_CAST")
        return this as T // TODO test
    }

    /**
     * Add a border around the outside of this menu with a select item.
     *
     * @param borderItem ItemStack to use to populate border slots
     * @param borders Vararg of border sides of this menu inventory
     * @return This builder for use in the builder pattern
     */
    fun addBorder(borderItem: ItemStack, vararg borders: MenuBuilderBorder) =
        addBorder(MenuItem(borderItem), *borders)

    /**
     * Add a border around the outside of this menu with a select item
     *
     * @param borderItem MenuItemBuilder to use to populate border slots
     * @return This builder for use in the builder pattern
     */
    fun addBorder(borderItem: MenuItemBuilder, vararg borders: MenuBuilderBorder) =
        addBorder(borderItem.build(), *borders)

    /**
     * Add a border around the outside of this menu with a select item
     *
     * @param borderItem MenuItem to use to populate border slots
     * @return This builder for use in the builder pattern
     */
    fun addBorder(borderItem: MenuItem) = addBorder(borderItem, *MenuBuilderBorder.values())

    /**
     * Add a border around the outside of this menu with a select item
     *
     * @param borderItem ItemStack to use to populate border slots
     * @return This builder for use in the builder pattern
     */
    fun addBorder(borderItem: ItemStack) = addBorder(borderItem, *MenuBuilderBorder.values())

    /**
     * Add a border around the outside of this menu with a select item
     *
     * @param borderItem MenuItemBuilder to use to populate border slots
     * @return This builder for use in the builder pattern
     */
    fun addBorder(borderItem: MenuItemBuilder) =
        addBorder(borderItem.build(), *MenuBuilderBorder.values())

    /**
     * Construct a new menu from the values stored in this builder.
     *
     * @return Generated menu
     */
    fun build(): Menu

    /**
     * Clone this builder into another builder object whose values will be identical in reference to
     * this one.
     *
     * @return Cloned builder
     */
    public override fun clone(): T
}
