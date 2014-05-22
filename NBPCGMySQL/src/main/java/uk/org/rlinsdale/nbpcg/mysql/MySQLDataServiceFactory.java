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
package uk.org.rlinsdale.nbpcg.mysql;

import uk.org.rlinsdale.nbpcg.datasupportlib.dataservice.DBDataServiceFactory;
import uk.org.rlinsdale.nbpcg.supportlib.DbConnectionParameters;
import org.openide.util.lookup.ServiceProvider;

/**
 * A Factory to create DataServices for MySQL databases.
 * 
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 */
@ServiceProvider(service = DBDataServiceFactory.class)
public class MySQLDataServiceFactory implements DBDataServiceFactory<MySQLDataService> {

    @Override
    public String getType() {
        return "mysql";
    }

    @Override
    public MySQLDataService createDataService(DbConnectionParameters p) {
        return new MySQLDataService(p);
    }
    
}