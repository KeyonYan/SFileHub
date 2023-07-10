import React from 'react'
import NavBar from '@/components/navBar/NavBar'
import { Layout} from 'antd';
import { Outlet } from 'react-router-dom';
const { Header, Content, Footer } = Layout;

const HomePage: React.FC = () => {
  return (
    <Layout className="layout">
      <Header className='bg-white' style={{ display: 'flex', alignItems: 'center' }}>
        <NavBar/>
      </Header>
      <Content className="p-10 bg-white space-y-10 flex justify-center items-center">
        <Outlet/>
      </Content>
      <Footer className='bottom-0 left-0 right-0' style={{ textAlign: 'center' }}>SFileHub ©2023 Created by Keyon</Footer>
    </Layout>
  )
}
export default HomePage;
