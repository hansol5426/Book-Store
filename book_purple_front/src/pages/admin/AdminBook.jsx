import React, { useEffect, useState } from 'react';
import '../../assets/css/admin/adminBook.css';
import { Link, useNavigate } from 'react-router';
import Pagination from '../../components/Pagination';
import { useQuery } from '@tanstack/react-query';
import { bookApi } from '../../service/bookList';

function AdminBook(props) {

    const [page, setPage] = useState(0);
    const [bookList, setBookList] = useState([]);
    const [totalRows, setTotalRows] = useState(0);

    const { data } = useQuery({
        queryKey: ['adminBook', page],
        queryFn: () => bookApi.adminList(page)
    })

    const navigator = useNavigate();
    
    useEffect(() => {
        if (data) {
            setPage(data.page);
            setTotalRows(data.total);
            setBookList(data.content);
        }
    
    }, [data]);
    
    const movePage = (pageNum) => {
        setPage(pageNum);
    }

    const goCreate = () => {
        navigator('/admin/book/create');
    }

    return (
         <>
           <div className='admin-book'>
                <h2>도서 관리</h2>  
                <section className='book-btn-box'>
                    <button type='button' className='admin-book-btn' onClick={goCreate}>등록</button>
                </section>
                <section>
                    <table className='table mx-auto'>
                        <colgroup>
                            <col style={{ width: '21%' }} />
                            <col style={{ width: '10%' }} />
                            <col style={{ width: '10%' }} />
                            <col style={{ width: '10%' }} />
                            <col style={{ width: '9%' }} />
                            <col style={{ width: '15%' }} />
                            <col style={{ width: '15%' }} />
                            <col style={{ width: '10%' }} />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>도서명</th>
                                <th>저자명</th>
                                <th>출판사</th>
                                <th>출판일</th>
                                <th>단가</th>
                                <th>등록일</th>
                                <th>수정일</th>
                                <th>삭제여부</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                bookList?.map(obj =>(
                                    <tr key={obj.bookId}>
                                        <td>
                                            <Link to={`/admin/book/${obj.bookId}`}
                                                  className='text-decoration-none text-black'
                                            >{obj.title}</Link>      
                                        </td>
                                        <td>{obj.author}</td>
                                        <td>{obj.publisher}</td>
                                        <td>{obj.publicationDate}</td>
                                        <td>{obj.price.toLocaleString()} Point</td>
                                        <td>{obj.createDate}</td>
                                        <td>{obj.updateDate}</td>
                                        <td>{obj.delYn}</td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                </section>
                 <section>
                     <Pagination page={page} totalRows={totalRows} movePage={movePage} />
                </section>
            </div> 
        </>
    );
}

export default AdminBook;