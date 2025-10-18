import React from 'react';
import '../../assets/css/bookStore/bookMain.css'
import { useQuery } from '@tanstack/react-query';
import { bookApi } from '../../service/bookList';

function BookMain(props) {

    const { data: bestsellers } = useQuery({
        queryKey: ['bestsellers'],
        queryFn: () => bookApi.getBestsellers()
    });

    const { data: newBooks } = useQuery({
        queryKey: ['newBooks'],
        queryFn: () => bookApi.getNewBooks()
    });

    return (
        <>
            <div className='book-main'>
                <div className='book-main-content'>
                    <a href='/book/list'>목록보기</a>
                    <h3>베스트 책 보라</h3>
                    <section className='best-book'>
                        {bestsellers?.map(book => (
                            <div key={book.bookId} className='book-card'>
                                <a href={`/book/${book.bookId}`}>
                                    <img src={`http://localhost:9090${book.imageUrl}`}
                                        alt={book.title} />
                                    <h6>{book.title}</h6>
                                </a>
                            </div>
                        ))}
                    </section>
                    <h3>새로운 책 보라</h3>
                    <section className='best-book'>
                        {newBooks?.map(book => (
                            <div key={book.bookId} className='book-card'>
                                <a href={`/book/${book.bookId}`}>
                                    <img src={`http://localhost:9090${book.imageUrl}`}
                                        alt={book.title} />
                                    <h6>{book.title}</h6>
                                </a>
                            </div>
                        ))}
                    </section>
                </div>
            </div>
        </>
    );
}

export default BookMain;