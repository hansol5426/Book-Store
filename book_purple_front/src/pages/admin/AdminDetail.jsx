import React, { useEffect, useRef } from 'react';
import '../../assets/css/admin/adminDetail.css';
import { useBook } from '../../hooks/useBook';
import { useNavigate, useParams } from 'react-router';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { bookApi } from '../../service/bookList';

function AdminDetail(props) {

    const { deleteBookMutation } = useBook();

    const { bookId } = useParams();

    const { data } = useQuery({
        queryKey: ['book', bookId],
        queryFn: () => bookApi.get(bookId),
        enabled: !!bookId
    });

    const queryClient = useQueryClient();

    const navigate = useNavigate();

    const goUpdate = () => {
        navigate(`/admin/book/${bookId}/update`);
    }

    const goDelete = async () => {
        if (confirm('정말 삭제하시겠습니까?\n삭제된 도서는 재고수량 및 상태 변경이 불가합니다.')) {
            try {
                const result = await deleteBookMutation.mutateAsync(bookId);
                if (result.resultCode === 200) {
                    alert('도서가 삭제되었습니다.');
                    queryClient.invalidateQueries({ queryKey: ['book', bookId] })
                    queryClient.invalidateQueries({ queryKey: ['adminBook'] })
                    queryClient.invalidateQueries({ queryKey: ['stock'] })
                    navigate('/admin/book');
                } else {
                    alert('도서 삭제가 실패하였습니다.')
                }
            } catch (error) {
                console.log(error);
            }
        }
    }

    const goList = () => {
        navigate('/admin/book');
    }


    return (
        <>
            <div className='admin-detail'>
                <h4>도서 상세보기</h4>
                <section className='detail-content'>
                    <table className='table'>
                        <tbody>
                            <tr>
                                <th>도서명</th>
                                <td>{data?.title}</td>
                            </tr>
                            <tr>
                                <th>저자명</th>
                                <td>{data?.author}</td>
                            </tr>
                            <tr>
                                <th>출판사</th>
                                <td>{data?.publisher}</td>
                            </tr>
                            <tr>
                                <th>출판일</th>
                                <td>{data?.publicationDate}</td>
                            </tr>
                            <tr>
                                <th>단가</th>
                                <td>{data?.price.toLocaleString()} Point</td>
                            </tr>
                            <tr>
                                <th>도서 설명</th>
                                <td>{data?.description}</td>
                            </tr>
                            <tr>
                                <th>도서 표지</th>
                                <td>
                                    <img src={`http://localhost:9090${data?.imageUrl}`}
                                        alt='도서 표지'
                                        style={{ width: '100px', height: '150px' }} />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <section className='detail-book-box'>
                        {data?.delYn !== 'Y' && (
                            <>
                                <button type='button' className='detail-book-btn' onClick={goUpdate}>수정</button>
                                <button type='button' className='detail-book-btn' onClick={goDelete}>삭제</button>
                            </>
                        )}
                        <button type='button' className='detail-book-btn' onClick={goList}>목록</button>
                    </section>
                </section>
            </div>
        </>
    );
}

export default AdminDetail;