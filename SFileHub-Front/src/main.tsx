import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import './tailwind.css'
import { create } from 'zustand'
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import LoginPage from '@/pages/login/loginPage';
import HomePage from '@/pages/home/homePage';
import ErrorPage from '@/pages/error/errorPage';
import DatasetPage from '@/pages/dataset/dataset';
import SoftwarePage from '@/pages/software/software';
import HardwarePage from '@/pages/hardware/hardware';
import FileUploadPage from './pages/fileupload/fileUpload'

export const useStore = create(set => ({
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
      { path: '/home', element: <DatasetPage/>},
      { path: '/home/software', element: <SoftwarePage/>},
      { path: '/home/hardware', element: <HardwarePage/>},
      { path: '/home/upload', element: <FileUploadPage/>},
    ]
  },
  { path: '/*', element: <ErrorPage/>}
])

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router}></RouterProvider>
  </React.StrictMode>,
)

