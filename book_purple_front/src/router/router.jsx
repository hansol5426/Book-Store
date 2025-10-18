import { createBrowserRouter, Navigate } from "react-router";
import Login from "../pages/login/Login";
import Register from "../pages/login/Register";
import UserLayout from "../pages/UserLayout";
import AdminLayout from "../pages/AdminLayout";
import BookList from "../pages/bookStore/BookList";
import BookMain from "../pages/bookStore/BookMain";
import BookDetail from "../pages/bookStore/BookDetail";
import BookCart from "../pages/bookStore/BookCart";
import BookOrder from "../pages/bookStore/BookOrder";
import UserList from "../pages/admin/UserList";
import AdminBook from "../pages/admin/AdminBook";
import AdminStock from "../pages/admin/AdminStock";
import StockDetail from "../pages/admin/StockDetail";
import BookUpdate from "../pages/admin/BookUpdate";
import BookCreate from "../pages/admin/BookCreate";
import AdminDetail from "../pages/admin/AdminDetail";
import PrivateRoute from "../components/PrivateRoute";
import BookSearch from "../pages/bookStore/BookSearch";

/* 경로설정 */
export const router = createBrowserRouter([
  {
    path: "/",
    element: <Navigate to="/login" />
  },
  {
    path: "/book",
    element: <PrivateRoute />,
    children: [
      {
        path: "", element: <UserLayout />,
        children: [
          { index: true, element: <BookMain /> },
          { path: "list", element: <BookList /> },
          { path: ":bookId", element: <BookDetail /> },
          { path: "cart", element: <BookCart /> },
          { path: "search", element: <BookSearch /> },
          { path: "order", element: <BookOrder /> },
        ]
      },
    ],
  },
  {
    path: "/admin",
    element: <PrivateRoute />,
    children: [
      {
        path: "", element: <AdminLayout />,
        children: [
          { index: true, element: <UserList /> },
          {
            path: "book",
            children: [
              { index: true, element: <AdminBook /> },
              { path: "create", element: <BookCreate /> },
              { path: ":bookId", element: <AdminDetail /> },
              { path: ":bookId/update", element: <BookUpdate /> },
            ],
          },
          {
            path: "stock",
            children: [
              { index: true, element: <AdminStock /> },
              { path: ":stockId", element: <StockDetail /> },
            ],
          },
        ]
      },
    ],
  },
  { path: "/login", element: <Login /> },
  { path: "/register", element: <Register /> },
]);
