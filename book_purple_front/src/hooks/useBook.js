import { useMutation, useQueryClient } from "@tanstack/react-query";
import { bookApi } from "../service/bookList";

export const useBook = () => {
    const queryClient = useQueryClient();

    const createBookMutation = useMutation({
        mutationFn: (formData) => bookApi.create(formData),
        onSuccess: () => {
            console.log('등록완료');
            queryClient.invalidateQueries({queryKey: ["book",0]});
        }
    });

    const updateBookMutation = useMutation({
        mutationFn: (formData) => bookApi.update(formData),
        onSuccess: () => {
            console.log('수정완료');
            queryClient.invalidateQueries({queryKey: ["book",0]});
        }
    });

    const deleteBookMutation = useMutation({
        mutationFn: (bookId) => bookApi.delete(bookId),
        onSuccess: () => {
            console.log('삭제완료');
            queryClient.invalidateQueries({queryKey: ["book",0]});
        }
    })

    return {
        createBookMutation,
        updateBookMutation,
        deleteBookMutation
    }
}