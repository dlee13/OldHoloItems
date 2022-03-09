package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.Item;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public abstract class Pet extends Item {
    private static final int quantity = 1;
    private static final int durability = 0;
    public static final boolean stackable = false;

    protected ArmorStand stand;
    private final String base64;

    public Pet(String name, Material material, String lore, boolean shiny, int cost, String base64){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
        this.base64 = base64;
    }

    protected void summon(Location loc){
        stand = loc.getWorld().spawn(loc.setDirection(new Vector(0, 0, -1)), ArmorStand.class);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setCanPickupItems(false);
        stand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING);
        stand.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, "hI");
        stand.getEquipment().setHelmet(Utility.playerHeadFromBase64(base64));
    }

    protected void unsummon(){
        stand.remove();
        stand = null;
    }
}
