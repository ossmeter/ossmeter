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
package com.tecnalia.ossmeter.model.eclipseproject.util;

import com.tecnalia.ossmeter.model.eclipseproject.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage
 * @generated
 */
public class EclipseprojectAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EclipseprojectPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseprojectAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = EclipseprojectPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EclipseprojectSwitch<Adapter> modelSwitch =
		new EclipseprojectSwitch<Adapter>() {
			@Override
			public Adapter caseEclipseWorld(EclipseWorld object) {
				return createEclipseWorldAdapter();
			}
			@Override
			public Adapter caseEclipseProject(EclipseProject object) {
				return createEclipseProjectAdapter();
			}
			@Override
			public Adapter caseProjectMember(ProjectMember object) {
				return createProjectMemberAdapter();
			}
			@Override
			public Adapter caseUser(User object) {
				return createUserAdapter();
			}
			@Override
			public Adapter caseCodeRepository(CodeRepository object) {
				return createCodeRepositoryAdapter();
			}
			@Override
			public Adapter caseCommunityResource(CommunityResource object) {
				return createCommunityResourceAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld <em>Eclipse World</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld
	 * @generated
	 */
	public Adapter createEclipseWorldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject <em>Eclipse Project</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject
	 * @generated
	 */
	public Adapter createEclipseProjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember <em>Project Member</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.ProjectMember
	 * @generated
	 */
	public Adapter createProjectMemberAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tecnalia.ossmeter.model.eclipseproject.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.User
	 * @generated
	 */
	public Adapter createUserAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tecnalia.ossmeter.model.eclipseproject.CodeRepository <em>Code Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.CodeRepository
	 * @generated
	 */
	public Adapter createCodeRepositoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tecnalia.ossmeter.model.eclipseproject.CommunityResource <em>Community Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.CommunityResource
	 * @generated
	 */
	public Adapter createCommunityResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //EclipseprojectAdapterFactory
