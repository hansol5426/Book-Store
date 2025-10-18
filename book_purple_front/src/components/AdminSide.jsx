import React from 'react';
import '../assets/css/admin/sideBar.css';
import { NavLink } from 'react-router';

function AdminSide(props) {
    return (
        <>
            <div className="sidebar">
                <ul>
                    <li>
                        <NavLink to="/admin" end className={({ isActive }) => isActive ? 'active' : ''}>
                            회원 관리
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/admin/book" end className={({ isActive }) => isActive ? 'active' : ''}>
                            도서 관리
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/admin/stock" end className={({ isActive }) => isActive ? 'active' : ''}>
                            재고 관리
                        </NavLink>
                    </li>
                </ul>
                <div className='side-divider'></div>
            </div>
        </>
    );
}

export default AdminSide;