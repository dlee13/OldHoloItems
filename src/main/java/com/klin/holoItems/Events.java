package com.klin.holoItems;

import com.klin.holoItems.abstractClasses.Crate;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.abstractClasses.Pack;
import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.collections.gen3.pekoraCollection.items.DoubleUp;
import com.klin.holoItems.collections.gen5.botanCollection.items.ScopedRifle;
import com.klin.holoItems.collections.gen5.botanCollection.items.Sentry;
import com.klin.holoItems.collections.misc.hiddenCollection.items.GalleryFrame;
import com.klin.holoItems.interfaces.*;
import com.klin.holoItems.interfaces.customMobs.Retaliable;
import com.klin.holoItems.interfaces.customMobs.Targetable;
import com.klin.holoItems.utility.ReflectionUtils;
import com.klin.holoItems.utility.Utility;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Levelled;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Events implements Listener {
    public static Set<Activatable> activatables = new HashSet<>();

    private static final Set<Material> deactive = Stream.of(
        Material.JUKEBOX,
        Material.CAMPFIRE,
        Material.SOUL_CAMPFIRE
    ).collect(Collectors.toCollection(HashSet::new));

    private static final Set<String> ingredients = new HashSet<>() {{
        for (Character key : Collections.findCollection('0').collection.keySet())
            add("0" + key);
        add(DoubleUp.id);
        add(Sentry.id);
        add(ScopedRifle.id);
    }};

    private static final Set<InventoryType> prohibitedInv = Stream.of(
        InventoryType.BEACON,
        InventoryType.BREWING,
        InventoryType.CARTOGRAPHY,
        InventoryType.LECTERN,
        InventoryType.LOOM,
        InventoryType.MERCHANT,
        InventoryType.SMOKER,
        InventoryType.STONECUTTER
    ).collect(Collectors.toCollection(HashSet::new));

    private static final Map<Integer, Enchantment[]> multiplier = new HashMap<>() {{
        put(1, new Enchantment[]{
                Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DAMAGE_ALL,
                Enchantment.DIG_SPEED, Enchantment.ARROW_DAMAGE,
                Enchantment.LOYALTY, Enchantment.PIERCING
        });
        put(2, new Enchantment[]{
                Enchantment.PROTECTION_FIRE, Enchantment.PROTECTION_FALL,
                Enchantment.PROTECTION_PROJECTILE, Enchantment.DAMAGE_UNDEAD,
                Enchantment.DAMAGE_ARTHROPODS, Enchantment.KNOCKBACK,
                Enchantment.DURABILITY, Enchantment.QUICK_CHARGE
        });
        put(4, new Enchantment[]{
                Enchantment.PROTECTION_EXPLOSIONS, Enchantment.OXYGEN,
                Enchantment.DEPTH_STRIDER, Enchantment.WATER_WORKER,
                Enchantment.FIRE_ASPECT, Enchantment.LOOT_BONUS_MOBS,
                Enchantment.LOOT_BONUS_BLOCKS, Enchantment.ARROW_KNOCKBACK,
                Enchantment.ARROW_FIRE, Enchantment.LUCK, Enchantment.LURE,
                Enchantment.FROST_WALKER, Enchantment.MENDING, Enchantment.IMPALING,
                Enchantment.RIPTIDE, Enchantment.MULTISHOT, Enchantment.SWEEPING_EDGE
        });
        put(8, new Enchantment[]{
                Enchantment.THORNS, Enchantment.SILK_TOUCH, Enchantment.ARROW_INFINITE,
                Enchantment.BINDING_CURSE, Enchantment.VANISHING_CURSE,
                Enchantment.CHANNELING, Enchantment.SOUL_SPEED
        });
    }};

    @EventHandler
    public static void clickItem(InventoryClickEvent event){
        if(event.isCancelled())
            return;

        InventoryView invView = event.getView();
        Inventory inv = invView.getTopInventory();
        InventoryType invType = inv.getType();
        Player player = (Player) event.getWhoClicked();

        int slot = event.getRawSlot();
        if(slot==2 && invType==InventoryType.ANVIL &&
                player.getGameMode()!=GameMode.CREATIVE){
            ItemStack itemA = inv.getItem(0);
            ItemStack itemB = inv.getItem(1);
            if(itemA==null || itemB==null)
                return;
            String typeA = itemA.getItemMeta().
                    getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            String typeB = itemB.getItemMeta().
                    getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            ItemStack curr = inv.getItem(2);
            if(typeA==null && typeB==null)
                return;
            //formerly if(typeA==null || !typeA.equals(typeB))
            //new if(typeA!=null && !typeA.equals(typeB))
            else if(typeA==null || !typeA.equals(typeB)){
                Item genericB = Collections.findItem(typeB);
                if(genericB instanceof Enchant){
                    if(curr!=null && curr.getItemMeta()!=null &&
                            curr.getItemMeta().getPersistentDataContainer().
                                    get(Utility.enchant, PersistentDataType.STRING)!=null)
                        return;
                    Enchant enchant = (Enchant) genericB;
                    if((enchant.acceptedIds==null ||
                            !enchant.acceptedIds.contains(typeA)) &&
                            (enchant.acceptedTypes==null ||
                            !enchant.acceptedTypes.contains(itemA.getType()))){
                        ItemStack no = new ItemStack(Material.BARRIER);
                        ItemMeta noMeta = no.getItemMeta();
                        noMeta.setDisplayName(" ");
                        no.setItemMeta(noMeta);
                        inv.setItem(2, no);
                        event.setCancelled(true);
                        return;
                    }
                    ((AnvilInventory) inv).setRepairCost(
                            Math.min(39, enchant.expCost+ReflectionUtils.getRepairCost(itemA)));
                    ItemStack combined = Utility.addEnchant(itemA.clone(), enchant);
                    if(typeA==null){
                        ItemMeta meta = combined.getItemMeta();
                        if(meta instanceof Damageable) {
                            meta.setUnbreakable(true);
                            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        }
                        List<String> addDurability = meta.getLore();
                        if (addDurability == null)
                            addDurability = new ArrayList<>();
                        if(!addDurability.get(addDurability.size()-1).startsWith("§fDurability: ")) {
                            int maxDurability = itemA.getType().getMaxDurability();
                            int currDurability = maxDurability - ((Damageable) meta).getDamage();
                            addDurability.add("§fDurability: " + currDurability + "/" + maxDurability);
                            meta.setLore(addDurability);
                            combined.setItemMeta(meta);
                        }
                    }
                    inv.setItem(2, combined);
                    event.setCancelled(true);
                    return;
                }

                if(itemB.getType()==Material.ENCHANTED_BOOK &&
                        itemB.getItemMeta() instanceof EnchantmentStorageMeta){
                    Item item = Collections.findItem(typeA);
                    if(item==null) {
                        event.setCancelled(true);
                        return;
                    }
                    Map<Enchantment, Integer> enchantments = ((EnchantmentStorageMeta)
                            itemB.getItemMeta()).getStoredEnchants();
                    ItemStack combined = itemA.clone();
                    int levelCost = 0;
                    for(Enchantment enchant : enchantments.keySet()) {
                        if(item.accepted==null)
                            break;
                        if (item.accepted.contains(enchant)){
                            int levelA = combined.getItemMeta().getEnchantLevel(enchant);
                            int levelB = enchantments.get(enchant);
                            if(levelA==levelB && levelB<enchant.getMaxLevel())
                                levelB++;
                            else if(levelA>levelB)
                                levelB = 0;
                            combined.addUnsafeEnchantment(enchant, Math.max(levelA, levelB));
                            levelCost += levelB*((findMultiplier(enchant)+1)/2);
                        }
                    }
                    if(levelCost>0) {
                        ItemMeta combinedMeta = combined.getItemMeta();
                        String renameText = ((AnvilInventory) inv).getRenameText();
                        String originalText = combinedMeta.getDisplayName().substring(2);
                        if (renameText != null && !renameText.trim().isEmpty() &&
                                !renameText.equals(originalText)) {
                            combinedMeta.setDisplayName("§6" + renameText);
                            levelCost++;
                        }
                        combined.setItemMeta(combinedMeta);

                        //if generated item matches item already in slot, then go to
                        //!=null check where it'll pass and item will be sent to cursor by game
                        if(curr==null ||
                                !combined.getEnchantments().equals(curr.getEnchantments())) {
                            ((AnvilInventory) inv).setRepairCost(levelCost+
                                    Math.max(ReflectionUtils.getRepairCost(itemA),
                                            ReflectionUtils.getRepairCost(itemB)));
                            inv.setItem(2, combined);
                            event.setCancelled(true);
                        }
                        return;
                    }
                    else{
                        ItemStack no = new ItemStack(Material.BARRIER);
                        ItemMeta noMeta = no.getItemMeta();
                        noMeta.setDisplayName(" ");
                        no.setItemMeta(noMeta);
                        inv.setItem(2, no);
                        event.setCancelled(true);
                        return;
                    }
                }
                else{
                    ItemStack no = new ItemStack(Material.BARRIER);
                    ItemMeta noMeta = no.getItemMeta();
                    noMeta.setDisplayName(" ");
                    no.setItemMeta(noMeta);
                    inv.setItem(2, no);
                    event.setCancelled(true);
                    return;
                }
            }

            //it will ALWAYS be null if it is itself, 2 Unbreakable items
            //unless it has been set prior
            if(curr!=null){
                if(curr.getType()==Material.BARRIER)
                    event.setCancelled(true);
                return;
            }

            Item item = Collections.findItem(typeA);
            if(item==null){
                event.setCancelled(true);
                return;
            }
            else if(item.durability==0){
                ItemStack no = new ItemStack(Material.BARRIER);
                ItemMeta noMeta = no.getItemMeta();
                noMeta.setDisplayName(" ");
                no.setItemMeta(noMeta);
                inv.setItem(2, no);
                event.setCancelled(true);
                return;
            }

            int levelCost = 0;
            ItemStack combined = itemA.clone();
            int[] durabilityA = Utility.getDurability(itemA.getItemMeta().getLore());
            if(durabilityA[0]!=durabilityA[1] && itemB.getItemMeta().getLore()!=null) {
                int[] durabilityB = Utility.getDurability(itemB.getItemMeta().getLore());
                Utility.addDurability(combined,
                        durabilityB[0] + (int) (0.12 * durabilityB[1]), player);
                levelCost += 2;
            }

            Map<Enchantment, Integer> enchantments = new HashMap<>(itemB.getEnchantments());
            for(Enchantment enchant : enchantments.keySet()) {
                if (item.accepted.contains(enchant)){
                    int levelA = combined.getItemMeta().getEnchantLevel(enchant);
                    int levelB = enchantments.get(enchant);
                    if(levelA==levelB && levelB<enchant.getMaxLevel())
                        levelB++;
                    else if(levelA>levelB)
                        levelB = 0;
                    combined.addUnsafeEnchantment(enchant, Math.max(levelA, levelB));
                    levelCost += levelB*(findMultiplier(enchant)+1)/2;
                }
            }
            ItemMeta meta = combined.getItemMeta();
            String renameText = ((AnvilInventory) inv).getRenameText();
            String originalText = meta.getDisplayName().substring(2);
            if(renameText!=null && !renameText.trim().isEmpty() &&
                    !renameText.equals(originalText)){
                meta.setDisplayName("§6"+renameText);
                levelCost++;
            }
            ((AnvilInventory) inv).setRepairCost(levelCost+
                    Math.max(ReflectionUtils.getRepairCost(itemA), ReflectionUtils.getRepairCost(itemB)));

            combined.setItemMeta(meta);
            inv.setItem(2, combined);
            event.setCancelled(true);
            return;
        }

        boolean current = true;
        for (ItemStack item : new ItemStack[]{event.getCurrentItem(), event.getCursor()}) {
            if (item == null || item.getItemMeta() == null) {
                current = false;
                continue;
            }
            ItemMeta meta = item.getItemMeta();
            String id = meta.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if (id == null) {
                current = false;
                continue;
            }
            if(current && Utility.onCooldown(item)) {
                meta.getPersistentDataContainer().remove(Utility.cooldown);
                item.setItemMeta(meta);
                return;
            }
            if (Collections.disabled.contains(id)) {
                current = false;
                continue;
            }
            Item generic = Collections.findItem(id);
            if (generic instanceof Clickable)
                ((Clickable) generic).ability(event, current);
            current = false;
        }

        if(inv.getHolder()==null){
            if(invView.getTitle().equals("Price")){
                (new GalleryFrame()).ability(event);
                return;
            }

            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType()!=Material.AIR && item.getItemMeta()!=null) {
                String id = item.getItemMeta().
                        getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
                if (id != null) {
                    Item generic = Collections.findItem(id);
                    if (generic instanceof Pack && (((Pack) generic).display ||
                            item.equals(event.getCurrentItem()))) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
        if(slot<inv.getSize())
            return;

        ItemStack item = event.getCurrentItem();
        if (item==null || item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if (id == null)
            return;

        //rewrite to account for new smeltable items
        if(prohibitedInv.contains(invType)) {
            event.setCancelled(true);
            return;
        }

        if(invType==InventoryType.GRINDSTONE && Collections.findItem(id).accepted==null)
            event.setCancelled(true);
    }

    @EventHandler
    public static void craftItem(CraftItemEvent event){
        if(event.isCancelled())
            return;
        CraftingInventory inv = event.getInventory();
        for(ItemStack item : event.getInventory().getMatrix()){
            if(item!=null && item.getItemMeta()!=null){
                String id = item.getItemMeta().getPersistentDataContainer().
                        get(Utility.key, PersistentDataType.STRING);
                if(id!=null) {
                    if(ingredients.contains(id))
                        continue;
                    event.setCancelled(true);
                    return;
                }
            }
        }
        Player player = (Player) event.getWhoClicked();
        ItemStack curr = event.getCurrentItem();
        if (curr == null || curr.getItemMeta() == null)
            return;
        ItemMeta currMeta = curr.getItemMeta();
        String id = currMeta.
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if (id == null)
            return;
        Collection collection = Collections.findCollection(id.charAt(0));
        Item item = collection.collection.get(id.charAt(1));

        if(player.getGameMode()!=GameMode.CREATIVE) {
            int progress = Utility.add(collection.getStat(player));
            if (item.cost > progress) {
                event.setCancelled(true);
                player.sendMessage("Progress to unlocking " +
                        Utility.formatName(item.name) + ": " + progress + "/" + item.cost);
            } else if (!item.stackable && item.item.getMaxStackSize() > 1) {
                World world = player.getWorld();
                Location loc = player.getLocation();
                boolean excess = false;
                for (ItemStack ingredient : inv.getMatrix()) {
                    if (ingredient == null)
                        continue;
                    if (ingredient.getAmount() > 1) {
                        excess = true;
                        ItemStack refund = ingredient.clone();
                        refund.setAmount(ingredient.getAmount() - 1);
                        ingredient.setAmount(1);
                        world.dropItemNaturally(loc, refund);
                    }
                }
                if (excess) {
                    event.setCancelled(true);
                    return;
                }

                currMeta.getPersistentDataContainer().set(Utility.stack, PersistentDataType.DOUBLE, Math.random());
                curr.setItemMeta(currMeta);
            }
        }

        if(Collections.disabled.contains(id)) {
            player.sendMessage("§cThis item has been disabled");
            return;
        }
        if(item instanceof Craftable)
            ((Craftable) item).ability(event);
    }

    @EventHandler
    public static void enchantItem(EnchantItemEvent event){
        if(event.isCancelled())
            return;
        ItemStack item = event.getItem();
        if(item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id==null)
            return;

        Set<Enchantment> accepted = Collections.findItem(id).accepted;
        if(accepted==null) {
            event.setCancelled(true);
            return;
        }
        int size = accepted.size();
        Enchantment[] acceptedArray = accepted.toArray(new Enchantment[size]);
        Map<Enchantment, Integer> enchantments = event.getEnchantsToAdd();
        Iterator<Enchantment> keys = new LinkedHashSet<>(enchantments.keySet()).iterator();
        boolean first = true;
        int initialSize = enchantments.size();

        Set<Enchantment> toRemove = new HashSet<>();
        for(int i=0; i<initialSize; i++){
            Enchantment next = keys.next();
            if(first){
                first = false;
                if(accepted.contains(next))
                    continue;
            }
            Enchantment randomEnchant;
            int failsafe = 0;
            do{
                randomEnchant = acceptedArray[(int) (Math.random()*size)];
                failsafe++;
            } while (item.containsEnchantment(randomEnchant) && failsafe<=5);
            item.addUnsafeEnchantment(randomEnchant,
                    Math.min(randomEnchant.getMaxLevel(), enchantments.get(next)));
            if(!randomEnchant.equals(next))
                toRemove.add(next);
        }
        new BukkitRunnable() {
            public void run(){
                for(Enchantment enchant : toRemove)
                    item.removeEnchantment(enchant);
            }
        }.runTaskLater(HoloItems.getInstance(), 1);
    }

    @EventHandler
    public static void mendItem(PlayerExpChangeEvent event){
        //no isCancelled()
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        for(ItemStack item : new ItemStack[]{
                inv.getItemInMainHand(), inv.getItemInOffHand()}) {
            if (item.getType()==Material.AIR || item.getItemMeta() == null ||
                    !item.getItemMeta().hasEnchant(Enchantment.MENDING))
                continue;
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            String id = container.get(Utility.key, PersistentDataType.STRING);
            String enchant = container.get(Utility.enchant, PersistentDataType.STRING);
            if (id==null && enchant==null)
                continue;
            event.setAmount(Utility.addDurability(item, event.getAmount(), player));
        }
    }

    @EventHandler
    public static void activateAbility(CreatureSpawnEvent event){
        if(event.isCancelled() || activatables.isEmpty())
            return;
        for(Activatable activatable : activatables)
            activatable.ability(event);
    }

    @EventHandler
    public static void afflictAbility(EntityDamageByEntityEvent event){
        if(event.isCancelled() || !(event.getDamager() instanceof LivingEntity))
            return;
        LivingEntity entity = (LivingEntity) event.getDamager();
        String modifiers = entity.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if(modifiers!=null){
            for(String modifier : modifiers.split("-")){
                Retaliable retaliable = Utility.findItem(modifier, Retaliable.class);
                if(retaliable!=null)
                    retaliable.ability(event, entity);
            }
        }

        EntityEquipment equipment = entity.getEquipment();
        if(equipment==null)
            return;
        ItemStack item = equipment.getItemInMainHand();
        if(item.getType()==Material.AIR || item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id!=null) {
            Utility.addDurability(item, -1, entity);
            if (Collections.disabled.contains(id))
                return;
            Item generic = Collections.findItem(id);
            if (generic instanceof Afflictable)
                ((Afflictable) generic).ability(event, item);
        }
    }

    @EventHandler
    public void collectAbility(EntityDropItemEvent event){
        if(event.isCancelled())
            return;
        Entity entity = event.getEntity();
        Collectable collectable = Utility.findItem(entity, Collectable.class);
        if(collectable!=null)
            collectable.ability(event, entity);
    }

    @EventHandler
    public static void consumeAbility(PlayerItemConsumeEvent event){
        if(event.isCancelled())
            return;
        ItemStack item = event.getItem();
        if (item.getType()==Material.AIR || item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if (id == null)
            return;
        if(Collections.disabled.contains(id)) {
            event.getPlayer().sendMessage("§cThis item has been disabled");
            return;
        }
        Item generic = Collections.findItem(id);
        if(generic instanceof Consumable)
            ((Consumable) generic).ability(event, item);
        else
            event.setCancelled(true);
    }

    @EventHandler
    public static void defendAbility(EntityDamageByEntityEvent event){
        if(event.isCancelled() || !(event.getEntity() instanceof LivingEntity))
            return;
        LivingEntity entity = (LivingEntity) event.getEntity();
        EntityEquipment equipment = entity.getEquipment();
        if(equipment==null)
            return;
        for(ItemStack item : equipment.getArmorContents()) {
            if (item == null || item.getItemMeta() == null)
                continue;
            String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if (id!=null) {
                //f0: reading glasses easter egg
                if(id.equals("f0") && item.containsEnchantment(Enchantment.BINDING_CURSE) || Collections.disabled.contains(id))
                    continue;
                Item generic = Collections.findItem(id);
                if (generic instanceof Wearable)
                    ((Wearable) generic).ability(event, Utility.addDurability(item, -1, entity) == -1);
            }
        }

        if(!(entity instanceof Player))
            return;
        Player player = (Player) entity;
        if(!player.isBlocking())
            return;
        PlayerInventory inv = player.getInventory();
        for(ItemStack item : new ItemStack[]{inv.getItemInMainHand(), inv.getItemInOffHand()}) {
            Defensible defensible = Utility.findItem(item, Defensible.class, player);
            if(defensible!=null) {
                defensible.ability(event);
                return;
            }
        }
    }

    @EventHandler
    public static void dispenseAbility(BlockDispenseEvent event){
        if(event.isCancelled())
            return;
        Block block = event.getBlock();
        BlockState state = block.getState();
        if(!(state instanceof Dispenser))
            return;

        Wiring wiring = Utility.findItem(block, Wiring.class);
        if(wiring!=null)
            wiring.ability(event);

        ItemStack item = event.getItem();
        if(item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id==null){
            Material type = item.getType();
            if(Utility.buckets.containsKey(type)){
                Block dispenser = event.getBlock();
                Block relative = dispenser.getRelative(((org.bukkit.block.data.type.Dispenser) dispenser.getBlockData()).getFacing());
                Material cauldron = relative.getType();
                if(Utility.buckets.containsValue(cauldron) && type!=Material.BOWL) {
                    if(relative.getBlockData() instanceof Levelled && ((Levelled) relative.getBlockData()).getLevel()!=3 ||
                            (type==Material.BUCKET) == (cauldron==Material.CAULDRON))
                        return;
                    relative.setType(Utility.buckets.get(type));
                    Material bucket;
                    if (relative.getBlockData() instanceof Levelled) {
                        bucket = Material.BUCKET;
                        Levelled levelled = (Levelled) relative.getBlockData();
                        levelled.setLevel(3);
                        relative.setBlockData(levelled);
                    } else {
                        String filling = cauldron.toString();
                        bucket = Material.getMaterial(filling.substring(0, filling.indexOf("CAULDRON")) + "BUCKET");
                    }
                    event.setCancelled(true);
                    new BukkitRunnable() {
                        public void run() {
                            Inventory inv = ((Dispenser) state).getInventory();
                            inv.removeItem(item);
                            inv.addItem(new ItemStack(bucket));
                        }
                    }.runTask(HoloItems.getInstance());
                    return;
                }

                Location loc = relative.getLocation().add(0.5, 0.5, 0.5);
                java.util.Collection<Entity> cows = loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5, entity -> (entity instanceof Cow));
                if(cows.isEmpty())
                    return;
                int mushroomCows = 0;
                for(Entity cow : cows){
                    if(cow instanceof MushroomCow)
                        mushroomCows++;
                }
                if(type==Material.BUCKET && cows.size()>mushroomCows)
                    event.setItem(new ItemStack(Material.MILK_BUCKET));
                else if(type==Material.BOWL && mushroomCows!=0)
                    event.setItem(new ItemStack(Material.MUSHROOM_STEW));
                else
                    return;
                new BukkitRunnable(){
                    public void run(){
                        Inventory inv = ((Dispenser) state).getInventory();
                        inv.removeItem(item);
                    }
                }.runTask(HoloItems.getInstance());
            }
            return;
        }

        if(Collections.disabled.contains(id))
            return;
        Item generic = Collections.findItem(id);
        if(generic instanceof Dispensable)
            ((Dispensable) generic).ability(event);
    }

    @EventHandler
    public static void dropAbility(PlayerDropItemEvent event){
        if(event.isCancelled())
            return;
        Player player = event.getPlayer();
        org.bukkit.entity.Item item = event.getItemDrop();
        Dropable dropable = Utility.findItem(item, Dropable.class, player);
        if(dropable!=null)
            dropable.ability(event);
    }

    @EventHandler
    public static void extractAbility(BlockBreakEvent event){
        if(event.isCancelled())
            return;

        Block block = event.getBlock();
        Player player = event.getPlayer();
        Crate crate = Utility.findItem(block, Crate.class, player);
        if(crate!=null)
            crate.ability(event);

        PlayerInventory inv = player.getInventory();
        ItemStack[] items = new ItemStack[]{inv.getItemInMainHand(), inv.getItemInOffHand()};
        for(int i=0; i<items.length; i++) {
            ItemStack item = items[i];
            if(item.getType()==Material.AIR || item.getItemMeta()==null)
                continue;
            String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            String enchantments = item.getItemMeta().getPersistentDataContainer().get(Utility.enchant, PersistentDataType.STRING);
            if(id!=null || enchantments!=null)
                Utility.addDurability(item, -1, event.getPlayer());
            else
                continue;
            Extractable extractable = Utility.findItem(id, Extractable.class, player);
            if(extractable !=null && i!=0 == extractable instanceof Holdable)
                extractable.ability(event);
            if(i==0) {
                if(enchantments==null)
                    continue;
                for (String enchantment : enchantments.split(" ")) {
                    Item generic = Collections.findItem(enchantment);
                    if (generic instanceof Extractable)
                        ((Extractable) generic).ability(event);
                }
            }
        }
    }

    @EventHandler
    public static void entityAbility(PlayerInteractEntityEvent event){
        //delayed isCancelled()
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
        Reactable reactable = Utility.findItem(entity, Reactable.class, player);
        if(reactable!=null)
            reactable.ability(event);

        if(event.isCancelled())
            return;
        ItemStack item;
        if(event.getHand()==EquipmentSlot.HAND)
            item = player.getInventory().getItemInMainHand();
        else
            item = player.getInventory().getItemInOffHand();
        Responsible responsible = Utility.findItem(item, Responsible.class, player);
        if(responsible!=null && responsible.ability(event, item))
            Utility.addDurability(item, -1, player);
    }

    @EventHandler
    public static void flauntAbility(AsyncPlayerChatEvent event){
        if(event.isCancelled() || !event.isAsynchronous())
            return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getHelmet();
        Flauntable flauntable = Utility.findItem(item, Flauntable.class, player);
        if(flauntable!=null)
            flauntable.ability(event);
    }

    @EventHandler
    public static void hangingAbility(HangingPlaceEvent event){
        if(event.isCancelled())
            return;
        Player player = event.getPlayer();
        if(player==null)
            return;
        ItemStack item = player.getInventory().getItemInMainHand();
        Hangable hangable = Utility.findItem(item, Hangable.class, player);
        if(hangable!=null)
            hangable.ability(event);
    }

    @EventHandler
    public static void hitAbility(ProjectileHitEvent event){
        //no isCancelled()
        Projectile projectile = event.getEntity();
        if(!(projectile instanceof Arrow) || projectile.doesBounce())
            return;
        Hitable hitable = Utility.findItem(projectile, Hitable.class);
        if(hitable!=null)
            hitable.ability(event);
    }

    @EventHandler
    public void igniteAbility(BlockIgniteEvent event){
        if(event.isCancelled())
            return;
        Block block = event.getBlock();
        Ignitable ignitable = Utility.findItem(block, Ignitable.class);
        if(ignitable !=null)
            ignitable.ability(event);
    }

    @EventHandler
    public static void interactAbility(PlayerInteractEvent event){
        //no isCancelled()
        Action action = event.getAction();
        Player player = event.getPlayer();
        if(action==Action.PHYSICAL){
            ItemStack item = player.getInventory().getBoots();
            Passable passable = Utility.findItem(item, Passable.class, player);
            if(passable!=null)
                passable.ability(event, player.getWorld().getBlockAt(player.getLocation()));
            return;
        }
        Block block = event.getClickedBlock();
        if(block!=null && !player.isSneaking()){
            Punchable punchable = Utility.findItem(block, Punchable.class, player);
            if (punchable!=null)
                punchable.ability(event, action);
        }
        ItemStack item = event.getItem();
        if(item==null || item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        String enchantments = item.getItemMeta().getPersistentDataContainer().get(Utility.enchant, PersistentDataType.STRING);
        if(id!=null) {
            if (block != null && deactive.contains(block.getType()))
                event.setUseItemInHand(Event.Result.DENY);
            else if (Collections.disabled.contains(id))
                player.sendMessage("§cThis item has been disabled");
            else {
                Item generic = Collections.findItem(id);
                if (generic instanceof Interactable)
                    ((Interactable) generic).ability(event, action);
            }
        }
        if(enchantments!=null) {
            for (String enchantment : enchantments.split(" ")) {
                if (Collections.disabled.contains(enchantment))
                    return;
                Item generic = Collections.findItem(enchantment);
                if (generic instanceof Interactable)
                    ((Interactable) generic).ability(event, action);
            }
        }
    }

    @EventHandler
    public static void launchAbility(ProjectileLaunchEvent event){
        if(event.isCancelled())
            return;
        ProjectileSource entity = event.getEntity().getShooter();
        if(!(entity instanceof LivingEntity))
            return;
        LivingEntity shooter = (LivingEntity) entity;
        if(event.isCancelled())
            return;
        EntityEquipment equipment = shooter.getEquipment();
        if(equipment==null)
            return;
        boolean offhand = false;
        for(ItemStack item : new ItemStack[]{equipment.getItemInMainHand(), equipment.getItemInOffHand()}) {
            Player player = shooter instanceof Player ? (Player) shooter : null;
            Launchable launchable = Utility.findItem(item, Launchable.class, player);
            if(launchable !=null && offhand == launchable instanceof Holdable){
                launchable.ability(event, item);
                Utility.addDurability(item, -1, player);
            }
            offhand = true;
        }
    }

    @EventHandler
    public static void manipulateAbility(PlayerArmorStandManipulateEvent event){
        if(event.isCancelled())
            return;
        ArmorStand stand = event.getRightClicked();
        String id = stand.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id==null || Collections.disabled.contains(id))
            return;
        event.setCancelled(true);

        Item generic = Collections.findItem(id);
        if(generic instanceof Manipulatable)
            ((Manipulatable) generic).ability(event);
    }

    @EventHandler
    public static void powerAbility(BlockRedstoneEvent event){
        //no isCancelled()
        Block block = event.getBlock();
        Powerable powerable = Utility.findItem(block, Powerable.class);
        if(powerable!=null)
            powerable.ability(event);
    }

    @EventHandler
    public static void packAbility(InventoryCloseEvent event){
        //no isCancelled()
        InventoryView view = event.getView();
        Inventory inv = view.getTopInventory();
        if(inv.getHolder()!=null)
            return;

        if(view.getTitle().equals("Price")){
            (new GalleryFrame()).ability(event);
            return;
        }

        Player player = (Player) event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Pack pack = Utility.findItem(item, Pack.class, player);
        if(pack!=null && view.getTitle().equals(pack.title))
            pack.ability(inv, item, player);
    }

    @EventHandler
    public static void perishAbility(EntityDeathEvent event){
        //no isCancelled()
        LivingEntity entity = event.getEntity();
        if(entity instanceof ArmorStand)
            return;
        String id = entity.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        String modifiers = entity.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if(id!=null || modifiers!=null) {
            event.getDrops().clear();
            event.setDroppedExp(0);
//            Perishable perishable = Utility.findItem(id, Perishable.class);
//            if(perishable!=null)
//                perishable.effect(event, id);
            if(entity.getType()==EntityType.SLIME){
                Slime slime = (Slime) entity;
                int size = slime.getSize();
                new BukkitRunnable() {
                    public void run() {
                        for (Entity nearby : entity.getNearbyEntities(0.5, 0.5, 0.5)) {
                            if (nearby instanceof Slime && ((Slime) nearby).getSize() == size/2) {
                                if (id != null)
                                    nearby.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
                                if (modifiers != null)
                                    nearby.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, modifiers);
                            }
                        }
                    }
                }.runTaskLater(HoloItems.getInstance(), 30);
            }
            return;
        }

        Player player = entity.getKiller();
        if(player==null)
            return;
        PlayerInventory inv = player.getInventory();
        for(ItemStack item : new ItemStack[]{inv.getItemInMainHand(), inv.getItemInOffHand()}) {
            Perishable perishable = Utility.findItem(item, Perishable.class, player);
            if(perishable!=null)
                perishable.cause(event, item);
        }
    }

    @EventHandler
    public static void placeAbility(BlockPlaceEvent event){
        if(event.isCancelled())
            return;
        ItemStack item = event.getItemInHand();
        if(item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id!=null) {
            event.setCancelled(true);
            if (Collections.disabled.contains(id))
                event.getPlayer().sendMessage("§cThis item has been disabled");
            else {
                Item generic = Collections.findItem(id);
                if (generic instanceof Placeable)
                    ((Placeable) generic).ability(event);
            }
        }
    }

    @EventHandler
    public static void retainAbility(PlayerDeathEvent event){
        //no isCancelled()
        Player player = event.getEntity();
        for(ItemStack item : player.getInventory().getContents()) {
            Retainable retainable = Utility.findItem(item, Retainable.class, player);
            if(retainable!=null && retainable.ability(event, item))
                item.setAmount(item.getAmount() - 1);
        }
    }

    @EventHandler
    public static void targetAbility(EntityTargetLivingEntityEvent event){
        if(event.isCancelled())
            return;
        String modifiers = event.getEntity().getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if(modifiers==null)
            return;
        for(String modifier : modifiers.split("-")){
            Targetable targetable = Utility.findItem(modifier, Targetable.class);
            if(targetable!=null)
                targetable.ability(event);
        }
    }

    @EventHandler
    public static void teleportAbility(EntityTeleportEvent event){
        if(event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if(!(entity instanceof Player) && entity.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING)!=null)
            event.setCancelled(true);
    }

    @EventHandler
    public void toggleAbility(PlayerToggleSneakEvent event){
        if(event.isCancelled())
            return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getBoots();
        if(item==null || item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        String enchantments = item.getItemMeta().getPersistentDataContainer().get(Utility.enchant, PersistentDataType.STRING);
        if(id!=null) {
            if (Collections.disabled.contains(id))
                player.sendMessage("§cThis item has been disabled");
            else {
                Item generic = Collections.findItem(id);
                if (generic instanceof Togglable)
                    ((Togglable) generic).ability(event, item);
            }
        }
        if(enchantments!=null) {
            for (String enchantment : enchantments.split(" ")) {
                if (Collections.disabled.contains(id))
                    player.sendMessage("§cThis item has been disabled");
                else {
                    Item generic = Collections.findItem(enchantment);
                    if (generic instanceof Togglable)
                        ((Togglable) generic).ability(event, item);
                }
            }
        }
    }

    private static int findMultiplier(Enchantment enchant){
        for(Integer i : multiplier.keySet()){
            for(Enchantment enchantment : multiplier.get(i)){
                if(enchantment.equals(enchant))
                    return i;
            }
        }
        return -1;
    }

    @EventHandler
    public static void preventWither(EntityChangeBlockEvent event){
        if(event.isCancelled())
            return;
        if(event.getBlock().getType()==Material.PLAYER_HEAD)
            event.setCancelled(true);
    }

    @EventHandler
    public static void preventExplosion(EntityExplodeEvent event){
        if(event.isCancelled())
            return;
        for(Block block : event.blockList()){
            if(block!=null && block.getType()==Material.PLAYER_HEAD){
                event.setCancelled(true);
                return;
            }
        }
    }
}
