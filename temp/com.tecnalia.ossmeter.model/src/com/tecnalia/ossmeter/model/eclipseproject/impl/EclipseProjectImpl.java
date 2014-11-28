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
import com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage;
import com.tecnalia.ossmeter.model.eclipseproject.EnumLicense;
import com.tecnalia.ossmeter.model.eclipseproject.EnumLiveliness;
import com.tecnalia.ossmeter.model.eclipseproject.EnumPlatformVersion;
import com.tecnalia.ossmeter.model.eclipseproject.EnumProjectLiveCycle;
import com.tecnalia.ossmeter.model.eclipseproject.ProjectMember;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Eclipse Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#isIsParentProject <em>Is Parent Project</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#getRepositories <em>Repositories</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#getLiveliness <em>Liveliness</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#getMembers <em>Members</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#getOrganizations <em>Organizations</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#getAvailablePlatformVersions <em>Available Platform Versions</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#getProjects <em>Projects</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#getLicense <em>License</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl#getCommunityResources <em>Community Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EclipseProjectImpl extends MinimalEObjectImpl.Container implements EclipseProject {
	/**
	 * The default value of the '{@link #isIsParentProject() <em>Is Parent Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsParentProject()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_PARENT_PROJECT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsParentProject() <em>Is Parent Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsParentProject()
	 * @generated
	 * @ordered
	 */
	protected boolean isParentProject = IS_PARENT_PROJECT_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected String url = URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final EnumProjectLiveCycle STATUS_EDEFAULT = EnumProjectLiveCycle.PRE_PROPOSAL;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected EnumProjectLiveCycle status = STATUS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRepositories() <em>Repositories</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositories()
	 * @generated
	 * @ordered
	 */
	protected EList<CodeRepository> repositories;

	/**
	 * The default value of the '{@link #getLiveliness() <em>Liveliness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiveliness()
	 * @generated
	 * @ordered
	 */
	protected static final EnumLiveliness LIVELINESS_EDEFAULT = EnumLiveliness.INACTIVE;

	/**
	 * The cached value of the '{@link #getLiveliness() <em>Liveliness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiveliness()
	 * @generated
	 * @ordered
	 */
	protected EnumLiveliness liveliness = LIVELINESS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMembers() <em>Members</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<ProjectMember> members;

	/**
	 * The default value of the '{@link #getOrganizations() <em>Organizations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganizations()
	 * @generated
	 * @ordered
	 */
	protected static final int ORGANIZATIONS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOrganizations() <em>Organizations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganizations()
	 * @generated
	 * @ordered
	 */
	protected int organizations = ORGANIZATIONS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAvailablePlatformVersions() <em>Available Platform Versions</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAvailablePlatformVersions()
	 * @generated
	 * @ordered
	 */
	protected EList<EnumPlatformVersion> availablePlatformVersions;

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
	 * The default value of the '{@link #getLicense() <em>License</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLicense()
	 * @generated
	 * @ordered
	 */
	protected static final EnumLicense LICENSE_EDEFAULT = EnumLicense.EPL_10;

	/**
	 * The cached value of the '{@link #getLicense() <em>License</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLicense()
	 * @generated
	 * @ordered
	 */
	protected EnumLicense license = LICENSE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCommunityResources() <em>Community Resources</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommunityResources()
	 * @generated
	 * @ordered
	 */
	protected EList<CommunityResource> communityResources;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EclipseProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EclipseprojectPackage.Literals.ECLIPSE_PROJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsParentProject() {
		return isParentProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsParentProject(boolean newIsParentProject) {
		boolean oldIsParentProject = isParentProject;
		isParentProject = newIsParentProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseprojectPackage.ECLIPSE_PROJECT__IS_PARENT_PROJECT, oldIsParentProject, isParentProject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseprojectPackage.ECLIPSE_PROJECT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrl(String newUrl) {
		String oldUrl = url;
		url = newUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseprojectPackage.ECLIPSE_PROJECT__URL, oldUrl, url));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumProjectLiveCycle getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(EnumProjectLiveCycle newStatus) {
		EnumProjectLiveCycle oldStatus = status;
		status = newStatus == null ? STATUS_EDEFAULT : newStatus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseprojectPackage.ECLIPSE_PROJECT__STATUS, oldStatus, status));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CodeRepository> getRepositories() {
		if (repositories == null) {
			repositories = new EObjectContainmentEList<CodeRepository>(CodeRepository.class, this, EclipseprojectPackage.ECLIPSE_PROJECT__REPOSITORIES);
		}
		return repositories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumLiveliness getLiveliness() {
		return liveliness;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLiveliness(EnumLiveliness newLiveliness) {
		EnumLiveliness oldLiveliness = liveliness;
		liveliness = newLiveliness == null ? LIVELINESS_EDEFAULT : newLiveliness;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseprojectPackage.ECLIPSE_PROJECT__LIVELINESS, oldLiveliness, liveliness));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProjectMember> getMembers() {
		if (members == null) {
			members = new EObjectContainmentEList<ProjectMember>(ProjectMember.class, this, EclipseprojectPackage.ECLIPSE_PROJECT__MEMBERS);
		}
		return members;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOrganizations() {
		return organizations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrganizations(int newOrganizations) {
		int oldOrganizations = organizations;
		organizations = newOrganizations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseprojectPackage.ECLIPSE_PROJECT__ORGANIZATIONS, oldOrganizations, organizations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EnumPlatformVersion> getAvailablePlatformVersions() {
		if (availablePlatformVersions == null) {
			availablePlatformVersions = new EDataTypeUniqueEList<EnumPlatformVersion>(EnumPlatformVersion.class, this, EclipseprojectPackage.ECLIPSE_PROJECT__AVAILABLE_PLATFORM_VERSIONS);
		}
		return availablePlatformVersions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EclipseProject> getProjects() {
		if (projects == null) {
			projects = new EObjectContainmentEList<EclipseProject>(EclipseProject.class, this, EclipseprojectPackage.ECLIPSE_PROJECT__PROJECTS);
		}
		return projects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumLicense getLicense() {
		return license;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLicense(EnumLicense newLicense) {
		EnumLicense oldLicense = license;
		license = newLicense == null ? LICENSE_EDEFAULT : newLicense;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseprojectPackage.ECLIPSE_PROJECT__LICENSE, oldLicense, license));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CommunityResource> getCommunityResources() {
		if (communityResources == null) {
			communityResources = new EObjectContainmentEList<CommunityResource>(CommunityResource.class, this, EclipseprojectPackage.ECLIPSE_PROJECT__COMMUNITY_RESOURCES);
		}
		return communityResources;
	}
	/**
	 * Search for a project in all the branch .
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
			case EclipseprojectPackage.ECLIPSE_PROJECT__REPOSITORIES:
				return ((InternalEList<?>)getRepositories()).basicRemove(otherEnd, msgs);
			case EclipseprojectPackage.ECLIPSE_PROJECT__MEMBERS:
				return ((InternalEList<?>)getMembers()).basicRemove(otherEnd, msgs);
			case EclipseprojectPackage.ECLIPSE_PROJECT__PROJECTS:
				return ((InternalEList<?>)getProjects()).basicRemove(otherEnd, msgs);
			case EclipseprojectPackage.ECLIPSE_PROJECT__COMMUNITY_RESOURCES:
				return ((InternalEList<?>)getCommunityResources()).basicRemove(otherEnd, msgs);
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
			case EclipseprojectPackage.ECLIPSE_PROJECT__IS_PARENT_PROJECT:
				return isIsParentProject();
			case EclipseprojectPackage.ECLIPSE_PROJECT__NAME:
				return getName();
			case EclipseprojectPackage.ECLIPSE_PROJECT__URL:
				return getUrl();
			case EclipseprojectPackage.ECLIPSE_PROJECT__STATUS:
				return getStatus();
			case EclipseprojectPackage.ECLIPSE_PROJECT__REPOSITORIES:
				return getRepositories();
			case EclipseprojectPackage.ECLIPSE_PROJECT__LIVELINESS:
				return getLiveliness();
			case EclipseprojectPackage.ECLIPSE_PROJECT__MEMBERS:
				return getMembers();
			case EclipseprojectPackage.ECLIPSE_PROJECT__ORGANIZATIONS:
				return getOrganizations();
			case EclipseprojectPackage.ECLIPSE_PROJECT__AVAILABLE_PLATFORM_VERSIONS:
				return getAvailablePlatformVersions();
			case EclipseprojectPackage.ECLIPSE_PROJECT__PROJECTS:
				return getProjects();
			case EclipseprojectPackage.ECLIPSE_PROJECT__LICENSE:
				return getLicense();
			case EclipseprojectPackage.ECLIPSE_PROJECT__COMMUNITY_RESOURCES:
				return getCommunityResources();
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
			case EclipseprojectPackage.ECLIPSE_PROJECT__IS_PARENT_PROJECT:
				setIsParentProject((Boolean)newValue);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__NAME:
				setName((String)newValue);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__URL:
				setUrl((String)newValue);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__STATUS:
				setStatus((EnumProjectLiveCycle)newValue);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__REPOSITORIES:
				getRepositories().clear();
				getRepositories().addAll((Collection<? extends CodeRepository>)newValue);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__LIVELINESS:
				setLiveliness((EnumLiveliness)newValue);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__MEMBERS:
				getMembers().clear();
				getMembers().addAll((Collection<? extends ProjectMember>)newValue);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__ORGANIZATIONS:
				setOrganizations((Integer)newValue);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__AVAILABLE_PLATFORM_VERSIONS:
				getAvailablePlatformVersions().clear();
				getAvailablePlatformVersions().addAll((Collection<? extends EnumPlatformVersion>)newValue);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__PROJECTS:
				getProjects().clear();
				getProjects().addAll((Collection<? extends EclipseProject>)newValue);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__LICENSE:
				setLicense((EnumLicense)newValue);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__COMMUNITY_RESOURCES:
				getCommunityResources().clear();
				getCommunityResources().addAll((Collection<? extends CommunityResource>)newValue);
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
			case EclipseprojectPackage.ECLIPSE_PROJECT__IS_PARENT_PROJECT:
				setIsParentProject(IS_PARENT_PROJECT_EDEFAULT);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__URL:
				setUrl(URL_EDEFAULT);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__STATUS:
				setStatus(STATUS_EDEFAULT);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__REPOSITORIES:
				getRepositories().clear();
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__LIVELINESS:
				setLiveliness(LIVELINESS_EDEFAULT);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__MEMBERS:
				getMembers().clear();
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__ORGANIZATIONS:
				setOrganizations(ORGANIZATIONS_EDEFAULT);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__AVAILABLE_PLATFORM_VERSIONS:
				getAvailablePlatformVersions().clear();
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__PROJECTS:
				getProjects().clear();
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__LICENSE:
				setLicense(LICENSE_EDEFAULT);
				return;
			case EclipseprojectPackage.ECLIPSE_PROJECT__COMMUNITY_RESOURCES:
				getCommunityResources().clear();
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
			case EclipseprojectPackage.ECLIPSE_PROJECT__IS_PARENT_PROJECT:
				return isParentProject != IS_PARENT_PROJECT_EDEFAULT;
			case EclipseprojectPackage.ECLIPSE_PROJECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EclipseprojectPackage.ECLIPSE_PROJECT__URL:
				return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
			case EclipseprojectPackage.ECLIPSE_PROJECT__STATUS:
				return status != STATUS_EDEFAULT;
			case EclipseprojectPackage.ECLIPSE_PROJECT__REPOSITORIES:
				return repositories != null && !repositories.isEmpty();
			case EclipseprojectPackage.ECLIPSE_PROJECT__LIVELINESS:
				return liveliness != LIVELINESS_EDEFAULT;
			case EclipseprojectPackage.ECLIPSE_PROJECT__MEMBERS:
				return members != null && !members.isEmpty();
			case EclipseprojectPackage.ECLIPSE_PROJECT__ORGANIZATIONS:
				return organizations != ORGANIZATIONS_EDEFAULT;
			case EclipseprojectPackage.ECLIPSE_PROJECT__AVAILABLE_PLATFORM_VERSIONS:
				return availablePlatformVersions != null && !availablePlatformVersions.isEmpty();
			case EclipseprojectPackage.ECLIPSE_PROJECT__PROJECTS:
				return projects != null && !projects.isEmpty();
			case EclipseprojectPackage.ECLIPSE_PROJECT__LICENSE:
				return license != LICENSE_EDEFAULT;
			case EclipseprojectPackage.ECLIPSE_PROJECT__COMMUNITY_RESOURCES:
				return communityResources != null && !communityResources.isEmpty();
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
			case EclipseprojectPackage.ECLIPSE_PROJECT___GET_PROJECT_AT_ANY_DEPTH__STRING:
				return getProjectAtAnyDepth((String)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (isParentProject: ");
		result.append(isParentProject);
		result.append(", name: ");
		result.append(name);
		result.append(", url: ");
		result.append(url);
		result.append(", status: ");
		result.append(status);
		result.append(", liveliness: ");
		result.append(liveliness);
		result.append(", organizations: ");
		result.append(organizations);
		result.append(", availablePlatformVersions: ");
		result.append(availablePlatformVersions);
		result.append(", license: ");
		result.append(license);
		result.append(')');
		return result.toString();
	}

} //EclipseProjectImpl
