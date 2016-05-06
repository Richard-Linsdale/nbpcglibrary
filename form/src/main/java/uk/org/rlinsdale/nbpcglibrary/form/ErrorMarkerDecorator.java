/*
 * Copyright (C) 2015-2016 Richard Linsdale.
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

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 * @param <T> type of data being handled by this field
 */
public class ErrorMarkerDecorator<T> extends FieldDecorator<T> {

    private final JLabel errorMarker = new JLabel();

    /**
     * the Constructor
     *
     * @param field the field to associate with this error marker.
     */
    public ErrorMarkerDecorator(FieldViewAPI<T> field) {
        super(field);
        errorMarker.setPreferredSize(new Dimension(16, 16));
    }

    @Override
    public List<JComponent> getViewComponents() {
        List<JComponent> c = new ArrayList<>();
        c.addAll(super.getViewComponents());
        c.add(errorMarker);
        return c;
    }

    @Override
    public void setErrorMarker(String errormessages) {
        errorMarker.setIcon(new ImageIcon(getClass().getResource(errormessages == null ? "empty.png" : "error.png")));
        errorMarker.setToolTipText(errormessages);
    }
}
