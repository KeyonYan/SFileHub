import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import './tailwind.css'
import { create } from 'zustand'
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import LoginPage from '@/pages/login/LoginPage';
import HomePage from '@/pages/home/HomePage';
import ErrorPage from '@/pages/error/ErrorPage';
import DatasetPage from '@/pages/dataset/DatasetPage';
import SoftwarePage from '@/pages/software/SoftwarePage';
import HardwarePage from '@/pages/hardware/HardwarePage';
import FileUploadPage from './pages/fileUpload/FileUpload'

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

