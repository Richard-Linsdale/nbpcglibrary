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
package uk.theretiredprogrammer.nbpcglibrary.common;

import java.util.logging.Level;

/**
 * Defines a rule to be associated with a field.
 *
 * @author Richard Linsdale (richard at theretiredprogrammer.uk)
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
     * Test if the rule is passing.
     *
     * @param sb the StringBuilder collecting failure messages
     * @return true if rule is passing
     */
    public final boolean check(StringBuilder sb) {
        if (ruleCheck()) {
            return true;
        }
        sb.append(failuremessage);
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
