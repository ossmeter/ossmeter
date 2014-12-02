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
package com.tecnalia.ossmeter.model.eclipseproject;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Member</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getUser <em>User</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getRole <em>Role</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getUrl <em>Url</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getProjectMember()
 * @model
 * @generated
 */
public interface ProjectMember extends EObject {
	/**
	 * Returns the value of the '<em><b>User</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User</em>' reference.
	 * @see #setUser(User)
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getProjectMember_User()
	 * @model required="true"
	 * @generated
	 */
	User getUser();

	/**
	 * Sets the value of the '{@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getUser <em>User</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User</em>' reference.
	 * @see #getUser()
	 * @generated
	 */
	void setUser(User value);

	/**
	 * Returns the value of the '<em><b>Role</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tecnalia.ossmeter.model.eclipseproject.EnumMemberRole}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role</em>' attribute.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumMemberRole
	 * @see #setRole(EnumMemberRole)
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getProjectMember_Role()
	 * @model required="true"
	 * @generated
	 */
	EnumMemberRole getRole();

	/**
	 * Sets the value of the '{@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getRole <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role</em>' attribute.
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EnumMemberRole
	 * @see #getRole()
	 * @generated
	 */
	void setRole(EnumMemberRole value);

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
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getProjectMember_Url()
	 * @model required="true"
	 * @generated
	 */
	String getUrl();

	/**
	 * Sets the value of the '{@link com.tecnalia.ossmeter.model.eclipseproject.ProjectMember#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(String value);

} // ProjectMember
