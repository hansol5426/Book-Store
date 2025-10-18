import React from 'react';
import '../../assets/css/login/login.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import logo from '../../assets/imgs/logo.png';
import bookName from '../../assets/imgs/book_purple.png';
import * as yup from 'yup';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { useLogin } from '../../hooks/useLogin';

const schema = yup.object().shape({
    username: yup.string().required('아이디를 입력하십시오.'),
    password: yup.string().required('패스워드를 입력하십시오.')
});

function Login(props) {

    const { register, handleSubmit, formState: { errors } } =
        useForm({ resolver: yupResolver(schema) });

    const userLogin = useLogin("USER");
    const adminLogin = useLogin("ADMIN");

    const goLogin = async (data, loginType) => {
        try {
            if (loginType === "USER") {
                await userLogin.mutateAsync(data);
            } else if (loginType === "ADMIN") {
                await adminLogin.mutateAsync(data);
            }
        } catch (error) {
            if (error.status === 401) {
                alert('아이디 또는 패스워드가 일치하지 않습니다.');
            } else if (error.status === 400) {
                alert('입력한 정보가 잘못되었습니다.');
            } else {
                alert('처리 중 오류가 발생하였습니다. 다시 시도해주세요.');
            }
        }
    }

    const userBtn = () => {
        handleSubmit((data) => goLogin(data, "USER"))();
    };

    const adminBtn = () => {
        handleSubmit((data) => goLogin(data, "ADMIN"))();
    };


    return (
        <>
            <div className='container'>
                <div className='content'>
                    <div className='logo-box'>
                        <img src={logo} alt='logo' className='logo-img' />
                        <img src={bookName} alt='storeName' className='logo-title' />
                    </div>
                    <form onSubmit={(e) => e.preventDefault()} className='login-form'>
                        <div className='mb-3'>
                            <input type='text' placeholder='ID' className={`form-control ${errors.username ? 'is-invalid' : ''}`}
                                id='username' {...register('username')} />
                            {errors.username && (<div className='invalid-feedback'>{errors.username.message}</div>)}
                        </div>
                        <div className='mb-3'>
                            <input type='password' placeholder='PASSWORD' className={`form-control ${errors.password ? 'is-invalid' : ''}`}
                                id='password' {...register('password')} />
                            {errors.password && (<div className='invalid-feedback'>{errors.password.message}</div>)}
                        </div>
                        <div className='btn-box'>
                            <button type='button' className='user-login' onClick={userBtn}>로그인</button>
                            <button type='button' className='admin-login' onClick={adminBtn}>관리자 로그인</button>
                        </div>
                        <hr />
                        <a href='/register' className='register_btn'>회원가입</a>
                    </form>
                </div>
            </div>
        </>
    );
}

export default Login;