package com.github.entrypointkr.chinventory;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCInventory;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.bukkit.BukkitMCInventory;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.logging.Level;

/**
 * Created by JunHyeong Lim on 2018-06-27
 */
@MSExtension("CHWindow")
public class CHInventory extends AbstractExtension {
    public static Optional<Integer> parseInteger(String contents) {
        try {
            return Optional.of(Integer.parseInt(contents));
        } catch (Exception ex) {
            // Empty
        }
        return Optional.empty();
    }

    public static Optional<Integer> parseInteger(Construct construct) {
        if (construct instanceof CString) {
            return parseInteger(construct.val());
        } else if (construct instanceof CInt) {
            return Optional.of((int) ((CInt) construct).getInt());
        }
        return Optional.empty();
    }

    public static MCInventory createInventory(CArray array, Target t) {
        Validate.isTrue(array.isAssociative());
        ArrayWrapper wrapper = new ArrayWrapper(array);
        InventoryType type = wrapper.get("type", CString.class)
                .map(typeCstr -> InventoryType.valueOf(typeCstr.val()))
                .orElse(InventoryType.CHEST);
        String title = wrapper.get("title", CString.class)
                .map(CString::val)
                .orElse(type.getDefaultTitle());
        Inventory inventory = type != InventoryType.CHEST
                ? Bukkit.createInventory(null, type, title)
                : Bukkit.createInventory(
                null,
                wrapper.get("size", CInt.class).map(CInt::getInt).orElse(9L).intValue(),
                title
        );
        wrapper.get("items", CArray.class)
                .ifPresent(items -> items.keySet().stream()
                        .map(CHInventory::parseInteger)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(i -> {
                            Construct value = items.get(i, t);
                            MCItemStack item = ObjectGenerator.GetGenerator().item(value, t);
                            inventory.setItem(i, (ItemStack) item.getHandle());
                        }));
        return new BukkitMCInventory(inventory);
    }

    @Override
    public Version getVersion() {
        return new SimpleVersion(1, 0, 0);
    }

    @Override
    public void onStartup() {
        Bukkit.getLogger().log(Level.INFO, "CHInventory " + getVersion() + " enabled.");
    }

    @Override
    public void onShutdown() {
        Bukkit.getLogger().log(Level.INFO, "CHInventory " + getVersion() + " enabled.");
    }
}
