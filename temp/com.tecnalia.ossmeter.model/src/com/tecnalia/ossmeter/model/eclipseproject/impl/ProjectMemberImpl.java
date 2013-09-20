/**
 */
package com.tecnalia.ossmeter.model.eclipseproject.impl;

import com.tecnalia.ossmeter.model.eclipseproject.EclipseprojectPackage;
import com.tecnalia.ossmeter.model.eclipseproject.EnumMemberRole;
import com.tecnalia.ossmeter.model.eclipseproject.ProjectMember;
import com.tecnalia.ossmeter.model.eclipseproject.User;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Member</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.ProjectMemberImpl#getUser <em>User</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.ProjectMemberImpl#getRole <em>Role</em>}</li>
 *   <li>{@link com.tecnalia.ossmeter.model.eclipseproject.impl.ProjectMemberImpl#getUrl <em>Url</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectMemberImpl extends MinimalEObjectImpl.Container implements ProjectMember {
	/**
	 * The cached value of the '{@link #getUser() <em>User</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected User user;

	/**
	 * The default value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected static final EnumMemberRole ROLE_EDEFAULT = EnumMemberRole.CONTRIBUTOR;

	/**
	 * The cached value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected EnumMemberRole role = ROLE_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectMemberImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EclipseprojectPackage.Literals.PROJECT_MEMBER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User getUser() {
		if (user != null && user.eIsProxy()) {
			InternalEObject oldUser = (InternalEObject)user;
			user = (User)eResolveProxy(oldUser);
			if (user != oldUser) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EclipseprojectPackage.PROJECT_MEMBER__USER, oldUser, user));
			}
		}
		return user;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User basicGetUser() {
		return user;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUser(User newUser) {
		User oldUser = user;
		user = newUser;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseprojectPackage.PROJECT_MEMBER__USER, oldUser, user));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumMemberRole getRole() {
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRole(EnumMemberRole newRole) {
		EnumMemberRole oldRole = role;
		role = newRole == null ? ROLE_EDEFAULT : newRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseprojectPackage.PROJECT_MEMBER__ROLE, oldRole, role));
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseprojectPackage.PROJECT_MEMBER__URL, oldUrl, url));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EclipseprojectPackage.PROJECT_MEMBER__USER:
				if (resolve) return getUser();
				return basicGetUser();
			case EclipseprojectPackage.PROJECT_MEMBER__ROLE:
				return getRole();
			case EclipseprojectPackage.PROJECT_MEMBER__URL:
				return getUrl();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EclipseprojectPackage.PROJECT_MEMBER__USER:
				setUser((User)newValue);
				return;
			case EclipseprojectPackage.PROJECT_MEMBER__ROLE:
				setRole((EnumMemberRole)newValue);
				return;
			case EclipseprojectPackage.PROJECT_MEMBER__URL:
				setUrl((String)newValue);
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
			case EclipseprojectPackage.PROJECT_MEMBER__USER:
				setUser((User)null);
				return;
			case EclipseprojectPackage.PROJECT_MEMBER__ROLE:
				setRole(ROLE_EDEFAULT);
				return;
			case EclipseprojectPackage.PROJECT_MEMBER__URL:
				setUrl(URL_EDEFAULT);
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
			case EclipseprojectPackage.PROJECT_MEMBER__USER:
				return user != null;
			case EclipseprojectPackage.PROJECT_MEMBER__ROLE:
				return role != ROLE_EDEFAULT;
			case EclipseprojectPackage.PROJECT_MEMBER__URL:
				return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
		}
		return super.eIsSet(featureID);
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
		result.append(" (role: ");
		result.append(role);
		result.append(", url: ");
		result.append(url);
		result.append(')');
		return result.toString();
	}

} //ProjectMemberImpl
