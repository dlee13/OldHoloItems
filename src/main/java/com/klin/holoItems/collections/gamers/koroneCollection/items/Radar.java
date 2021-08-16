package com.klin.holoItems.collections.gamers.koroneCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gamers.koroneCollection.KoroneCollection;
import com.klin.holoItems.interfaces.Swappable;
import com.klin.holoItems.utility.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.util.HashSet;
import java.util.Set;

public class Radar extends Item implements Swappable {
    public static final String name = "radar";
    protected static Set<Player> trackers = new HashSet<>();

    private static final Material material = Material.FILLED_MAP;
    private static final int quantity = 1;
    private static final String lore =
            "Track prey";
    private static final int durability = 0;
    private static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '0';
    public static final String id = ""+KoroneCollection.key+key;

    public Radar(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aba","bcb", "aba");
        recipe.setIngredient('a', Material.PAPER);
        recipe.setIngredient('b', Material.COPPER_INGOT);
        recipe.setIngredient('c', Material.COMPASS);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerSwapHandItemsEvent event, Player player, ItemStack item, boolean main){
        if(main)
            return;
        MapMeta meta = (MapMeta) item.getItemMeta();
        MapMeta mapMeta = (MapMeta) this.item.getItemMeta();
        if(!mapMeta.hasMapView()) {
            World world = player.getWorld();
            MapView view = Bukkit.getMap(0);
            if(view==null)
                view = Bukkit.createMap(world);
            view.setScale(MapView.Scale.CLOSE);
            view.addRenderer(new CircleRenderer());
            view.addRenderer(new RadarRenderer());
            mapMeta.setMapView(view);
            this.item.setItemMeta(mapMeta);
            if(!meta.hasMapView()) {
                meta.setMapView(view);
                item.setItemMeta(meta);
            }
        }
        else if(!meta.hasMapView()) {
            meta.setMapView(mapMeta.getMapView());
            item.setItemMeta(meta);
        }

        trackers.add(player);
        new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            public void run(){
                if(increment>=1200 || !item.equals(player.getInventory().getItemInOffHand())){
                    trackers.remove(player);
                    cancel();
                    return;
                }
                increment++;
            }
        };
    }
}
