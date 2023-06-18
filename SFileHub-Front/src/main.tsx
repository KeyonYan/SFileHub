import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'
import './tailwind.css'
import { create } from 'zustand';

export const useStore = create(set => ({
  userName: "",
  setUserName: (newVal: string) => set(() => ({ userName: newVal })),
}));

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
