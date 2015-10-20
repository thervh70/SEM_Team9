package nl.tudelft.ti2206.group9.util;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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

}
