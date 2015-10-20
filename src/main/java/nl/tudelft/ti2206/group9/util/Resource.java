package nl.tudelft.ti2206.group9.util;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;

/**
 * Helper class to quickly load resource URLs, URI strings, etc.
 * @author Maarten
 */
public final class Resource {

    /** Hiding public constructor. */
    private Resource() { }

    /**
     * @param packagePath The package path of the resource.
     * @return the URI.toURL() of this resource.
     */
    public static URL getURL(final String packagePath) {
        final URL res = Thread.currentThread().getContextClassLoader()
                .getResource(packagePath);
        if (res == null) {
            OBSERVABLE.notify(Category.ERROR, Error.RESOURCEEXCEPTION,
                    "Resource.getURL(String)", "getResource() == null",
                    packagePath);
        }
        return res;
    }

    /**
     * @param packagePath The package path of the resource.
     * @return the URI of this resource.
     */
    public static URI getURI(final String packagePath) {
        try {
            return getURL(packagePath).toURI();
        } catch (URISyntaxException e) {
            OBSERVABLE.notify(Category.ERROR, Error.RESOURCEEXCEPTION,
                    "Resource.getURI(String)", e.getMessage(), packagePath);
        }
        return null;
    }

    /**
     * @param packagePath The package path of the resource.
     * @return the URI.toString() of this resource.
     */
    public static String getURIString(final String packagePath) {
        return getURI(packagePath).toString();
    }

    /**
     * @param packagePath The package path of the resource.
     * @return the URL.toString() of this resource.
     */
    public static String getURLString(final String packagePath) {
        return getURL(packagePath).toString();
    }

    /**
     * @param packagePath The package path of the folder.
     * @return A list of the filenames (+ extension) of the files in the folder.
     *         Returns null if folder is not found.
     */
    public static List<String> getFolder(final String packagePath) {
        List<String> res = getFolderJAR(packagePath);
        if (res == null) {
            res = getFolderIDE(packagePath);
        }
        return res;
    }

    /**
     * @param packagePath The package path of the folder.
     * @return If the folder exists, a list of the filenames (+ extension)
     *         of the files in the folder. If the folder does not exist
     *         (e.g. because you're not running from a JAR), returns null.
     */
    private static List<String> getFolderJAR(final String packagePath) {
        final File jarFile = new File(Resource.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath()
                .replaceAll("%20", " "));
        if (!jarFile.isFile()) {
            return null;
        }
        try {
            final ArrayList<String> res = new ArrayList<>();
            final JarFile jar = new JarFile(jarFile);
            final Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                final String name = entries.nextElement().getName();
                if (name.startsWith(packagePath + "/")) {
                    final String sub =
                            name.substring(packagePath.length() + 1);
                    if (!sub.contains("/")) {
                        res.add(sub);
                    }
                }
            }
            jar.close();
            return res;
        } catch (IOException e) {
            OBSERVABLE.notify(Category.ERROR, Error.RESOURCEEXCEPTION,
                    "Resource.getFolder(String)", e.getMessage(),
                    packagePath);
        }
        return null;
    }

    /**
     * @param packagePath The package path of the folder.
     * @return If the folder exists, a list of the filenames (+ extension)
     *         of the files in the folder. If the folder does not exist
     *         (e.g. because it's inside a JAR), returns null.
     */
    private static List<String> getFolderIDE(final String packagePath) {
        final File folder = new File(getURI(packagePath));
        if (!folder.isDirectory()) {
            return null;
        }

        final File[] files = folder.listFiles();
        if (files == null) {
            return null;
        }

        final ArrayList<String> res = new ArrayList<>();
        for (final File file : files) {
            res.add(file.getName());
        }

        return res;
    }

    /**
     * @param packagePath The package path of the resource.
     * @return an opened stream if all succeeded, else null.
     */
    public static InputStream getStream(final String packagePath) {
        try {
            return getURL(packagePath).openStream();
        } catch (IOException e) {
            OBSERVABLE.notify(Category.ERROR, Error.RESOURCEEXCEPTION,
                    "Resource.getStream(String)", e.getMessage(), packagePath);
            return null;
        }
    }

}
