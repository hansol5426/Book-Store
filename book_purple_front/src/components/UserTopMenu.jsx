import React, { useState } from 'react';
import logo from '../assets/imgs/logo.png';
import bookName from '../assets/imgs/book_purple.png';
import { CiSearch } from "react-icons/ci";
import '../assets/css/menu/topMenu.css';
import { Nav, Navbar } from 'react-bootstrap';
import { NavLink, useNavigate } from 'react-router';
import { authStore } from '../store/authStore';

function TopMenu(props) {

    const { clearAuth, userName } = authStore();

    const [searchType, setSearchType] = useState("title");
    const [searchText, setSearchText] = useState("");

    const navigate = useNavigate();

    const searchBook = () => {
        navigate(`/book/search?searchType=${searchType}&searchText=${searchText}`);
    }

    const enterKey = (e) => {
        if (e.key === 'Enter') {
            searchBook();
        }
    }

    const logout = () => {
        clearAuth();
        localStorage.removeItem("auth-info");
        navigate('/login');
    }


    return (
        <Navbar>
            <Nav className="top-menu">
                <Nav.Link as={NavLink} to="/book">
                    <img src={logo} alt='logo' className='regi-logo-img' />
                    <img src={bookName} alt='storeName' className='regi-logo-title' />
                    <span>안녕하세요 {userName}님</span>
                </Nav.Link>
                <section className='search-box'>
                    <div className='search-form'>
                        <select className='search-title'
                            value={searchType}
                            onChange={(e) => setSearchType(e.target.value)}>
                            <option value="title">책 제목</option>
                            <option value="author">저자</option>
                        </select>
                        <div className='input-wrapper'>
                            <input type='text'
                                id='sch-input'
                                placeholder='검색어를 입력하세요'
                                onChange={(e) => setSearchText(e.target.value)}
                                onKeyDown={enterKey} />
                            <button type='button' className='sch-btn' onClick={searchBook}>
                                <CiSearch />
                            </button>
                        </div>
                    </div>
                </section>
                <section className='top-menu-end'>
                    <Nav.Link as={NavLink} to={"/book/cart"}>장바구니</Nav.Link>
                    <Nav.Link as={NavLink} to={"/book/order"}>주문목록</Nav.Link>
                    <Nav.Link as='button' onClick={logout}>로그아웃</Nav.Link>
                </section>
            </Nav>
        </Navbar>
    );
}

export default TopMenu;