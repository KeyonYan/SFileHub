import React from 'react'
import NavBar from '../../components/navBar/navBar'
import { Breadcrumb, Layout, Menu, theme, Skeleton } from 'antd';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import FileUpload from '../fileupload/fileUpload';
import Dataset from '../dataset/dataset';
import Software from '../software/software';
import Hardware from '../hardware/hardware';

const { Header, Content, Footer } = Layout;

const HomePage: React.FC = () => {
  return (
    <Layout className="layout">
      <Header className='bg-white' style={{ display: 'flex', alignItems: 'center' }}>
        <NavBar/>
      </Header>
      <Content className="p-10 bg-white space-y-10">
        {/* <Skeleton active />
        <Skeleton active />
        <Skeleton active />
        <Skeleton active />
        <Skeleton active />
        <Skeleton active /> */}
        <Routes>
          <Route path='/home/dataset' element={<Dataset/>}/>
          <Route path='/home/software' element={<Software/>}/>
          <Route path='/home/hardware' element={<Hardware/>}/>
          <Route path='/home/upload' element={<FileUpload/>}/>
        </Routes>
        
      </Content>
      <Footer className='bottom-0 left-0 right-0' style={{ textAlign: 'center' }}>SFileHub ©2023 Created by Keyon</Footer>
    </Layout>
  )
}
export default HomePage;