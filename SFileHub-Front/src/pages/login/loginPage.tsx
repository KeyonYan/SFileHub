import React from 'react';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Checkbox, Form, Input, message } from 'antd';
import './loginPage.css';
import { useNavigate } from 'react-router-dom';
import { login } from '@/api';
import { useStore } from '@/main';

const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const setUserName = useStore(state => state.setUserName);
  const setUserRole = useStore(state => state.setUserRole);
  const [messageApi, contextHolder] = message.useMessage();
  const onFinish = (values: any) => {
    console.log('Received values of form: ', values);
    login({ username: values.username, password: values.password, remember: values.remember})
    .then(res => {
        console.log(res);
        if (res.code === 0) {
          setUserName(res.data.username);
          setUserRole(res.data.roles[0].label)
          navigate('/home');
        } else {
          messageApi.open({
            type: 'error',
            content: res.msg,
          })
        }
    });
  };

  return (
    <div className='login-page-color h-screen flex justify-center items-center z-1'>
      {contextHolder}
      <Form
          name="normal_login"
          className="login-form w-1/3 p-8 bg-white shadow-lg rounded"
          initialValues={{ remember: true }}
          onFinish={onFinish}
        >
          <div className='flex justify-center items-center py-8'>
            <h1 className='text-5xl font-bold'>SFile🗃️Hub</h1>
          </div>
          <Form.Item
            name="username"
            rules={[{ required: true, message: 'Please input your Username!' }]}
          >
            <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" />
          </Form.Item>
          <Form.Item
            name="password"
            rules={[{ required: true, message: 'Please input your Password!' }]}
          >
            <Input
              prefix={<LockOutlined className="site-form-item-icon" />}
              type="password"
              placeholder="Password"
            />
          </Form.Item>
          <Form.Item>
            <Form.Item name="remember" valuePropName="checked" noStyle>
              <Checkbox>Remember me</Checkbox>
            </Form.Item>
          </Form.Item>

          <Form.Item className='flex justify-center items-center'>
            <Button htmlType="submit" className="login-form-button">
              Login
            </Button>
          </Form.Item>
      </Form>
    </div>
    
  );
};
export default LoginPage;