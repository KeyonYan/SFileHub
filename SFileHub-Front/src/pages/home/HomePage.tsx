import React from 'react'
import NavBar from '@/components/navBar/NavBar'
import { Layout} from 'antd';
import { Outlet } from 'react-router-dom';
const { Header, Content } = Layout;

const HomePage: React.FC = () => {
  return (
    <Layout className="layout h-full">
      <Header className='bg-white' style={{ display: 'flex', alignItems: 'center' }}>
        <NavBar/>
      </Header>
      <Content className="p-10 bg-gray-50 space-y-10 flex justify-center items-start">
        <Outlet/>
      </Content>
      {/* <Footer className='bottom-0 left-0 right-0' style={{ textAlign: 'center' }}>SFileHub ©2023 Created by Keyon</Footer> */}
    </Layout>
  )
}
export default HomePage;
