import React from 'react';
import '../../assets/css/admin/bookCreate.css';
import { TbBookOff } from "react-icons/tb";
import * as yup from 'yup';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { useBook } from '../../hooks/useBook';
import { useNavigate } from 'react-router';
import { useQueryClient } from '@tanstack/react-query';

const schema = yup.object().shape({
    title: yup.string().required('도서명을 입력하십시오'),
    author: yup.string().required('저자명을 입력하십시오'),
    publisher: yup.string().required('출판사를 입력하십시오'),
    publicationDate: yup.string().required('출판일을 입력하십시오'),
    price: yup.string()
        .required('단가를 입력하십시오')
        .matches(/^\d+$/, '단가는 0 이상의 숫자만 입력하세요'),
    quantity: yup.string()
        .required('재고 수량을 입력하십시오')
        .matches(/^\d+$/, '재고 수량은 0 이상의 숫자만 입력하세요'),
    description: yup.string().required('도서 설명을 입력하십시오'),
    file: yup.mixed()
        .nullable()
        .test('fileSize', '파일은 2MB 이하여야 합니다.', (value) => {
            if (!value || value.length === 0) return true;
            return value[0].size <= 2 * 1024 * 1024;
        })
})

function BookCreate(props) {

    const { register, handleSubmit, watch, formState: { errors } } =
        useForm({ resolver: yupResolver(schema) });

    const queryClient = useQueryClient();    

    const { createBookMutation } = useBook();

    const navigate = useNavigate();

    const createBook = async(data) => {
        const formData = new FormData();

        formData.append('title', data.title);
        formData.append('author', data.author);
        formData.append('publisher', data.publisher);
        formData.append('publicationDate', data.publicationDate);
        formData.append('price', data.price)
        formData.append('quantity', data.quantity)
        formData.append('description', data.description)

        if(data.file && data.file.length > 0){
            formData.append('imageUrl', data.file[0]);
        }

        try{
            const result = await createBookMutation.mutateAsync(formData);

            if(result.resultCode === 200){
                alert('도서 등록이 완료되었습니다.');
                queryClient.invalidateQueries({queryKey: ['adminBook']})
                navigate('/admin/book');
            }else{
                alert('도서 등록이 실패하였습니다.');
            }
        }catch(error){
            console.log(error);
        }
    }

    const goList = () => {
        navigate('/admin/book');
    }


    return (
        <>
            <div className='book-create'>
                <h4>도서 등록</h4>
                <section className='create-content'>
                    <form onSubmit={handleSubmit(createBook)}>
                        <table className='table'>
                            <tbody>
                                <tr>
                                    <th>도서명</th>
                                    <td>
                                        <input type='text' className='form-control' id='title' {...register('title')} autoComplete='off'/>
                                        {errors.title && <p className='error'>{errors.title.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>저자명</th>
                                    <td>
                                        <input type='text' className='form-control' id='author' {...register('author')} autoComplete='off'/>
                                        {errors.author && <p className='error'>{errors.author.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>출판사</th>
                                    <td>
                                        <input type='text' className='form-control' id='publisher' {...register('publisher')} autoComplete='off'/>
                                        {errors.publisher && <p className='error'>{errors.publisher.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>출판일</th>
                                    <td>
                                        <input type='date' className='form-control' id='publicationDate' {...register('publicationDate')} autoComplete='off'/>
                                        {errors.publicationDate && <p className='error'>{errors.publicationDate.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>단가</th>
                                    <td>
                                        <input type='text' className='form-control' id='price' {...register('price')} autoComplete='off'/>
                                        {errors.price && <p className='error'>{errors.price.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>초기 재고수량</th>
                                    <td>
                                        <input type='text' className='form-control' id='quantity' {...register('quantity')} autoComplete='off'/>
                                        {errors.quantity && <p className='error'>{errors.quantity.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>도서 설명</th>
                                    <td>
                                        <textarea type='text' className='form-control' id='description' {...register('description')} autoComplete='off'/>
                                        {errors.description && <p className='error'>{errors.description.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>도서 표지</th>
                                        <td>
                                            <input type='file' className='form-control mb-2' id='file' {...register('file')}/>
                                            {watch('file') && watch('file')[0] ? (
                                                <img 
                                                    src={URL.createObjectURL(watch('file')[0])} 
                                                    alt="미리보기" 
                                                    style={{ width: '100px', height: '150px' }} />
                                            ) : (
                                                <TbBookOff size={100} />
                                            )}
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <section className='create-book-box'>
                            <button type='submit' className='create-book-btn'>등록</button>
                            <button type='button' className='create-book-btn' onClick={goList}>취소</button>
                        </section>
                    </form>
                </section>
            </div>   
        </>
    );
}

export default BookCreate;