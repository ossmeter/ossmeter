/**
 */
package com.tecnalia.ossmeter.model.eclipseproject.impl;

import com.tecnalia.ossmeter.model.eclipseproject.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EclipseprojectFactoryImpl extends EFactoryImpl implements EclipseprojectFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EclipseprojectFactory init() {
		try {
			EclipseprojectFactory theEclipseprojectFactory = (EclipseprojectFactory)EPackage.Registry.INSTANCE.getEFactory(EclipseprojectPackage.eNS_URI);
			if (theEclipseprojectFactory != null) {
				return theEclipseprojectFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EclipseprojectFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseprojectFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case EclipseprojectPackage.ECLIPSE_WORLD: return createEclipseWorld();
			case EclipseprojectPackage.ECLIPSE_PROJECT: return createEclipseProject();
			case EclipseprojectPackage.PROJECT_MEMBER: return createProjectMember();
			case EclipseprojectPackage.USER: return createUser();
			case EclipseprojectPackage.CODE_REPOSITORY: return createCodeRepository();
			case EclipseprojectPackage.COMMUNITY_RESOURCE: return createCommunityResource();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case EclipseprojectPackage.ENUM_COMMUNITY_TYPE:
				return createEnumCommunityTypeFromString(eDataType, initialValue);
			case EclipseprojectPackage.ENUM_REPOSITORY:
				return createEnumRepositoryFromString(eDataType, initialValue);
			case EclipseprojectPackage.ENUM_PROJECT_LIVE_CYCLE:
				return createEnumProjectLiveCycleFromString(eDataType, initialValue);
			case EclipseprojectPackage.ENUM_LIVELINESS:
				return createEnumLivelinessFromString(eDataType, initialValue);
			case EclipseprojectPackage.ENUM_PLATFORM_VERSION:
				return createEnumPlatformVersionFromString(eDataType, initialValue);
			case EclipseprojectPackage.ENUM_LICENSE:
				return createEnumLicenseFromString(eDataType, initialValue);
			case EclipseprojectPackage.ENUM_MEMBER_ROLE:
				return createEnumMemberRoleFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case EclipseprojectPackage.ENUM_COMMUNITY_TYPE:
				return convertEnumCommunityTypeToString(eDataType, instanceValue);
			case EclipseprojectPackage.ENUM_REPOSITORY:
				return convertEnumRepositoryToString(eDataType, instanceValue);
			case EclipseprojectPackage.ENUM_PROJECT_LIVE_CYCLE:
				return convertEnumProjectLiveCycleToString(eDataType, instanceValue);
			case EclipseprojectPackage.ENUM_LIVELINESS:
				return convertEnumLivelinessToString(eDataType, instanceValue);
			case EclipseprojectPackage.ENUM_PLATFORM_VERSION:
				return convertEnumPlatformVersionToString(eDataType, instanceValue);
			case EclipseprojectPackage.ENUM_LICENSE:
				return convertEnumLicenseToString(eDataType, instanceValue);
			case EclipseprojectPackage.ENUM_MEMBER_ROLE:
				return convertEnumMemberRoleToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseWorld createEclipseWorld() {
		EclipseWorldImpl eclipseWorld = new EclipseWorldImpl();
		return eclipseWorld;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseProject createEclipseProject() {
		EclipseProjectImpl eclipseProject = new EclipseProjectImpl();
		return eclipseProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectMember createProjectMember() {
		ProjectMemberImpl projectMember = new ProjectMemberImpl();
		return projectMember;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User createUser() {
		UserImpl user = new UserImpl();
		return user;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodeRepository createCodeRepository() {
		CodeRepositoryImpl codeRepository = new CodeRepositoryImpl();
		return codeRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommunityResource createCommunityResource() {
		CommunityResourceImpl communityResource = new CommunityResourceImpl();
		return communityResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumCommunityType createEnumCommunityTypeFromString(EDataType eDataType, String initialValue) {
		EnumCommunityType result = EnumCommunityType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnumCommunityTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumRepository createEnumRepositoryFromString(EDataType eDataType, String initialValue) {
		EnumRepository result = EnumRepository.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnumRepositoryToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumProjectLiveCycle createEnumProjectLiveCycleFromString(EDataType eDataType, String initialValue) {
		EnumProjectLiveCycle result = EnumProjectLiveCycle.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnumProjectLiveCycleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumLiveliness createEnumLivelinessFromString(EDataType eDataType, String initialValue) {
		EnumLiveliness result = EnumLiveliness.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnumLivelinessToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumPlatformVersion createEnumPlatformVersionFromString(EDataType eDataType, String initialValue) {
		EnumPlatformVersion result = EnumPlatformVersion.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnumPlatformVersionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumLicense createEnumLicenseFromString(EDataType eDataType, String initialValue) {
		EnumLicense result = EnumLicense.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnumLicenseToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumMemberRole createEnumMemberRoleFromString(EDataType eDataType, String initialValue) {
		EnumMemberRole result = EnumMemberRole.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnumMemberRoleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseprojectPackage getEclipseprojectPackage() {
		return (EclipseprojectPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EclipseprojectPackage getPackage() {
		return EclipseprojectPackage.eINSTANCE;
	}

} //EclipseprojectFactoryImpl
