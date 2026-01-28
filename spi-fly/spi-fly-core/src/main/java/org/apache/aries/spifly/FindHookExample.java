package org.apache.aries.spifly;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.hooks.bundle.FindHook;
import java.util.Collection;

/**
 * Example implementation of FindHook.
 * This hook is called during bundle find operations and can modify the collection of bundles found.
 * 
 * To use this hook, register it as a service in an OSGi bundle activator:
 * 
 * public class MyActivator implements BundleActivator {
 *     public void start(BundleContext context) {
 *         context.registerService(FindHook.class, new FindHookExample(), null);
 *     }
 *     public void stop(BundleContext context) {}
 * }
 */
public class FindHookExample implements FindHook {

    @Override
    public void find(BundleContext context, Collection<Bundle> bundles) {
        // Example: Filter out bundles whose symbolic name starts with "unwanted"
        bundles.removeIf(bundle -> bundle.getSymbolicName().startsWith("unwanted"));
    }
}