package com.klin.holoItems;

import com.klin.holoItems.collections.en2.BaeCollection.BaeCollection;
import com.klin.holoItems.collections.en2.FaunaCollection.FaunaCollection;
import com.klin.holoItems.collections.en2.KroniiCollection.KroniiCollection;
import com.klin.holoItems.collections.en2.MumeiCollection.MumeiCollection;
import com.klin.holoItems.collections.en2.SanaCollection.SanaCollection;
import com.klin.holoItems.collections.en1.calliCollection.CalliCollection;
import com.klin.holoItems.collections.en1.guraCollection.GuraCollection;
import com.klin.holoItems.collections.en1.inaCollection.InaCollection;
import com.klin.holoItems.collections.en1.irysCollection.IrysCollection;
import com.klin.holoItems.collections.en1.kiaraCollection.KiaraCollection;
import com.klin.holoItems.collections.en1.watsonCollection.WatsonCollection;
import com.klin.holoItems.collections.gamers.koroneCollection.KoroneCollection;
import com.klin.holoItems.collections.gamers.mioCollection.MioCollection;
import com.klin.holoItems.collections.gamers.okayuCollection.OkayuCollection;
import com.klin.holoItems.collections.gen0.azkiCollection.AzkiCollection;
import com.klin.holoItems.collections.gen0.mikoCollection.MikoCollection;
import com.klin.holoItems.collections.gen0.robocoCollection.RobocoCollection;
import com.klin.holoItems.collections.gen0.soraCollection.SoraCollection;
import com.klin.holoItems.collections.gen0.suiseiCollection.SuiseiCollection;
import com.klin.holoItems.collections.gen1.akiCollection.AkiCollection;
import com.klin.holoItems.collections.gen1.fubukiCollection.FubukiCollection;
import com.klin.holoItems.collections.gen1.haachamaCollection.HaachamaCollection;
import com.klin.holoItems.collections.gen1.matsuriCollection.MatsuriCollection;
import com.klin.holoItems.collections.gen1.melCollection.MelCollection;
import com.klin.holoItems.collections.gen2.aquaCollection.AquaCollection;
import com.klin.holoItems.collections.gen2.ayameCollection.AyameCollection;
import com.klin.holoItems.collections.gen2.chocoCollection.ChocoCollection;
import com.klin.holoItems.collections.gen2.shionCollection.ShionCollection;
import com.klin.holoItems.collections.gen2.subaruCollection.SubaruCollection;
import com.klin.holoItems.collections.gen3.flareCollection.FlareCollection;
import com.klin.holoItems.collections.gen3.marineCollection.MarineCollection;
import com.klin.holoItems.collections.gen3.noelCollection.NoelCollection;
import com.klin.holoItems.collections.gen3.pekoraCollection.PekoraCollection;
import com.klin.holoItems.collections.gen3.rushiaCollection.RushiaCollection;
import com.klin.holoItems.collections.gen4.cocoCollection.CocoCollection;
import com.klin.holoItems.collections.gen4.kanataCollection.KanataCollection;
import com.klin.holoItems.collections.gen4.lunaCollection.LunaCollection;
import com.klin.holoItems.collections.gen4.towaCollection.TowaCollection;
import com.klin.holoItems.collections.gen4.watameCollection.WatameCollection;
import com.klin.holoItems.collections.gen5.botanCollection.BotanCollection;
import com.klin.holoItems.collections.gen5.lamyCollection.LamyCollection;
import com.klin.holoItems.collections.gen5.neneCollection.NeneCollection;
import com.klin.holoItems.collections.gen5.polkaCollection.PolkaCollection;
import com.klin.holoItems.collections.hidden.utilityCollection.UtilityCollection;
import com.klin.holoItems.collections.stars1.aruranCollection.AruranCollection;
import com.klin.holoItems.collections.stars2.astelCollection.AstelCollection;
import com.klin.holoItems.collections.stars1.izuruCollection.IzuruCollection;
import com.klin.holoItems.collections.stars1.miyabiCollection.MiyabiCollection;
import com.klin.holoItems.collections.stars3.ogaCollection.OgaCollection;
import com.klin.holoItems.collections.stars1.rikkaCollection.RikkaCollection;
import com.klin.holoItems.collections.stars2.roberuCollection.RoberuCollection;
import com.klin.holoItems.collections.stars3.shienCollection.ShienCollection;
import com.klin.holoItems.collections.stars2.temmaCollection.TemmaCollection;
import com.klin.holoItems.collections.id1.iofiCollection.IofiCollection;
import com.klin.holoItems.collections.id1.moonaCollection.MoonaCollection;
import com.klin.holoItems.collections.id1.risuCollection.RisuCollection;
import com.klin.holoItems.collections.id2.anyaCollection.AnyaCollection;
import com.klin.holoItems.collections.id2.ollieCollection.OllieCollection;
import com.klin.holoItems.collections.id2.reineCollection.ReineCollection;
import com.klin.holoItems.collections.misc.achanCollection.AchanCollection;
import com.klin.holoItems.collections.hidden.franCollection.FranCollection;
import com.klin.holoItems.collections.misc.ingredientsCollection.IngredientsCollection;
import com.klin.holoItems.collections.hidden.klinCollection.KlinCollection;
import com.klin.holoItems.collections.hidden.opCollection.OpCollection;
import com.klin.holoItems.interfaces.Activatable;
import com.klin.holoItems.utility.SkullCreator;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.regex.Pattern;

public class Collections implements CommandExecutor, Listener, TabCompleter {
    public static Map<String, Collection> collections = new HashMap<>();
    private static final Map<Integer, Set<Collection>> gens = new HashMap<>();
    private static final Map<Integer, ItemStack> front = new HashMap<>();
    private static final Map<ItemStack, Integer> back = new HashMap<>();
    private static final Map<String, ItemStack> heads = new HashMap<>();
    public static Set<String> disabled = new HashSet<>();
    public static Map<String, Item> items = new LinkedHashMap<>();
    public static final Set<Integer> taskIds = new HashSet<>();

    public Collections(){
        int[] index = {0};

        Set<Collection> misc = new LinkedHashSet<>();
        misc.add(new IngredientsCollection());
        misc.add(new AchanCollection());
        gens.put(index[0], misc);
        item(Material.PAPER, "Misc.", index);

        Set<Collection> gen0 = new LinkedHashSet<>();
        gen0.add(new SuiseiCollection());
        gen0.add(new SoraCollection());
        gen0.add(new RobocoCollection());
        gen0.add(new MikoCollection());
        gen0.add(new AzkiCollection());
        gens.put(index[0], gen0);
        item(Material.PAPER, "Gen 0", index);

        Set<Collection> gen1 = new LinkedHashSet<>();
        gen1.add(new MelCollection());
        gen1.add(new FubukiCollection());
        gen1.add(new MatsuriCollection());
        gen1.add(new AkiCollection());
        gen1.add(new HaachamaCollection());
        gens.put(index[0], gen1);
        item(Material.PAPER, "Gen 1", index);

        Set<Collection> gen2 = new LinkedHashSet<>();
        gen2.add(new AquaCollection());
        gen2.add(new ShionCollection());
        gen2.add(new AyameCollection());
        gen2.add(new ChocoCollection());
        gen2.add(new SubaruCollection());
        gens.put(index[0], gen2);
        item(Material.PAPER, "Gen 2", index);

        Set<Collection> gamers = new LinkedHashSet<>();
        gamers.add(new MioCollection());
        gamers.add(new OkayuCollection());
        gamers.add(new KoroneCollection());
        gens.put(index[0], gamers);
        item(Material.PAPER, "Gamers", index);

        Set<Collection> gen3 = new LinkedHashSet<>();
        gen3.add(new PekoraCollection());
        gen3.add(new RushiaCollection());
        gen3.add(new FlareCollection());
        gen3.add(new NoelCollection());
        gen3.add(new MarineCollection());
        gens.put(index[0], gen3);
        item(Material.PAPER, "Gen 3", index);

        Set<Collection> gen4 = new LinkedHashSet<>();
        gen4.add(new KanataCollection());
        gen4.add(new CocoCollection());
        gen4.add(new WatameCollection());
        gen4.add(new TowaCollection());
        gen4.add(new LunaCollection());
        gens.put(index[0], gen4);
        item(Material.PAPER, "Gen 4", index);

        Set<Collection> id1 = new LinkedHashSet<>();
        id1.add(new RisuCollection());
        id1.add(new MoonaCollection());
        id1.add(new IofiCollection());
        gens.put(index[0], id1);
        item(Material.PAPER, "Id 1", index);

        Set<Collection> gen5 = new LinkedHashSet<>();
        gen5.add(new LamyCollection());
        gen5.add(new NeneCollection());
        gen5.add(new BotanCollection());
        gen5.add(new PolkaCollection());
        gens.put(index[0], gen5);
        item(Material.PAPER, "Gen 5", index);

        Set<Collection> en1 = new LinkedHashSet<>();
        en1.add(new CalliCollection());
        en1.add(new KiaraCollection());
        en1.add(new InaCollection());
        en1.add(new GuraCollection());
        en1.add(new WatsonCollection());
        en1.add(new IrysCollection());
        gens.put(index[0], en1);
        item(Material.PAPER, "Myth", index);

        Set<Collection> id2 = new LinkedHashSet<>();
        id2.add(new OllieCollection());
        id2.add(new AnyaCollection());
        id2.add(new ReineCollection());
        gens.put(index[0], id2);
        item(Material.PAPER, "Id 2", index);

        Set<Collection> en2 = new LinkedHashSet<>();
        en2.add(new SanaCollection());
        en2.add(new FaunaCollection());
        en2.add(new KroniiCollection());
        en2.add(new MumeiCollection());
        en2.add(new BaeCollection());
        gens.put(index[0], en2);
        item(Material.PAPER, "Council", index);

        Set<Collection> stars1 = new LinkedHashSet<>();
        stars1.add(new MiyabiCollection());
        stars1.add(new IzuruCollection());
        stars1.add(new AruranCollection());
        stars1.add(new RikkaCollection());
        gens.put(index[0], stars1);
        item(Material.PAPER, "Stars 1", index);

        Set<Collection> stars2 = new LinkedHashSet<>();
        stars2.add(new AstelCollection());
        stars2.add(new TemmaCollection());
        stars2.add(new RoberuCollection());
        gens.put(index[0], stars2);
        item(Material.PAPER, "Stars 2", index);

        Set<Collection> stars3 = new LinkedHashSet<>();
        stars3.add(new ShienCollection());
        stars3.add(new OgaCollection());
        gens.put(index[0], stars3);
        item(Material.PAPER, "Stars 3", index);

        Set<Collection> hidden = new LinkedHashSet<>();
        hidden.add(new FranCollection());
        hidden.add(new KlinCollection());
        hidden.add(new OpCollection());
        hidden.add(new UtilityCollection());
        gens.put(index[0], hidden);

        for(Set<Collection> set : gens.values()){
            for(Collection collection : set)
                collections.put(collection.name, collection);
        }

        ItemStack up = new ItemStack(Material.LANTERN);
        ItemMeta upMeta = up.getItemMeta();
        upMeta.setDisplayName("§fUp");
        up.setItemMeta(upMeta);
        front.put(-1, up);

        ItemStack down = new ItemStack(Material.SOUL_LANTERN);
        ItemMeta downMeta = down.getItemMeta();
        downMeta.setDisplayName("§fDown");
        down.setItemMeta(downMeta);
        front.put(-2, down);

        ItemStack top = new ItemStack(Material.REDSTONE_LAMP);
        ItemMeta topMeta = top.getItemMeta();
        topMeta.setDisplayName("§fBack to Top");
        top.setItemMeta(topMeta);
        front.put(-3, top);

        for(Integer key : front.keySet())
            back.put(front.get(key), key);

        setupHeads();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        String command = cmd.getName().toLowerCase();
        switch(command) {
            case "collections":
                player.openInventory(createInv(0, player));
                return true;

            case "coordinates":
                Location loc = player.getLocation();
                player.sendMessage(player.getWorld().getName()+" "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ());
                return true;

            case "custommodeldata":
                ItemStack model = player.getInventory().getItemInMainHand();
                if(model.getType()!=Material.AIR && model.getItemMeta()!=null){
                    ItemMeta meta = model.getItemMeta();
                    String id = meta.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
                    if(id!=null) {
                        int hash = 5;
                        for (int i=0; i<id.length(); i++)
                            hash = hash*3 + id.charAt(i);
                        if(!meta.hasCustomModelData() || meta.getCustomModelData()!=hash) {
                            meta.setCustomModelData(hash);
                            model.setItemMeta(meta);
                            player.sendMessage("Updated CustomModelData to "+hash);
                        }
                        else
                            player.sendMessage("This item's CustomModelData is "+hash);
                    }
                }
                else
                    player.sendMessage("Invalid item");
                return true;

            case "acquire":
                if(!player.hasPermission("holoItems.op") && player.getGameMode()!=GameMode.CREATIVE || args.length<1) {
                    String list = "";
                    for(Collection collection : collections.values()){
                        if(collection.collection.isEmpty() || collection.base64==null)
                            continue;
                        list += "\n§f" + collection.name + "\n§7";
                        for(Item item : collection.collection)
                            list += item.name + " ";
                    }
                    player.sendMessage(list.substring(3));
                    return true;
                }
                Item item = items.get(args[0]);
                if (item == null || item.cost==-1 && !player.hasPermission("holoItems.op")) {
                    player.sendMessage("No such item");
                    return true;
                }
                player.getInventory().addItem(item.item);
                player.sendMessage(Utility.formatName(item.name) + " acquired");
                return true;

            //holoItems.op

            case "accelerate":
                if(args.length<3)
                    return false;
                try {
                    player.setVelocity(new Vector(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
                }catch (NumberFormatException e){return false;}
                return true;

            case "canceltasks":
                BukkitScheduler scheduler = Bukkit.getScheduler();
                for(Integer taskId : taskIds)
                    scheduler.cancelTask(taskId);
                taskIds.clear();
                player.sendMessage("Tasks cancelled");
                return true;

            case "clearactivatables":
                for(Activatable activatable : Events.activatables)
                    activatable.survey().clear();
                Events.activatables.clear();
                player.sendMessage("Activatables cleared");
                return true;

            case "collect":
                if(args.length<3)
                    return false;
                try {
                    Player temp;
                    if(args.length>3) {
                        temp = Bukkit.getPlayer(args[3]);
                        if(temp==null) {
                            player.sendMessage("Unknown player");
                            return true;
                        }
                    } else temp = player;
                    Statistic stat = Statistic.valueOf(args[0].toUpperCase());
                    switch(stat.getType()){
                        case ITEM:
                        case BLOCK:
                            Material type = Material.valueOf(args[1].toUpperCase());
                            int i = Integer.parseInt(args[2]);
                            temp.setStatistic(stat, type, i);
                            player.sendMessage(stat+":"+type+" set to "+i);
                            break;
                        case ENTITY:
                            EntityType entity = EntityType.valueOf(args[1].toUpperCase());
                            int j = Integer.parseInt(args[2]);
                            temp.setStatistic(stat, entity, j);
                            player.sendMessage(stat+":"+entity+" set to "+j);
                            break;
                        case UNTYPED:
                            int k = Integer.parseInt(args[1]);
                            temp.setStatistic(stat, k);
                            player.sendMessage(stat+" set to "+k);
                    }
                }
                catch(IllegalArgumentException e){
                    player.sendMessage("Invalid argument/s");
                    return true;
                }
                return true;

            case "revert":
                args = new String[0];
            case "convert":
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                item = Utility.findItem(itemStack, Item.class);
                if(item!=null && args.length<1){
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.getPersistentDataContainer().remove(Utility.key);
                    itemStack.setItemMeta(meta);
                    player.sendMessage(item.name + " reverted");
                    return true;
                } if(itemStack.getType()==Material.AIR)
                    return true;
                ItemMeta meta = itemStack.getItemMeta();
                if(meta==null)
                    return true;
                item = items.get(args[0]);
                if(item==null){
                    player.sendMessage("No such item");
                    return true;
                }
                meta.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, args[0]);
                itemStack.setItemMeta(meta);
                player.sendMessage("Converted to " + args[0]);
                return true;

            case "disable":
                if(args.length>=1) {
                    item = items.get(args[0]);
                    if(item==null){
                        player.sendMessage("No such item");
                        return true;
                    }
                    disabled.add(args[0]);
                    player.sendMessage(Utility.formatName(item.name)+" disabled");
                }
                else
                    player.sendMessage("Disabled items: "+disabled.toString());
                return true;

            case "enable":
                if(args.length>=1) {
                    if(args[0].equals("all")){
                        disabled.clear();
                        return true;
                    }
                    item = items.get(args[0]);
                    if(item==null){
                        player.sendMessage("No such item");
                        return true;
                    }
                    if(!disabled.contains(args[0])){
                        player.sendMessage("Item wasn't disabled");
                        return true;
                    }
                    disabled.remove(args[0]);
                    player.sendMessage(Utility.formatName(item.name)+" enabled");
                }
                else
                    player.sendMessage("Disabled items: "+disabled.toString());
                return true;

            case "getactivatables":
                if(Events.activatables.isEmpty())
                    player.sendMessage("Activatables is empty");
                else {
                    boolean first = true;
                    for (Activatable activatable : Events.activatables) {
                        if(!first)
                            player.sendMessage("");
                        player.sendMessage(activatable.toString() + "\n-----------------------------------------------------\n" + activatable.survey());
                        first = false;
                    }
                }
                return true;

            case "gethead":
                if(!heads.isEmpty() && args.length>=1) {
                    ItemStack head = heads.get(args[0]);
                    if(head!=null) {
                        ItemMeta headMeta = head.getItemMeta();
                        headMeta.setLore(new ArrayList<>());
                        head.setItemMeta(headMeta);
                        player.getInventory().addItem(head);
                    }
                    else
                        player.sendMessage("No such head");
                }
                else
                    return false;
                return true;

            case "getmap":
                if(args.length<1)
                    return false;
                MapView view;
                try{
                    view = Bukkit.getMap(Integer.parseInt(args[0]));
                }catch (NumberFormatException e){
                    player.sendMessage("Invalid index");
                    return true;
                }
                ItemStack map = new ItemStack(Material.FILLED_MAP);
                MapMeta mapMeta = (MapMeta) map.getItemMeta();
                mapMeta.setMapView(view);
                map.setItemMeta(mapMeta);
                player.getInventory().addItem(map);
                return true;

            case "removechunktickets":
                World world = player.getWorld();
                java.util.Collection<Chunk> chunks = world.getPluginChunkTickets().get(HoloItems.getInstance());
                int quantity = chunks==null?0:chunks.size();
                player.sendMessage("Chunk tickets to be removed: "+quantity);
                if(quantity>0)
                    world.removePluginChunkTickets(HoloItems.getInstance());
                return true;

            case "setcustommodeldata":
                if(args.length<1)
                    return false;
                model = player.getInventory().getItemInMainHand();
                if(model.getType()!=Material.AIR && model.getItemMeta()!=null) {
                    meta = model.getItemMeta();
                    try {
                        int data = Integer.parseInt(args[0]);
                        player.sendMessage("Set CustomModelData from "+meta.getCustomModelData()+" to "+args[0]);
                        meta.setCustomModelData(data);
                        model.setItemMeta(meta);
                    }
                    catch(NumberFormatException e) {
                        player.sendMessage("Argument needs to be an integer");
                        return true;
                    }
                }
                else
                    player.sendMessage("Invalid item");
                return true;

            case "setenchantments":
                if(args.length<1)
                    return false;
                ItemStack stack = player.getInventory().getItemInMainHand();
                if(stack.getType()!=Material.AIR && stack.getItemMeta()!=null) {
                    String enchantments = "";
                    for (String arg : args)
                        enchantments += arg + " ";
                    enchantments = enchantments.substring(0, enchantments.length()-1);
                    meta = stack.getItemMeta();
                    meta.getPersistentDataContainer().set(Utility.enchant, PersistentDataType.STRING, enchantments);
                    stack.setItemMeta(meta);
                    player.sendMessage("Enchantments set to: "+enchantments);
                }
                return true;

            case "setid":
                if(args.length<1)
                    return false;
                stack = player.getInventory().getItemInMainHand();
                if(stack.getType()!=Material.AIR && stack.getItemMeta()!=null) {
                    meta = stack.getItemMeta();
                    meta.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, args[0]);
                    stack.setItemMeta(meta);
                    player.sendMessage("Id set to: "+args[0]);
                }
                return true;

            case "settype":
                if(args.length<1)
                    return false;
                itemStack = player.getInventory().getItemInMainHand();
                if(itemStack.getType()!=Material.AIR) {
                    Material type = Material.getMaterial(args[0]);
                    if(type!=null)
                        itemStack.setType(type);
                }
                return true;

            case "tasks":
                player.sendMessage(taskIds.toString());
                return true;

            case "test":
                Utility.test = !Utility.test;
                player.sendMessage("Test set to: "+Utility.test);
        }
        return true;
    }

    @EventHandler
    public static void viewCollection(InventoryClickEvent event){
        InventoryView view = event.getView();
        Inventory top = view.getTopInventory();
        if(top.getHolder()!=null || !view.getTitle().contains("Collections"))
            return;
        ItemStack curr = event.getCurrentItem();
        if(curr==null)
            return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        Integer index = back.get(curr);
        if(index!=null){
            switch (index){
                case -3:
                    index = 0;
                    break;
                case -2:
                    index = back.get(top.getItem(0)) + 1;
                    break;
                case -1:
                    index = back.get(top.getItem(0)) - 1;
                    break;
                default:
                    if(event.getRawSlot()%9==0)
                        updateInv(index, player, top);
                    else {
                        int i = index;
                        new BukkitRunnable() {
                            public void run() {
                                view.close();
                                player.openInventory(createInv(i, player));
                            }
                        }.runTask(HoloItems.getInstance());
                    }
                    return;
            }
            updateInv(index, player, top);
            return;
        }

        if(event.getRawSlot()>=top.getSize() || !view.getTitle().equals("Collections"))
            return;
        ItemMeta meta = curr.getItemMeta();
        List<String> lore = meta.getLore();
        if(lore==null)
            return;
        Collection collection = collections.get(meta.getDisplayName().substring(2));
        Map<String, Integer> stat = collection.getStat(player);

        if(event.getClick()==ClickType.RIGHT) {
            if(stat==null)
                return;
            if(lore.get(lore.size() - 1).contains(collection.theme)){
                lore.remove(lore.size()-1);
                for(String key : stat.keySet())
                    lore.add("§f"+key+": §7"+stat.get(key));
                meta.setLore(lore);
                curr.setItemMeta(meta);
                return;
            }
            for(int i=0; i<stat.size(); i++)
                lore.remove(lore.size()-1);
            lore.add("§f"+collection.theme+": §7"+Utility.add(stat));
            meta.setLore(lore);
            curr.setItemMeta(meta);
            return;
        }

        int cost = stat==null?0:Utility.add(stat);
        Inventory inv = Bukkit.createInventory(null, ((collection.collection.size()-1)/9+1)*9, "Collections: "+collection.name);
        ItemStack locked = new ItemStack(Material.FIREWORK_STAR);
        int count = 0;
        for(Item item : collection.collection) {
            if(item.cost==-1)
                continue;
            if(cost<item.cost)
                inv.setItem(count, tilUnlocked(locked, item.name, item.cost-cost));
            else {
                inv.addItem(item.item);
                //temp: auto-discover when npc unlock conditions met
                for(Recipe recipe : Bukkit.getRecipesFor(item.item)){
                    if(recipe instanceof ShapedRecipe)
                        player.discoverRecipe(((ShapedRecipe) recipe).getKey());
                    else if(recipe instanceof ShapelessRecipe)
                        player.discoverRecipe(((ShapelessRecipe) recipe).getKey());
                    else if(recipe instanceof FurnaceRecipe)
                        player.discoverRecipe(((FurnaceRecipe) recipe).getKey());
                    else if(recipe instanceof BlastingRecipe)
                        player.discoverRecipe(((BlastingRecipe) recipe).getKey());
                }
            }
            count++;
        }
        inv.setItem(8, top.getItem(event.getRawSlot()/9*9));
        Utility.reOpen(view, inv, player);
    }

    @Override
    public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equals("acquire") && sender instanceof Player && args.length==1) {
            List<String> list = new ArrayList<>(items.keySet());
            list.removeIf(name -> !Pattern.compile(Pattern.quote(args[0]), Pattern.CASE_INSENSITIVE).matcher(name).find());
            if(!sender.hasPermission("holoItems.op"))
                list.removeIf(name -> items.get(name).cost==-1);
            return list;
        }
        return new ArrayList<>();
    }

    private static void setupHeads(){
        for(Collection collection : collections.values()){
            if(collection.base64==null)
                continue;
            ItemStack head = SkullCreator.itemFromBase64(collection.base64);
            ItemMeta headMeta = head.getItemMeta();
            headMeta.setDisplayName("§b"+collection.name);
            if(!collection.desc.isEmpty()) {
                List<String> desc = Utility.processStr(collection.desc);
                desc.remove(0);
                desc.add("");
                headMeta.setLore(desc);
            }
            head.setItemMeta(headMeta);
            heads.put(collection.name, head);
        }
    }

    private static Inventory createInv(Integer index, Player player){
        return updateInv(index, player, Bukkit.createInventory(null, 54, "Collections"));
    }

    private static Inventory updateInv(Integer index, Player player, Inventory inv){
        inv.clear();
        if(index <= 0)
            index = 0;
        else
            inv.setItem(8, front.get(-1));
        if(index >= gens.size()-7) {
            index = gens.size() - 7;
            inv.setItem(53, front.get(-3));
        } else
            inv.setItem(53, front.get(-2));
        for(int i=0; i<6; i++){
            int slot = i*9;
            inv.setItem(slot, front.get(index+i));
            int j = 2;
            for(Collection collection : gens.get(index+i)){
                ItemStack item = heads.get(collection.name);
                item = item.clone();
                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore();
                if (lore == null)
                    lore = new ArrayList<>();
                lore.add("§f" + collection.theme + ": §7" + Utility.add(collection.getStat(player)));
                meta.setLore(lore);
                item.setItemMeta(meta);
                inv.setItem(slot+j, item);
                j++;
            }
        }
        return inv;
    }

    private static ItemStack tilUnlocked(ItemStack item, String name, int cost){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§f"+Utility.formatName(name));
        meta.setLore(new ArrayList<>(java.util.Collections.singletonList("§7" + cost + " til Unlocked")));
        item.setItemMeta(meta);
        return item;
    }

    private static void item(Material material, String name, int[] index){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6"+name);
        item.setItemMeta(meta);
        front.put(index[0], item);
        index[0] = index[0] + 1;
    }
}
