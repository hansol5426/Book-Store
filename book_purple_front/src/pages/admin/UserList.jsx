import React, { useEffect, useState } from 'react';
import '../../assets/css/admin/userList.css';
import { useQuery } from '@tanstack/react-query';
import { userApi } from '../../service/userList';
import Pagination from '../../components/Pagination';

function UserList(props) {

    const [page, setPage] = useState(0);
    const [userList, setUserList] = useState([]);
    const [totalRows, setTotalRows] = useState(0);

    const { data } = useQuery({
        queryKey: ['user', page],
        queryFn: () => userApi.list(page)
    })
    
    useEffect(() => {
        if (data) {
            setPage(data.page);
            setTotalRows(data.total);
            setUserList(data.content);
        }
    
    }, [data]);
    
    const movePage = (pageNum) => {
        setPage(pageNum);
    }

    return (
        <>
           <div className='user-list'>
                <h2>회원 관리</h2>  
                <section className='user-box'>
                    <table className='table mx-auto'>
                        <colgroup>
                            <col style={{ width: '15%' }} />
                            <col style={{ width: '15%' }} />
                            <col style={{ width: '20%' }} />
                            <col style={{ width: '20%' }} />
                            <col style={{ width: '20%' }} />
                            <col style={{ width: '10%' }} />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>회원명</th>
                                <th>아이디</th>
                                <th>이메일</th>
                                <th>전화번호</th>
                                <th>가입일</th>
                                <th>탈퇴여부</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                userList?.map(obj =>(
                                    <tr key={obj.userId}>
                                        <td>{obj.userName}</td>
                                        <td>{obj.userId}</td>
                                        <td>{obj.email}</td>
                                        <td>{obj.phone}</td>
                                        <td>{obj.createDate}</td>
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

export default UserList;