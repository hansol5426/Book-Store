import React from 'react';
import '../assets/css/menu/footer.css';
import logo from '../assets/imgs/logo.png';
import bookName from '../assets/imgs/book_purple.png';
import { userApi } from '../service/userList';
import { useNavigate } from 'react-router';
import { authStore } from '../store/authStore';

function Footer(props) {

    const { getUserId, clearAuth } = authStore();

    const navigate = useNavigate();

    const withdraw = async () => {
        if (confirm('정말 탈퇴 하시겠습니까?')) {
            try {
                const result = await userApi.withdraw(getUserId());
                if (result.resultCode === 200) {
                    alert('회원 탈퇴가 완료되었습니다.');
                    localStorage.removeItem("auth-info");
                    clearAuth();
                    navigate('/login');
                } else {
                    alert('회원 탈퇴가 실패하였습니다.')
                }
            } catch (error) {
                console.log(error);
            }

        }
    }

    return (
        <footer>
            <div className="footer-content">
                <h4>회사정보</h4>
                <ul>
                    <li>대표이사: 이한솔</li>
                    <li>사업자등록번호: 123-45-67890</li>
                    <li>대표전화: 0000-0000</li>
                </ul>
            </div>
            <div className='footer-logo'>
                <div className='footer-img'>
                    <img src={logo} alt='logo' className='regi-logo-img' />
                    <img src={bookName} alt='storeName' className='regi-logo-title' />
                </div>
                <button type='button' onClick={withdraw}>회원탈퇴</button>
            </div>
        </footer>
    );
}

export default Footer;