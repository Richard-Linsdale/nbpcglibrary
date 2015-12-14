/*
 * Copyright (C) 2014-2015 Richard Linsdale (richard.linsdale at blueyonder.co.uk).
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
import javax.swing.JComponent;
import uk.org.rlinsdale.nbpcglibrary.common.LogBuilder;
import uk.org.rlinsdale.nbpcglibrary.api.HasInstanceDescription;

/**
 * A collection of fields - for use in defining the fields content of a form
 * segment.
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 */
public abstract class TableDef implements HasInstanceDescription {

//    private final List<Field> fields = new ArrayList<>();
//    private String[] parameters;
//    private final ErrorMarker errorMarker = new ErrorMarker();
    private final String title;
    private final FieldsDefRules tabledefrules;
    private final  List<String> headings;

    /**
     * Constructor
     *
     * @param title the table title (or null if no title to be displayed
     * @param tabledefrules the table level rules or null if notable level rules
     * @param headings the list of column headings
     */
    public TableDef(String title, FieldsDefRules tabledefrules, List<String> headings) {
        this.title = title;
        this.tabledefrules = tabledefrules;
        this.headings = headings;
    }

    @Override
    public String instanceDescription() {
        return LogBuilder.instanceDescription(this);
    }
    
    /**
     * Get the Title string for the table
     * 
     * @return the table title or null if no title required
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Get the list of column headings for this table
     * 
     * @return A list of column headings
     */
    public List<String> getColumnHeadings() {
        return headings;
    }
    
    /**
     * Get the list of row components for this table
     * 
     * @return A list of row components
     */
    public abstract List<JComponent> getRowComponents();

//    /**
//     * add a field to this collection.
//     *
//     * @param f the field to add
//     */
//    public final void add(Field f) {
//        fields.add(f);
//    }
//
//    /**
//     * Get the collection of fields.
//     *
//     * @return the collection of fields
//     */
//    public final List<Field> getFields() {
//        return fields;
//    }
//
//    /**
//     * Set the values of all fields.
//     */
//    public final void updateAllFieldsFromSource() {
//        fields.stream().forEach((f) -> {
//            f.updateFieldFromSource();
//        });
//    }
//
//    /**
//     * Set the values of all fields into sources.
//     */
//    public final void updateAllSourcesFromFields() {
//        fields.stream().filter((f) -> (f instanceof EditableField)).forEach((f) -> {
//            ((EditableField) f).updateSourceFromField();
//        });
//    }
//
//    /**
//     * Finalise the save action from source to other storage (eg persistent
//     * storage)
//     *
//     * @return true if save was successful
//     * @throws IOException if problems occurred during save
//     */
//    public abstract boolean save() throws IOException;
//
//    String[] getParameters() {
//        return parameters;
//    }
//
//    /**
//     * Set the parameters to be returned from the fielddef (consolidated at
//     * dialog completion)
//     *
//     * @param parameters the set of parameters
//     */
//    protected void setParameters(String... parameters) {
//        this.parameters = parameters;
//    }
//
//    /**
//     * Check if all rules in the collection's rule set and each individual field
//     * are valid.
//     *
//     * @return true if all rules are valid
//     */
//    public final boolean checkRules() {
//        boolean valid = true;
//        for (Field f : fields) {
//            if (f instanceof EditableField) {
//                if (!((EditableField) f).checkRules()) {
//                    valid = false;
//                }
//            }
//        }
//        if (!checkFieldsDefRules()) {
//            valid = false;
//        }
//        return valid;
//    }
//
//    /**
//     * Check the rules defined for the fieldDef.
//     *
//     * @return true if the rules are obeyed (ie OK)
//     */
//    public boolean checkFieldsDefRules() {
//        if (tabledefrules != null) {
//            boolean res = tabledefrules.checkRules();
//            if (res) {
//                errorMarker.clearError();
//            } else {
//                errorMarker.setError(tabledefrules.getErrorMessages());
//            }
//            return res;
//        } else {
//            return true;
//        }
//    }
}