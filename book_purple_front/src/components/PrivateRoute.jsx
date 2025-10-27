import React from 'react';
import { authStore } from '../store/authStore';
import { Navigate, Outlet } from 'react-router';

function PrivateRoute({requiredRole}) {

    const isAuthenticated = authStore((state) => state.isAuthenticated());
    const user = authStore((state) => state.getUserRole())

    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;
    }

    // 권한 체크
    if(requiredRole && user !== requiredRole){
        alert('접근 권한이 없습니다.');
        return <Navigate to="/book" replace />;
    }

    return <Outlet />;
}

export default PrivateRoute;