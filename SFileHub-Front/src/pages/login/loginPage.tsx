import React, { useState } from 'react';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Checkbox, Form, Input } from 'antd';
import './loginPage.css';
import { useNavigate } from 'react-router-dom';
import { login, hello } from '../../api/index';
import { useStore } from '../../main';

const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const setUserName = useStore(state => state.setUserName);
  const onFinish = (values: any) => {
    console.log('Received values of form: ', values);
    login({ username: values.username, password: values.password, remember: values.remember}).then(res => {
        console.log(res);
        if (res.code === 200) {
          setUserName(values.username);
          navigate('/home');
        } else {
          alert(res.msg);
        }
    });
  };

  return (
    <Form
      name="normal_login"
      className="login-form"
      initialValues={{ remember: true }}
      onFinish={onFinish}
    >
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

        <a className="login-form-forgot" href="">
          Forgot password
        </a>
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit" className="login-form-button">
          Log in
        </Button>
      </Form.Item>
    </Form>
  );
};
export default LoginPage;