/*
 * Copyright 2015-2017 Richard Linsdale.
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
package uk.theretiredprogrammer.nbpcglibrary.expressionparserandevaluate.parse;

import uk.theretiredprogrammer.nbpcglibrary.expressionparserandevaluate.parsetree.ParseTree;
import uk.theretiredprogrammer.nbpcglibrary.common.LogBuilder;

/**
 * A Boolean Value Token.
 * 
 * @author Richard Linsdale (richard at theretiredprogrammer.uk)
 */
public class BooleanToken implements TerminalToken {

    private final boolean value;

    /**
     *Constructor.
     * 
     * @param value the string value
     */
    public BooleanToken(boolean value) {
        this.value = value;
    }
    
    @Override
    public String instanceDescription() {
        return LogBuilder.instanceDescription(this,Boolean.toString(value));
    }

    /**
     * Get the Token's Boolean value.
     * 
     * @return the token's value
     */
    public boolean getTokenValue() {
        return value;
    }

    @Override
    public ParseTree getParseTree() {
        return new ParseTreeBooleanValue(value);
    }
}
