/*
 * Copyright (C) 2014 Richard Linsdale (richard.linsdale at blueyonder.co.uk).
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package uk.org.rlinsdale.nbpcg.datasupportlib.entity;

import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * A registry of entity states which are centrally collated. The current use
 * case includes entities in error and entities which have been modified and
 * need saving.
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 */
public final class EntityRegistry {

    private static final InstanceContent IC = new InstanceContent();
    private static final Lookup LOOKUP = new AbstractLookup(IC);

    /**
     * Get the Registry.
     * 
     * @return the Registry (a Lookup)
     */
    public static Lookup getRegistry() {
        return LOOKUP;
    }

    /**
     * Test if there are any entity errors recorded in the registry.
     * 
     * @return true if 1 or more errors recorded
     */
    public static boolean hasErrors() {
        return LOOKUP.lookup(EntityInError.class) != null;
    }

    /**
     * Add an entity registration to the registry.
     * 
     * @param er the entity registration
     */
    public static void register(EntityRegistration er) {
        IC.add(er);
    }

    /**
     * Remove an entity registration from the registry.
     * @param er the entity registration
     */
    public static void unregister(EntityRegistration er) {
        IC.remove(er);
    }
}