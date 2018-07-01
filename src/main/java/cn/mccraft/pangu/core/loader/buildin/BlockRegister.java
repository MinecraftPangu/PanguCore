package cn.mccraft.pangu.core.loader.buildin;

import cn.mccraft.pangu.core.PanguCore;
import cn.mccraft.pangu.core.loader.BaseRegister;
import cn.mccraft.pangu.core.loader.RegisteringItem;
import cn.mccraft.pangu.core.loader.annotation.RegBlock;
import cn.mccraft.pangu.core.util.NameBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;

/**
 * Block register
 *
 * @author mouse
 */
public class BlockRegister extends BaseRegister<Block, RegBlock> {

    @SubscribeEvent
    public void registerItem(RegistryEvent.Register<Block> event) {
        for (RegisteringItem<Block, RegBlock> registeringItem : itemSet) try {
            Block block = registeringItem.getItem();
            RegBlock regBlock = registeringItem.getAnnotation();

            String[] name = regBlock.value();
            if (name.length == 0) {
                name = NameBuilder.apart(registeringItem.getField().getName());
            }

            // start register
            event.getRegistry().register(
                    // set registry name
                    block.setRegistryName(registeringItem.buildRegistryName(name))
                            // set unlocalized name
                            .setUnlocalizedName(registeringItem.buildUnlocalizedName(name))
            );

            // check if there contains ore dict
            if (regBlock.oreDict().length > 0) {
                // for each all ore dict from RegItem
                for (String oreName : regBlock.oreDict()) {
                    // registering ore dictionary to item
                    OreDictionary.registerOre(oreName, block);
                }
            }
        } catch (Exception ex) {
            PanguCore.getLogger().error("Unable to register model of " + registeringItem.getField().toGenericString(), ex);
        }
        PanguCore.getLogger().info("Processed " + itemSet.size() + " RegBlock annotations");
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void registerItemBlocks(RegistryEvent.Register<Item> event) {
        itemSet.stream()
                // check if need register ItemBlock
                .filter(it -> it.getAnnotation().isRegisterItemBlock())
                // create ItemBlock and set RegistryName
                .map(it -> new ItemBlock(it.getItem()).setRegistryName(it.getItem().getRegistryName()))
                // registering
                .forEach(event.getRegistry()::register);
    }
}
