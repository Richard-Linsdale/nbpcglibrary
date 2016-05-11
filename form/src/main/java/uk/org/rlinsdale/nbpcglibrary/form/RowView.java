/*
 * Copyright (C) 2014-2016 Richard Linsdale (richard.linsdale at blueyonder.co.uk).
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

/**
 * Class representing a Row View
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 */
public class RowView implements PaneView<FieldViewAPI> {

    private final TableView parentview;

    /**
     * Constructor
     *
     * @param parentview the table view into which this row is to be inserted
     */
    public RowView(TableView parentview) {
        this.parentview = parentview;
    }

    @Override
    public void insertChildViews(List<FieldViewAPI> childviews) {
        parentview.insertChildViews(childviews);
    }

    @Override
    public JComponent getViewComponent() {
        return parentview.getViewComponent(); // not sure if this is correct as I dont think it will ever be called!!
    }
}
