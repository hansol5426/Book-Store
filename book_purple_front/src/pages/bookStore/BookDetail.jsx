import React, { useState } from 'react';
import '../../assets/css/bookStore/bookDetail.css';
import Button from 'react-bootstrap/Button';
import { useNavigate, useParams } from 'react-router';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { bookApi } from '../../service/bookList';
import { cartApi } from '../../service/cartList';
import { orderApi } from '../../service/orderList';
import { authStore } from '../../store/authStore';

function BookDetail(props) {

    const { getUserId } = authStore();

    const { bookId } = useParams();

    const { data } = useQuery({
        queryKey: ['book', bookId],
        queryFn: () => bookApi.userGet(bookId),
        enabled: !!bookId
    });

    console.log(data);

    const [quantity, setQuantity] = useState(1);

    const increment = () => setQuantity((q) => q + 1);
    const decrement = () => setQuantity((q) => (q > 1 ? q - 1 : 1));

    const totalPrice = data?.price * quantity;

    const queryClient = useQueryClient();

    const navigate = useNavigate();

    const goOrder = async () => {
        if (!getUserId()) {
            alert('로그인이 필요합니다.');
            return;
        }

        try {
            const result = await orderApi.direct({

                userId: getUserId(),
                totalPrice: totalPrice,
                orderItems: [
                    {
                        bookId: data?.bookId,
                        quantity: quantity,
                        price: data?.price,
                    }
                ]

            });

            if (result.resultCode === 200) {
                alert(`구매가 완료되었습니다.\n총 금액: ${totalPrice.toLocaleString()} Point\n구매해주셔서 감사합니다.`);
                navigate('/book/order')
                queryClient.invalidateQueries({ queryKey: ['order'] })
                queryClient.invalidateQueries({ queryKey: ['stock'] })
                queryClient.invalidateQueries({ queryKey: ['search'] })
                queryClient.invalidateQueries({ queryKey: ['book'] })
            } else {
                console.log('구매 실패');
            }
        } catch (error) {
            alert(error.response?.data?.message);
            console.log(error);
        }

    }

    const goCart = async () => {
        if (!getUserId()) {
            alert('로그인이 필요합니다.');
            return;
        }

        try {
            const result = await cartApi.add({
                userId: getUserId(),
                bookId: data?.bookId,
                quantity: quantity
            });

            if (result.resultCode === 200) {
                queryClient.invalidateQueries({ queryKey: ['cart'] })
                queryClient.invalidateQueries({ queryKey: ['stock'] })
                queryClient.invalidateQueries({ queryKey: ['search'] })
                navigate('/book/cart');
            } else {
                console.log('장바구니 등록 실패');
            }

        } catch (error) {
            console.log(error);
        }

    }

    const goList = () => {
        navigate('/book/list');
    }

    return (
        <>
            <div className='book-detail'>
                <div className='detail'>
                    <section className='book-img'>
                        <img src={`http://localhost:9090${data?.imageUrl}`} alt='상세페이지 도서' />
                        <p>{data?.title}</p>
                    </section>
                    <section className='book-info'>
                        <div className='price'>
                            <h6>단가</h6>
                            <span>{data?.price.toLocaleString()} Point</span>
                        </div>
                        <hr />
                        <div className='quantity'>
                            <h6>구매 수량</h6>
                            <div className='quantity-count'>
                                <Button variant="outline-secondary" onClick={decrement}>-</Button>
                                <input type='number'
                                    value={quantity}
                                    onChange={(e) => setQuantity(e.target.value)}
                                    autoComplete='off' />
                                <Button variant="outline-secondary" onClick={increment}>+</Button>
                            </div>
                        </div>
                        <hr />
                        <div className='total'>
                            <h6>구매 금액</h6>
                            <span>{totalPrice.toLocaleString()} Point</span>
                        </div>
                        <hr />
                        <div className='detail-btn'>
                            {data?.status === 'AVAILABLE' ? (
                                <>
                                    <button type="button" onClick={goOrder}>구매</button>
                                    <button type="button" onClick={goCart}>장바구니</button>
                                </>
                            ) : (
                                <span style={{ color: 'red', fontWeight: 'bold', fontSize: 'large' }}>품절</span>
                            )}
                        </div>
                    </section>
                </div>
                <hr className='detail-divider' />
                <section className='detail-info'>
                    <h4>상세 정보</h4>
                    <table>
                        <thead>
                            <tr>
                                <th>저자</th>
                                <td>{data?.author}</td>
                            </tr>
                            <tr>
                                <th>출판사</th>
                                <td>{data?.publisher}</td>
                            </tr>
                            <tr>
                                <th>출시일</th>
                                <td>{data?.publicationDate}</td>
                            </tr>
                            <tr>
                                <th>책 소개</th>
                                <td>{data?.description}</td>
                            </tr>
                        </thead>
                    </table>
                    <button type='button' className='go-list' onClick={goList}>목록가기</button>
                </section>
            </div>
        </>
    );
}

export default BookDetail;