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
package uk.org.rlinsdale.nbpcglibrary.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a set of rules - usually associated with a field. Allows the rules to
 * be tested and error messages combined.
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 */
public class Rules {

    private final List<Rule> rules = new ArrayList<>();

    /**
     * Add a rule to this Rule Set.
     *
     * @param r the rule to add
     */
    public final void addRule(Rule r) {
        rules.add(r);
    }

    /**
     * Add failure messages to the StringBuilder for each rule in this rule set
     * which is failing.
     *
     * @param sb the StringBuilder collecting failure messages
     */
    public final void addFailureMessages(StringBuilder sb) {
        rules.stream().forEach((r) -> {
            r.addFailureMessage(sb);
        });
    }

    /**
     * Check if all rules in the set are valid.
     *
     * @return true if all rules are valid
     */
    public final boolean checkRules() {
        boolean valid = true;
        for (Rule r : rules) {
            boolean checkresult = r.check();
            valid = valid && checkresult;
        }
        return valid;
    }

    /**
     * Check if all rules (except those marked as unique) in the set are valid.
     *
     * @return true if all rules (except unique) are valid
     */
    public final boolean checkRulesAtLoad() {
        boolean valid = true;
        for (Rule r : rules) {
            if (!(r instanceof UniqueRule)) {
                boolean checkresult = r.check();
                valid = valid && checkresult;
            }
        }
        return valid;
    }
}