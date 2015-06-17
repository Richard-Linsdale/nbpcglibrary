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
package uk.org.rlinsdale.nbpcglibrary.mysql;

import java.util.Properties;
import org.openide.util.lookup.ServiceProvider;
import uk.org.rlinsdale.nbpcglibrary.api.PersistenceUnitProviderFactory;

/**
 * A Factory to create DataAccessManager for local MySQL databases.
 * 
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 */
@ServiceProvider(service = LocalMySQLPersistenceUnitProviderFactory.class)
public class LocalMySQLPersistenceUnitProviderFactory implements PersistenceUnitProviderFactory<LocalMySQLPersistenceUnitProvider> {

    @Override
    public String getType() {
        return "mysql";
    }

    @Override
    public LocalMySQLPersistenceUnitProvider createPersistenceUnitProvider(Properties p) {
        return new LocalMySQLPersistenceUnitProvider(p);
    }
}
