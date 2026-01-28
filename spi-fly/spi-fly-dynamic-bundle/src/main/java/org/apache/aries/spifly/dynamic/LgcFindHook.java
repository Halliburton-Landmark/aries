package org.apache.aries.spifly.dynamic;

import java.util.Collection;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.hooks.bundle.FindHook;

/**
 * A FindHook implementation that filters bundles based on a predefined list of symbolic names. Only bundles whose
 * symbolic names start with any of the specified prefixes will be retained. The list of prefixes can be configured via
 * the system property "lgc.spifly.additional.bundles", which should contain a comma-separated list of bundle symbolic
 * name prefixes.
 */
public class LgcFindHook implements FindHook {

    private static final String LGC_SPIFLY_FILTERED_BUNDLES = "lgc.spifly.filtered.bundles";
    static String[] shouldBeActivatedBundles = null;
    /*
     * Initialize the list of bundle symbolic name prefixes to be activated from a system property. The system property
     * "lgc.spifly.filtered.bundles" should contain a comma-separated list We expect "org.eclipse.jetty.http",
     * "slf4j.api", "org.mortbay.jasper.apache-jsp", "com.lgc.dsp.logging.slf4j.eclipse"
     */

    static private void initShouldBeActivatedBundles() {
        String additionalBundles = System.getProperty(LGC_SPIFLY_FILTERED_BUNDLES);
        if (additionalBundles != null && !additionalBundles.trim().isEmpty()) {
            shouldBeActivatedBundles = additionalBundles.split(",");
        } else {
            shouldBeActivatedBundles = null;
        }
    }

    static {
        initShouldBeActivatedBundles();
    }

    @Override
    public void find(BundleContext context, Collection<Bundle> bundles) {
        if (shouldBeActivatedBundles == null || shouldBeActivatedBundles.length == 0) {
            return;
        }
        bundles.removeIf(bundle -> shouldBeRemoved(bundle.getSymbolicName()));
    }

    static private boolean shouldBeRemoved(String symbolicName) {
        // Define your criteria for removal here
        if (symbolicName == null) {
            return true;
        }
        for (String name : shouldBeActivatedBundles) {
            if (symbolicName.startsWith(name)) {
                return false;
            }
        }
        return true;
    }
}
