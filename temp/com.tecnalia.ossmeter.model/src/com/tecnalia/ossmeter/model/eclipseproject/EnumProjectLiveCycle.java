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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Enum Project Live Cycle</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEnumProjectLiveCycle()
 * @model
 * @generated
 */
public enum EnumProjectLiveCycle implements Enumerator {
	/**
	 * The '<em><b>PRE PROPOSAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRE_PROPOSAL_VALUE
	 * @generated
	 * @ordered
	 */
	PRE_PROPOSAL(0, "PRE_PROPOSAL", "PRE_PROPOSAL"),

	/**
	 * The '<em><b>PROPOSAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPOSAL_VALUE
	 * @generated
	 * @ordered
	 */
	PROPOSAL(1, "PROPOSAL", "PROPOSAL"),

	/**
	 * The '<em><b>INCUBATION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INCUBATION_VALUE
	 * @generated
	 * @ordered
	 */
	INCUBATION(2, "INCUBATION", "INCUBATION"),

	/**
	 * The '<em><b>MATURE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MATURE_VALUE
	 * @generated
	 * @ordered
	 */
	MATURE(3, "MATURE", "MATURE"),

	/**
	 * The '<em><b>TOP LEVEL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TOP_LEVEL_VALUE
	 * @generated
	 * @ordered
	 */
	TOP_LEVEL(4, "TOP_LEVEL", "TOP_LEVEL"),

	/**
	 * The '<em><b>ARCHIVED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ARCHIVED_VALUE
	 * @generated
	 * @ordered
	 */
	ARCHIVED(5, "ARCHIVED", "ARCHIVED");

	/**
	 * The '<em><b>PRE PROPOSAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PRE PROPOSAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PRE_PROPOSAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PRE_PROPOSAL_VALUE = 0;

	/**
	 * The '<em><b>PROPOSAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPOSAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPOSAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPOSAL_VALUE = 1;

	/**
	 * The '<em><b>INCUBATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>INCUBATION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INCUBATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INCUBATION_VALUE = 2;

	/**
	 * The '<em><b>MATURE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MATURE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MATURE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MATURE_VALUE = 3;

	/**
	 * The '<em><b>TOP LEVEL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TOP LEVEL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TOP_LEVEL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TOP_LEVEL_VALUE = 4;

	/**
	 * The '<em><b>ARCHIVED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ARCHIVED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ARCHIVED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ARCHIVED_VALUE = 5;

	/**
	 * An array of all the '<em><b>Enum Project Live Cycle</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EnumProjectLiveCycle[] VALUES_ARRAY =
		new EnumProjectLiveCycle[] {
			PRE_PROPOSAL,
			PROPOSAL,
			INCUBATION,
			MATURE,
			TOP_LEVEL,
			ARCHIVED,
		};

	/**
	 * A public read-only list of all the '<em><b>Enum Project Live Cycle</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EnumProjectLiveCycle> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Enum Project Live Cycle</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EnumProjectLiveCycle get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EnumProjectLiveCycle result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Enum Project Live Cycle</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EnumProjectLiveCycle getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EnumProjectLiveCycle result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Enum Project Live Cycle</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EnumProjectLiveCycle get(int value) {
		switch (value) {
			case PRE_PROPOSAL_VALUE: return PRE_PROPOSAL;
			case PROPOSAL_VALUE: return PROPOSAL;
			case INCUBATION_VALUE: return INCUBATION;
			case MATURE_VALUE: return MATURE;
			case TOP_LEVEL_VALUE: return TOP_LEVEL;
			case ARCHIVED_VALUE: return ARCHIVED;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EnumProjectLiveCycle(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
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
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //EnumProjectLiveCycle
