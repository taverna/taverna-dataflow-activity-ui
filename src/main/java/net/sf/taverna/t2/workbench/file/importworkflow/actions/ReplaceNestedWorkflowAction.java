package net.sf.taverna.t2.workbench.file.importworkflow.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;

import net.sf.taverna.t2.activities.dataflow.DataflowActivity;
import net.sf.taverna.t2.ui.menu.MenuManager;
import net.sf.taverna.t2.workbench.activityicons.ActivityIconManager;
import net.sf.taverna.t2.workbench.configuration.colour.ColourManager;
import net.sf.taverna.t2.workbench.configuration.workbench.WorkbenchConfiguration;
import net.sf.taverna.t2.workbench.edits.EditManager;
import net.sf.taverna.t2.workbench.file.FileManager;
import net.sf.taverna.t2.workbench.file.importworkflow.gui.ImportWorkflowWizard;
import net.sf.taverna.t2.workbench.ui.Utils;
import net.sf.taverna.t2.workbench.ui.actions.activity.ActivityConfigurationAction;
import net.sf.taverna.t2.workflowmodel.Dataflow;
import net.sf.taverna.t2.workflowmodel.Edit;

public class ReplaceNestedWorkflowAction extends
		ActivityConfigurationAction<DataflowActivity, Dataflow> {
	private static final long serialVersionUID = 1L;

	private final EditManager editManager;
	private final FileManager fileManager;
	private final MenuManager menuManager;

	private final ColourManager colourManager;

	private final WorkbenchConfiguration workbenchConfiguration;

	public ReplaceNestedWorkflowAction(DataflowActivity activity, EditManager editManager,
			FileManager fileManager, MenuManager menuManager,
			ActivityIconManager activityIconManager, ColourManager colourManager,
			WorkbenchConfiguration workbenchConfiguration) {
		super(activity, activityIconManager);
		this.editManager = editManager;
		this.fileManager = fileManager;
		this.menuManager = menuManager;
		this.colourManager = colourManager;
		this.workbenchConfiguration = workbenchConfiguration;
		putValue(NAME, "Replace nested workflow");
	}

	public void actionPerformed(ActionEvent e) {
		final Component parentComponent;
		if (e.getSource() instanceof Component) {
			parentComponent = (Component) e.getSource();
		} else {
			parentComponent = null;
		}
		ImportWorkflowWizard wizard = new ImportWorkflowWizard(
				Utils.getParentFrame(parentComponent), editManager, fileManager, menuManager,
				colourManager, workbenchConfiguration) {
			private static final long serialVersionUID = 1L;

			@Override
			protected Edit<?> makeInsertNestedWorkflowEdit(Dataflow nestedFlow, String name) {
				return editManager.getEdits().getConfigureActivityEdit(getActivity(), nestedFlow);
			}

			@Override
			protected DataflowActivity getInsertedActivity() {
				return getActivity();
			}
		};

		wizard.setMergeEnabled(false);
		wizard.setCustomDestinationDataflow(fileManager.getCurrentDataflow(),
				"Existing nested workflow");
		wizard.setDestinationEnabled(false);
		wizard.setVisible(true);
	}

}
