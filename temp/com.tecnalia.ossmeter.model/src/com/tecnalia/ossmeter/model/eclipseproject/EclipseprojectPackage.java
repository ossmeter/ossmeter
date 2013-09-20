/**
 */
package com.tecnalia.ossmeter.model.eclipseproject;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectFactory
 * @model kind="package"
 * @generated
 */
public interface EclipseprojectPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "eclipseproject";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://com.tecnalia.ossmeter.model/eclipse_project";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "eclipse_project";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EclipseprojectPackage eINSTANCE = com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseWorldImpl <em>Eclipse World</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseWorldImpl
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEclipseWorld()
	 * @generated
	 */
	int ECLIPSE_WORLD = 0;

	/**
	 * The feature id for the '<em><b>Projects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_WORLD__PROJECTS = 0;

	/**
	 * The number of structural features of the '<em>Eclipse World</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_WORLD_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Get Project At Any Depth</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_WORLD___GET_PROJECT_AT_ANY_DEPTH__STRING = 0;

	/**
	 * The number of operations of the '<em>Eclipse World</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_WORLD_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl <em>Eclipse Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEclipseProject()
	 * @generated
	 */
	int ECLIPSE_PROJECT = 1;

	/**
	 * The feature id for the '<em><b>Is Parent Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__IS_PARENT_PROJECT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__URL = 2;

	/**
	 * The feature id for the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__STATUS = 3;

	/**
	 * The feature id for the '<em><b>Repositories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__REPOSITORIES = 4;

	/**
	 * The feature id for the '<em><b>Liveliness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__LIVELINESS = 5;

	/**
	 * The feature id for the '<em><b>Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__MEMBERS = 6;

	/**
	 * The feature id for the '<em><b>Organizations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__ORGANIZATIONS = 7;

	/**
	 * The feature id for the '<em><b>Available Platform Versions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__AVAILABLE_PLATFORM_VERSIONS = 8;

	/**
	 * The feature id for the '<em><b>Projects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__PROJECTS = 9;

	/**
	 * The feature id for the '<em><b>License</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__LICENSE = 10;

	/**
	 * The feature id for the '<em><b>Community Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT__COMMUNITY_RESOURCES = 11;

	/**
	 * The number of structural features of the '<em>Eclipse Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT_FEATURE_COUNT = 12;

	/**
	 * The operation id for the '<em>Get Project At Any Depth</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT___GET_PROJECT_AT_ANY_DEPTH__STRING = 0;

	/**
	 * The number of operations of the '<em>Eclipse Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLIPSE_PROJECT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.ProjectMemberImpl <em>Project Member</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.ProjectMemberImpl
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getProjectMember()
	 * @generated
	 */
	int PROJECT_MEMBER = 2;

	/**
	 * The feature id for the '<em><b>User</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_MEMBER__USER = 0;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_MEMBER__ROLE = 1;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_MEMBER__URL = 2;

	/**
	 * The number of structural features of the '<em>Project Member</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_MEMBER_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Project Member</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_MEMBER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.UserImpl <em>User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.UserImpl
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getUser()
	 * @generated
	 */
	int USER = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__URL = 1;

	/**
	 * The number of structural features of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.CodeRepositoryImpl <em>Code Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.CodeRepositoryImpl
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getCodeRepository()
	 * @generated
	 */
	int CODE_REPOSITORY = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODE_REPOSITORY__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODE_REPOSITORY__URL = 1;

	/**
	 * The number of structural features of the '<em>Code Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODE_REPOSITORY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Code Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODE_REPOSITORY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.CommunityResourceImpl <em>Community Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.CommunityResourceImpl
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getCommunityResource()
	 * @generated
	 */
	int COMMUNITY_RESOURCE = 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_RESOURCE__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_RESOURCE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_RESOURCE__URL = 2;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_RESOURCE__DATA = 3;

	/**
	 * The number of structural features of the '<em>Community Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_RESOURCE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Community Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_RESOURCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumCommunityType <em>Enum Community Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumCommunityType
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumCommunityType()
	 * @generated
	 */
	int ENUM_COMMUNITY_TYPE = 6;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumRepository <em>Enum Repository</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumRepository
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumRepository()
	 * @generated
	 */
	int ENUM_REPOSITORY = 7;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumProjectLiveCycle <em>Enum Project Live Cycle</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumProjectLiveCycle
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumProjectLiveCycle()
	 * @generated
	 */
	int ENUM_PROJECT_LIVE_CYCLE = 8;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumLiveliness <em>Enum Liveliness</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumLiveliness
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumLiveliness()
	 * @generated
	 */
	int ENUM_LIVELINESS = 9;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumPlatformVersion <em>Enum Platform Version</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumPlatformVersion
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumPlatformVersion()
	 * @generated
	 */
	int ENUM_PLATFORM_VERSION = 10;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumLicense <em>Enum License</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumLicense
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumLicense()
	 * @generated
	 */
	int ENUM_LICENSE = 11;

	/**
	 * The meta object id for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumMemberRole <em>Enum Member Role</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumMemberRole
	 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumMemberRole()
	 * @generated
	 */
	int ENUM_MEMBER_ROLE = 12;


	/**
	 * Returns the meta object for class '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld <em>Eclipse World</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Eclipse World</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld
	 * @generated
	 */
	EClass getEclipseWorld();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld#getProjects <em>Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Projects</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld#getProjects()
	 * @see #getEclipseWorld()
	 * @generated
	 */
	EReference getEclipseWorld_Projects();

	/**
	 * Returns the meta object for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld#getProjectAtAnyDepth(java.lang.String) <em>Get Project At Any Depth</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Project At Any Depth</em>' operation.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld#getProjectAtAnyDepth(java.lang.String)
	 * @generated
	 */
	EOperation getEclipseWorld__GetProjectAtAnyDepth__String();

	/**
	 * Returns the meta object for class '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject <em>Eclipse Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Eclipse Project</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject
	 * @generated
	 */
	EClass getEclipseProject();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#isIsParentProject <em>Is Parent Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Parent Project</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#isIsParentProject()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EAttribute getEclipseProject_IsParentProject();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getName()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EAttribute getEclipseProject_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getUrl()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EAttribute getEclipseProject_Url();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getStatus()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EAttribute getEclipseProject_Status();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getRepositories <em>Repositories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Repositories</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getRepositories()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EReference getEclipseProject_Repositories();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getLiveliness <em>Liveliness</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Liveliness</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getLiveliness()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EAttribute getEclipseProject_Liveliness();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getMembers <em>Members</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Members</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getMembers()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EReference getEclipseProject_Members();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getOrganizations <em>Organizations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Organizations</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getOrganizations()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EAttribute getEclipseProject_Organizations();

	/**
	 * Returns the meta object for the attribute list '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getAvailablePlatformVersions <em>Available Platform Versions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Available Platform Versions</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getAvailablePlatformVersions()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EAttribute getEclipseProject_AvailablePlatformVersions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getProjects <em>Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Projects</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getProjects()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EReference getEclipseProject_Projects();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getLicense <em>License</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>License</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getLicense()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EAttribute getEclipseProject_License();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getCommunityResources <em>Community Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Community Resources</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getCommunityResources()
	 * @see #getEclipseProject()
	 * @generated
	 */
	EReference getEclipseProject_CommunityResources();

	/**
	 * Returns the meta object for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getProjectAtAnyDepth(java.lang.String) <em>Get Project At Any Depth</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Project At Any Depth</em>' operation.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseProject#getProjectAtAnyDepth(java.lang.String)
	 * @generated
	 */
	EOperation getEclipseProject__GetProjectAtAnyDepth__String();

	/**
	 * Returns the meta object for class '{@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember <em>Project Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Member</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.ProjectMember
	 * @generated
	 */
	EClass getProjectMember();

	/**
	 * Returns the meta object for the reference '{@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>User</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getUser()
	 * @see #getProjectMember()
	 * @generated
	 */
	EReference getProjectMember_User();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Role</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getRole()
	 * @see #getProjectMember()
	 * @generated
	 */
	EAttribute getProjectMember_Role();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getUrl()
	 * @see #getProjectMember()
	 * @generated
	 */
	EAttribute getProjectMember_Url();

	/**
	 * Returns the meta object for class '{@link com.tecnalia.ossmeter.model.eclipseproject.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.User
	 * @generated
	 */
	EClass getUser();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.User#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.User#getName()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.User#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.User#getUrl()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Url();

	/**
	 * Returns the meta object for class '{@link com.tecnalia.ossmeter.model.eclipseproject.CodeRepository <em>Code Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Code Repository</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.CodeRepository
	 * @generated
	 */
	EClass getCodeRepository();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.CodeRepository#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.CodeRepository#getType()
	 * @see #getCodeRepository()
	 * @generated
	 */
	EAttribute getCodeRepository_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.CodeRepository#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.CodeRepository#getUrl()
	 * @see #getCodeRepository()
	 * @generated
	 */
	EAttribute getCodeRepository_Url();

	/**
	 * Returns the meta object for class '{@link com.tecnalia.ossmeter.model.eclipseproject.CommunityResource <em>Community Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Community Resource</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.CommunityResource
	 * @generated
	 */
	EClass getCommunityResource();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.CommunityResource#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.CommunityResource#getType()
	 * @see #getCommunityResource()
	 * @generated
	 */
	EAttribute getCommunityResource_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.CommunityResource#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.CommunityResource#getName()
	 * @see #getCommunityResource()
	 * @generated
	 */
	EAttribute getCommunityResource_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.CommunityResource#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.CommunityResource#getUrl()
	 * @see #getCommunityResource()
	 * @generated
	 */
	EAttribute getCommunityResource_Url();

	/**
	 * Returns the meta object for the attribute '{@link com.tecnalia.ossmeter.model.eclipseproject.CommunityResource#getData <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.CommunityResource#getData()
	 * @see #getCommunityResource()
	 * @generated
	 */
	EAttribute getCommunityResource_Data();

	/**
	 * Returns the meta object for enum '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumCommunityType <em>Enum Community Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enum Community Type</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumCommunityType
	 * @generated
	 */
	EEnum getEnumCommunityType();

	/**
	 * Returns the meta object for enum '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumRepository <em>Enum Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enum Repository</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumRepository
	 * @generated
	 */
	EEnum getEnumRepository();

	/**
	 * Returns the meta object for enum '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumProjectLiveCycle <em>Enum Project Live Cycle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enum Project Live Cycle</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumProjectLiveCycle
	 * @generated
	 */
	EEnum getEnumProjectLiveCycle();

	/**
	 * Returns the meta object for enum '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumLiveliness <em>Enum Liveliness</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enum Liveliness</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumLiveliness
	 * @generated
	 */
	EEnum getEnumLiveliness();

	/**
	 * Returns the meta object for enum '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumPlatformVersion <em>Enum Platform Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enum Platform Version</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumPlatformVersion
	 * @generated
	 */
	EEnum getEnumPlatformVersion();

	/**
	 * Returns the meta object for enum '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumLicense <em>Enum License</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enum License</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumLicense
	 * @generated
	 */
	EEnum getEnumLicense();

	/**
	 * Returns the meta object for enum '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumMemberRole <em>Enum Member Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enum Member Role</em>'.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumMemberRole
	 * @generated
	 */
	EEnum getEnumMemberRole();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EclipseprojectFactory getEclipseprojectFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseWorldImpl <em>Eclipse World</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseWorldImpl
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEclipseWorld()
		 * @generated
		 */
		EClass ECLIPSE_WORLD = eINSTANCE.getEclipseWorld();

		/**
		 * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLIPSE_WORLD__PROJECTS = eINSTANCE.getEclipseWorld_Projects();

		/**
		 * The meta object literal for the '<em><b>Get Project At Any Depth</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ECLIPSE_WORLD___GET_PROJECT_AT_ANY_DEPTH__STRING = eINSTANCE.getEclipseWorld__GetProjectAtAnyDepth__String();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl <em>Eclipse Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseProjectImpl
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEclipseProject()
		 * @generated
		 */
		EClass ECLIPSE_PROJECT = eINSTANCE.getEclipseProject();

		/**
		 * The meta object literal for the '<em><b>Is Parent Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ECLIPSE_PROJECT__IS_PARENT_PROJECT = eINSTANCE.getEclipseProject_IsParentProject();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ECLIPSE_PROJECT__NAME = eINSTANCE.getEclipseProject_Name();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ECLIPSE_PROJECT__URL = eINSTANCE.getEclipseProject_Url();

		/**
		 * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ECLIPSE_PROJECT__STATUS = eINSTANCE.getEclipseProject_Status();

		/**
		 * The meta object literal for the '<em><b>Repositories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLIPSE_PROJECT__REPOSITORIES = eINSTANCE.getEclipseProject_Repositories();

		/**
		 * The meta object literal for the '<em><b>Liveliness</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ECLIPSE_PROJECT__LIVELINESS = eINSTANCE.getEclipseProject_Liveliness();

		/**
		 * The meta object literal for the '<em><b>Members</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLIPSE_PROJECT__MEMBERS = eINSTANCE.getEclipseProject_Members();

		/**
		 * The meta object literal for the '<em><b>Organizations</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ECLIPSE_PROJECT__ORGANIZATIONS = eINSTANCE.getEclipseProject_Organizations();

		/**
		 * The meta object literal for the '<em><b>Available Platform Versions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ECLIPSE_PROJECT__AVAILABLE_PLATFORM_VERSIONS = eINSTANCE.getEclipseProject_AvailablePlatformVersions();

		/**
		 * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLIPSE_PROJECT__PROJECTS = eINSTANCE.getEclipseProject_Projects();

		/**
		 * The meta object literal for the '<em><b>License</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ECLIPSE_PROJECT__LICENSE = eINSTANCE.getEclipseProject_License();

		/**
		 * The meta object literal for the '<em><b>Community Resources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLIPSE_PROJECT__COMMUNITY_RESOURCES = eINSTANCE.getEclipseProject_CommunityResources();

		/**
		 * The meta object literal for the '<em><b>Get Project At Any Depth</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ECLIPSE_PROJECT___GET_PROJECT_AT_ANY_DEPTH__STRING = eINSTANCE.getEclipseProject__GetProjectAtAnyDepth__String();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.ProjectMemberImpl <em>Project Member</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.ProjectMemberImpl
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getProjectMember()
		 * @generated
		 */
		EClass PROJECT_MEMBER = eINSTANCE.getProjectMember();

		/**
		 * The meta object literal for the '<em><b>User</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_MEMBER__USER = eINSTANCE.getProjectMember_User();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_MEMBER__ROLE = eINSTANCE.getProjectMember_Role();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_MEMBER__URL = eINSTANCE.getProjectMember_Url();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.UserImpl <em>User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.UserImpl
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getUser()
		 * @generated
		 */
		EClass USER = eINSTANCE.getUser();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__NAME = eINSTANCE.getUser_Name();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__URL = eINSTANCE.getUser_Url();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.CodeRepositoryImpl <em>Code Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.CodeRepositoryImpl
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getCodeRepository()
		 * @generated
		 */
		EClass CODE_REPOSITORY = eINSTANCE.getCodeRepository();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODE_REPOSITORY__TYPE = eINSTANCE.getCodeRepository_Type();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODE_REPOSITORY__URL = eINSTANCE.getCodeRepository_Url();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.impl.CommunityResourceImpl <em>Community Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.CommunityResourceImpl
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getCommunityResource()
		 * @generated
		 */
		EClass COMMUNITY_RESOURCE = eINSTANCE.getCommunityResource();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMUNITY_RESOURCE__TYPE = eINSTANCE.getCommunityResource_Type();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMUNITY_RESOURCE__NAME = eINSTANCE.getCommunityResource_Name();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMUNITY_RESOURCE__URL = eINSTANCE.getCommunityResource_Url();

		/**
		 * The meta object literal for the '<em><b>Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMUNITY_RESOURCE__DATA = eINSTANCE.getCommunityResource_Data();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumCommunityType <em>Enum Community Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumCommunityType
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumCommunityType()
		 * @generated
		 */
		EEnum ENUM_COMMUNITY_TYPE = eINSTANCE.getEnumCommunityType();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumRepository <em>Enum Repository</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumRepository
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumRepository()
		 * @generated
		 */
		EEnum ENUM_REPOSITORY = eINSTANCE.getEnumRepository();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumProjectLiveCycle <em>Enum Project Live Cycle</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumProjectLiveCycle
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumProjectLiveCycle()
		 * @generated
		 */
		EEnum ENUM_PROJECT_LIVE_CYCLE = eINSTANCE.getEnumProjectLiveCycle();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumLiveliness <em>Enum Liveliness</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumLiveliness
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumLiveliness()
		 * @generated
		 */
		EEnum ENUM_LIVELINESS = eINSTANCE.getEnumLiveliness();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumPlatformVersion <em>Enum Platform Version</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumPlatformVersion
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumPlatformVersion()
		 * @generated
		 */
		EEnum ENUM_PLATFORM_VERSION = eINSTANCE.getEnumPlatformVersion();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumLicense <em>Enum License</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumLicense
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumLicense()
		 * @generated
		 */
		EEnum ENUM_LICENSE = eINSTANCE.getEnumLicense();

		/**
		 * The meta object literal for the '{@link com.tecnalia.ossmeter.model.eclipseproject.EnumMemberRole <em>Enum Member Role</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumMemberRole
		 * @see com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectPackageImpl#getEnumMemberRole()
		 * @generated
		 */
		EEnum ENUM_MEMBER_ROLE = eINSTANCE.getEnumMemberRole();

	}

} //EclipseprojectPackage
