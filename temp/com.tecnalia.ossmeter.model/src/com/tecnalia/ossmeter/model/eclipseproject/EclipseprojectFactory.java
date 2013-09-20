/**
 */
package com.tecnalia.ossmeter.model.eclipseproject;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage
 * @generated
 */
public interface EclipseprojectFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EclipseprojectFactory eINSTANCE = com.tecnalia.ossmeter.model.eclipseproject.impl.EclipseprojectFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Eclipse World</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Eclipse World</em>'.
	 * @generated
	 */
	EclipseWorld createEclipseWorld();

	/**
	 * Returns a new object of class '<em>Eclipse Project</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Eclipse Project</em>'.
	 * @generated
	 */
	EclipseProject createEclipseProject();

	/**
	 * Returns a new object of class '<em>Project Member</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Member</em>'.
	 * @generated
	 */
	ProjectMember createProjectMember();

	/**
	 * Returns a new object of class '<em>User</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>User</em>'.
	 * @generated
	 */
	User createUser();

	/**
	 * Returns a new object of class '<em>Code Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Code Repository</em>'.
	 * @generated
	 */
	CodeRepository createCodeRepository();

	/**
	 * Returns a new object of class '<em>Community Resource</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Community Resource</em>'.
	 * @generated
	 */
	CommunityResource createCommunityResource();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EclipseprojectPackage getEclipseprojectPackage();

} //EclipseprojectFactory
