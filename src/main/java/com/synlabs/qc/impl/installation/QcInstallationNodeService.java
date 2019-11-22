package com.synlabs.qc.impl.installation;

import com.synlabs.qc.impl.style.Style;
import com.synlabs.qc.impl.style.V3Style;
import com.synlabs.qc.impl.style.V5Style;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.installation.ContributionConfiguration;
import com.ur.urcap.api.contribution.installation.CreationContext;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeService;
import com.ur.urcap.api.domain.SystemAPI;
import com.ur.urcap.api.domain.data.DataModel;

import java.util.Locale;

public class QcInstallationNodeService implements SwingInstallationNodeService<QcInstallationNodeContribution, QcInstallationNodeView> {


    @Override
    public void configureContribution(ContributionConfiguration configuration) {
    }

    @Override
    public String getTitle(Locale locale) {
        return "Quality Check";
    }

    @Override
    public QcInstallationNodeView createView(ViewAPIProvider apiProvider) {
        SystemAPI systemAPI = apiProvider.getSystemAPI();
        Style style = systemAPI.getSoftwareVersion().getMajorVersion() >= 5 ? new V5Style() : new V3Style();
        return new QcInstallationNodeView(style);
    }

    @Override
    public QcInstallationNodeContribution createInstallationNode(InstallationAPIProvider apiProvider, QcInstallationNodeView view, DataModel model, CreationContext context) {
        return new QcInstallationNodeContribution(apiProvider,view,model,context);
    }


}
