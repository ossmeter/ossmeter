package com.googlecode.pongo.ui;

import java.io.File;

import org.eclipse.core.internal.runtime.Log;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.googlecode.pongo.PongoGenerator;

public class PongoObjectActionDelegate implements IObjectActionDelegate {

	private Shell shell;
	protected ISelection selection = null;
	
	/**
	 * Constructor for Action1.
	 */
	public PongoObjectActionDelegate() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		IFile selectedFile = (IFile) ((IStructuredSelection) selection).getFirstElement();
		PongoGenerator generator = new PongoGenerator("com.googlecode.pongo.generatepongosandpluginxml".equals(action.getId()));
		try {
			generator.generate(new File(selectedFile.getLocation().toOSString()));
			selectedFile.getProject().refreshLocal(IFile.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (Exception e) {
			MessageDialog.openError(shell, "Error", e.getMessage());
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
	
}
