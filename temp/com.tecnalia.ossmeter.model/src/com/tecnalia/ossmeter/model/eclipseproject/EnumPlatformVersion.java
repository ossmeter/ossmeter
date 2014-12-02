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
 * A representation of the literals of the enumeration '<em><b>Enum Platform Version</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEnumPlatformVersion()
 * @model
 * @generated
 */
public enum EnumPlatformVersion implements Enumerator {
	/**
	 * The '<em><b>GANYMEDE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GANYMEDE_VALUE
	 * @generated
	 * @ordered
	 */
	GANYMEDE(0, "GANYMEDE", "GANYMEDE"),

	/**
	 * The '<em><b>GALILEO</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GALILEO_VALUE
	 * @generated
	 * @ordered
	 */
	GALILEO(1, "GALILEO", "GALILEO"),

	/**
	 * The '<em><b>HELIOS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HELIOS_VALUE
	 * @generated
	 * @ordered
	 */
	HELIOS(2, "HELIOS", "HELIOS"),

	/**
	 * The '<em><b>INDIGO</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INDIGO_VALUE
	 * @generated
	 * @ordered
	 */
	INDIGO(3, "INDIGO", "INDIGO"),

	/**
	 * The '<em><b>JUNO</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JUNO_VALUE
	 * @generated
	 * @ordered
	 */
	JUNO(4, "JUNO", "JUNO"),

	/**
	 * The '<em><b>KEPLER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #KEPLER_VALUE
	 * @generated
	 * @ordered
	 */
	KEPLER(5, "KEPLER", "KEPLER"),

	/**
	 * The '<em><b>LUNA</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LUNA_VALUE
	 * @generated
	 * @ordered
	 */
	LUNA(6, "LUNA", "LUNA");

	/**
	 * The '<em><b>GANYMEDE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>GANYMEDE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GANYMEDE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GANYMEDE_VALUE = 0;

	/**
	 * The '<em><b>GALILEO</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>GALILEO</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GALILEO
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GALILEO_VALUE = 1;

	/**
	 * The '<em><b>HELIOS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>HELIOS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HELIOS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int HELIOS_VALUE = 2;

	/**
	 * The '<em><b>INDIGO</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>INDIGO</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INDIGO
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INDIGO_VALUE = 3;

	/**
	 * The '<em><b>JUNO</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>JUNO</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JUNO
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int JUNO_VALUE = 4;

	/**
	 * The '<em><b>KEPLER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>KEPLER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #KEPLER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int KEPLER_VALUE = 5;

	/**
	 * The '<em><b>LUNA</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LUNA</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LUNA
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LUNA_VALUE = 6;

	/**
	 * An array of all the '<em><b>Enum Platform Version</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EnumPlatformVersion[] VALUES_ARRAY =
		new EnumPlatformVersion[] {
			GANYMEDE,
			GALILEO,
			HELIOS,
			INDIGO,
			JUNO,
			KEPLER,
			LUNA,
		};

	/**
	 * A public read-only list of all the '<em><b>Enum Platform Version</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EnumPlatformVersion> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Enum Platform Version</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EnumPlatformVersion get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EnumPlatformVersion result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Enum Platform Version</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EnumPlatformVersion getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EnumPlatformVersion result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Enum Platform Version</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EnumPlatformVersion get(int value) {
		switch (value) {
			case GANYMEDE_VALUE: return GANYMEDE;
			case GALILEO_VALUE: return GALILEO;
			case HELIOS_VALUE: return HELIOS;
			case INDIGO_VALUE: return INDIGO;
			case JUNO_VALUE: return JUNO;
			case KEPLER_VALUE: return KEPLER;
			case LUNA_VALUE: return LUNA;
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
	private EnumPlatformVersion(int value, String name, String literal) {
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
	
} //EnumPlatformVersion
