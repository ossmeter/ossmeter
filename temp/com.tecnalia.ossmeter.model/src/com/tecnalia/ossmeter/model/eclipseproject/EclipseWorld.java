/**
 */
package com.tecnalia.ossmeter.model.eclipseproject;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Eclipse World</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.EclipseWorld#getProjects <em>Projects</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseWorld()
 * @model
 * @generated
 */
public interface EclipseWorld extends EObject {
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
	 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEclipseWorld_Projects()
	 * @model containment="true"
	 * @generated
	 */
	EList<EclipseProject> getProjects();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EclipseProject getProjectAtAnyDepth(String name);

} // EclipseWorld
