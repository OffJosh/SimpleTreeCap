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

    int blocksBroken = 0;

    public BreakBlockEventHandler() {
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent blockBreakEvent) {
        Block block = blockBreakEvent.getBlock();
        Material material = block.getBlockData().getMaterial();
        Player player = blockBreakEvent.getPlayer();
        GameMode gameMode = player.getGameMode();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if(isLog(material) && gameMode.equals(GameMode.SURVIVAL) && player.isSneaking()){
            blocksBroken = 1;
            breakJoinedLogs(block, heldItem, player);
        }

    }

    private boolean isLog(Material material) {
        return material.name().contains("LOG") || material.name().equals("CRIMSON_STEM") || material.name().equals("WARPED_STEM");
    }

    private void breakJoinedLogs(Block block, ItemStack heldItem, Player player){
        //TODO held item can sometimes be null if its broken.
        if(heldItem.toString().contains("_AXE") && blocksBroken < 16) {
            int x = block.getX();
            int y = block.getY();
            int z = block.getZ();
            World world = block.getWorld();

            Block northBlock = world.getBlockAt(x, y, z - 1);
            Block eastBlock = world.getBlockAt(x + 1, y, z);
            Block southBlock = world.getBlockAt(x, y, z + 1);
            Block westBlock = world.getBlockAt(x - 1, y, z);
            Block upBlock = world.getBlockAt(x, y + 1, z);
            Block downBlock = world.getBlockAt(x, y - 1, z);

            ArrayList<Block> blocks = new ArrayList<>();
            blocks.add(northBlock);
            blocks.add(eastBlock);
            blocks.add(southBlock);
            blocks.add(westBlock);
            blocks.add(upBlock);
            blocks.add(downBlock);

            for (Block newBlock : blocks) {
                if (isLog(newBlock.getBlockData().getMaterial())) {
                    newBlock.breakNaturally();
                    damageItem(heldItem, player);
                    blocksBroken++;
                    breakJoinedLogs(newBlock, heldItem, player);
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




