import React from 'react';
import { Outlet } from 'react-router';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../assets/css/layout.css';
import AdminTopMenu from '../components/AdminTopMenu';
import AdminSide from '../components/AdminSide';

function Layout(props) {
    return (
        <div>
            <AdminTopMenu/>
            <div className='side-layout'>
                <AdminSide/>
                <section className='page-content'>
                    <Outlet/>
                </section>
            </div>
        </div>
    );
}

export default Layout;