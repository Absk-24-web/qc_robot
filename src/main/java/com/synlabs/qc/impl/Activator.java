package com.synlabs.qc.impl;

import com.synlabs.qc.impl.installation.QcInstallationNodeService;
import com.synlabs.qc.impl.program.QcProgramNodeService;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeService;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Hello world activator for the OSGi bundle URCAPS contribution
 *
 */
public class Activator implements BundleActivator {
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		QcProgramNodeService qcProgramNodeService = new QcProgramNodeService();
		QcInstallationNodeService qcInstallationNodeService = new QcInstallationNodeService();

		bundleContext.registerService(SwingInstallationNodeService.class, qcInstallationNodeService, null);
		bundleContext.registerService(SwingProgramNodeService.class,qcProgramNodeService, null);
		System.out.println("Qc");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
	}
}

