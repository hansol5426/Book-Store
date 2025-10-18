import React from 'react';
import '../../assets/css/bookStore/bookOrder.css';
import { useQuery } from '@tanstack/react-query';
import { orderApi } from '../../service/orderList';
import { useNavigate } from 'react-router';

function BookCart(props) {

    const { data } = useQuery({
        queryKey: ['order'],
        queryFn: () => orderApi.get(),
    });

    console.log(data);

    const navigate = useNavigate();

    const goList = () => {
        navigate('/book/list');
    }

    return (
        <>
            <div className='book-order'>
                <h2>주문목록</h2>
                <div className='book-order-content'>
                    {data?.order.map(order => (
                        <div key={order.orderId} className='order-box'>
                            <div className='orders'>
                                <h4>주문번호: {order.orderId}</h4>
                                <p>주문날짜: {order.orderDate}</p>
                            </div>
                            {order.orderItems.map(item => (
                                <div key={item.oitemId}>
                                    <div className='order-info'>
                                        <section className='order-img'>
                                            <div className="img-box">
                                                <img src={`http://localhost:9090${item.imageUrl}`} alt='내가 담은 도서' />
                                            </div>
                                            <div className='order-basic'>
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
                                    <hr className='order-divider' />
                                </div>
                            ))}
                            <div className='orders'>
                                <p>주문금액: {order.orderItems.reduce((sum, item) => sum + item.totalPrice, 0).toLocaleString()} Point</p>
                            </div>
                        </div>
                    ))}
                </div>
                <div className='order-btn-box'>
                    <button type='button' className='order-btn' onClick={goList}>목록가기</button>
                </div>
            </div >
        </>
    );
}

export default BookCart;