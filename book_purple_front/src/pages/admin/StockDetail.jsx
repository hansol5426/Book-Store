import React, { useEffect } from 'react';
import '../../assets/css/admin/stockDetail.css';
import { useNavigate, useParams } from 'react-router';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { stockApi } from '../../service/stockList';
import { useStock } from '../../hooks/useStock';

const schema = yup.object().shape({
    quantity: yup.string()
        .matches(/^\d+$/, '재고수량은 0 이상의 숫자만 입력하세요'),
})

function StockDetail(props) {

    const { register, handleSubmit, formState: { errors }, reset } =
        useForm({ resolver: yupResolver(schema) });

    const { stockId } = useParams();

    const { data } = useQuery({
        queryKey: ['stock', stockId],
        queryFn: () => stockApi.get(stockId),
        enabled: !!stockId
    });

    const queryClient = useQueryClient();

    const { updateStockMutation } = useStock();

    const navigate = useNavigate();


    useEffect(() => {
        if (data) {
            reset({
                stockId: data.stockId,
                title: data.title,
                quantity: data.quantity,
                status: data.status
            })
        }

    }, [data, reset]);

    console.log(data);

    const updateStock = async (data) => {
        const json = {
            stockId: Number(data.stockId),
            quantity: Number(data.quantity),
            status: data.status,
        };

        try {
            const result = await updateStockMutation.mutateAsync(json);

            if (result.resultCode === 200) {
                alert('재고 수정이 완료되었습니다.');
                queryClient.invalidateQueries({ queryKey: ['adminBook'] })
                queryClient.invalidateQueries({ queryKey: ['stock'] })
                navigate('/admin/stock');
            } else {
                alert('재고 수정이 실패하였습니다.');
            }
        } catch (error) {
            console.log(error);
        }
    }

    const goList = () => {
        navigate('/admin/stock');
    }

    return (
        <>
            <div className='stock-detail'>
                <h4>재고 수정</h4>
                <section className='stock-content'>
                    <form onSubmit={handleSubmit(updateStock)}>
                        <table className='table'>
                            <tbody>
                                <tr>
                                    <td>
                                        <input type='hidden' {...register('stockId')} />
                                    </td>
                                </tr>
                                <tr>
                                    <th>도서명</th>
                                    <td>{data?.title}</td>
                                </tr>
                                <tr>
                                    <th>재고수량</th>
                                    <td>
                                        <input type='text'
                                            className='form-control w-50'
                                            {...register('quantity')}
                                            autoComplete='off'
                                            disabled={data?.delYn == 'Y'} />
                                        {errors.quantity && <p className='error'>{errors.quantity.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>상태</th>
                                    <td>
                                        <select className='form-select w-50'
                                            {...register('status')}
                                            disabled={data?.delYn == 'Y'}>
                                            <option value='AVAILABLE'>판매가능</option>
                                            <option value='SOLD_OUT'>품절</option>
                                            <option value='DISCONTINUED'>판매불가</option>
                                        </select>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <section className='stock-box'>
                            {data?.delYn !== 'Y' && (
                                <button type='submit' className='stock-btn'>수정</button>
                            )}
                            <button type='button' className='stock-btn' onClick={goList}>취소</button>
                        </section>
                    </form>
                </section>
            </div>
        </>
    );
}

export default StockDetail;