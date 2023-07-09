import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import './tailwind.css'
import { create } from 'zustand'
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import LoginPage from '/@/pages/login/LoginPage';
import HomePage from '/@/pages/home/HomePage';
import ErrorPage from '/@/pages/error/ErrorPage';
import FileUploadPage from '/@/pages/fileUpload/FileUpload'
import FileDownloadPage from '/@/pages/fileDownload/FileDownload'

interface UserState {
  userName: string;
  userRole: string;
  setUserName: (newVal: string) => void;
  setUserRole: (newVal: string) => void;
}

export const useStore = create<UserState>()((set) => ({
  userName: "Unknown User",
  userRole: "",
  setUserName: (newVal: string) => set({ userName: newVal }),
  setUserRole: (newVal: string) => set({ userRole: newVal }),
}));

const router = createBrowserRouter([
  { path: '/', element: <LoginPage/>},
  {
    path: '/home',
    element: <HomePage/>,
    children: [
      { path: '/home', element: <FileUploadPage/>},
      { path: '/home/download', element: <FileDownloadPage/>},
    ]
  },
  { path: '/*', element: <ErrorPage/>}
])

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router}></RouterProvider>
  </React.StrictMode>,
)

