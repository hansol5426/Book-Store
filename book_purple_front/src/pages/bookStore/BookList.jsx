import React, { useEffect, useState } from 'react';
import '../../assets/css/bookStore/bookList.css';
import { Link } from 'react-router';
import { bookApi } from '../../service/bookList';
import { useQuery } from '@tanstack/react-query';
import Pagination from '../../components/Pagination';

function BookList(props) {

    const [page, setPage] = useState(0);
    const [bookList, setBookList] = useState([]);
    const [totalRows, setTotalRows] = useState(0);

    const { data } = useQuery({
        queryKey: ['userBook', page],
        queryFn: () => bookApi.userList(page)
    })

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

    return (
        <>
            <div className='book-list'>
                <h2>현재 도서 목록</h2>
                <section>
                    <table className='table mx-auto'>
                        <colgroup>
                            <col style={{ width: '30%' }} />
                            <col style={{ width: '15%' }} />
                            <col style={{ width: '20%' }} />
                            <col style={{ width: '15%' }} />
                            <col style={{ width: '20%' }} />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>도서명</th>
                                <th>저자명</th>
                                <th>출판사</th>
                                <th>가격</th>
                                <th>출간일</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                bookList?.map(obj => (
                                    <tr key={obj.bookId}>
                                        <td>
                                            <Link to={`/book/${obj.bookId}`}
                                                className='text-decoration-none text-black'
                                            >{obj.title}</Link>
                                        </td>
                                        <td>{obj.author}</td>
                                        <td>{obj.publisher}</td>
                                        <td>{obj.price.toLocaleString()} Point</td>
                                        <td>{obj.publicationDate}</td>
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

export default BookList;