package com.klin.holoItems;

import com.klin.holoItems.collections.en.calliCollection.CalliCollection;
import com.klin.holoItems.collections.en.guraCollection.GuraCollection;
import com.klin.holoItems.collections.en.inaCollection.InaCollection;
import com.klin.holoItems.collections.en.irysCollection.IrysCollection;
import com.klin.holoItems.collections.en.kiaraCollection.KiaraCollection;
import com.klin.holoItems.collections.en.watsonCollection.WatsonCollection;
import com.klin.holoItems.collections.gamers.koroneCollection.KoroneCollection;
import com.klin.holoItems.collections.gamers.mioCollection.MioCollection;
import com.klin.holoItems.collections.gamers.okayuCollection.OkayuCollection;
import com.klin.holoItems.collections.gen0.azkiCollection.AzkiCollection;
import com.klin.holoItems.collections.gen0.mikoCollection.MikoCollection;
import com.klin.holoItems.collections.gen0.robocosanCollection.RobocosanCollection;
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
import com.klin.holoItems.collections.holostars.aruranCollection.AruranCollection;
import com.klin.holoItems.collections.holostars.astelCollection.AstelCollection;
import com.klin.holoItems.collections.holostars.izuruCollection.IzuruCollection;
import com.klin.holoItems.collections.holostars.miyabiCollection.MiyabiCollection;
import com.klin.holoItems.collections.holostars.ogaCollection.OgaCollection;
import com.klin.holoItems.collections.holostars.rikkaCollection.RikkaCollection;
import com.klin.holoItems.collections.holostars.roberuCollection.RoberuCollection;
import com.klin.holoItems.collections.holostars.shienCollection.ShienCollection;
import com.klin.holoItems.collections.holostars.temmaCollection.TemmaCollection;
import com.klin.holoItems.collections.id1.iofiCollection.IofiCollection;
import com.klin.holoItems.collections.id1.moonaCollection.MoonaCollection;
import com.klin.holoItems.collections.id1.risuCollection.RisuCollection;
import com.klin.holoItems.collections.id2.anyaCollection.AnyaCollection;
import com.klin.holoItems.collections.id2.ollieCollection.OllieCollection;
import com.klin.holoItems.collections.id2.reineCollection.ReineCollection;
import com.klin.holoItems.collections.misc.achanCollection.AchanCollection;
import com.klin.holoItems.collections.misc.franCollection.FranCollection;
import com.klin.holoItems.collections.misc.ingredientCollection.IngredientCollection;
import com.klin.holoItems.collections.misc.klinCollection.KlinCollection;
import com.klin.holoItems.collections.misc.opCollection.OpCollection;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.*;

public class Collections implements CommandExecutor, Listener, TabCompleter {
    public static Map<String, Collection> collections = new LinkedHashMap<>();
    public static final Set<Integer> taskIds = new HashSet<>();
    private static final Map<String, ItemStack> heads = new HashMap<>();
    private static ItemStack back;
    public static Set<String> disabled = new HashSet<>();
    public static Map<String, Item> items = new LinkedHashMap<>();

    public Collections(){
        collections.put(IngredientCollection.name, new IngredientCollection());

        collections.put(SuiseiCollection.name, new SuiseiCollection());
        collections.put(SoraCollection.name, new SoraCollection());
        collections.put(RobocosanCollection.name, new RobocosanCollection());
        collections.put(MikoCollection.name, new MikoCollection());
        collections.put(AzkiCollection.name, new AzkiCollection());

        collections.put(MelCollection.name, new MelCollection());
        collections.put(FubukiCollection.name, new FubukiCollection());
        collections.put(MatsuriCollection.name, new MatsuriCollection());
        collections.put(AkiCollection.name, new AkiCollection());
        collections.put(HaachamaCollection.name, new HaachamaCollection());

        collections.put(AquaCollection.name, new AquaCollection());
        collections.put(ShionCollection.name, new ShionCollection());
        collections.put(AyameCollection.name, new AyameCollection());
        collections.put(ChocoCollection.name, new ChocoCollection());
        collections.put(SubaruCollection.name, new SubaruCollection());

        collections.put(MioCollection.name, new MioCollection());
        collections.put(OkayuCollection.name, new OkayuCollection());
        collections.put(KoroneCollection.name, new KoroneCollection());

        collections.put(PekoraCollection.name, new PekoraCollection());
        collections.put(RushiaCollection.name, new RushiaCollection());
        collections.put(FlareCollection.name, new FlareCollection());
        collections.put(NoelCollection.name, new NoelCollection());
        collections.put(MarineCollection.name, new MarineCollection());

        collections.put(KanataCollection.name, new KanataCollection());
        collections.put(CocoCollection.name, new CocoCollection());
        collections.put(WatameCollection.name, new WatameCollection());
        collections.put(TowaCollection.name, new TowaCollection());
        collections.put(LunaCollection.name, new LunaCollection());

        collections.put(RisuCollection.name, new RisuCollection());
        collections.put(MoonaCollection.name, new MoonaCollection());
        collections.put(IofiCollection.name, new IofiCollection());

        collections.put(LamyCollection.name, new LamyCollection());
        collections.put(NeneCollection.name, new NeneCollection());
        collections.put(BotanCollection.name, new BotanCollection());
        collections.put(PolkaCollection.name, new PolkaCollection());

        collections.put(CalliCollection.name, new CalliCollection());
        collections.put(KiaraCollection.name, new KiaraCollection());
        collections.put(InaCollection.name, new InaCollection());
        collections.put(GuraCollection.name, new GuraCollection());
        collections.put(WatsonCollection.name, new WatsonCollection());

        collections.put(OllieCollection.name, new OllieCollection());
        collections.put(AnyaCollection.name, new AnyaCollection());
        collections.put(ReineCollection.name, new ReineCollection());

        collections.put(IrysCollection.name, new IrysCollection());
        collections.put(AchanCollection.name, new AchanCollection());

        collections.put(MiyabiCollection.name, new MiyabiCollection());
        collections.put(IzuruCollection.name, new IzuruCollection());
        collections.put(AruranCollection.name, new AruranCollection());
        collections.put(RikkaCollection.name, new RikkaCollection());
        collections.put(AstelCollection.name, new AstelCollection());
        collections.put(TemmaCollection.name, new TemmaCollection());
        collections.put(RoberuCollection.name, new RoberuCollection());
        collections.put(ShienCollection.name, new ShienCollection());
        collections.put(OgaCollection.name, new OgaCollection());

        collections.put(FranCollection.name, new FranCollection());
        collections.put(KlinCollection.name, new KlinCollection());
        collections.put(OpCollection.name, new OpCollection());

        back = new ItemStack(Material.SPRUCE_TRAPDOOR);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName("§fBack");
        back.setItemMeta(backMeta);

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
                if (heads.isEmpty())
                    return true;
                Inventory inv = Bukkit.createInventory(null, 54, "Collections");
                for (Collection collection : collections.values()) {
                    if (collection.base64 == null)
                        continue;
                    ItemStack item = heads.get(collection.name);
                    item = item.clone();
                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = meta.getLore();
                    if (lore == null)
                        lore = new ArrayList<>();
                    lore.add("§7" + collection.theme + ": " + Utility.add(collection.getStat(player)));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    inv.addItem(item);
                }
                player.openInventory(inv);
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
                        int data = id.charAt(0)*10 + Character.getNumericValue(id.charAt(1));
                        if(!meta.hasCustomModelData() || meta.getCustomModelData()!=data) {
                            meta.setCustomModelData(data);
                            model.setItemMeta(meta);
                            player.sendMessage("Updated CustomModelData to "+data);
                        }
                        else
                            player.sendMessage("This item's CustomModelData is "+data);
                    }
                }
                else
                    player.sendMessage("Invalid item");
                return true;

            case "acquire":
                if(!player.isOp() && player.getGameMode()!=GameMode.CREATIVE || args.length<1) {
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
                if (item == null) {
                    player.sendMessage("No such item");
                    return true;
                }
                player.getInventory().addItem(item.item);
                player.sendMessage(Utility.formatName(item.name) + " acquired");
                return true;
        }

        if(!player.isOp())
            return true;
        switch(command){
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
                    Statistic stat = Statistic.valueOf(args[0]);
                    switch(stat.getType()){
                        case ITEM:
                        case BLOCK:
                            Material type = Material.valueOf(args[1]);
                            int i = Integer.parseInt(args[2]);
                            player.setStatistic(stat, type, i);
                            player.sendMessage(stat+":"+type+" set to "+i);
                            break;
                        case ENTITY:
                            EntityType entity = EntityType.valueOf(args[1]);
                            int j = Integer.parseInt(args[2]);
                            player.setStatistic(stat, entity, j);
                            player.sendMessage(stat+":"+entity+" set to "+j);
                            break;
                        case UNTYPED:
                            int k = Integer.parseInt(args[1]);
                            player.setStatistic(stat, k);
                            player.sendMessage(stat+" set to "+k);
                    }
                }
                catch(IllegalArgumentException e){
                    player.sendMessage("Invalid argument/s");
                    return true;
                }
                return true;

            case "disable":
                if(args.length>=1) {
                    Item item = items.get(args[0]);
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
                    Item item = items.get(args[0]);
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
                ItemStack model = player.getInventory().getItemInMainHand();
                if(model.getType()!=Material.AIR && model.getItemMeta()!=null) {
                    ItemMeta meta = model.getItemMeta();
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
                    ItemMeta meta = stack.getItemMeta();
                    meta.getPersistentDataContainer().set(Utility.enchant, PersistentDataType.STRING, enchantments);
                    stack.setItemMeta(meta);
                    player.sendMessage("Enchantments set to: "+enchantments);
                }
                return true;

            case "settype":
                if(args.length<1)
                    return false;
                ItemStack item = player.getInventory().getItemInMainHand();
                if(item.getType()!=Material.AIR) {
                    Material type = Material.getMaterial(args[0]);
                    if(type!=null)
                        item.setType(type);
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
        if(view.getTopInventory().getHolder()!=null || !view.getTitle().contains("Collections"))
            return;
        ItemStack curr = event.getCurrentItem();
        if(curr==null)
            return;
        event.setCancelled(true);
        if(curr.equals(back)){
            new BukkitRunnable(){
                @Override
                public void run(){
                    view.close();
                    ((Player) event.getWhoClicked()).performCommand("collections");
                }
            }.runTask(HoloItems.getInstance());
            return;
        }
        if(event.getRawSlot()>=view.getTopInventory().getSize() || !view.getTitle().equals("Collections"))
            return;

        ItemMeta meta = curr.getItemMeta();
        Collection collection = collections.get(meta.getDisplayName().substring(2));
        Player player = (Player) event.getWhoClicked();

        List<String> lore = meta.getLore();
        if(lore==null)
            return;
        boolean expand = lore.get(lore.size() - 1).contains(collection.theme);
        Map<String, Integer> stat = collection.getStat(player);
        if(stat==null)
            return;

        if(event.getClick()==ClickType.RIGHT) {
            if(!expand){
                for(int i=0; i<stat.size(); i++)
                    lore.remove(lore.size()-1);
                lore.add("§7"+collection.theme+": "+Utility.add(collection.getStat(player)));
                meta.setLore(lore);
                curr.setItemMeta(meta);
                return;
            }
            lore.remove(lore.size()-1);
            for(String key : stat.keySet())
                lore.add("§7"+key+": "+stat.get(key));
            meta.setLore(lore);
            curr.setItemMeta(meta);
            return;
        }

        int cost = Utility.add(stat);
        Inventory inv = Bukkit.createInventory(null,
                ((collection.collection.size()-1)/9+1)*9, "Collections: "+collection.name);
        ItemStack locked = new ItemStack(Material.FIREWORK_STAR);
        int count = 0;
        for(Item item : collection.collection) {
            if(cost<item.cost)
                inv.setItem(count, tilUnlocked(locked, item.name, item.cost-cost));
            else
                inv.addItem(item.item);
            count++;
        }
        inv.setItem(8, back);

        Utility.reOpen(view, inv, player);
    }

    @Override
    public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equals("acquire") && sender instanceof Player && args.length==1) {
            List<String> list = new ArrayList<>(items.keySet());
            list.removeIf(name -> !name.startsWith(args[0]));
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
            headMeta.setDisplayName("§6"+collection.name);
            if(!collection.desc.isEmpty()) {
                List<String> desc = Utility.processStr(collection.desc);
                desc.add("");
                headMeta.setLore(desc);
            }
            head.setItemMeta(headMeta);
            heads.put(collection.name, head);
        }
    }

    private static ItemStack tilUnlocked(ItemStack item, String name, int cost){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§f"+Utility.formatName(name));
        meta.setLore(new ArrayList<>(java.util.Collections.singletonList("§7" + cost + " til Unlocked")));
        item.setItemMeta(meta);
        return item;
    }
}
