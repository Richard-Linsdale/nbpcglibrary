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
package uk.org.rlinsdale.nbpcglibrary.common;

import java.util.logging.Level;

/**
 * Defines a rule to be associated with a field.
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 */
public abstract class Rule {

    private final String failuremessage;

    /**
     * Constructor
     *
     * @param failuremessage the failure message to be displayed when this rule
     * is broken
     */
    public Rule(String failuremessage) {
        this.failuremessage = failuremessage;
    }

    /**
     * Add a failure message to the StringBuilder if the rule is currently
     * failing.
     *
     * @param sb the StringBuilder collecting failure messages
     */
    public final void addFailureMessage(StringBuilder sb) {
        if (!ruleCheck()) {
            sb.append(failuremessage);
            sb.append("; ");
        }
    }

    /**
     * Test if the rule is passing.
     *
     * @return true if rule is passing
     */
    public final boolean check() {
        if (ruleCheck()) {
            return true;
        }
        LogBuilder.create("nbpcglibrary.common", Level.FINEST).addMethodName(this, "check")
                .addMsg("Rule failure: {0}", failuremessage).write();
        return false;
    }

    /**
     * Test if the rule is passing.
     *
     * @return true if rule is passing
     */
    protected abstract boolean ruleCheck();
}
