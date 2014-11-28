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

import com.tecnalia.ossmeter.model.eclipseproject.CodeRepository;
import com.tecnalia.ossmeter.model.eclipseproject.CommunityResource;
import com.tecnalia.ossmeter.model.eclipseproject.EclipseProject;
import com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld;
import com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectFactory;
import com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage;
import com.tecnalia.ossmeter.model.eclipseproject.EnumCommunityType;
import com.tecnalia.ossmeter.model.eclipseproject.EnumLicense;
import com.tecnalia.ossmeter.model.eclipseproject.EnumLiveliness;
import com.tecnalia.ossmeter.model.eclipseproject.EnumMemberRole;
import com.tecnalia.ossmeter.model.eclipseproject.EnumPlatformVersion;
import com.tecnalia.ossmeter.model.eclipseproject.EnumProjectLiveCycle;
import com.tecnalia.ossmeter.model.eclipseproject.EnumRepository;
import com.tecnalia.ossmeter.model.eclipseproject.ProjectMember;
import com.tecnalia.ossmeter.model.eclipseproject.User;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EclipseprojectPackageImpl extends EPackageImpl implements EclipseprojectPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eclipseWorldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eclipseProjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectMemberEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass codeRepositoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communityResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum enumCommunityTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum enumRepositoryEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum enumProjectLiveCycleEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum enumLivelinessEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum enumPlatformVersionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum enumLicenseEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum enumMemberRoleEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EclipseprojectPackageImpl() {
		super(eNS_URI, EclipseprojectFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link EclipseprojectPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EclipseprojectPackage init() {
		if (isInited) return (EclipseprojectPackage)EPackage.Registry.INSTANCE.getEPackage(EclipseprojectPackage.eNS_URI);

		// Obtain or create and register package
		EclipseprojectPackageImpl theEclipseprojectPackage = (EclipseprojectPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EclipseprojectPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EclipseprojectPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theEclipseprojectPackage.createPackageContents();

		// Initialize created meta-data
		theEclipseprojectPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEclipseprojectPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EclipseprojectPackage.eNS_URI, theEclipseprojectPackage);
		return theEclipseprojectPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEclipseWorld() {
		return eclipseWorldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEclipseWorld_Projects() {
		return (EReference)eclipseWorldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEclipseWorld__GetProjectAtAnyDepth__String() {
		return eclipseWorldEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEclipseProject() {
		return eclipseProjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEclipseProject_IsParentProject() {
		return (EAttribute)eclipseProjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEclipseProject_Name() {
		return (EAttribute)eclipseProjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEclipseProject_Url() {
		return (EAttribute)eclipseProjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEclipseProject_Status() {
		return (EAttribute)eclipseProjectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEclipseProject_Repositories() {
		return (EReference)eclipseProjectEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEclipseProject_Liveliness() {
		return (EAttribute)eclipseProjectEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEclipseProject_Members() {
		return (EReference)eclipseProjectEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEclipseProject_Organizations() {
		return (EAttribute)eclipseProjectEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEclipseProject_AvailablePlatformVersions() {
		return (EAttribute)eclipseProjectEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEclipseProject_Projects() {
		return (EReference)eclipseProjectEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEclipseProject_License() {
		return (EAttribute)eclipseProjectEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEclipseProject_CommunityResources() {
		return (EReference)eclipseProjectEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEclipseProject__GetProjectAtAnyDepth__String() {
		return eclipseProjectEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProjectMember() {
		return projectMemberEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectMember_User() {
		return (EReference)projectMemberEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectMember_Role() {
		return (EAttribute)projectMemberEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectMember_Url() {
		return (EAttribute)projectMemberEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUser() {
		return userEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_Name() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_Url() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCodeRepository() {
		return codeRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCodeRepository_Type() {
		return (EAttribute)codeRepositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCodeRepository_Url() {
		return (EAttribute)codeRepositoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommunityResource() {
		return communityResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommunityResource_Type() {
		return (EAttribute)communityResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommunityResource_Name() {
		return (EAttribute)communityResourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommunityResource_Url() {
		return (EAttribute)communityResourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommunityResource_Data() {
		return (EAttribute)communityResourceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEnumCommunityType() {
		return enumCommunityTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEnumRepository() {
		return enumRepositoryEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEnumProjectLiveCycle() {
		return enumProjectLiveCycleEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEnumLiveliness() {
		return enumLivelinessEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEnumPlatformVersion() {
		return enumPlatformVersionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEnumLicense() {
		return enumLicenseEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEnumMemberRole() {
		return enumMemberRoleEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseprojectFactory getEclipseprojectFactory() {
		return (EclipseprojectFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		eclipseWorldEClass = createEClass(ECLIPSE_WORLD);
		createEReference(eclipseWorldEClass, ECLIPSE_WORLD__PROJECTS);
		createEOperation(eclipseWorldEClass, ECLIPSE_WORLD___GET_PROJECT_AT_ANY_DEPTH__STRING);

		eclipseProjectEClass = createEClass(ECLIPSE_PROJECT);
		createEAttribute(eclipseProjectEClass, ECLIPSE_PROJECT__IS_PARENT_PROJECT);
		createEAttribute(eclipseProjectEClass, ECLIPSE_PROJECT__NAME);
		createEAttribute(eclipseProjectEClass, ECLIPSE_PROJECT__URL);
		createEAttribute(eclipseProjectEClass, ECLIPSE_PROJECT__STATUS);
		createEReference(eclipseProjectEClass, ECLIPSE_PROJECT__REPOSITORIES);
		createEAttribute(eclipseProjectEClass, ECLIPSE_PROJECT__LIVELINESS);
		createEReference(eclipseProjectEClass, ECLIPSE_PROJECT__MEMBERS);
		createEAttribute(eclipseProjectEClass, ECLIPSE_PROJECT__ORGANIZATIONS);
		createEAttribute(eclipseProjectEClass, ECLIPSE_PROJECT__AVAILABLE_PLATFORM_VERSIONS);
		createEReference(eclipseProjectEClass, ECLIPSE_PROJECT__PROJECTS);
		createEAttribute(eclipseProjectEClass, ECLIPSE_PROJECT__LICENSE);
		createEReference(eclipseProjectEClass, ECLIPSE_PROJECT__COMMUNITY_RESOURCES);
		createEOperation(eclipseProjectEClass, ECLIPSE_PROJECT___GET_PROJECT_AT_ANY_DEPTH__STRING);

		projectMemberEClass = createEClass(PROJECT_MEMBER);
		createEReference(projectMemberEClass, PROJECT_MEMBER__USER);
		createEAttribute(projectMemberEClass, PROJECT_MEMBER__ROLE);
		createEAttribute(projectMemberEClass, PROJECT_MEMBER__URL);

		userEClass = createEClass(USER);
		createEAttribute(userEClass, USER__NAME);
		createEAttribute(userEClass, USER__URL);

		codeRepositoryEClass = createEClass(CODE_REPOSITORY);
		createEAttribute(codeRepositoryEClass, CODE_REPOSITORY__TYPE);
		createEAttribute(codeRepositoryEClass, CODE_REPOSITORY__URL);

		communityResourceEClass = createEClass(COMMUNITY_RESOURCE);
		createEAttribute(communityResourceEClass, COMMUNITY_RESOURCE__TYPE);
		createEAttribute(communityResourceEClass, COMMUNITY_RESOURCE__NAME);
		createEAttribute(communityResourceEClass, COMMUNITY_RESOURCE__URL);
		createEAttribute(communityResourceEClass, COMMUNITY_RESOURCE__DATA);

		// Create enums
		enumCommunityTypeEEnum = createEEnum(ENUM_COMMUNITY_TYPE);
		enumRepositoryEEnum = createEEnum(ENUM_REPOSITORY);
		enumProjectLiveCycleEEnum = createEEnum(ENUM_PROJECT_LIVE_CYCLE);
		enumLivelinessEEnum = createEEnum(ENUM_LIVELINESS);
		enumPlatformVersionEEnum = createEEnum(ENUM_PLATFORM_VERSION);
		enumLicenseEEnum = createEEnum(ENUM_LICENSE);
		enumMemberRoleEEnum = createEEnum(ENUM_MEMBER_ROLE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(eclipseWorldEClass, EclipseWorld.class, "EclipseWorld", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEclipseWorld_Projects(), this.getEclipseProject(), null, "projects", null, 0, -1, EclipseWorld.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getEclipseWorld__GetProjectAtAnyDepth__String(), this.getEclipseProject(), "getProjectAtAnyDepth", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(eclipseProjectEClass, EclipseProject.class, "EclipseProject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEclipseProject_IsParentProject(), ecorePackage.getEBoolean(), "isParentProject", "false", 1, 1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEclipseProject_Name(), ecorePackage.getEString(), "name", null, 1, 1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEclipseProject_Url(), ecorePackage.getEString(), "url", null, 1, 1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEclipseProject_Status(), this.getEnumProjectLiveCycle(), "status", null, 1, 1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEclipseProject_Repositories(), this.getCodeRepository(), null, "repositories", null, 0, -1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEclipseProject_Liveliness(), this.getEnumLiveliness(), "liveliness", null, 1, 1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEclipseProject_Members(), this.getProjectMember(), null, "members", null, 1, -1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEclipseProject_Organizations(), ecorePackage.getEInt(), "organizations", null, 1, 1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEclipseProject_AvailablePlatformVersions(), this.getEnumPlatformVersion(), "availablePlatformVersions", null, 1, -1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEclipseProject_Projects(), this.getEclipseProject(), null, "projects", null, 0, -1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEclipseProject_License(), this.getEnumLicense(), "license", null, 0, 1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEclipseProject_CommunityResources(), this.getCommunityResource(), null, "CommunityResources", null, 0, -1, EclipseProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getEclipseProject__GetProjectAtAnyDepth__String(), this.getEclipseProject(), "getProjectAtAnyDepth", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(projectMemberEClass, ProjectMember.class, "ProjectMember", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProjectMember_User(), this.getUser(), null, "user", null, 1, 1, ProjectMember.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectMember_Role(), this.getEnumMemberRole(), "role", null, 1, 1, ProjectMember.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectMember_Url(), ecorePackage.getEString(), "url", null, 1, 1, ProjectMember.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(userEClass, User.class, "User", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUser_Name(), ecorePackage.getEString(), "name", null, 1, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_Url(), ecorePackage.getEString(), "url", null, 1, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(codeRepositoryEClass, CodeRepository.class, "CodeRepository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCodeRepository_Type(), this.getEnumRepository(), "type", null, 1, 1, CodeRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCodeRepository_Url(), ecorePackage.getEString(), "url", null, 1, 1, CodeRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(communityResourceEClass, CommunityResource.class, "CommunityResource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommunityResource_Type(), ecorePackage.getEString(), "type", null, 1, 1, CommunityResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommunityResource_Name(), ecorePackage.getEString(), "name", null, 1, 1, CommunityResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommunityResource_Url(), ecorePackage.getEString(), "url", null, 1, 1, CommunityResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommunityResource_Data(), ecorePackage.getEJavaObject(), "data", null, 0, 1, CommunityResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(enumCommunityTypeEEnum, EnumCommunityType.class, "EnumCommunityType");
		addEEnumLiteral(enumCommunityTypeEEnum, EnumCommunityType.MAILING_LIST);
		addEEnumLiteral(enumCommunityTypeEEnum, EnumCommunityType.FORUM);
		addEEnumLiteral(enumCommunityTypeEEnum, EnumCommunityType.WEBSITE);
		addEEnumLiteral(enumCommunityTypeEEnum, EnumCommunityType.WIKI);
		addEEnumLiteral(enumCommunityTypeEEnum, EnumCommunityType.BUG_TRACKER);

		initEEnum(enumRepositoryEEnum, EnumRepository.class, "EnumRepository");
		addEEnumLiteral(enumRepositoryEEnum, EnumRepository.SUBVERSION);
		addEEnumLiteral(enumRepositoryEEnum, EnumRepository.GIT);
		addEEnumLiteral(enumRepositoryEEnum, EnumRepository.CVS);

		initEEnum(enumProjectLiveCycleEEnum, EnumProjectLiveCycle.class, "EnumProjectLiveCycle");
		addEEnumLiteral(enumProjectLiveCycleEEnum, EnumProjectLiveCycle.PRE_PROPOSAL);
		addEEnumLiteral(enumProjectLiveCycleEEnum, EnumProjectLiveCycle.PROPOSAL);
		addEEnumLiteral(enumProjectLiveCycleEEnum, EnumProjectLiveCycle.INCUBATION);
		addEEnumLiteral(enumProjectLiveCycleEEnum, EnumProjectLiveCycle.MATURE);
		addEEnumLiteral(enumProjectLiveCycleEEnum, EnumProjectLiveCycle.TOP_LEVEL);
		addEEnumLiteral(enumProjectLiveCycleEEnum, EnumProjectLiveCycle.ARCHIVED);

		initEEnum(enumLivelinessEEnum, EnumLiveliness.class, "EnumLiveliness");
		addEEnumLiteral(enumLivelinessEEnum, EnumLiveliness.INACTIVE);
		addEEnumLiteral(enumLivelinessEEnum, EnumLiveliness.ACTIVE);

		initEEnum(enumPlatformVersionEEnum, EnumPlatformVersion.class, "EnumPlatformVersion");
		addEEnumLiteral(enumPlatformVersionEEnum, EnumPlatformVersion.GANYMEDE);
		addEEnumLiteral(enumPlatformVersionEEnum, EnumPlatformVersion.GALILEO);
		addEEnumLiteral(enumPlatformVersionEEnum, EnumPlatformVersion.HELIOS);
		addEEnumLiteral(enumPlatformVersionEEnum, EnumPlatformVersion.INDIGO);
		addEEnumLiteral(enumPlatformVersionEEnum, EnumPlatformVersion.JUNO);
		addEEnumLiteral(enumPlatformVersionEEnum, EnumPlatformVersion.KEPLER);
		addEEnumLiteral(enumPlatformVersionEEnum, EnumPlatformVersion.LUNA);

		initEEnum(enumLicenseEEnum, EnumLicense.class, "EnumLicense");
		addEEnumLiteral(enumLicenseEEnum, EnumLicense.EPL_10);

		initEEnum(enumMemberRoleEEnum, EnumMemberRole.class, "EnumMemberRole");
		addEEnumLiteral(enumMemberRoleEEnum, EnumMemberRole.CONTRIBUTOR);
		addEEnumLiteral(enumMemberRoleEEnum, EnumMemberRole.COMMITTER);
		addEEnumLiteral(enumMemberRoleEEnum, EnumMemberRole.LEADER);

		// Create resource
		createResource(eNS_URI);
	}

} //EclipseprojectPackageImpl
