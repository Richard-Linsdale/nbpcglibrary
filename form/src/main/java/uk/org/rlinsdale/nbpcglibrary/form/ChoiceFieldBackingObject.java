/*
 * Copyright (C) 2015 Richard Linsdale.
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
package uk.org.rlinsdale.nbpcglibrary.form;

import java.util.List;

/**
 * The backing Object interface for an editable field
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 */
public abstract class ChoiceFieldBackingObject implements FieldBackingObject<String> {

    /**
     * Get a set of Choice Strings
     * @return the set of choice strings
     */
    public abstract List<String> getChoices();

    // normally there is no need for validation on choices - provide default null implementations
    
    @Override
    public boolean checkRules() {
        return true;
    }

    @Override
    public String getErrorMessages() {
        return "";
    }
}
