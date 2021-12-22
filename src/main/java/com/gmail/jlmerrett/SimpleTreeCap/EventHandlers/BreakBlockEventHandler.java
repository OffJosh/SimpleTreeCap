package com.gmail.jlmerrett.SimpleTreeCap.EventHandlers;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

public class BreakBlockEventHandler implements Listener {

    private static final String TREECAP_META = "treecap_meta";

    public BreakBlockEventHandler() {
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent blockBreakEvent) {
        Block block = blockBreakEvent.getBlock();
        Material material = block.getBlockData().getMaterial();
        Player player = blockBreakEvent.getPlayer();
        GameMode gameMode = player.getGameMode();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        boolean enabled = player.getMetadata(TREECAP_META).get(0).asBoolean();

        if(isLog(material) && gameMode.equals(GameMode.SURVIVAL) && player.isSneaking() && enabled){
            breakJoinedLogs(block, heldItem, player);
        }
    }

    private boolean isLog(Material material) {
        if (material.name().contains("STRIPPED")){
            return false;
        }
        return material.name().contains("LOG") || material.name().equals("CRIMSON_STEM") || material.name().equals("WARPED_STEM");
    }

    private boolean isLeaf(Material material) {
        return material.name().contains("_LEAVES");
    }

    private void breakJoinedLogs(Block block, ItemStack heldItem, Player player){
        //TODO held item can sometimes be null if its broken.
        if(heldItem.toString().contains("_AXE")) {
            int x = block.getX();
            int y = block.getY();
            int z = block.getZ();
            World world = block.getWorld();
            ArrayList<Block> potentialLogBlocks = new ArrayList<>();
            ArrayList<Block> potentialLeafBlocks = new ArrayList<>();

            for(int i = -1; i <=1; i++){
                for (int j = -1; j <=1; j++){
                    for (int k = -1; k <=1; k++){
                        potentialLogBlocks.add(world.getBlockAt(x+i, y+j, z+k));
                    }
                }
            }

            for(int i = -3; i <=3; i++){
                for (int j = 0; j <=2; j++) {
                    for (int k = -3; k <=3; k++) {
                        potentialLeafBlocks.add(world.getBlockAt(x + i, y+j, z + k));
                    }
                }
            }

            for (Block newBlock : potentialLogBlocks) {
                if (isLog(newBlock.getBlockData().getMaterial())) {
                    newBlock.breakNaturally();
                    damageItem(heldItem, player);
                    breakJoinedLogs(newBlock, heldItem, player);
                }
            }

            for (Block newBlock : potentialLeafBlocks) {
                if (isLeaf(newBlock.getBlockData().getMaterial())) {
                    newBlock.breakNaturally();
                }
            }
        }
    }

    private void damageItem(ItemStack heldItem, Player player){
        int durability = heldItem.getType().getMaxDurability();
        Damageable item = (Damageable) heldItem.getItemMeta();
        int itemDamage = item.getDamage();
        if(willDamage(heldItem)) {
            item.setDamage(itemDamage+1);
        }
        heldItem.setItemMeta((ItemMeta) item);
        if(itemDamage >= durability){
            heldItem.setAmount(0);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
        }
    }

    public boolean willDamage(ItemStack heldItem){
        if(heldItem.containsEnchantment(Enchantment.DURABILITY)){
            int enchantmentLevel = heldItem.getEnchantmentLevel(Enchantment.DURABILITY);
            int chance = 100 / (enchantmentLevel + 1);
            Random random = new Random();
            int randomNumber = random.nextInt(100) + 1;
            return (randomNumber <= chance);
        }
        return true;
    }

}




