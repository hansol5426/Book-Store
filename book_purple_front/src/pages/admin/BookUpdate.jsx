import React, { useEffect, useRef, useState } from 'react';
import '../../assets/css/admin/bookUpdate.css';
import { TbBookOff } from "react-icons/tb";   // 표지 없으면 기본 아이콘
import * as yup from 'yup';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { useBook } from '../../hooks/useBook';
import { useNavigate, useParams } from 'react-router';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { bookApi } from '../../service/bookList';

const schema = yup.object().shape({
    title: yup.string().required('도서명을 입력하십시오'),
    author: yup.string().required('저자명을 입력하십시오'),
    publisher: yup.string().required('출판사를 입력하십시오'),
    publicationDate: yup.string().required('출판일을 입력하십시오'),
    price: yup.string()
        .required('단가를 입력하십시오')
        .matches(/^\d+$/, '단가는 0 이상의 숫자만 입력하세요'),
    description: yup.string().required('도서 설명을 입력하십시오'),
    file: yup.mixed()
        .nullable()
        .test('fileSize', '파일은 2MB 이하여야 합니다.', (value) => {
            if (!value || value.length === 0) return true;
            return value[0].size <= 2 * 1024 * 1024;
        })
})

function BookUpdate(props) {

    const { register, handleSubmit, watch, formState: { errors }, reset, setValue } =
        useForm({ resolver: yupResolver(schema) });

    const { bookId } = useParams();

    const isFile = useRef();

    const queryClient = useQueryClient();

    const { updateBookMutation } = useBook();

    const navigate = useNavigate();

    const { data } = useQuery({
        queryKey: ['book', bookId],
        queryFn: () => bookApi.get(bookId),
        enabled: !!bookId
    });

    const [preview, setPreview] = useState(null);

    useEffect(() => {
        if (data) {
            reset({
                bookId: data.bookId,
                title: data.title,
                author: data.author,
                publisher: data.publisher,
                publicationDate: data.publicationDate,
                price: data.price,
                description: data.description
            })
            isFile.current = !!data.imageUrl;

            if (isFile.current) {
                console.log('기본:', data.imageUrl);
                setPreview(`http://localhost:9090${data?.imageUrl}`);
            }
        }

    }, [data, reset]);

    const updateBook = async (data) => {
        const formData = new FormData();
        formData.append('bookId', data.bookId);
        formData.append('title', data.title);
        formData.append('author', data.author);
        formData.append('publisher', data.publisher);
        formData.append('publicationDate', data.publicationDate);
        formData.append('price', data.price)
        formData.append('description', data.description)

        if (data.file && data.file.length > 0) {

            if (isFile.current) {
                if (!confirm('이미지를 등록하시면 기존 이미지가 삭제됩니다.\n정말 진행하시겠습니까?')) {
                    setValue('imageUrl', '');
                    return false;
                }
            }
            formData.append('imageUrl', data.file[0]);
        }

        try {
            const result = await updateBookMutation.mutateAsync(formData);

            if (result.resultCode === 200) {
                alert('도서 수정이 완료되었습니다.');
                queryClient.invalidateQueries({ queryKey: ['book', bookId] })
                queryClient.invalidateQueries({ queryKey: ['adminBook'] })
                navigate('/admin/book');
            } else {
                alert('도서 수정이 실패하였습니다.');
            }
        } catch (error) {
            console.log(error);
        }
    }

    const goDetail = () => {
        navigate(`/admin/book/${bookId}`);
    }


    return (
        <>
            <div className='book-update'>
                <h4>도서 수정</h4>
                <section className='update-content'>
                    <form onSubmit={handleSubmit(updateBook)}>
                        <table className='table'>
                            <tbody>
                                <tr>
                                    <td>
                                        <input type='hidden' {...register('bookId')} />
                                    </td>
                                </tr>
                                <tr>
                                    <th>도서명</th>
                                    <td>
                                        <input type='text' className='form-control' id='title' {...register('title')} autoComplete='off' />
                                        {errors.title && <p className='error'>{errors.title.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>저자명</th>
                                    <td>
                                        <input type='text' className='form-control' id='author' {...register('author')} autoComplete='off' />
                                        {errors.author && <p className='error'>{errors.author.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>출판사</th>
                                    <td>
                                        <input type='text' className='form-control' id='publisher' {...register('publisher')} autoComplete='off' />
                                        {errors.publisher && <p className='error'>{errors.publisher.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>출판일</th>
                                    <td>
                                        <input type='date' className='form-control' id='publicationDate' {...register('publicationDate')} autoComplete='off' />
                                        {errors.publicationDate && <p className='error'>{errors.publicationDate.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>단가</th>
                                    <td>
                                        <input type='text' className='form-control' id='price' {...register('price')} autoComplete='off' />
                                        {errors.price && <p className='error'>{errors.price.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>도서 설명</th>
                                    <td>
                                        <textarea type='text' className='form-control' id='description' {...register('description')} autoComplete='off' />
                                        {errors.description && <p className='error'>{errors.description.message}</p>}
                                    </td>
                                </tr>
                                <tr>
                                    <th>도서 표지</th>
                                    <td>
                                        <input type='file' className='form-control mb-2' id='file' {...register('file')} />
                                        {watch('file') && watch('file')[0] ? (
                                            <img
                                                src={URL.createObjectURL(watch('file')[0])}
                                                alt="미리보기"
                                                style={{ width: '100px', height: '150px' }} />
                                        ) : preview ? (
                                            <img
                                                src={preview}
                                                alt="기본이미지 미리보기"
                                                style={{ width: '100px', height: '150px' }} />
                                        ) : (
                                            <TbBookOff size={100} />
                                        )}
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <section className='update-book-box'>
                            <button type='submit' className='update-book-btn'>저장</button>
                            <button type='button' className='update-book-btn' onClick={goDetail}>취소</button>
                        </section>
                    </form>
                </section>
            </div>
        </>
    );
}

export default BookUpdate;