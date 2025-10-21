import React, { useState } from 'react';
import '../../assets/css/login/register.css';
import logo from '../../assets/imgs/logo.png';
import bookName from '../../assets/imgs/book_purple.png';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router';
import { userApi } from '../../service/userList';
import PostCode from '../../components/PostCode';

const schema = yup.object().shape({
    userId: yup.string().required('아이디를 입력하십시오.'),
    password: yup.string().required('패스워드를 입력하십시오.'),
    userName: yup.string().required('이름을 입력하십시오.'),
    phone: yup.string().required('전화번호를 입력하십시오.'),
    mailId: yup.string().required('이메일을 입력하십시오.'),
    agency: yup.string().required('도메인을 선택하거나 입력하십시오.'),
    address: yup.string().required('주소를 입력하십시오.')
});

function Register(props) {

    const { register, handleSubmit, formState: { errors }, setValue } =
        useForm({ resolver: yupResolver(schema) });

    const [selfDomain, setSelfDomain] = useState(false);
    const [isPostOpen, setIsPostOpen] = useState(false);

    const navigate = useNavigate();

    const handleMail = (e) => {
        const value = e.target.value;
        if (value === "none") {
            setSelfDomain(false);
            setValue("agency", "");
        } else if (value === "self") {
            setSelfDomain(true);
            setValue("agency", "");
        } else {
            setSelfDomain(false);
            setValue("agency", value);
        }
    }

    const goRegister = async (data) => {
        const email = `${data.mailId}@${data.agency}`
        const sendData = {
            userId: data.userId,
            password: data.password,
            userName: data.userName,
            phone: data.phone,
            address: data.address,
            addressDetail: data.addressDetail,
            email,
        };

        console.log(sendData);

        try {
            const result = await userApi.register(sendData);
            if (result.resultCode === 200) {
                alert('회원가입이 완료되었습니다.');
                navigate('/login');
            }
        } catch (error) {
            alert(error.response?.data?.message || '오류가 발생했습니다.');
            console.log(error);
        }
    }

    const searchAddr = () => {
        setIsPostOpen(true);
    }

    const handleComplete = (fullAddr) => {
        setValue('address', fullAddr, { shouldValidate: true });
        setIsPostOpen(false);
    }

    return (
        <>
            <div className='regi-container'>
                <div className='regi-logo'>
                    <img src={logo} alt='logo' className='regi-logo-img' />
                    <img src={bookName} alt='storeName' className='regi-logo-title' />
                </div>
                <h1>회원가입</h1>
                <form className='regi-form' onSubmit={handleSubmit(goRegister)}>
                    <div className='regi'>
                        <label htmlFor='userId'>ID</label>
                        <div className='addr-form'>
                            <input type='text' className={`form-control ${errors.userId ? 'is-invalid' : ''}`}
                                id='userId' {...register('userId')} />
                            {errors.userId && (<div className='invalid-feedback'>{errors.userId.message}</div>)}
                        </div>
                    </div>
                    <div className='regi'>
                        <label htmlFor='password'>PASSWORD</label>
                        <div className='addr-form'>
                            <input type='password' className={`form-control ${errors.password ? 'is-invalid' : ''}`}
                                id='password' {...register('password')} />
                            {errors.password && (<div className='invalid-feedback'>{errors.password.message}</div>)}
                        </div>
                    </div>
                    <div className='regi'>
                        <label htmlFor='userName'>이름</label>
                        <div className='addr-form'>
                            <input type='text' className={`form-control ${errors.userName ? 'is-invalid' : ''}`}
                                id='userName' {...register('userName')} />
                            {errors.userName && (<div className='invalid-feedback'>{errors.userName.message}</div>)}
                        </div>
                    </div>
                    <div className='regi'>
                        <label htmlFor='phone'>전화번호</label>
                        <div className='addr-form'>
                            <input type='text' className={`form-control ${errors.phone ? 'is-invalid' : ''}`}
                                id='phone' {...register('phone')} />
                            {errors.phone && (<div className='invalid-feedback'>{errors.phone.message}</div>)}
                        </div>
                    </div>
                    <div className='regi'>
                        <label htmlFor='mailId'>이메일</label>
                        <div className='addr-form'>
                            <input type='text' className={`form-control ${errors.mailId ? 'is-invalid' : ''}`}
                                id='mailId' {...register('mailId')} />
                            <span>@</span>
                            <input type='text'
                                className={`form-control ${errors.agency ? 'is-invalid' : ''}`}
                                readOnly={!selfDomain}
                                id='agency' {...register('agency')}
                                placeholder='도메인 선택' />
                            <select className='form-select w-auto'
                                onChange={handleMail}>
                                <option value="none"> 선택해주세요. </option>
                                <option value="self">직접선택</option>
                                <option value="gmail.com">gmail.com</option>
                                <option value="naver.com">naver.com</option>
                                <option value="kakao.com">kakao.com</option>
                            </select>
                            {errors.mailId && (<div className='invalid-feedback'>{errors.mailId.message}</div>)}
                            {errors.agency && (<div className='invalid-feedback'>{errors.agency.message}</div>)}
                        </div>
                    </div>
                    <div className='regi'>
                        <label htmlFor='address'>주소</label>
                        <div className='addr-form'>
                            <input type='text'
                                className={`form-control ${errors.address ? 'is-invalid' : ''}`}
                                id='address' {...register('address')}
                                readOnly />
                            <button type='button' className='addr-btn' onClick={searchAddr}>주소 찾기</button>
                            {errors.address && (<div className='invalid-feedback'>{errors.address.message}</div>)}
                            <PostCode isOpen={isPostOpen}
                                onComplete={handleComplete}
                                onClose={() => setIsPostOpen(false)}
                            />
                        </div>
                    </div>
                    <div className='regi'>
                        <label htmlFor='addressDetail'></label>
                        <input type='text' id='addressDetail'
                            className='form-control'
                            placeholder='상세주소'
                            {...register('addressDetail')} />
                    </div>
                    <div className='text-center'>
                        <button type='submit' className='save'>저장</button>
                    </div>
                </form>
            </div>
        </>
    );
}

export default Register;