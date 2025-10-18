import React from 'react';
import { Outlet } from 'react-router';
import 'bootstrap/dist/css/bootstrap.min.css';
import UserTopMenu from '../components/UserTopMenu';
import UserFooter from '../components/UserFooter';

function Layout(props) {
    return (
        <div>
            <UserTopMenu />
            <section>
                <Outlet />
            </section>
            <UserFooter />
        </div>
    );
}

export default Layout;