import { useEffect, useState } from "react";
import '../../assets/css/bookStore/bookSearch.css';
import { useLocation, Link } from "react-router";
import { bookApi } from "../../service/bookList";
import { useQuery } from "@tanstack/react-query";
import Pagination from "../../components/Pagination";

function BookSearch() {

    const [page, setPage] = useState(0);
    const [bookSearch, setBookSearch] = useState([]);
    const [totalRows, setTotalRows] = useState(0);

    const location = useLocation();

    const search = new URLSearchParams(location.search);
    const searchType = search.get("searchType") || "";
    const searchText = search.get("searchText") || "";

    const { data } = useQuery({
        queryKey: ['search', searchType, searchText, page],
        queryFn: () => bookApi.search(searchType, searchText, page),
    });
    console.log(data);

    useEffect(() => {
        if (data) {
            setPage(data.page);
            setTotalRows(data.total);
            setBookSearch(data.content);
        }

    }, [data]);

    const movePage = (pageNum) => {
        setPage(pageNum);
    }

    return (
        <>
            <div className="search-content">
                <div className="a">
                    <h2>검색 결과: "{searchText}"</h2>
                    {bookSearch && bookSearch.length > 0 ? (
                        <ul>
                            {bookSearch?.map(book => (
                                <li key={book.bookId}>
                                    <Link to={`/book/${book.bookId}`}>
                                        <div className="search-img">
                                            <img src={`http://localhost:9090${book.imageUrl}`} alt='검색 도서' />
                                        </div>
                                    </Link>
                                    <div className="search-result">
                                        <p>도서명 : {book.title}</p>
                                        <p>저자명 : {book.author}</p>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p className="search-no">검색 결과가 없습니다.</p>
                    )}
                    <section>
                        <Pagination page={page} totalRows={totalRows} movePage={movePage} />
                    </section>
                </div>
            </div>
        </>
    );
}

export default BookSearch;
