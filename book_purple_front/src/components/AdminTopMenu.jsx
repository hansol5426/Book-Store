import React from 'react';
import logo from '../assets/imgs/logo.png';
import bookName from '../assets/imgs/book_purple.png';
import '../assets/css/admin/topMenu.css';
import { Nav, Navbar } from 'react-bootstrap';
import { NavLink, useNavigate } from 'react-router';
import { authStore } from '../store/authStore';

function AdminTopMenu(props) {

    const { clearAuth, userName } = authStore();

    const navigate = useNavigate();

    const logout = () => {
        clearAuth();
        localStorage.removeItem("auth-info");
        navigate('/login');
    }

    return (
        <Navbar>
            <Nav className="ad-top-menu">
                <Nav.Link as={NavLink} to="/admin">
                    <img src={logo} alt='logo' className='regi-logo-img' />
                    <img src={bookName} alt='storeName' className='regi-logo-title' />
                </Nav.Link>
                <h2>관리자 페이지</h2>
                <section className='ad-top-menu-end'>
                    <span>안녕하세요 {userName}님</span>
                    <Nav.Link as='button' onClick={logout}>로그아웃</Nav.Link>
                </section>
            </Nav>
        </Navbar>
    );
}

export default AdminTopMenu;