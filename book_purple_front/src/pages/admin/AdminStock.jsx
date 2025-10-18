import React, { useEffect, useState } from 'react';
import '../../assets/css/admin/adminStock.css';
import { useQuery } from '@tanstack/react-query';
import { stockApi } from '../../service/stockList';
import Pagination from '../../components/Pagination';
import { Link } from 'react-router';

function AdminStock(props) {

    const [page, setPage] = useState(0);
    const [stockList, setstockList] = useState([]);
    const [totalRows, setTotalRows] = useState(0);

    const { data } = useQuery({
        queryKey: ['stock', page],
        queryFn: () => stockApi.list(page)
    })

    useEffect(() => {
        if (data) {
            setPage(data.page);
            setTotalRows(data.total);
            setstockList(data.content);
        }

    }, [data]);

    const movePage = (pageNum) => {
        setPage(pageNum);
    }

    return (
        <>
            <div className='admin-stock'>
                <h2>재고 관리</h2>
                <section>
                    <table className='table mx-auto'>
                        <colgroup>
                            <col style={{ width: '30%' }} />
                            <col style={{ width: '10%' }} />
                            <col style={{ width: '10%' }} />
                            <col style={{ width: '25%' }} />
                            <col style={{ width: '25%' }} />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>도서명</th>
                                <th>재고수량</th>
                                <th>상태</th>
                                <th>입고일</th>
                                <th>수정일</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                stockList?.map(obj => (
                                    <tr key={obj.stockId}>
                                        <td>
                                            <Link to={`/admin/stock/${obj.stockId}`}
                                                className='text-decoration-none text-black'
                                            >{obj.title}</Link>
                                        </td>
                                        <td>{obj.quantity.toLocaleString()} 권</td>
                                        { }
                                        <td>{obj.status}</td>
                                        <td>{obj.createDate}</td>
                                        <td>{obj.updateDate}</td>
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

export default AdminStock;