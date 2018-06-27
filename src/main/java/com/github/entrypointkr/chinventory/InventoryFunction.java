package com.github.entrypointkr.chinventory;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCInventory;
import com.laytonsmith.abstraction.MCInventoryView;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;

import java.util.Optional;

/**
 * Created by JunHyeong Lim on 2018-06-27
 */
public class InventoryFunction {
    @api
    public static class InventoryOpen extends AbstractFunction {
        @Override
        public Class<? extends CREThrowable>[] thrown() {
            return new Class[0];
        }

        @Override
        public boolean isRestricted() {
            return false;
        }

        @Override
        public Boolean runAsync() {
            return false;
        }

        @Override
        public Construct exec(Target t, Environment env, Construct... args) throws ConfigRuntimeException {
            MCPlayer player = args.length > 2
                    ? Static.GetPlayer(args[0], t)
                    : Static.getPlayer(env, t);
            CArray array = Static.getArray(args.length > 2 ? args[1] : args[0], t);
            player.openInventory(CHInventory.createInventory(array, t));
            return CVoid.VOID;
        }

        @Override
        public Version since() {
            return CHVersion.V3_3_2;
        }

        @Override
        public String getName() {
            return "inventory_open";
        }

        @Override
        public Integer[] numArgs() {
            return new Integer[]{1, 2};
        }

        @Override
        public String docs() {
            return "";
        }
    }

    @api
    public static class InventoryTitle extends AbstractFunction {
        @Override
        public Class<? extends CREThrowable>[] thrown() {
            return new Class[0];
        }

        @Override
        public boolean isRestricted() {
            return false;
        }

        @Override
        public Boolean runAsync() {
            return false;
        }

        @Override
        public Construct exec(Target t, Environment env, Construct... args) throws ConfigRuntimeException {
            MCPlayer player = args.length > 2
                    ? Static.GetPlayer(args[0], t)
                    : Static.getPlayer(env, t);
            return Optional.ofNullable(player.getOpenInventory())
                    .map(MCInventoryView::getTopInventory)
                    .map(MCInventory::getTitle)
                    .map(CString::GetConstruct)
                    .orElse(CNull.NULL);
        }

        @Override
        public Version since() {
            return CHVersion.V3_3_2;
        }

        @Override
        public String getName() {
            return "inventory_title";
        }

        @Override
        public Integer[] numArgs() {
            return new Integer[]{0, 1};
        }

        @Override
        public String docs() {
            return "";
        }
    }

}
