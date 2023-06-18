import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'
import './tailwind.css'
import { create } from 'zustand'
import { devtools, persist } from 'zustand/middleware'

export const useStore = create(set => ({
  userName: "Null",
  setUserName: (newVal: string) => set({ userName: newVal }),
}));

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
