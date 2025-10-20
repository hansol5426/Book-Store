import React from 'react';
import '../assets/css/admin/sideBar.css';
import { NavLink, useLocation } from 'react-router';

function AdminSide(props) {

    const location = useLocation();

    const isActiveRoute = (path) => {
        return location.pathname === path || location.pathname.startsWith(path + '/');
    };

    return (
        <>
            <div className="sidebar">
                <ul>
                    <li>
                        <NavLink to="/admin" className={() => location.pathname === '/admin' ? 'active' : ''}>
                            회원 관리
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/admin/book" end className={() => isActiveRoute('/admin/book') ? 'active' : ''}>
                            도서 관리
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/admin/stock" end className={() => isActiveRoute('/admin/stock') ? 'active' : ''}>
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