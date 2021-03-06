/*
Copyright (c) 2015, Rockwell Collins.
Developed with the sponsorship of Defense Advanced Research Projects Agency (DARPA).

Permission is hereby granted, free of charge, to any person obtaining a copy of this data, 
including any software or models in source or binary form, as well as any drawings, specifications, 
and documentation (collectively "the Data"), to deal in the Data without restriction, including
without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
and/or sell copies of the Data, and to permit persons to whom the Data is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or 
substantial portions of the Data.

THE DATA IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS, SPONSORS, DEVELOPERS, CONTRIBUTORS, OR COPYRIGHT HOLDERS BE LIABLE 
FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
ARISING FROM, OUT OF OR IN CONNECTION WITH THE DATA OR THE USE OR OTHER DEALINGS IN THE DATA.
*/
package edu.uah.rsesc.aadlsimulator.ui.views;

import java.util.Objects;

import org.eclipse.core.commands.Command;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.FrameworkUtil;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.RegistryToggleState;
import edu.uah.rsesc.aadlsimulator.ui.UIConstants;
import edu.uah.rsesc.aadlsimulator.ui.services.SimulationUIService;
import edu.uah.rsesc.aadlsimulator.ui.services.SimulatorUIExtensionService;

// ViewPart which wraps the E4 Style PropertiesView POJO.
public class PropertiesViewPart extends ViewPart {
	private PropertiesView view;

	public PropertiesViewPart() {
		final SimulationUIService simulationUiService = Objects.requireNonNull(EclipseContextFactory.getServiceContext(FrameworkUtil.getBundle(getClass()).getBundleContext()).get(SimulationUIService.class), "unable to get simulation UI service");
		final SimulatorUIExtensionService extService = Objects.requireNonNull((SimulatorUIExtensionService)PlatformUI.getWorkbench().getService(SimulatorUIExtensionService.class), "unable to get simulation UI extension service");
		view = new PropertiesView(simulationUiService, extService);
	}
	
	@Override
	public void createPartControl(final Composite parent) {
		view.createView(parent);
		
		final ICommandService commandService = (ICommandService)getSite().getService(ICommandService.class);
		final Command command = commandService.getCommand(UIConstants.TOGGLE_SHOW_ONLY_ERRORS_AND_WARNINGS_COMMAND_ID);
		setShowOnlyErrorsAndWarnings((Boolean)command.getState(RegistryToggleState.STATE_ID).getValue());
	}
	
	@Override
	public void dispose() {		
		// Destroy the variables view
		if(view != null) {
			view.preDestroy();
			view = null;
		}
		
		super.dispose();
	}
	
	
	@Override
	public void setFocus() {
		view.setFocus();
	}
	
	public void setShowOnlyErrorsAndWarnings(final boolean value) {
		view.setShowOnlyErrorsAndWarnings(value);
	}
}
