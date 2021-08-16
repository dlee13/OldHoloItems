package com.klin.holoItems;

import com.klin.holoItems.collections.en.calliCollection.CalliCollection;
import com.klin.holoItems.collections.en.guraCollection.GuraCollection;
import com.klin.holoItems.collections.en.inaCollection.InaCollection;
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
import com.klin.holoItems.collections.misc.opCollection.OpCollection;
import com.klin.holoItems.collections.misc.opCollection.Recipes;
import com.klin.holoItems.collections.misc.ingredientCollection.IngredientCollection;
import com.klin.holoItems.collections.misc.klinCollection.KlinCollection;
import com.klin.holoItems.collections.misc.utilityCollection.UtilityCollection;
import com.klin.holoItems.interfaces.Activatable;
import com.klin.holoItems.utility.SkullCreator;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Collections implements CommandExecutor, Listener {
    public static Map<Character, Collection> collections = new LinkedHashMap<>();
    private static Map<String, ItemStack> heads = new HashMap<>();
    private static String registry;
    private static ItemStack back;
    public static Set<String> disabled = new HashSet<>();

    Collections(){
        collections.put(IngredientCollection.key, new IngredientCollection());

        collections.put(SuiseiCollection.key, new SuiseiCollection());
        collections.put(SoraCollection.key, new SoraCollection());
        collections.put(RobocosanCollection.key, new RobocosanCollection());
        collections.put(MikoCollection.key, new MikoCollection());
        collections.put(AzkiCollection.key, new AzkiCollection());

        collections.put(MelCollection.key, new MelCollection());
        collections.put(FubukiCollection.key, new FubukiCollection());
        collections.put(MatsuriCollection.key, new MatsuriCollection());
        collections.put(AkiCollection.key, new AkiCollection());
        collections.put(HaachamaCollection.key, new HaachamaCollection());

        collections.put(AquaCollection.key, new AquaCollection());
        collections.put(ShionCollection.key, new ShionCollection());
        collections.put(AyameCollection.key, new AyameCollection());
        collections.put(ChocoCollection.key, new ChocoCollection());
        collections.put(SubaruCollection.key, new SubaruCollection());

        collections.put(MioCollection.key, new MioCollection());
        collections.put(OkayuCollection.key, new OkayuCollection());
        collections.put(KoroneCollection.key, new KoroneCollection());

        collections.put(PekoraCollection.key, new PekoraCollection());
        collections.put(RushiaCollection.key, new RushiaCollection());
        collections.put(FlareCollection.key, new FlareCollection());
        collections.put(NoelCollection.key, new NoelCollection());
        collections.put(MarineCollection.key, new MarineCollection());

        collections.put(KanataCollection.key, new KanataCollection());
        collections.put(CocoCollection.key, new CocoCollection());
        collections.put(WatameCollection.key, new WatameCollection());
        collections.put(TowaCollection.key, new TowaCollection());
        collections.put(LunaCollection.key, new LunaCollection());

        collections.put(RisuCollection.key, new RisuCollection());
        collections.put(MoonaCollection.key, new MoonaCollection());
        collections.put(IofiCollection.key, new IofiCollection());

        collections.put(LamyCollection.key, new LamyCollection());
        collections.put(NeneCollection.key, new NeneCollection());
        collections.put(BotanCollection.key, new BotanCollection());
        collections.put(PolkaCollection.key, new PolkaCollection());

        collections.put(CalliCollection.key, new CalliCollection());
        collections.put(KiaraCollection.key, new KiaraCollection());
        collections.put(InaCollection.key, new InaCollection());
        collections.put(GuraCollection.key, new GuraCollection());
        collections.put(WatsonCollection.key, new WatsonCollection());

        collections.put(OllieCollection.key, new OllieCollection());
        collections.put(AnyaCollection.key, new AnyaCollection());
        collections.put(ReineCollection.key, new ReineCollection());

        //collections.put(IrysCollection.key, new IrysCollection());

        collections.put(AchanCollection.key, new AchanCollection());

        collections.put(MiyabiCollection.key, new MiyabiCollection());
        collections.put(IzuruCollection.key, new IzuruCollection());
        collections.put(AruranCollection.key, new AruranCollection());
        collections.put(RikkaCollection.key, new RikkaCollection());
        collections.put(AstelCollection.key, new AstelCollection());
        collections.put(TemmaCollection.key, new TemmaCollection());
        collections.put(RoberuCollection.key, new RoberuCollection());
        collections.put(ShienCollection.key, new ShienCollection());
        collections.put(OgaCollection.key, new OgaCollection());

        collections.put(FranCollection.key, new FranCollection());
        collections.put(KlinCollection.key, new KlinCollection());
        collections.put(OpCollection.key, new OpCollection());
        collections.put(UtilityCollection.key, new UtilityCollection());

        Recipes.registerRecipes();

        registry = "§6HoloItems"+"\n";
        for(Collection collection : collections.values()){
            if(collection.key=='X')
                break;
            registry += "§7"+collection.name+"\n§f";
            for(Item item : collection.collection.values()){
                registry += ""+collection.key+item.key+" "+item.name+"\n";
            }
        }
        registry = registry.substring(0, registry.length()-2);

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
                    if (collection.uuid == null)
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

            case "worldname":
                player.sendMessage(player.getWorld().getName());
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
                if(!player.isOp() && player.getGameMode()!=GameMode.CREATIVE)
                    return true;
                if (args.length >= 1 && !(!player.isOp() &&
                        (args[0].contains("X") || args[0].contains("Y") || args[0].contains("Z")))) {
                    Item item = findItem(args[0]);
                    if (item == null) {
                        player.sendMessage("No such item");
                        return true;
                    }
                    player.getInventory().addItem(item.item);
                    player.sendMessage(Utility.formatName(item.name) + " acquired");
                }
                else
                    player.sendMessage("No such item");
                return true;

            case "registry":
                player.sendMessage(registry);
                return true;
        }

        if(!player.isOp())
            return true;

        switch(command){
            case "disable":
                if(args.length>=1) {
                    Item item = findItem(args[0]);
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
                    Item item = findItem(args[0]);
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

            case "collect":
                if(args.length>=3) {
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
                }
                else
                    player.sendMessage("No such statistic");
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
                    player.sendMessage("No such head");
                return true;

            case "setcustommodeldata":
                ItemStack model = player.getInventory().getItemInMainHand();
                if(args.length>=1 && model.getType()!=Material.AIR && model.getItemMeta()!=null) {
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

            case "settype":
                ItemStack item = player.getInventory().getItemInMainHand();
                if(item.getType()!=Material.AIR && args.length>=1) {
                    Material type = Material.getMaterial(args[0]);
                    if(type!=null)
                        item.setType(type);
                }
                return true;

            case "setenchantments":
                ItemStack stack = player.getInventory().getItemInMainHand();
                if(stack.getType()!=Material.AIR && stack.getItemMeta()!=null && args.length>=1) {
                    String enchantments = "";
                    for(int i=0; i<args.length; i++)
                        enchantments += args[i] + " ";
                    enchantments = enchantments.substring(0, enchantments.length()-1);
                    ItemMeta meta = stack.getItemMeta();
                    meta.getPersistentDataContainer().set(Utility.enchant, PersistentDataType.STRING, enchantments);
                    stack.setItemMeta(meta);
                    player.sendMessage("Enchantments set to: "+enchantments);
                }
                return true;

            case "removechunktickets":
                World world = player.getWorld();
                java.util.Collection<Chunk> chunks = world.getPluginChunkTickets().get(HoloItems.getInstance());
                int quantity = chunks==null?0:chunks.size();
                player.sendMessage("Chunk tickets to be removed: "+quantity);
                if(quantity>0)
                    world.removePluginChunkTickets(HoloItems.getInstance());
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

            case "clearactivatables":
                for(Activatable activatable : Events.activatables)
                    activatable.survey().clear();
                Events.activatables.clear();
                player.sendMessage("Activatables cleared");
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
        if(event.getRawSlot() >= view.getTopInventory().getSize())
            return;
        if(!view.getTitle().equals("Collections"))
            return;

        ItemMeta meta = curr.getItemMeta();
        char id = meta.getDisplayName().charAt(meta.getDisplayName().length()-1);
        Collection collection = findCollection(id);
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
        for(Item item : collection.collection.values()) {
            if(cost<item.cost)
                inv.setItem(count, tilUnlocked(locked, item.name, item.cost-cost));
            else
                inv.addItem(item.item);
            count++;
        }
        inv.setItem(8, back);

        Utility.reOpen(view, inv, player);
    }

    public static Collection findCollection(char key){
        return collections.get(key);
    }

    public static Item findItem(String id){
        if(id==null || id.length()!=2)
            return null;
        Collection collection = findCollection(id.charAt(0));
        if(collection==null)
            return null;
        return collection.collection.get(id.charAt(1));
    }

    private static void setupHeads(){
        for(Collection collection : collections.values()){
            if(collection.ign==null)
                continue;
            ItemStack head = SkullCreator.itemFromBase64(collection.base64);
            ItemMeta headMeta = head.getItemMeta();
            headMeta.setDisplayName("§6"+collection.name+"§0"+collection.key);
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
        meta.setLore(new ArrayList<>(Arrays.asList("§7"+cost+" til Unlocked")));
        item.setItemMeta(meta);
        return item;
    }
}
