import React, { useEffect, useState } from 'react';
import '../../assets/css/bookStore/bookCart.css';
import { IoWarningOutline } from "react-icons/io5";
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { cartApi } from '../../service/cartList';
import { orderApi } from '../../service/orderList';
import { useNavigate } from 'react-router';

function BookCart(props) {

    const { data } = useQuery({
        queryKey: ['cart'],
        queryFn: () => cartApi.get(),
    });

    const queryClient = useQueryClient();

    const navigate = useNavigate();

    const [checkCItem, setCheckCItem] = useState([]);

    useEffect(() => {
        if (data?.cart?.cartItems) {
            setCheckCItem(data.cart.cartItems.map(item => item.citemId));
        }
    }, [data]);

    console.log(data);

    const handleCheck = (citemId) => {
        setCheckCItem(prev =>
            prev.includes(citemId)
                ? prev.filter(id => id !== citemId)
                : [...prev, citemId]
        );
    }

    const cartItems = data?.cart?.cartItems || [];
    const totalPrice = cartItems
        .filter(item => checkCItem.includes(item.citemId))
        .reduce((sum, item) => sum + item.price * item.quantity, 0) || 0;

    const getOrder = async () => {
        if (checkCItem.length === 0) {
            alert("구매할 항목을 선택해주세요.");
            return;
        }

        try {
            const result = await orderApi.cart({
                userId: data?.cart.userId,
                totalPrice: totalPrice,
                orderItems: data?.cart.cartItems
                    .filter(item => checkCItem.includes(item.citemId))
                    .map(item => ({
                        bookId: item.bookId,
                        quantity: item.quantity,
                        price: item.price,
                        citemId: item.citemId
                    }))
            });

            if (result.resultCode === 200) {
                alert(`구매가 완료되었습니다.\n총 금액: ${totalPrice.toLocaleString()} Point\n구매해주셔서 감사합니다.`);
                setCheckCItem([]);
                queryClient.invalidateQueries({ queryKey: ['cart'] })
                queryClient.invalidateQueries({ queryKey: ['stock'] })
                queryClient.invalidateQueries({ queryKey: ['book'] })
                navigate('/book/order')
                queryClient.invalidateQueries({ queryKey: ['order'] })
            } else {
                console.log('장바구니 구매 실패');
            }

        } catch (error) {
            alert(error.response?.data?.message || '오류가 발생했습니다.');
            console.log(error);
        }
    }

    const goList = () => {
        navigate('/book/list');
    }

    const cartDel = async () => {
        if (checkCItem.length === 0) {
            alert("삭제할 항목을 선택해주세요.");
            return;
        }

        if (confirm('정말 삭제하시겠습니까?')) {
            try {
                const result = await cartApi.delete(checkCItem);
                if (result.resultCode === 200) {
                    alert('선택한 도서가 삭제되었습니다.');
                    setCheckCItem([]);
                    queryClient.invalidateQueries({ queryKey: ['cart'] })
                } else {
                    alert('선택한 도서 삭제가 실패하였습니다.')
                }

            } catch (error) {
                console.log(error);
            }
        }
    }

    return (
        <>
            <div className='book-cart'>
                <h2>장바구니</h2>
                <div className='book-cart-content'>
                    {data?.cart.cartItems.map(item => (
                        <div key={item.citemId}>
                            <div className='cart-info'>
                                <section className='cart-img'>
                                    <input type="checkbox"
                                        checked={checkCItem.includes(item.citemId)}
                                        onChange={() => handleCheck(item.citemId)}
                                    />
                                    <div className="img-box">
                                        <img src={`http://localhost:9090${item.imageUrl}`} alt='내가 담은 도서' />
                                    </div>
                                    <div className='cart-basic'>
                                        <div className='title'>
                                            <h5>책 이름</h5>
                                            <p>{item.title}</p>
                                        </div>
                                        <div className='title'>
                                            <h5>단가</h5>
                                            <p>{item.price.toLocaleString()} Point</p>
                                        </div>
                                    </div>
                                </section>
                                <div className='col-divider'></div>
                                <section className='buy-info'>
                                    <div className='buy'>
                                        <h5>구매 수량</h5>
                                        <p>{item.quantity} 권</p>
                                    </div>
                                    <div className='buy'>
                                        <h5>구매 금액</h5>
                                        <p>{(item.price * item.quantity).toLocaleString()} Point</p>
                                    </div>
                                </section>
                            </div>
                            <hr className='cart-divider' />
                        </div>
                    ))}
                </div>
                <div className='total-cart'>
                    <h4>총 금액: {totalPrice.toLocaleString()} Point</h4>
                    {data?.cart?.point < totalPrice && (
                        <>
                            <p style={{ color: 'red' }}>포인트가 부족합니다 <IoWarningOutline /></p>
                            <p style={{ color: 'red' }}>현재 보유 Point: {data.cart.point} Point </p>
                        </>
                    )}
                </div>
                <div className='cart-btn-box'>
                    <button type='button' className='cart-btn' onClick={getOrder}>주문하기</button>
                    <button type='button' className='cart-btn' onClick={goList}>목록가기</button>
                    <button type='button' className='cart-btn' onClick={cartDel}>삭제하기</button>
                </div>
            </div>
        </>
    );
}

export default BookCart;