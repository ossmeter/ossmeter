/**
 */
package com.tecnalia.ossmeter.model.eclipseproject;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Eclipse Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#isIsParentProject <em>Is Parent Project</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getName <em>Name</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getUrl <em>Url</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getRepositories <em>Repositories</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getLiveliness <em>Liveliness</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getMembers <em>Members</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getOrganizations <em>Organizations</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getAvailablePlatformVersions <em>Available Platform Versions</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getProjects <em>Projects</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getLicense <em>License</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getCommunityResources <em>Community Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject()
 * @model
 * @generated
 */
public interface EclipseProject extends EObject {
	/**
	 * Returns the value of the '<em><b>Is Parent Project</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Parent Project</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Parent Project</em>' attribute.
	 * @see #setIsParentProject(boolean)
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_IsParentProject()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isIsParentProject();

	/**
	 * Sets the value of the '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#isIsParentProject <em>Is Parent Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Parent Project</em>' attribute.
	 * @see #isIsParentProject()
	 * @generated
	 */
	void setIsParentProject(boolean value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_Name()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url</em>' attribute.
	 * @see #setUrl(String)
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_Url()
	 * @model required="true"
	 * @generated
	 */
	String getUrl();

	/**
	 * Sets the value of the '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(String value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tecnalia.ossmeter.model.eclipseproject.EnumProjectLiveCycle}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumProjectLiveCycle
	 * @see #setStatus(EnumProjectLiveCycle)
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_Status()
	 * @model required="true"
	 * @generated
	 */
	EnumProjectLiveCycle getStatus();

	/**
	 * Sets the value of the '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumProjectLiveCycle
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(EnumProjectLiveCycle value);

	/**
	 * Returns the value of the '<em><b>Repositories</b></em>' containment reference list.
	 * The list contents are of type {@link com.tecnalia.ossmeter.model.eclipseproject.CodeRepository}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repositories</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repositories</em>' containment reference list.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_Repositories()
	 * @model containment="true"
	 * @generated
	 */
	EList<CodeRepository> getRepositories();

	/**
	 * Returns the value of the '<em><b>Liveliness</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tecnalia.ossmeter.model.eclipseproject.EnumLiveliness}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Liveliness</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Liveliness</em>' attribute.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumLiveliness
	 * @see #setLiveliness(EnumLiveliness)
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_Liveliness()
	 * @model required="true"
	 * @generated
	 */
	EnumLiveliness getLiveliness();

	/**
	 * Sets the value of the '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getLiveliness <em>Liveliness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Liveliness</em>' attribute.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumLiveliness
	 * @see #getLiveliness()
	 * @generated
	 */
	void setLiveliness(EnumLiveliness value);

	/**
	 * Returns the value of the '<em><b>Members</b></em>' containment reference list.
	 * The list contents are of type {@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Members</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Members</em>' containment reference list.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_Members()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ProjectMember> getMembers();

	/**
	 * Returns the value of the '<em><b>Organizations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organizations</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organizations</em>' attribute.
	 * @see #setOrganizations(int)
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_Organizations()
	 * @model required="true"
	 * @generated
	 */
	int getOrganizations();

	/**
	 * Sets the value of the '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getOrganizations <em>Organizations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Organizations</em>' attribute.
	 * @see #getOrganizations()
	 * @generated
	 */
	void setOrganizations(int value);

	/**
	 * Returns the value of the '<em><b>Available Platform Versions</b></em>' attribute list.
	 * The list contents are of type {@link com.tecnalia.ossmeter.model.eclipseproject.EnumPlatformVersion}.
	 * The literals are from the enumeration {@link com.tecnalia.ossmeter.model.eclipseproject.EnumPlatformVersion}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Available Platform Versions</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Available Platform Versions</em>' attribute list.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumPlatformVersion
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_AvailablePlatformVersions()
	 * @model required="true"
	 * @generated
	 */
	EList<EnumPlatformVersion> getAvailablePlatformVersions();

	/**
	 * Returns the value of the '<em><b>Projects</b></em>' containment reference list.
	 * The list contents are of type {@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Projects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projects</em>' containment reference list.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_Projects()
	 * @model containment="true"
	 * @generated
	 */
	EList<EclipseProject> getProjects();

	/**
	 * Returns the value of the '<em><b>License</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tecnalia.ossmeter.model.eclipseproject.EnumLicense}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>License</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>License</em>' attribute.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumLicense
	 * @see #setLicense(EnumLicense)
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_License()
	 * @model
	 * @generated
	 */
	EnumLicense getLicense();

	/**
	 * Sets the value of the '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getLicense <em>License</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>License</em>' attribute.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumLicense
	 * @see #getLicense()
	 * @generated
	 */
	void setLicense(EnumLicense value);

	/**
	 * Returns the value of the '<em><b>Community Resources</b></em>' containment reference list.
	 * The list contents are of type {@link com.tecnalia.ossmeter.model.eclipseproject.CommunityResource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Community Resources</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Community Resources</em>' containment reference list.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseProject_CommunityResources()
	 * @model containment="true"
	 * @generated
	 */
	EList<CommunityResource> getCommunityResources();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EclipseProject getProjectAtAnyDepth(String name);

} // EclipseProject
