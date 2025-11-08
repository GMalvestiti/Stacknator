package net.riser876.stacknator.core;

import net.minecraft.item.Item;

public class StackItem {

    private final String key;
    private final boolean isDamageable;
    private final int defaultStackSize;
    private int stackSize;

    public StackItem(Item item) {
        this.key = item.toString();
        this.stackSize = item.getMaxCount();
        this.defaultStackSize = item.getMaxCount();
        this.isDamageable = item.getDefaultStack().isDamageable();
    }

    public String getKey() {
        return this.key;
    }

    public int getDefaultStackSize() {
        return this.defaultStackSize;
    }

    public int getStackSize() {
        return this.stackSize;
    }

    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }

    public boolean isDamageable() {
        return this.isDamageable;
    }
}
