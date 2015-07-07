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
package uk.org.rlinsdale.nbpcglibrary.node.nodes;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import uk.org.rlinsdale.nbpcglibrary.data.entity.EntityManager;
import uk.org.rlinsdale.nbpcglibrary.node.ImageFileFinder;
import uk.org.rlinsdale.nbpcglibrary.common.LogicException;
import org.openide.cookies.InstanceCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import uk.org.rlinsdale.nbpcglibrary.common.LogBuilder;
import uk.org.rlinsdale.nbpcglibrary.data.entity.CoreEntity;
import uk.org.rlinsdale.nbpcglibrary.data.entity.Entity;

/**
 * Read-Write Icon Node Abstract Class
 *
 * @author Richard Linsdale (richard.linsdale at blueyonder.co.uk)
 * @param <K> the Entity primary Key class
 * @param <E> the Entity Class
 * @param <P> the parent Entity Class
 * @param <F> the Entity Field enum class
 */
public abstract class IconNode<K, E extends Entity<K, E, P, F>, P extends CoreEntity, F> extends TreeNode<K, E, P, F> {

    private final ImageFileFinder<E> imagefilefinder;
    private final String nodename;

    /**
     * Constructor
     *
     * @param nodename the node name
     * @param e the entity
     * @param cf the childfactory
     * @param emclass the entity manager class
     * @param allowedDataFlavors allowed paste actions
     * @param operationsEnabled set for copy , cut and delete enabled
     */
    public IconNode(String nodename, E e, BasicChildFactory<K, E, P> cf, Class<? extends EntityManager> emclass, DataFlavor[] allowedDataFlavors, int operationsEnabled) {
        super(nodename, e, cf, emclass, allowedDataFlavors, operationsEnabled);
        this.nodename = nodename;
        imagefilefinder = getImageFileFinder(nodename);
    }

    /**
     * Constructor.
     *
     * @param nodename the node name
     * @param e the entity
     * @param emclass the entity manager class
     * @param operationsEnabled set for copy , cut and delete enabled
     */
    protected IconNode(String nodename, E e, Class<? extends EntityManager> emclass, int operationsEnabled) {
        super(nodename, e, emclass, operationsEnabled);
        this.nodename = nodename;
        imagefilefinder = getImageFileFinder(nodename);
    }

    private ImageFileFinder<E> getImageFileFinder(String nodename) {
        FileObject ff = FileUtil.getConfigFile("nbpcglibrary/node/" + nodename + "/imagefilefinder"); // get folder
        if (ff == null) {
            throw new LogicException("no image file finders defined for node " + nodename);
        }
        FileObject[] ffc = ff.getChildren();
        switch (ffc.length) {
            case 0:
                throw new LogicException("no image file finders defined for node " + nodename);
            case 1:
                return getInstanceFromFileObject(ffc[0]);
            default:
                throw new LogicException("multiple image file finders defined for node " + nodename);
        }
    }

    private ImageFileFinder<E> getInstanceFromFileObject(FileObject fo) {
        try {
            DataObject dobj = DataObject.find(fo);
            InstanceCookie ic = dobj.getLookup().lookup(InstanceCookie.class);
            if (ic == null) {
                throw new LogicException("Bad image file finder - no instance cookie");
            }
            return (ImageFileFinder<E>) ic.instanceCreate();
        } catch (DataObjectNotFoundException ex) {
            throw new LogicException("Bad image file finder", ex);
        } catch (IOException | ClassNotFoundException ex) {
            throw new LogicException("Bad image file finder", ex);
        }
    }

    @Override
    public String getHtmlDisplayName() {
        return null;
    }

    @Override
    public Image getIcon(int type) {
        E entity = getEntity();
        File fi = imagefilefinder.getFile(entity);
        if (fi == null) {
            LogBuilder.create("nbpcglibrary.node", Level.WARNING).addMethodName(this, "getIcon")
                    .addMsg("Nodename is {0} - No image defined", nodename).write();
            return entity.getIconWithError();
        }
        try {
            return entity.checkRules() ? ImageIO.read(fi) : entity.addErrorToIcon(ImageIO.read(fi));
        } catch (IOException ex) {
            LogBuilder.create("nbpcglibrary.node", Level.WARNING).addMethodName(this, "getIcon")
                    .addMsg("Nodename is {0} - IOException when reading image", nodename).addExceptionMessage(ex).write();
            return entity.getIconWithError();
        }
    }
}
