import { useMutation, useQueryClient } from "@tanstack/react-query";
import { stockApi } from "../service/stockList";

export const useStock = () => {
    const queryClient = useQueryClient();

    const updateStockMutation = useMutation({
        mutationFn: (json) => stockApi.update(json),
        onSuccess: () => {
            console.log('수정완료');
            queryClient.invalidateQueries({queryKey: ["stock",0]});
        }
    });

    return {
        updateStockMutation
    }
}