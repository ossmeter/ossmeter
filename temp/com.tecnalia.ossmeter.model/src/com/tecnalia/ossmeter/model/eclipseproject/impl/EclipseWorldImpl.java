/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/**
 */
package com.tecnalia.ossmeter.model.eclipseproject.impl;

import com.tecnalia.ossmeter.model.eclipseproject.EclipseProject;
import com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld;
import com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Eclipse World</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseWorldImpl#getProjects <em>Projects</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EclipseWorldImpl extends MinimalEObjectImpl.Container implements EclipseWorld {
	/**
	 * The cached value of the '{@link #getProjects() <em>Projects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjects()
	 * @generated
	 * @ordered
	 */
	protected EList<EclipseProject> projects;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EclipseWorldImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EclipseprojectPackage.Literals.ECLIPSE_WORLD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EclipseProject> getProjects() {
		if (projects == null) {
			projects = new EObjectContainmentEList<EclipseProject>(EclipseProject.class, this, EclipseprojectPackage.ECLIPSE_WORLD__PROJECTS);
		}
		return projects;
	}

	/**
	 * Search for a project in all the tree.
	 * <p>Please note that each project has a unique name in all the eclipse projects world</p>
	 * <p>The project is searched from branch to subbranch, and not from level n to level n+1. Example 1, 1.1,1.2,2,2.1</p>
	 * @param name the project name, null if not found 
	 * @generated NOT
	 */
	public EclipseProject getProjectAtAnyDepth(String name) {
		for (EclipseProject project:this.getProjects()){
			if(project.getName().equals(name)){
				return project;
			}else{
				EclipseProject result=project.getProjectAtAnyDepth(name);
				if (result!=null)return result;
			}
		}
		//if not found return null
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EclipseprojectPackage.ECLIPSE_WORLD__PROJECTS:
				return ((InternalEList<?>)getProjects()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EclipseprojectPackage.ECLIPSE_WORLD__PROJECTS:
				return getProjects();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EclipseprojectPackage.ECLIPSE_WORLD__PROJECTS:
				getProjects().clear();
				getProjects().addAll((Collection<? extends EclipseProject>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case EclipseprojectPackage.ECLIPSE_WORLD__PROJECTS:
				getProjects().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EclipseprojectPackage.ECLIPSE_WORLD__PROJECTS:
				return projects != null && !projects.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case EclipseprojectPackage.ECLIPSE_WORLD___GET_PROJECT_AT_ANY_DEPTH__STRING:
				return getProjectAtAnyDepth((String)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //EclipseWorldImpl
