/*
 * Copyright 2014-2017 Richard Linsdale.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.theretiredprogrammer.nbpcglibrary.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Presenter for a table object
 *
 * @author Richard Linsdale (richard at theretiredprogrammer.uk)
 */
public class TablePresenter implements PanePresenter<RowPresenter>, ActionListener, ItemListener {

    private List<RowPresenter> rowpresenters = new ArrayList<>();
    private final Runnable newaction;
    private final TableView view;
    private List<FieldViewAPI> tableheaderfields;
    //
    private IconButton addbutton;
    private IconButton deletebutton;
    private IconButton copybutton;
    private JPanel buttons;
    private Supplier<List<RowPresenter>> getRowPresentersFunction;

    /**
     * Constructor
     *
     * @param newaction supplier to create a new entity
     * @param columnheadings the column headings to be displayed table
     */
    public TablePresenter(Runnable newaction, String... columnheadings) {
        this(null, newaction, columnheadings);
    }

    /**
     * Constructor
     *
     * @param title the table
     * @param newaction supplier to create a new entity
     * @param columnheadings the column headings to be displayed
     */
    public TablePresenter(String title, Runnable newaction, String... columnheadings) {
        List<String> colheadings = Arrays.asList(columnheadings);
        view = new TableView(title, colheadings.size() * 2 + 3);
        this.newaction = newaction;
        createButtons();
        createTableHeader(colheadings);
    }

    @Override
    public void setGetChildPresentersFunction(Supplier<List<RowPresenter>> getRowPresentersFunction) {
        this.getRowPresentersFunction = getRowPresentersFunction;
    }

    @Override
    public TableView getView() {
        return view;
    }

    private void createTableHeader(List<String> columnheadings) {
        tableheaderfields = new ArrayList<>();
        tableheaderfields.add(new NullField(2));
        columnheadings.stream().forEach((label) -> {
            tableheaderfields.add(new LabelDecorator(label, new NullField(1)));
        });
    }

    private void createButtons() {
        buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        buttons.add(addbutton = new IconButton("add", "Add new line"));
        addbutton.setActionCommand("add");
        addbutton.addActionListener(this);
        buttons.add(deletebutton = new IconButton("delete", "Delete line"));
        deletebutton.setEnabled(false);
        deletebutton.setActionCommand("delete");
        deletebutton.addActionListener(this);
        buttons.add(copybutton = new IconButton("arrow_divide", "Copy line"));
        copybutton.setEnabled(false);
        copybutton.setActionCommand("copy");
        copybutton.addActionListener(this);
    }

    private void drawTable() {
        deletebutton.setEnabled(false);
        copybutton.setEnabled(false);
        view.clear();
        view.insertChildViews(tableheaderfields);
        rowpresenters = getRowPresentersFunction.get();
        rowpresenters.stream().forEach(presenter -> presenter.enableView());
        view.insertSpannedRow(buttons);
        view.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                newaction.run();
                break;
            case "delete":
                rowpresenters.stream().filter(rowpresenter -> rowpresenter.isChecked()).
                        forEach(rowpresenter -> rowpresenter.delete());
                break;
            case "copy":
                rowpresenters.stream().filter(rowpresenter -> rowpresenter.isChecked()).
                        forEach(rowpresenter -> rowpresenter.copy());
                break;
        }
        drawTable();
    }

    @Override
    public final void itemStateChanged(ItemEvent e) {
        if (rowpresenters.stream().filter(rowpresenter -> rowpresenter.isChecked()).count() == 0) {
            deletebutton.setEnabled(false);
            copybutton.setEnabled(false);
        } else {
            deletebutton.setEnabled(true);
            copybutton.setEnabled(true);
        }
    }

    @Override
    public boolean test(StringBuilder sb) {
        return rowpresenters.stream().filter(presenter -> !presenter.test(sb)).count() == 0;
    }

    @Override
    public boolean save(StringBuilder sb) {
        return rowpresenters.stream().filter(presenter -> !presenter.save(sb)).count() == 0;
    }

    @Override
    public void enableView() {
        drawTable();
    }

    @Override
    public void refreshView() {
        rowpresenters.stream().forEach(rp -> rp.refreshView());
    }
}
