package com.klin.holoItems;

import com.klin.holoItems.abstractClasses.Crate;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.abstractClasses.Pack;
import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.collections.gen3.pekoraCollection.items.DoubleUp;
import com.klin.holoItems.collections.misc.hiddenCollection.items.GalleryFrame;
import com.klin.holoItems.interfaces.*;
import com.klin.holoItems.utility.ReflectionUtils;
import com.klin.holoItems.utility.Utility;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.block.data.Openable;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Events implements Listener {
    private static final Map<Integer, Enchantment[]> multiplier =
    new HashMap<Integer, Enchantment[]>(){{
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

    private static final Set<InventoryType> prohibitedInv = new HashSet<InventoryType>(){{
        add(InventoryType.BEACON);
        add(InventoryType.BREWING);
        add(InventoryType.CARTOGRAPHY);
        add(InventoryType.BLAST_FURNACE);
        add(InventoryType.FURNACE);
        add(InventoryType.LECTERN);
        add(InventoryType.LOOM);
        add(InventoryType.MERCHANT);
        add(InventoryType.SMITHING);
        add(InventoryType.SMOKER);
        add(InventoryType.STONECUTTER);
    }};

    private static final Set<Material> deactive = new HashSet<Material>(){{
        add(Material.JUKEBOX);
        add(Material.CAMPFIRE);
        add(Material.SOUL_CAMPFIRE);
    }};

    private static final Set<String> exception = new HashSet<String>(){{
        for(Character key : Collections.findCollection('0').collection.keySet())
            add("0"+key);
        add(DoubleUp.id);
    }};

    private static final Set<String> stackException = new HashSet<String>(){{
        add(DoubleUp.id);
    }};

    @EventHandler
    public static void dispenseAbility(BlockDispenseEvent event){
        if(event.isCancelled())
            return;
        BlockState state = event.getBlock().getState();
        if(!(state instanceof Dispenser))
            return;

        String itemId = ((Dispenser) state).getPersistentDataContainer().
                get(Utility.key, PersistentDataType.STRING);
        if(itemId!=null){
            if(Collections.disabled.contains(itemId))
                return;
            Item generic = Collections.findItem(itemId);
            if(generic instanceof Wiring)
                ((Wiring) generic).ability(event);
        }

        ItemStack item = event.getItem();
        if(item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id==null)
            return;

        if(Collections.disabled.contains(id))
            return;
        Item generic = Collections.findItem(id);
        if(generic instanceof Dispensable)
            ((Dispensable) generic).ability(event);
    }

    @EventHandler
    public static void activateAbility(PlayerInteractEvent event){
        //no isCancelled()
        Action action = event.getAction();
        if(action==Action.PHYSICAL)
            return;
        Block block = event.getClickedBlock();
        if(block!=null){
            if(block.getState() instanceof Container || block.getBlockData() instanceof Openable)
                return;
        }
        ItemStack item = event.getItem();
        if(item==null || item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        String enchant = item.getItemMeta().
                getPersistentDataContainer().get(Utility.enchant, PersistentDataType.STRING);
        if(id!=null) {
            if (block != null && deactive.contains(block.getType())) {
                event.setUseItemInHand(Event.Result.DENY);
            }
            else if (Collections.disabled.contains(id)) {
                event.getPlayer().sendMessage("§cThis item has been disabled");
            }
            else {
                Item generic = Collections.findItem(id);
                if (generic instanceof Interactable)
                    ((Interactable) generic).ability(event, action);
            }
        }
        if(enchant!=null) {
            if (Collections.disabled.contains(enchant))
                return;
            Item generic = Collections.findItem(enchant);
            if (generic instanceof Interactable)
                ((Interactable) generic).ability(event, action);
        }
    }

    @EventHandler
    public static void blockAbility(BlockPlaceEvent event){
        if(event.isCancelled())
            return;
        ItemStack item = event.getItemInHand();
        if(item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        String enchant = item.getItemMeta().
                getPersistentDataContainer().get(Utility.enchant, PersistentDataType.STRING);
        Player player = event.getPlayer();
        if(id!=null) {
            event.setCancelled(true);
            if (Collections.disabled.contains(id))
                player.sendMessage("§cThis item has been disabled");
            else {
                Item generic = Collections.findItem(id);
                if (generic instanceof Placeable)
                    ((Placeable) generic).ability(event);
            }
        }
        if(enchant!=null) {
            if (player.getGameMode() != GameMode.CREATIVE)
                Utility.addDurability(item, -1, player);
        }
    }

    @EventHandler
    public static void retainAbility(PlayerDeathEvent event){
        //no isCancelled()
        Player player = event.getEntity();
        Entity killer = player.getKiller();
        if(killer!=null && killer.equals(player) && event.getDeathMessage().contains("was slain by"))
            event.setDeathMessage(player.getName()+" ga shinda");

        for(ItemStack item : player.getInventory().getContents()) {
            if(item==null || item.getType()==Material.AIR || item.getItemMeta() == null)
                continue;
            String id = item.getItemMeta().
                    getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if (id == null)
                continue;

            if(Collections.disabled.contains(id)){
                player.sendMessage("§cThis item has been disabled");
                return;
            }
            Item generic = Collections.findItem(id);
            if(generic instanceof Retainable) {
                if(((Retainable) generic).ability(event))
                    item.setAmount(item.getAmount() - 1);
            }
        }
    }

    @EventHandler
    public static void clickAbility(InventoryClickEvent event){
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
                            List<String> addDurability = meta.getLore();
                            if (addDurability == null)
                                addDurability = new ArrayList<>();
                            int maxDurability = itemA.getType().getMaxDurability();
                            int currDurability = maxDurability-((Damageable) meta).getDamage();
                            addDurability.add("§fDurability: "+currDurability+"/"+maxDurability);
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
            String id = item.getItemMeta().
                    getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if (id == null) {
                current = false;
                continue;
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
    public static void acceptable(EnchantItemEvent event){
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
    public static void bowCause(ProjectileLaunchEvent event){
        if(event.isCancelled())
            return;
        Projectile proj = event.getEntity();
        if(!(proj.getShooter() instanceof LivingEntity))
            return;

        LivingEntity shooter = (LivingEntity) proj.getShooter();
        boolean offhand = false;
        EntityEquipment equip = shooter.getEquipment();
        if(equip==null)
            return;
        for(ItemStack item : new ItemStack[]{equip.getItemInMainHand(), equip.getItemInOffHand()}) {
            if (item==null || item.getItemMeta()==null) {
                offhand = true;
                continue;
            }
            String id = item.getItemMeta().
                    getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if (id == null) {
                offhand = true;
                continue;
            }

            if(Collections.disabled.contains(id)) {
                offhand = true;
                continue;
            }
            Item generic = Collections.findItem(id);
            if(generic instanceof Shootable) {
                ((Shootable) generic).cause(event, item);
                if (shooter instanceof Player){
                    Player player = (Player) shooter;
                    if(player.getGameMode()!=GameMode.CREATIVE)
                        Utility.addDurability(item, -1, player);
                }
            }
            else if(offhand && generic instanceof Holdable)
                ((Holdable) generic).ability(event);

            offhand = true;
        }
    }

    @EventHandler
    public static void bowEffect(ProjectileHitEvent event){
        //no isCancelled()
        Projectile proj = event.getEntity();
        if(!(proj instanceof Arrow) || proj.doesBounce())
            return;
        String id = proj.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id==null)
            return;

        if(Collections.disabled.contains(id))
            return;
        Item generic = Collections.findItem(id);
        if(generic instanceof Shootable)
            ((Shootable) generic).effect(event);
    }

    @EventHandler
    public static void necroticAbility(EntityDeathEvent event){
        //no isCancelled()

        LivingEntity entity = event.getEntity();
        String itemId = entity.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(itemId!=null) {
            event.getDrops().clear();
            event.setDroppedExp(0);

//            if(Collections.disabled.contains(itemId))
//                return;
//            Item generic = Collections.findItem(itemId);
//            if(generic instanceof Necrotic)
//                ((Necrotic) generic).effect(event, itemId);
            return;
        }

        Player player = entity.getKiller();
        if(player==null)
            return;

        PlayerInventory inv = player.getInventory();
        for(ItemStack item : new ItemStack[]{inv.getItemInMainHand(), inv.getItemInOffHand()}) {
            if (item==null || item.getItemMeta()==null)
                continue;
            String id = item.getItemMeta().
                    getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if (id == null)
                continue;

            if(Collections.disabled.contains(id))
                continue;
            Item generic = Collections.findItem(id);
            if(generic instanceof Necrotic)
                ((Necrotic) generic).cause(event, item);
        }
    }

//    @EventHandler
//    public static void targetAbility(EntityTargetLivingEntityEvent event){
//        if(event.isCancelled())
//            return;
//
//        if(!(event.getTarget() instanceof Player))
//            return;
//        PlayerInventory inv = ((Player) event.getTarget()).getInventory();
//        for(ItemStack item : new ItemStack[]{inv.getItemInMainHand(), inv.getItemInOffHand()}) {
//            if (item==null || item.getItemMeta()==null)
//                continue;
//            String id = item.getItemMeta().
//                    getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
//            if (id == null)
//                continue;
//
//            if(Collections.disabled.contains(id))
//                continue;
//            Item generic = Collections.findItem(id);
//            if(generic instanceof Necrotic)
//                ((Necrotic) generic).ability(event);
//        }
//    }

    @EventHandler
    public static void flauntAbility(AsyncPlayerChatEvent event){
        if(event.isCancelled() || !event.isAsynchronous())
            return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getHelmet();
        if(item==null || item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id==null)
            return;

        if(Collections.disabled.contains(id)){
            player.sendMessage("§cThis item has been disabled");
            return;
        }
        Item generic = Collections.findItem(id);
        if(generic instanceof Flauntable)
            ((Flauntable) generic).ability(event);
    }

    @EventHandler
    public static void breakItemAttacking(EntityDamageByEntityEvent event){
        if(event.isCancelled())
            return;
        if(!(event.getDamager() instanceof Player))
            return;
        Player player = (Player) event.getDamager();
        if (player.getGameMode()==GameMode.CREATIVE)
            return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType()==Material.AIR || item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id==null)
            return;

        Utility.addDurability(item, -1, player);
    }

    @EventHandler
    public static void breakItemDefending(EntityDamageByEntityEvent event){
        if(event.isCancelled())
            return;
        if(!(event.getEntity() instanceof Player))
            return;
        Player player = (Player) event.getEntity();
        if (player.getGameMode()==GameMode.CREATIVE)
            return;

        for(ItemStack item : player.getInventory().getArmorContents()) {
            if (item==null || item.getItemMeta() == null)
                continue;
            String id = item.getItemMeta().
                    getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if (id == null)
                continue;

            //mel: reading glasses easter egg
            if(id.equals("f0") && item.containsEnchantment(Enchantment.BINDING_CURSE))
                continue;

            if(Collections.disabled.contains(id))
                continue;
            Item generic = Collections.findItem(id);
            if(generic instanceof Wearable)
                ((Wearable) generic).ability(event,
                        Utility.addDurability(item, -1, player)==-1);
        }

        if(!player.isBlocking())
            return;
        PlayerInventory inv = player.getInventory();
        for(ItemStack item : new ItemStack[]{inv.getItemInMainHand(), inv.getItemInOffHand()}) {
            if (item==null || item.getItemMeta()==null)
                continue;
            String id = item.getItemMeta().
                    getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if (id == null)
                continue;

            if(Collections.disabled.contains(id))
                continue;
            Item generic = Collections.findItem(id);
            if(generic instanceof Defensible) {
                ((Defensible) generic).ability(event);
                return;
            }
        }
    }

    @EventHandler
    public static void breakItemMining(BlockBreakEvent event){
        if(event.isCancelled())
            return;

        BlockState blockState = event.getBlock().getState();
        if(blockState instanceof TileState){
            String itemId = ((TileState) blockState).
                    getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if(itemId!=null){
                Item generic = Collections.findItem(itemId);
                if(generic instanceof Crate)
                    ((Crate) generic).ability(event);
            }
        }

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if(item.getType()==Material.AIR || item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        String enchant = item.getItemMeta().
                getPersistentDataContainer().get(Utility.enchant, PersistentDataType.STRING);
        boolean durability = false;
        if(id!=null) {
            Player player = event.getPlayer();
            durability = true;
            if (player.getGameMode() != GameMode.CREATIVE)
                Utility.addDurability(item, -1, player);
            if (Collections.disabled.contains(id))
                event.getPlayer().sendMessage("§cThis item has been disabled");
            else {
                Item generic = Collections.findItem(id);
                if (generic instanceof Extractable)
                    ((Extractable) generic).ability(event);
            }
        }
        if(enchant!=null) {
            if(!durability){
                Player player = event.getPlayer();
                if (player.getGameMode() != GameMode.CREATIVE)
                    Utility.addDurability(item, -1, player);
            }
            if (Collections.disabled.contains(enchant))
                return;
            Item generic = Collections.findItem(enchant);
            if (generic instanceof Extractable)
                ((Extractable) generic).ability(event);
        }
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
    public static void craftItem(CraftItemEvent event){
        if(event.isCancelled())
            return;
        CraftingInventory inv = event.getInventory();
        for(ItemStack item : event.getInventory().getMatrix()){
            if(item!=null && item.getItemMeta()!=null){
                String id = item.getItemMeta().getPersistentDataContainer().
                        get(Utility.key, PersistentDataType.STRING);
                if(id!=null) {
                    if(exception.contains(id))
                        continue;
                    event.setCancelled(true);
                    return;
                }
            }
        }
        Player player = (Player) event.getWhoClicked();
        if(player.getGameMode()==GameMode.CREATIVE)
            return;
        ItemStack curr = event.getCurrentItem();
        if(curr==null || curr.getItemMeta()==null)
            return;
        ItemMeta currMeta = curr.getItemMeta();
        String id = currMeta.
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id==null)
            return;

        Collection collection = Collections.findCollection(id.charAt(0));
        Item item = collection.collection.get(id.charAt(1));
        int progress = Utility.add(collection.getStat(player));
        if(item.cost>progress){
            event.setCancelled(true);
            player.sendMessage("Progress to unlocking "+
                    Utility.formatName(item.name)+": "+progress+"/"+item.cost);
        }
        else if(!item.stackable && item.item.getMaxStackSize()>1){
            if(stackException.contains(id))
                return;
            World world = player.getWorld();
            Location loc = player.getLocation();
            boolean excess = false;
            for (ItemStack ingredient : inv.getMatrix()) {
                if(ingredient==null)
                    continue;
                if(ingredient.getAmount()>1) {
                    excess = true;
                    ItemStack refund = ingredient.clone();
                    refund.setAmount(ingredient.getAmount() - 1);
                    ingredient.setAmount(1);
                    world.dropItemNaturally(loc, refund);
                }
            }
            if(excess) {
                event.setCancelled(true);
                return;
            }

            currMeta.getPersistentDataContainer().set(Utility.stack, PersistentDataType.DOUBLE, Math.random());
            curr.setItemMeta(currMeta);
        }
    }

    @EventHandler
    public static void sealAbility(InventoryCloseEvent event){
        //no isCancelled()
        InventoryView view = event.getView();
        Inventory inv = view.getTopInventory();
        if(inv.getHolder()!=null)
            return;

        if(view.getTitle().equals("Price")){
            (new GalleryFrame()).ability(event);
            return;
        }

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getType()==Material.AIR || item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id==null)
            return;

        if(Collections.disabled.contains(id))
            return;
        Item generic = Collections.findItem(id);
        if(generic instanceof Pack) {
            Pack pack = (Pack) generic;
            if (view.getTitle().equals(pack.title))
                pack.ability(inv, item, (Player) event.getPlayer());
        }
    }

    @EventHandler
    public static void preventConsume(PlayerItemConsumeEvent event){
        if(event.isCancelled())
            return;
        ItemStack item = event.getItem();
        if (item.getType()==Material.AIR || item.getItemMeta()==null)
            return;
        String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if (id != null)
            event.setCancelled(true);
    }

    @EventHandler
    public static void respondAbility(PlayerInteractEntityEvent event){
        Entity entity = event.getRightClicked();
        String itemId = entity.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if (itemId != null) {
            event.setCancelled(true);

            if(!Collections.disabled.contains(itemId)) {
                Item generic = Collections.findItem(itemId);
                if (generic instanceof Reactable)
                    ((Reactable) generic).ability(event);
            }
            else
                event.getPlayer().sendMessage("§cThis item has been disabled");
            return;
        }
        if(event.isCancelled())
            return;

        Player player = event.getPlayer();
        ItemStack item;
        if(event.getHand()==EquipmentSlot.HAND)
            item = player.getInventory().getItemInMainHand();
        else
            item = player.getInventory().getItemInOffHand();
        if (item.getType()!=Material.AIR && item.getItemMeta()!=null) {
            String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if (id != null) {
                event.setCancelled(true);

                if(Collections.disabled.contains(id)){
                    player.sendMessage("§cThis item has been disabled");
                    return;
                }
                Item generic = Collections.findItem(id);
                if(generic instanceof Responsible)
                    ((Responsible) generic).ability(event);
                    Utility.addDurability(item, -1, player);
            }
        }
    }

    @EventHandler
    public static void hangingAbility(HangingPlaceEvent event){
        if(event.isCancelled())
            return;

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getType()==Material.AIR || item.getItemMeta() == null)
            return;
        String id = item.getItemMeta().
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id==null)
            return;

        if(Collections.disabled.contains(id)) {
            event.getPlayer().sendMessage("§cThis item has been disabled");
            return;
        }
        Item generic = Collections.findItem(id);
        if(generic instanceof Hangable)
            ((Hangable) generic).ability(event);
    }

    @EventHandler
    public static void protectPlacableWither(EntityChangeBlockEvent event){
        if(event.isCancelled())
            return;
        if(event.getBlock().getType()==Material.PLAYER_HEAD)
            event.setCancelled(true);
    }

    @EventHandler
    public static void protectPlacableExplosion(EntityExplodeEvent event){
        if(event.isCancelled())
            return;
        for(Block block : event.blockList()){
            if(block!=null && block.getType()==Material.PLAYER_HEAD){
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void burnableAbility(BlockIgniteEvent event){
        BlockState state = event.getBlock().getState();
        if(!(state instanceof TileState))
            return;

        String id = ((TileState) state).getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        if(id==null)
            return;

        if(Collections.disabled.contains(id))
            return;
        Item generic = Collections.findItem(id);
        if(generic instanceof Burnable)
            ((Burnable) generic).ability(event);
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
}
