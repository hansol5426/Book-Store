import { useMutation, useQueryClient } from "@tanstack/react-query";
import { authStore } from "../store/authStore";
import api from "../api/axiosApi";
import { useNavigate } from "react-router";

export const useLogin = (loginType) => {
  const { setLogin } = authStore();
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  return useMutation({
    mutationFn: async (credentials) => {
      try {
        const response = await api.post("/api/v1/login", credentials, {
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
        });
        return response.data;
      } catch (error) {
        throw error.response?.data || error;
      }
    },
    onSuccess: (data) => {
      queryClient.invalidateQueries({ queryKey: ["user"] });
      setLogin(data.content);

      if(loginType === "USER"){
        navigate("/book");
      }else if(loginType === "ADMIN"){

        if(data.content.userRole === "ROLE_ADMIN"){
          navigate('/admin');
        }else{
          alert("관리자 권한이 없습니다.")
        }
      }      

      console.log(data.content);
    },
    onError: (error) => {
      console.error("Login 실패", error);
    },
  });
};
