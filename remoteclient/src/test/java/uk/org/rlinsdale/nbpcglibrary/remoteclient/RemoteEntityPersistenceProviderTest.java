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
package uk.org.rlinsdale.nbpcglibrary.remoteclient;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.org.rlinsdale.nbpcglibrary.api.EntityFields;
import uk.org.rlinsdale.nbpcglibrary.api.EntityPersistenceProvider;
import uk.org.rlinsdale.nbpcglibrary.api.EntityPersistenceProviderManager;
import uk.org.rlinsdale.nbpcglibrary.common.LogicException;

/**
 * Test for RemoteEntityPersistenceProvider
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 */
public class RemoteEntityPersistenceProviderTest {

    private static EntityPersistenceProvider instance;

    /**
     * Constructor
     */
    public RemoteEntityPersistenceProviderTest() {
    }

    /**
     * Setup class
     *
     * @throws IOException if problems
     */
    @BeforeClass
    public static void setUpClass() throws IOException {
        String[] entitynames = new String[]{
            "Application", "Role", "Permission", "User", "UserRole", "Userpermission"
        };
        Properties p = new Properties();
        p.setProperty("key", "authentication2");
        p.setProperty("connection", "http://localhost:8080/remoteauthentication2");
        p.setProperty("entitypersistenceprovidertype", "remote");
        p.setProperty("persistenceunitprovidertype", "remote");
        EntityPersistenceProviderManager.set(p, entitynames);
        instance = EntityPersistenceProviderManager.getEntityPersistenceProvider("authentication2", "Application");
        assertNotNull(instance);
    }

    /**
     * Tear down class
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Setup test
     */
    @Before
    public void setUp() {
    }

    /**
     * tear down test
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of instanceDescription method, of class
     * RemoteEntityPersistenceProvider.
     */
    @Test
    public void testInstanceDescription() {
        System.out.println("instanceDescription");
        String expResult = "RemoteAutoIDEntityPersistenceProvider[RemotePersistenceUnitProvider-Application]";
        String result = instance.instanceDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class RemoteEntityPersistenceProvider.
     *
     * @throws Exception if problems
     */
    @Test
    public void testGet_int() throws Exception {
        System.out.println("get");
        int pkey = 48;
        EntityFields result = instance.get(pkey);
        assertEquals("RL12", result.get("updatedby"));
        assertEquals("RL12", result.get("createdby"));
        assertEquals("20150422213408", result.get("updatedon"));
        assertEquals("20150422213408", result.get("createdon"));
        assertEquals(48, result.get("id"));
        assertEquals("JsonApp60", result.get("application"));
    }

    /**
     * Test of find method, of class RemoteEntityPersistenceProvider.
     *
     * @throws Exception if problems
     */
    @Test
    public void testFind() throws Exception {
        System.out.println("find");
        List<Integer> result = instance.find();
        System.out.println(result.toString());
        assertEquals(28, result.size());
    }

    /**
     * Test of get method, of class RemoteEntityPersistenceProvider.
     *
     * @throws Exception if problems
     */
    @Test
    public void testGet_String_JsonValue() throws Exception {
        System.out.println("get");
        List<EntityFields> result = instance.get("application", "JsonApp83");
        System.out.println(result.toString());
        assertEquals(1, result.size());
        EntityFields ef = result.get(0);
        assertEquals(46, ef.get("id"));
    }

    /**
     * Test of getOne method, of class RemoteEntityPersistenceProvider.
     *
     * @throws Exception if problems
     */
    @Test
    public void testGetOne() throws Exception {
        System.out.println("getOne");
        EntityFields result = instance.getOne("application", "JsonApp83");
        assertEquals(46, result.get("id"));
    }

    /**
     * Test of findNextIdx method, of class RemoteEntityPersistenceProvider.
     *
     * @throws Exception if problems
     */
    @Test(expected = LogicException.class)
    public void testFindNextIdx() throws Exception {
        System.out.println("findNextIdx");
        int result = instance.findNextIdx();
        fail("Exception should have been thrown in this case.");
    }

}
