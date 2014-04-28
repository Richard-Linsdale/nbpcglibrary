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
package linsdale.nbpcg.datasupportlib.entity;

import linsdale.nbpcg.datasupportlib.dataaccess.DataAccessRW;
import linsdale.nbpcg.datasupportlib.dbfields.DBFieldsRWIndexed;
import linsdale.nbpcg.supportlib.*;

/**
 * The abstract class defining an editable Entity, with a index (orderable)
 * field.
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 * @param <E> the entity class
 */
public abstract class EntityRWIndexed<E extends EntityRWIndexed> extends EntityRW<E> {

    private final DBFieldsRWIndexed<E> dbfields;
    private final DataAccessRW dataAccess;

    /**
     * Constructor.
     *
     * @param entityname the entity name
     * @param id the entity Id
     * @param em the entity manager for this entity class
     * @param dbfields the entity fields
     */
    public EntityRWIndexed(String entityname, int id, EntityManagerRW<E> em, DBFieldsRWIndexed<E> dbfields) {
        super(entityname, id, em, dbfields);
        this.dbfields = dbfields;
        this.dataAccess = em.getDataAccess();
    }

    @Override
    public final boolean save() {
        IntWithDescription oldState = getState();
        if (oldState == EntityStateChangeListenerParams.NEW || oldState == EntityStateChangeListenerParams.NEWEDITING) {
            if (dbfields.getIndex() == Integer.MAX_VALUE) {
                dbfields.setIndex(dataAccess.getNextIdx());
            }
        }
        return super.save();
    }

    /**
     * Get the Index Field value.
     *
     * @return the index value
     */
    public int getIndex() {
        return dbfields.getIndex();
    }

    /**
     * Set the Index Field value.
     *
     * @param i the index value
     */
    public final void setIndex(int i) {
        ensureEditing();
        dbfields.setIndex(i);
        fireFieldChange(FieldChangeListenerParams.IDXFIELD);
    }
}