/*
 * Copyright (c) 2006-2008 by Carnegie Mellon University and others.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    1. Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *    3. Neither the names of the authors nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHORS ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.cmu.cs.planno;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.ISharedImages;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.text.java.ClasspathFixProcessor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.swt.graphics.Image;

/**
 * A classpath fix for the plaid annotations. When you type one of the plaid annotations,
 * this fix will suggest adding the Plaid Annotations container to the classpath.
 * 
 * @author Nels E. Beckman
 * @date Dec 12, 2008
 *
 */
public class PlaidClasspathFix extends ClasspathFixProcessor {
	
	private static final String PLURAL_ANNOTATION_PKG = "edu.cmu.cs.plural.annot";
	private static final String CRYSTAL_ANNOTATION_PKG = "edu.cmu.cs.crytal.annotations";
	
	private static final Set<String> PLANNO_CLASS_NAMES;
	
	static {
		String[] plaid_names = 
		{ 
				"Capture", "Cases", "ClassStates", "FalseIndicates",
				"Full", "Fulls", "Imm", "Imms", "IsResult", "Lend",
				"NoEffects","NonReentrant","Param","Perm","Pures","Pure",
				"Range","Refine","Release","ResultFull","ResultImm","ResultShare",
				"ResultUnique","Share","Shares","State","States","TrueIndicates",
				"Unique","Uniques" 
		};
		
		String[] crystal_names =
		{
				"FailingTest","MultiAnnotation","PassingTest","UseAnalyses"
		};
		
		PLANNO_CLASS_NAMES = new HashSet<String>();
		for(String s : plaid_names)
			PLANNO_CLASS_NAMES.add(s);
		
		for(String s : crystal_names)
			PLANNO_CLASS_NAMES.add(s);
		
	}
	
	@Override
	public ClasspathFixProposal[] getFixImportProposals(IJavaProject project,
			String name) throws CoreException {

		if( name.startsWith(PLURAL_ANNOTATION_PKG) ||  
			name.startsWith(CRYSTAL_ANNOTATION_PKG) ||
			PLANNO_CLASS_NAMES.contains(name) ) {
			// This is probably ours.
			return new ClasspathFixProposal[] { new PlaidFixProposal(project) };
		}
		
		return null;
	}


	private class PlaidFixProposal extends ClasspathFixProposal {

		private final IJavaProject project;

		public PlaidFixProposal(IJavaProject project) {
			super();
			this.project = project;
		}

		@Override
		public Change createChange(IProgressMonitor monitor)
				throws CoreException {
			if (monitor == null) {
				monitor= new NullProgressMonitor();
			}
			monitor.beginTask("Adding Plaid annotations to classpath.", 1);
			try {
				// This 'new' classpath is a combination of the old one, plus the plaid
				// class path.
				// TODO: What we don't do here which they do do in JUnit is to
				// check to see if Plaid annotations is already in the classpath.
				IClasspathEntry[] old_classpath = project.getRawClasspath();
				IClasspathEntry[] new_classpath = new IClasspathEntry[old_classpath.length + 1];
				System.arraycopy(old_classpath, 0, new_classpath, 0, old_classpath.length);
				
				// We put our entry in as the last element.
				IPath containerPath = PlaidClasspathContainer.ID;
		        IClasspathEntry new_entry = JavaCore.newContainerEntry(containerPath);
				new_classpath[old_classpath.length] = new_entry;
				
				Change result = newClasspathChange(project, new_classpath, project.getOutputLocation());
				return result;
			} finally {
				monitor.done();
			}
		}

		@Override
		public String getAdditionalProposalInfo() {
			return "This will add the Plaid Annotations to the project classpath. If you are using a Crystal analysis, this is probably important.";
		}

		@Override
		public String getDisplayString() {
			return "Add Plaid Annotations";
		}

		@Override
		public Image getImage() {
			return JavaUI.getSharedImages().getImage(ISharedImages.IMG_OBJS_LIBRARY);
		}

		@Override
		public int getRelevance() {
			// Why 15? I have no idea. It's what JUnit returns, and it's supposed to be between 1 and 100.
			return 15;
		}
		
	}
}
