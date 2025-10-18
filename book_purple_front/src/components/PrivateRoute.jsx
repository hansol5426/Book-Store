import React from 'react';
import { authStore } from '../store/authStore';
import { Navigate, Outlet } from 'react-router';

function PrivateRoute(props) {

    const isAuthenticated = authStore((state) => state.isAuthenticated());

    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;
    }

    return <Outlet />;
    }

export default PrivateRoute;