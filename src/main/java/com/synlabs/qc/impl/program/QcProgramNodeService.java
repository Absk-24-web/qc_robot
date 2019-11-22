package com.synlabs.qc.impl.program;


import com.synlabs.qc.impl.style.Style;
import com.synlabs.qc.impl.style.V3Style;
import com.synlabs.qc.impl.style.V5Style;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.ContributionConfiguration;
import com.ur.urcap.api.contribution.program.CreationContext;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;
import com.ur.urcap.api.domain.SystemAPI;
import com.ur.urcap.api.domain.data.DataModel;

import java.util.Locale;

public class QcProgramNodeService implements SwingProgramNodeService<QcProgramNodeContribution, QcProgramNodeView> {
    @Override
    public String getId() {
        return "Quality Check";
    }

    @Override
    public void configureContribution(ContributionConfiguration configuration) {
        configuration.setChildrenAllowed(true);
    }

    @Override
    public String getTitle(Locale locale) {
        return "Quality Check";
    }

    @Override
    public QcProgramNodeView createView(ViewAPIProvider apiProvider) {
        SystemAPI systemAPI = apiProvider.getSystemAPI();
        Style style = systemAPI.getSoftwareVersion().getMajorVersion()  >=5 ?  new V5Style() : new V3Style();
        return new QcProgramNodeView(style);
    }

    @Override
    public QcProgramNodeContribution createNode(ProgramAPIProvider apiProvider, QcProgramNodeView view, DataModel model, CreationContext context) {
        return new QcProgramNodeContribution(apiProvider,view,model);
    }
}
