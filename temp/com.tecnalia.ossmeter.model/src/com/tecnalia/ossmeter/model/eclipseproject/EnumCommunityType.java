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
 * A representation of the literals of the enumeration '<em><b>Enum Community Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage#getEnumCommunityType()
 * @model
 * @generated
 */
public enum EnumCommunityType implements Enumerator {
	/**
	 * The '<em><b>MAILING LIST</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAILING_LIST_VALUE
	 * @generated
	 * @ordered
	 */
	MAILING_LIST(0, "MAILING_LIST", "MAILING_LIST"),

	/**
	 * The '<em><b>FORUM</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FORUM_VALUE
	 * @generated
	 * @ordered
	 */
	FORUM(1, "FORUM", "FORUM"),

	/**
	 * The '<em><b>WEBSITE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WEBSITE_VALUE
	 * @generated
	 * @ordered
	 */
	WEBSITE(2, "WEBSITE", "WEBSITE"), /**
	 * The '<em><b>WIKI</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WIKI_VALUE
	 * @generated
	 * @ordered
	 */
	WIKI(3, "WIKI", "WIKI"), /**
	 * The '<em><b>BUG TRACKER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BUG_TRACKER_VALUE
	 * @generated
	 * @ordered
	 */
	BUG_TRACKER(4, "BUG_TRACKER", "BUG_TRACKER");

	/**
	 * The '<em><b>MAILING LIST</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MAILING LIST</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MAILING_LIST
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MAILING_LIST_VALUE = 0;

	/**
	 * The '<em><b>FORUM</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FORUM</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FORUM
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FORUM_VALUE = 1;

	/**
	 * The '<em><b>WEBSITE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WEBSITE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WEBSITE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WEBSITE_VALUE = 2;

	/**
	 * The '<em><b>WIKI</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WIKI</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WIKI
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WIKI_VALUE = 3;

	/**
	 * The '<em><b>BUG TRACKER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BUG TRACKER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BUG_TRACKER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BUG_TRACKER_VALUE = 4;

	/**
	 * An array of all the '<em><b>Enum Community Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EnumCommunityType[] VALUES_ARRAY =
		new EnumCommunityType[] {
			MAILING_LIST,
			FORUM,
			WEBSITE,
			WIKI,
			BUG_TRACKER,
		};

	/**
	 * A public read-only list of all the '<em><b>Enum Community Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EnumCommunityType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Enum Community Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EnumCommunityType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EnumCommunityType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Enum Community Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EnumCommunityType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EnumCommunityType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Enum Community Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EnumCommunityType get(int value) {
		switch (value) {
			case MAILING_LIST_VALUE: return MAILING_LIST;
			case FORUM_VALUE: return FORUM;
			case WEBSITE_VALUE: return WEBSITE;
			case WIKI_VALUE: return WIKI;
			case BUG_TRACKER_VALUE: return BUG_TRACKER;
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
	private EnumCommunityType(int value, String name, String literal) {
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
	
} //EnumCommunityType
