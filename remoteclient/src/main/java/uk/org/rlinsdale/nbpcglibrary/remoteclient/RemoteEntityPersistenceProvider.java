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
package uk.org.rlinsdale.nbpcglibrary.remoteclient;

import java.io.IOException;
import java.util.Properties;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import uk.org.rlinsdale.nbpcglibrary.common.LogBuilder;
import uk.org.rlinsdale.nbpcglibrary.api.EntityPersistenceProvider;
import uk.org.rlinsdale.nbpcglibrary.common.Settings;
import uk.org.rlinsdale.nbpcglibrary.json.JsonUtil;

/**
 * EntityPersistenceProvider Class for accessing remote entities.
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 */
public class RemoteEntityPersistenceProvider implements EntityPersistenceProvider {

    private final String entityname;
    private final RemotePersistenceUnitProvider persistenceUnitProvider;
    private final String idx;

    /**
     * Constructor.
     *
     * @param entityname the entity table name in entity storage
     * @param properties the properties used for configuration
     * @param pup the data access manager to use to access the data
     */
    public RemoteEntityPersistenceProvider(String entityname, Properties properties, RemotePersistenceUnitProvider pup) {
        this.entityname = entityname;
        this.persistenceUnitProvider = pup;
        idx = null;
    }

    /**
     * Constructor.
     *
     * @param entityname the entity table name in entity storage
     * @param idx the index field - used to order the returned entities
     * @param properties the properties used for configuration
     * @param pup the data access manager to use to access the data
     */
    public RemoteEntityPersistenceProvider(String entityname, String idx, Properties properties, RemotePersistenceUnitProvider pup) {
        this.entityname = entityname;
        this.persistenceUnitProvider = pup;
        this.idx = null;
    }

    @Override
    public String instanceDescription() {
        return LogBuilder.instanceDescription(this, LogBuilder.instanceDescription(persistenceUnitProvider) + "-" + entityname);
    }

    @Override
    public final synchronized JsonArray get() throws IOException {
        LogBuilder.writeLog("nbpcglib.RemoteEntityPersistenceProvider", this, "get");
        return idx == null
                ? persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "getall")
                        .add("entity", entityname)
                        .build()
                ).getJsonArray("entities")
                : persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "getall")
                        .add("entity", entityname)
                        .add("orderby", idx)
                        .build()
                ).getJsonArray("entities");
    }

    @Override
    public final synchronized JsonObject get(int id) throws IOException {
        LogBuilder.writeLog("nbpcglib.RemoteEntityPersistenceProvider", this, "get", id);
        return persistenceUnitProvider.executeSingleCommand(
                Json.createObjectBuilder()
                .add("action", "get")
                .add("entity", entityname)
                .add("id", id)
                .build()
        ).getJsonObject("fields");
    }

    @Override
    public final synchronized JsonArray find() throws IOException {
        LogBuilder.writeLog("nbpcglib.RemoteEntityPersistenceProvider", this, "find");
        return idx == null
                ? persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "findall")
                        .add("entity", entityname)
                        .build()
                ).getJsonArray("ids")
                : persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "findall")
                        .add("entity", entityname)
                        .add("orderby", idx)
                        .build()
                ).getJsonArray("ids");
    }

    @Override
    public final synchronized JsonArray get(String parametername, JsonValue parametervalue) throws IOException {
        LogBuilder.writeLog("nbpcglib.RemoteEntityPersistenceProvider", this, "get", parametername, parametervalue.toString());
        return idx == null
                ? persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "getbyfield")
                        .add("entity", entityname)
                        .add("field", parametername)
                        .add("value", parametervalue)
                        .build()
                ).getJsonArray("entities")
                : persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "getbyfield")
                        .add("entity", entityname)
                        .add("field", parametername)
                        .add("value", parametervalue)
                        .add("orderby", idx)
                        .build()
                ).getJsonArray("entities");
    }

    @Override
    public final synchronized JsonArray find(String parametername, JsonValue parametervalue) throws IOException {
        LogBuilder.writeLog("nbpcglib.RemoteEntityPersistenceProvider", this, "find", parametername, parametervalue.toString());
        return idx == null
                ? persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "findbyfield")
                        .add("entity", entityname)
                        .add("field", parametername)
                        .add("value", parametervalue)
                        .build()
                ).getJsonArray("ids")
                : persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "findbyfield")
                        .add("entity", entityname)
                        .add("field", parametername)
                        .add("value", parametervalue)
                        .add("orderby", idx)
                        .build()
                ).getJsonArray("ids");
    }

    @Override
    public final synchronized JsonObject getOne(String parametername, JsonValue parametervalue) throws IOException {
        LogBuilder.writeLog("nbpcglib.RemoteEntityPersistenceProvider", this, "getOne", parametername, parametervalue.toString());
        JsonObject response = idx == null
                ? persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "getbyfield")
                        .add("entity", entityname)
                        .add("field", parametername)
                        .add("value", parametervalue)
                        .build()
                )
                : persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "getbyfield")
                        .add("entity", entityname)
                        .add("field", parametername)
                        .add("value", parametervalue)
                        .add("orderby", idx)
                        .build()
                );
        if (JsonUtil.getObjectKeyIntegerValue(response, "count") != 1) {
            throw new IOException("Single row expected");
        }
        return response.getJsonArray("entities").getJsonObject(0);
    }

    @Override
    public final synchronized JsonValue findOne(String parametername, JsonValue parametervalue) throws IOException {
        LogBuilder.writeLog("nbpcglib.RemoteEntityPersistenceProvider", this, "findOne", parametername, parametervalue.toString());
        JsonObject response = idx == null
                ? persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "findbyfield")
                        .add("entity", entityname)
                        .add("field", parametername)
                        .add("value", parametervalue)
                        .build()
                )
                : persistenceUnitProvider.executeSingleCommand(
                        Json.createObjectBuilder()
                        .add("action", "findbyfield")
                        .add("entity", entityname)
                        .add("field", parametername)
                        .add("value", parametervalue)
                        .add("orderby", idx)
                        .build()
                );
        if (JsonUtil.getObjectKeyIntegerValue(response, "count") != 1) {
            throw new IOException("Single row expected");
        }
        return response.getJsonArray("ids").getJsonNumber(0);
    }

    @Override
    public final synchronized int findNextIdx() throws IOException {
        LogBuilder.writeLog("nbpcglib.RemoteEntityPersistenceProvider", this, "findNextIdx");
        if (idx == null) {
            throw new IOException("findNextIdx() should not be called if the entity is not ordered");
        }
        return Integer.MAX_VALUE; // temporary 
    }

    @Override
    public final synchronized int insert(JsonObject values) throws IOException {
        LogBuilder.writeLog("nbpcglib.RemoteEntityPersistenceProvider", this, "insert", values.toString());
        JsonObject job = persistenceUnitProvider.executeSingleCommand(
                Json.createObjectBuilder()
                .add("action", "create")
                .add("entity", entityname)
                .add("user", Settings.get("Usercode", "????"))
                .add("fields", values)
                .build()
        );
        int id = job.getInt("id");
        LogBuilder.writeExitingLog("nbpcglib.RemoteEntityPersistenceProvider", this, "insert", id);
        return id;
    }

    @Override
    public final synchronized void update(int id, JsonObject diff) throws IOException {
        LogBuilder.writeLog("nbpcglib.RemoteEntityPersistenceProvider", this, "update", id, diff.toString());
        persistenceUnitProvider.executeSingleCommand(
                Json.createObjectBuilder()
                .add("action", "update")
                .add("entity", entityname)
                .add("id", id)
                .add("fields", diff)
                .build()
        );
        LogBuilder.writeExitingLog("nbpcglib.RemoteEntityPersistenceProvider", this, "update");
    }

    @Override
    public final synchronized void delete(int id) throws IOException {
        LogBuilder.writeLog("nbpcglib.RemoteEntityPersistenceProvider", this, "delete", id);
        persistenceUnitProvider.executeSingleCommand(
                Json.createObjectBuilder()
                .add("action", "delete")
                .add("entity", entityname)
                .add("id", id)
                .build()
        );
    }
}
