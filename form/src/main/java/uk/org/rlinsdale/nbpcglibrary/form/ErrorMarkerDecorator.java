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
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import uk.org.rlinsdale.nbpcglibrary.common.CallbackReport;

/**
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 * @param <T> type of data being handled by this field
 */
public class ErrorMarkerDecorator<T> extends FieldDecorator<T> implements CallbackReport {

    private final JLabel errorMarker = new JLabel();

    @SuppressWarnings("LeakingThisInConstructor")
    public ErrorMarkerDecorator(EditableField<T> field) {
        super(field);
        field.setErrorReporter(this);
    }
    
    public ErrorMarkerDecorator() {
        super(null);
    }

    @Override
    public String instanceDescription() {
        return field.instanceDescription()+"/ERRORMARKER";
    }

    @Override
    public List<JComponent> getComponents() {
        List<JComponent> c = super.getComponents();
        c.add(errorMarker);
        return c;
    }

    @Override
    public void report(String errormessages) {
        errorMarker.setIcon(new ImageIcon(getClass().getResource("error.png")));
        errorMarker.setToolTipText(errormessages);
    }

    @Override
    public void clear() {
        errorMarker.setIcon(new ImageIcon(getClass().getResource("empty.png")));
        errorMarker.setToolTipText(null);
    }

}